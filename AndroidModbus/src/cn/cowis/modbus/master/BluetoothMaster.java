package cn.cowis.modbus.master;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;

import cn.cowis.modbus.exception.TransferException;

import com.serotonin.util.queue.ByteQueue;

import android.bluetooth.BluetoothSocket;


/**
 * @author Administrator 通过已近配对好的 蓝牙设备 建立个主机
 * 
 */
public class BluetoothMaster implements Serializable,Master{

	private static final long serialVersionUID = 1L;
	private BluetoothSocket socket = null;
	private OutputStream outputStream = null;
	private InputStream inputStream = null;
	
	private int allWait=3000;

	public BluetoothMaster(BluetoothSocket socket) {

		this.socket = socket;

	}

	@Override
	public byte[] readValue(int slaveId, int position, int num) throws Exception {
	
		//System.out.println("开始 "+i);
		outputStream.flush();
		
		ByteQueue queue = new ByteQueue(); 
		try {
			outputStream.write(MontageReceiveMessage.montageReader(slaveId,
					position, num));//write(byte[] buffer)； 发送 读报文，如：06 03 0034 0001 CRC
			
			testReadBack(num);
			
			if (inputStream.available() > 0) {
				while(inputStream.available()>0){
					queue.push(inputStream.read());
				}
			} else {
				throw new TransferException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TransferException();
		}
		
		return MontageReceiveMessage.receiveReader(queue.popAll());//解析读到的报文（即将读到的报文数据区截下来，放在一个字节型数组里）后返回
	}
 
	@Override
	public void writeValue(int slaveId, int position, Object obj)
			throws Exception {

		outputStream.flush();
		ByteQueue queue = new ByteQueue();
		try {
			
			outputStream.write(MontageReceiveMessage.montageWriter(slaveId,
					position, obj));// 发送写报文（含有向下位机下发的命令）
			
			Thread.sleep(1000);
			if (inputStream.available() > 0) {
				
				while(inputStream.available()>0){
					queue.push(inputStream.read());
				}
				
			} else {
				throw new TransferException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TransferException();
		}

		MontageReceiveMessage.receiveWriter(queue.popAll());//写完后，处理并返回下位机回复的信息（还未做处理）

	}

	@Override
	public void destroy() {
		try {
			if(socket!=null){
			socket.close();
			outputStream.close();
			inputStream.close();
			
			
			Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void open() throws Exception {
		
			
			socket.connect();
			
			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();
			Thread.sleep(1000);
		

	}
	
	
	@Override
	public byte[] testSlaveId(int slaveId) {
		byte[] params =null;
		try {
			params = readValue(slaveId,1,1); //试着随便读一个地址，如果该地址没有下位机则该语句会报错,返回基本参数（波特率等）
			return params;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	
	@Override
	public String toString(){
		if(socket==null){
			return null;
		}
		return socket.toString();
	}


	/**
	 * @param num
	 *        验证是否返回完整报文
	 * @throws IOException 
	 */
	public void testReadBack(int num) throws IOException {
		
		int i=0;
		int total;
		while(true){
			total=++i*300;
			
			try {
				Thread.sleep(300);//循环100毫秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(total>=allWait){
				System.out.println("过了2秒了我靠====================================");
				return;
			
			}
			
			if((5+2*num)==inputStream.available()){
				return;
			}
			
			
		}
		
	}

	
}
