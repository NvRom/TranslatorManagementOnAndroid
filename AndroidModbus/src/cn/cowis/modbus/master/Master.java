package cn.cowis.modbus.master;

import java.io.IOException;
import java.util.Date;

import android.util.Log;
import cn.cowis.modbus.exception.ErrorCodeException;
import cn.cowis.modbus.tool.ConvertData;

import com.serotonin.util.queue.ByteQueue;

/**
 * @author Administrator
 *
 *master控制读和写 还有销毁和打开
 */
public interface Master {
	
	/**
	 * 读变送器
	 * @param slaveId 从机id
	 * @param position 位置
	 * @param num  寄存器的个数
	 */
	byte[] readValue(int slaveId,int position,int num) throws Exception;
	
	/**写变送器
	 * @param slaveId 从机id
	 * @param position 位置
	 * @param obj  写的值
	 */
	void writeValue(int slaveId,int position,Object obj) throws Exception;
	/**
	 * 销毁主机
	 */
	void destroy();
	/**
	 * 打开主机
	 */
	void open()throws Exception;
	
	/**
	 * 验证从机地址  如果 此从机可以用
	 * 则 返回true
	 * @return
	 */
	byte[] testSlaveId(int slaveId);
	
	
	
	/**
	 * @author Administrator
	 *用于拼接报文 和解析接收报文
	 */
	public  class MontageReceiveMessage{
		
		public static final int READFUNCTIONCODE=0X03;//读的功能码
		public static final int WRITEFUNCTIONCODE=0X10; //写的功能码
		
		private static final ByteQueue queue=new ByteQueue();
		/**
		 * 拼接 读报文
		 * @return
		 */
		public static byte[] montageReader(int slaveId,int position,int num){
			
			queue.push(slaveId);//id
			queue.push(READFUNCTIONCODE);//功能码
			queue.push(ConvertData.toByte((short)position));//起始位置
			queue.push(ConvertData.toByte((short)num));//寄存器的个数
			queue.push(CRC16(queue.peekAll()));  //crc    ,peekAll()把队列中数据全部复制出来但原来数据还在队列中，
													//     popAll()把队列中数据全部取出来，原队列清空
			Log.i("write",dataToString(queue.peekAll()) );
			return queue.popAll();
			
			
		}
		
		/**
		 * 解析读取的报文
		 * @return
		 * @throws ErrorCodeException 
		 */
		public static byte[]  receiveReader(byte [] values) throws ErrorCodeException{
			
			Log.i("get",dataToString(values));  //打印读取的报文
			
			if(values.length==5){
				
				if(values[2]==9){
					//下线
					throw new ErrorCodeException(9);
				}else{
					//上线
					throw new ErrorCodeException(10);
				}
				
			}
			byte[] datas=new byte[values.length-5];//3个是id和功能码和个数2个crc
			System.arraycopy(values, 3, datas, 0, datas.length);//Copies "datas.length" elements from the array "values",
																//starting at offset 3, into the array "datas", starting at offset 0. 

			return datas;
		}
		
		/**
		 * 拼接 写报文
		 * @param slaveId
		 * @param position
		 * @param obj
		 * @return
		 */
		public static byte[] montageWriter(int slaveId,int position,Object obj){
			
			queue.push(slaveId);//id
			queue.push(WRITEFUNCTIONCODE);//功能码
			queue.push(ConvertData.toByte((short)position));
			byte[] values= objToBytes(obj);
			//起始位置
			queue.push(0);
			queue.push(values.length/2);//寄存器的个数
			queue.push(values.length);//字节的长度
			queue.push(values);			//报文中修改命令
			
			queue.push(CRC16(queue.peekAll()));//crc
			
			Log.i("send",dataToString(queue.peekAll()));
			
			return queue.popAll();
		}
		
		/**
		 * 解析写的报文
		 * @param values
		 */
		public static void receiveWriter(byte[] values){
			
			Log.i("get",dataToString(values));
			//暂时还没搞
		}
		
		
		/**
		 * 现在的obj中的类型为 Short,Integer,Float,float[],String,date类型
		 * @param obj
		 * @return
		 */
		private static byte[] objToBytes(Object obj){
			
			Log.i("ssss", obj.getClass().toString());
			
			if(obj instanceof Short){
				return ConvertData.toByte((Short)obj);
			}else if(obj instanceof Integer){
				return ConvertData.toByte((Integer)obj);
			}else if(obj instanceof Float){
				
				return ConvertData.toByte((Float)obj);
			}else if(obj instanceof float[]){
				return ConvertData.toByte((float[])obj);
				
			}else if(obj instanceof String){
				return ((String)obj).getBytes();
			}else if(obj instanceof Date){
				return ConvertData.toByte((Date)obj);
			}else if(obj instanceof short[]){
				return ConvertData.toByte((short[])obj);
			}
			else{
				
				Log.e("master", "没有这个类型");
			}
			return null;
			
		}
		
		private static String dataToString(byte[] values) {
			StringBuffer strBuf = new StringBuffer();
			StringBuffer changeStrBuf = new StringBuffer();
			
			try{
			for (byte i = 0; i < values.length; i++)
				if (values[i] < 0) {
					strBuf.append(Integer
							.toHexString(values[i] & 0xff | 0xffffff00)
							.substring(6));
				} else {
					if (values[i] < 16)
						strBuf.append("0");
					strBuf.append(Integer.toHexString(values[i]));
				}

			for (int j = 0; j < strBuf.toString().length(); j++)
				if ((j + 1) % 2 == 0)
					changeStrBuf.append((new StringBuilder(String.valueOf(strBuf
							.toString().charAt(j)))).append(" ").toString());
				else
					changeStrBuf.append(strBuf.toString().charAt(j));

			}catch(Exception e){
				
			}
			return changeStrBuf.toString();
		}
		
		/**
		 * crc校验
		 * @param Buf
		 * @param Len
		 * @return
		 */
		private static byte[] CRC16(byte[] Buf) {
			int CRC;
			int i, Temp;
			CRC = 0xffff;
			for (i = 0; i < Buf.length; i++) {
				CRC = CRC ^ byteToInteger(Buf[i]); 
				// System.out.println(byteToInteger(Buf[i]));
				for (Temp = 0; Temp < 8; Temp++) {
					if ((CRC & 0x01) == 1)
						CRC = (CRC >> 1) ^ 0xA001;
					else
						CRC = CRC >> 1;
				}
			}
			byte [] crcs=new byte[2];
			crcs[0]=(byte) (CRC % 256);
			crcs[1]= (byte) (CRC / 256);
			return crcs;
		}
		private static int byteToInteger(byte b) {
			int value;
			value = b & 0xff;
			return value;
		}
		
		
	}
	
	

}
