
package cn.cowis.modbus.entity;

import java.nio.charset.Charset;
import java.util.Date;

import android.annotation.SuppressLint;
import cn.cowis.modbus.MasterProxy;
import cn.cowis.modbus.tool.ConvertData;

/**
 * @author Administrator
 *                  shuangge
 *
 */
public abstract class Sensor {

	protected int slaveId;
	protected int sensorAddr;
	protected MasterProxy masterProxy;
	private SensorParams sensorParams;
	
	private Short sensorType=null;

	protected Sensor(int slaveId, int sensorAddr, MasterProxy masterProxy) throws Exception {

		this.sensorAddr = sensorAddr;
		this.slaveId = slaveId;
		this.masterProxy = masterProxy;
		sensorType=readSensorType();
		this.sensorParams=new SensorParams(sensorType);
	}

	public SensorParams getSensorParams() {
		return sensorParams;
	}

	public Integer getSensorAddr() {
		return sensorAddr;
	}
	
	public short getSensorType() {
		return sensorType;
	}

	/**
	 * @return
	 *        读结果单位
	 * @throws Exception
	 */
	public abstract byte[] readResultUnit() throws Exception;
	
	/**            写结果单位
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public abstract void writeResultUnit(byte[] values) throws Exception; 
	
    /**
     * @return 
     *        读结果的保存位数
     * @throws Exception
     */
    public abstract byte[] readResultCount() throws Exception;
	
	/**
	 * @param values  写结果的保存位数
	 * @return
	 * @throws Exception
	 */
	public abstract void writeResultCount(byte[] values) throws Exception; 
	
	public void setSlaveId(int slaveId) {
		this.slaveId = slaveId;
	}

	/**
	 * 璇讳紶鎰熷櫒绫诲瀷
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public Short readSensorType() throws Exception {

		byte[] values= masterProxy.syncGetValue(slaveId,
				 AddrConst.SENSOR_TYPE
						+ sensorAddr, 1);

		short type= ConvertData.toShort(values)[0];
		
		return type;
	}

	/**
	 * 鍐欎紶鎰熷櫒绫诲瀷
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeSensorType(short p) throws Exception{
		masterProxy.syncSetValue(slaveId, 
				AddrConst.SENSOR_TYPE + sensorAddr,
				 p);
		sensorType=p;
		sensorParams=new SensorParams(p);
	}

	/**
	 * 璇讳紶鎰熷櫒淇℃伅
	 * @throws ModbusTransportException 
	 */
	public String readSensorInfo() throws Exception{
		byte values[] = masterProxy.syncGetValue(slaveId, AddrConst.SENSOR_INFO + sensorAddr, 16);
		
		
		return ConvertData.byteToString(values);
	}

	
	/**
	 * 鍐欎紶鎰熷櫒淇℃伅
	 * 
	 * @param data
	 * @throws ModbusTransportException
	 * @throws OutRangeException
	 */
	@SuppressLint("NewApi")
	public void writeSensorInfo(String data) throws Exception{
		
		System.out.println("data"+data);
		byte[] datas = new byte[32];
		
		
		byte[] real = data.getBytes(Charset.forName("UTF-8"));
		
		if (real.length > 32) {
			throw new Exception();
		}
		
		System.arraycopy(real, 0, datas, 0, real.length);

		masterProxy.syncSetValue(slaveId, AddrConst.SENSOR_INFO + sensorAddr,
				data);
		
		
		
	}

	/**
	 * 璇荤敓浜ф棩鏈�
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public Date readSensorMfdDate() throws Exception{
		byte[] values= masterProxy.syncGetValue(slaveId,
				 AddrConst.SENSOR_MFD_DATE
						+ sensorAddr,2);
		

		return ConvertData.byteToDate(values);
	}

	/**
	 * 鍐欑敓浜ф棩鏈�
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public void writeSensorMfdDate(Date date) throws Exception{
		
		masterProxy.syncSetValue(slaveId, 
				AddrConst.SENSOR_MFD_DATE + sensorAddr,
				date);
		
	}

	/**
	 * 璇诲惎鐢ㄦ棩鏈�
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public Date readSensorEnableDate() throws Exception {
		byte[] values= masterProxy.syncGetValue(slaveId,
				 AddrConst.SENSOR_ENABLE_DATE
						+ sensorAddr,2);
		
		return ConvertData.byteToDate(values);
	}

	/**
	 * 鍐欏惎鐢ㄦ棩鏈�
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public void writeSensorEnableDate(Date date)
			throws Exception {
		
		masterProxy.syncSetValue(slaveId, 
				AddrConst.SENSOR_ENABLE_DATE + sensorAddr,
				 date);
	}

	/**
	 * 璇昏繃鏈熸棩鏈�
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public Date readSensorAgeOutDate() throws Exception{
		byte[] values= masterProxy.syncGetValue(slaveId,
				 AddrConst.SENSOR_AGE_OUT_DATE
						+ sensorAddr, 2);
		
		return  ConvertData.byteToDate(values);
	}

	/**
	 * 鍐欒繃鏈熸棩鏈�
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public void writeSensorAgeOutDate(Date date)
			throws Exception {
		
		masterProxy.syncSetValue(slaveId, 
				AddrConst.SENSOR_AGE_OUT_DATE + sensorAddr,
				date);
	
	}

	/**
	 * 璇绘渶杩戜竴娆℃爣瀹氭棩鏈�
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public Date readSensorLastCallDate() throws Exception {
		byte[] values= masterProxy.syncGetValue(slaveId,
				 AddrConst.SENSOR_LAST_CALL_DATE
						+ sensorAddr, 2);
		
		return ConvertData.byteToDate(values);
	}

	/**
	 * 鍐欐渶杩戜竴娆℃爣瀹氭棩鏈�
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public void writeSensorLastCallDate(Date date)
			throws Exception {

		
		 masterProxy.syncSetValue(slaveId, 
				AddrConst.SENSOR_LAST_CALL_DATE + sensorAddr,
				date);
		
	}

	/**
	 * 璇婚噸鏂版爣瀹氭棩鏈�
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public Date readSensorNextCallDate() throws Exception {
		byte[] values= masterProxy.syncGetValue(slaveId,
				 AddrConst.SENSOR_NEXT_CALL_DATE
						+ sensorAddr, 2);
		
		return  ConvertData.byteToDate(values);
	}

	/**
	 * 鍐欓噸鏂版爣瀹氭棩鏈�
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public void writeSensorNextCallDate(Date date)
			throws Exception{
		
		masterProxy.syncSetValue(slaveId,
				AddrConst.SENSOR_NEXT_CALL_DATE + sensorAddr,
				date);
	}

	/**
	 * 璇绘爣瀹氫俊鎭�
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public String readSensorCallInfo() throws Exception {
		byte values[] = masterProxy.syncGetValue(slaveId, AddrConst.SENSOR_CALL_INFO
				+ sensorAddr, 8);
	
		return ConvertData.byteToString(values);
	}

	/**
	 * 鍐欐爣瀹氫俊鎭�
	 * 
	 * @param data
	 * @throws ModbusTransportException
	 * @throws OutRangeException
	 */
	@SuppressLint("NewApi")
	public void writeSensorCallInfo(String data)
			throws Exception {
		byte[] datas = new byte[16];
		
		
		byte[] real = data.getBytes(Charset.forName("UTF-8"));
		if (real.length > 16) {
			throw new Exception();
		}

		System.arraycopy(real, 0, datas, 0, real.length);
		masterProxy.syncSetValue(slaveId, AddrConst.SENSOR_CALL_INFO + sensorAddr,
				data);
		
	}

	/**
	 * 读采集数量和间隔
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public Short readSampleCountInterval() throws Exception {

		byte[] values= masterProxy.syncGetValue(slaveId,
				 AddrConst.SAMPLE_COUNT_INTERVAL
						+ sensorAddr, 1);
		
		

		return ConvertData.toShort(values)[0];
	}

	/**
	 * 写采集数量和间隔
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeSampleCountInterval(short p)
			throws Exception{
		masterProxy.syncSetValue(slaveId, 
				AddrConst.SAMPLE_COUNT_INTERVAL + sensorAddr,
				p);
		
	}
	
	/**
	 * @return
	 *       读采集间隔
	 * @throws Exception
	 */
	public short readSampleInterval() throws Exception{
		
		return (short) ((readSampleCountInterval() >> 8) & 0xff);
	}
	
	

	

	/**
	 * 读采集间隔
	 * 
	 * @param val
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public void writeSampleInterval(short val) throws Exception{

		short p = (short) ((readSampleCountInterval() & 0xff) + (val << 8));

		writeSampleCountInterval(p);

	}
	
	
	/**
	 * @return
	 *     读采集数量
	 * @throws Exception
	 */
	public short readSampleCount() throws Exception{
		
		return (short) (readSampleCountInterval() & 0xff);
	}
	
	

	/**
	 * 写采集数量
	 * 
	 * @param val
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public void writeSampleCount(short val) throws Exception{
		short p = (short) ((readSampleCountInterval() & 0xff00) + (val&0xff));

		writeSampleCountInterval(p);

	}

	/**
	 * 写 传感器状态开关
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeSensorStatueSwitch(short p)
			throws Exception{
		masterProxy.syncSetValue(slaveId, 
				AddrConst.SENSOR_STATE + sensorAddr,
				 p);
		
	}
	
	/**
	 * @return
	 *       读传感器 的一些状态
	 * @throws Exception
	 */
	public short readSensorStatueSwitch() throws Exception {

		byte[] values= masterProxy.syncGetValue(slaveId,
				 AddrConst.SENSOR_STATE
						+ sensorAddr,1);

		return ConvertData.toShort(values)[0];//修改传感器中“自动标定开关”和“工作状态”的值，便于get
		  //return sensorStateSwitch;
	}

	
	/**
	 * @param  传感器的状态
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public abstract void writeSensorStatue(byte val) throws Exception;

	/**
	 *传感器的模拟量开关
	 * 
	 * @param val
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public abstract void writeSensorAnalogSwitch(byte val)
			throws Exception;

	/**
	 * 传感器的温度开关
	 * @param val
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public abstract void writeSensorTemSwitch(byte val) throws Exception;

	/**
	 * 传感器的标定开关
	 * 
	 * @param val
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public abstract void writeSensorDemaStatue(byte val)
			throws Exception ;
	
	
	
	/**
	 * @param  读传感器的状态
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public abstract byte readSensorStatue() throws Exception;

	/**
	 *读传感器的模拟量开关
	 * 
	 * @param val
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public abstract byte readSensorAnalogSwitch()
			throws Exception;

	/**
	 * 读传感器的温度开关
	 * @param val
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public abstract byte readSensorTemSwitch() throws Exception;

		

	/**
	 * 读传感器的标定开关
	 * 
	 * @param val
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public abstract byte readSensorDemaStatue()
			throws Exception ;

	/**
	 * 读传感器的左检测值
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public abstract float readAnalogOutputLeftValue()
			throws Exception;

	/**
	 * 写传感器的左检测值
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public abstract void writeAnalogOutputLeftValue(float val)
			throws Exception;

	/**
	 * 读传感器的右检测值
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public abstract float readAnalogOutputRightValue()
			throws Exception;

	/**
	 * 写传感器的右检测值
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public abstract void writeAnalogOutputRightValue(float val)
			throws Exception;

	/**
	 * 读DA输出标准模拟信号更新间隔|标准模拟信号输出方式【暂时未用】
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public abstract short readAutoIntervalAnalogType()
			throws Exception;

	/**
	 * 写DA输出标准模拟信号更新间隔|标准模拟信号输出方式【暂时未用】
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public abstract void writeAutoIntervalAnalogType(short val)
			throws Exception;

	
	
	/**
	 * @return
	 *     读自动模拟量间隔
	 * @throws Exception
	 */
	public short readAutoInterval() throws Exception{
		
		return (byte) ((readAutoIntervalAnalogType() >> 8) & 0xff);
	}
	
	/**
	 * 写模拟量间隔
	 * 
	 * @param val
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeAutoInterval(short val) throws Exception {
		
		
		short p = (short) ((readAutoIntervalAnalogType() & 0xff) + (val << 8));

		writeAutoIntervalAnalogType(p);

	}
	
	/**
	 * @return
	 *     读自动模拟量类型
	 * @throws Exception
	 */
	public short readAnalogType() throws Exception{
		
		return (byte) (readAutoIntervalAnalogType() & 0xff);
	}

	/**
	 * (读)模拟量的类型
	 * 
	 * @param val
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeAnalogType(byte val) throws Exception {
		
		short p = (short) ((readAutoIntervalAnalogType() & 0xff00) + (val&0xff));

		writeAutoIntervalAnalogType(p);

		

	}

	/**
	 * 读采样结果
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public abstract float readDbAnalogResult() throws Exception;

	/**
	 * 读db 原始采样结果
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public abstract short[] readDbSampleResults()
			throws Exception;

	/**
	 * 读检测结果1
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */

	public abstract float readSensorResultR1() throws Exception;

	/**
	 * 读检测结果2
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public float readSensorResultR2() throws Exception {
		return 0;
	};

	/**
	 * 读检测结果3
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public float readSensorResultR3() throws Exception {
		return 0;
	};

	/**
	 * 读检测结果4
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public float readSensorResultR4() throws Exception {
		return 0;
	};
	
	public abstract float[] readSensorDemaAllParams()throws Exception;
	

	/**
	 * 
	 * @author Administrator 
	 *    补偿参数和标定参数
	 *        
	 */
	public class SensorParams {

		private ParamsPlace places = new ParamsPlace(new int[0],new int[0],new int[0],new int[0],new int[0]);
		
	
	
		public SensorParams(int type) throws Exception {
			
			switch (type) {
			case 0x0100:
				
				places = new ParamsPlace(new int[0]													// 琛ュ伩鍙傛暟compensateParams(浠庝笅浣嶆満璇诲彇鐨�)
											,new int[] {AddrConst.D00 + sensorAddr + 1 * 2, 1 * 2 } // 鏍囧畾鍙傛暟
											,new int[0]												// 鏍℃鍙傛暟
											,new int[] {AddrConst.D00 + sensorAddr + 2 * 2, 4 * 2 } // 杈撳叆鍙傛暟
											,new int[] { AddrConst.D00 + sensorAddr + 6 * 2, 1 * 2 }// 鍏跺畠鍙傛暟
											); 
				break;
			case 0x0200:
				places = new ParamsPlace(new int[] {AddrConst.D00 + sensorAddr, 1 * 2 }
											,new int[] {AddrConst.D00 + sensorAddr + 1 * 2, 3 * 2 }
											,new int[0]
											,new int[] {AddrConst.D00 + sensorAddr + 4 * 2, 4 * 2 }
											,new int[0]
											);
				break;
			case 0x0300:
				places = new ParamsPlace(new int[] {AddrConst.D00 + sensorAddr, 2 * 2 }
											, new int[] {AddrConst.D00 + sensorAddr + 2 * 2, 2 * 2 }
											, new int[0] 
											, new int[] {AddrConst.D00 + sensorAddr + 4 * 2, 2 * 2 }
											, new int[] {AddrConst.D00 + sensorAddr + 6 * 2, 1 * 2 }
											);
				break;
			case 0x0400:
				places = new ParamsPlace(new int[] {AddrConst.D00 + sensorAddr, 3 * 2 }
											,new int[] {AddrConst.D00 + sensorAddr + 3 * 2, 2 * 2 }
											,new int[] {AddrConst.D00 + sensorAddr + 5 * 2, 2 * 2 }
											,new int[] {AddrConst.D00 + sensorAddr + 7 * 2, 5 * 2 }
											,new int[] {AddrConst.D00 + sensorAddr + 12 * 2, 1 * 2 }
											);
				break;
			case 0x0401:
				places = new ParamsPlace(new int[] {AddrConst.D00 + sensorAddr, 3 * 2 }
											, new int[] {AddrConst.D00 + sensorAddr + 3 * 2, 2 * 2 }
											, new int[] {AddrConst.D00 + sensorAddr + 5 * 2, 2 * 2 }
											, new int[] {AddrConst.D00 + sensorAddr + 7 * 2, 5 * 2 }
											, new int[] {AddrConst.D00 + sensorAddr + 12 * 2, 1 * 2 }
											);
				break;
			case 0x0500:
				places = new ParamsPlace(new int[0] 
						, new int[] {AddrConst.D00 + sensorAddr + 1 * 2, 4 * 2 }
											, new int[0]
											, new int[0] 
											
											, new int[] {AddrConst.D00 + sensorAddr + 5 * 2, 1 * 2 }
											);
				
				break;
			case 0x0600:
				places = new ParamsPlace(new int[] {AddrConst.D00 + sensorAddr, 1 * 2 }
											, new int[] {AddrConst.D00 + sensorAddr + 1 * 2, 2 * 2 }
											, new int[0]
											, new int[0]
											, new int[0]
											);
				break;
			case 0x0700:
				places = new ParamsPlace(new int[] {AddrConst.D00 + sensorAddr, 5 * 2 }
											, new int[] {AddrConst.D00 + sensorAddr + 5 * 2, 23 * 2 }
											, new int[] {AddrConst.D00 + sensorAddr + 28 * 2, 5 * 2 }
											, new int[] {AddrConst.D00 + sensorAddr + 33 * 2, 4 * 2 }
											, new int[0]
											);
				break;
			case 0x0800:
				places = new ParamsPlace(new int[] {AddrConst.D00 + sensorAddr, 3 * 2 }
											, new int[] {AddrConst.D00 + sensorAddr + 3 * 2, 18 * 2 }
											, new int[] {AddrConst.D00 + sensorAddr + 21 * 2, 5 * 2 }
											, new int[] {AddrConst.D00 + sensorAddr + 26 * 2, 3 * 2 }
											, new int[0]
											);
				break;
			}

		}

		/**
		 * 读补偿参数
		 * 
		 * @return
		 * @throws ModbusTransportException
		 *             
		 */
		public float[] readCompensateParams() throws Exception {

			if(places.getCompensateParamsPlace().length==0)return null;
				byte datas[] = masterProxy.syncGetValue(slaveId
												,places.getCompensateParamsPlace()[0] 
												,places.getCompensateParamsPlace()[1]);
				

			return ConvertData.toFloat(datas);
		}

		/**
		 * 写补偿参数
		 * 
		 * @param values
		 * @throws ModbusTransportException
		 */
		public void writeCompensateParams(float[] values)
				throws Exception {

		 	int needLen= places.getCompensateParamsPlace()[1]/2;

			masterProxy.syncSetValue(slaveId, places.getCompensateParamsPlace()[0]+(needLen-values.length)*2,values);
		}

		/**
		 * 读标定参数
		 * 
		 * @return
		 * @throws ModbusTransportException
		 */
		public float[] readDemaParams() throws Exception {

			if(places.getDemaParamsPlace().length==0)return null;
			int length=places.getDemaParamsPlace()[1];
			
			byte [] datas=new byte[length*2];
			
			for(int i=0;i<2;i++){
				
				byte data[] = masterProxy.syncGetValue(slaveId
						,places.getDemaParamsPlace()[0]+i*length/2,length/2);
				System.arraycopy(data, 0, datas, i*data.length, data.length);
			}
			
			return ConvertData.toFloat(datas);
		}
		
		/**
		 * 写标定标定参数
		 * 
		 * @param values
		 * @throws ModbusTransportException
		 */
		public void writeDemaParams(float[] values) throws Exception {
			int length = values.length;
			
			float[] values1=new float[length/2];
			
			System.arraycopy(values, 0, values1, 0, values1.length);
			
			float[] values2=null;
			if(length%2==0){
				values2=new float[length/2];
				
			}else{
				values2=new float[length/2+1];
				
			}
			System.arraycopy(values, length/2, values2, 0, values2.length);
			
			
			masterProxy.syncSetValue(slaveId, places.getDemaParamsPlace()[0], values1);
			
			masterProxy.syncSetValue(slaveId, places.getDemaParamsPlace()[0]+values1.length*2, values2);
			
		}

		/**
		 * 读校准参数
		 * 
		 * @return
		 * @throws ModbusTransportException
		 */
		public float[] readCorrectParams() throws Exception {
			
			if(places.getCorrectParamsPlace().length==0)return null;
			
				byte datas[] = masterProxy.syncGetValue(slaveId,
						places.getCorrectParamsPlace()[0],
						places.getCorrectParamsPlace()[1]);
				return ConvertData.toFloat(datas);
		}

		/**
		 * 校正参数
		 * 
		 * @param values
		 * @throws ModbusTransportException
		 */
		public void writeCorrectParams(float[] values)
				throws Exception {

			masterProxy.syncSetValue(slaveId
					, places.getCorrectParamsPlace()[0]//鑻ヨint[]娌℃湁鍊硷紝鍒欒璇ユ柟娉曚細鎶ラ敊		
					,values);
			
		}

		/**
		 * 璇昏緭鍏ュ弬鏁�
		 * 
		 * @return
		 * @throws ModbusTransportException
		 */
		public float[] readInputParams() throws Exception {
			
			if(places.getInputParamsPlace().length==0)return null;
				byte datas[] = masterProxy.syncGetValue(slaveId,
						places.getInputParamsPlace()[0],
						places.getInputParamsPlace()[1]);
				return ConvertData.toFloat(datas);
		}

		/**
		 * 鍐欒緭鍏ュ弬鏁�
		 * 
		 * @param values
		 * @throws ModbusTransportException
		 */
		public void writeInputParams(float[] values)
				throws Exception {
			masterProxy.syncSetValue(slaveId, places.getInputParamsPlace()[0], values);
			writeSensorDemaStatue((byte)1);
		}

		/**
		 * 璇诲叾瀹冨弬鏁�
		 * 
		 * @return
		 * @throws ModbusTransportException
		 */
		public float[] readOtherParams() throws Exception {

			if(places.getOtherParamsPlace().length==0)return null;
				byte datas[] = masterProxy.syncGetValue(slaveId,
						places.getOtherParamsPlace()[0],
						places.getOtherParamsPlace()[1]);
				return ConvertData.toFloat(datas);
			
		}

		/**
		 * 鍐欏叾瀹冨弬鏁�
		 * 
		 * @param values
		 * @throws ModbusTransportException
		 */
		public void writeOtherParams(float[] values)
				throws Exception {

			
			masterProxy.syncSetValue(slaveId, places.getOtherParamsPlace()[0], values);
		

		}
		

		/**
		 * @author Administrator 
		 * 			标定 检测中  所要用到的一些参数位置集合
		 */
		private class ParamsPlace {  
		
			private int[] compensateParamsPlace = new int[0];// 补偿参数位置

			private int[] demaParamsPlace = new int[0];  // 标定参数位置

			private int[] correctParamsPlace = new int[0];// 矫正参数位置

			private int[] inputParamsPlace = new int[0];  // 输入参数位置

			private int[] otherParamsPlace = new int[0];  // 其他参数位置

			public ParamsPlace(int[] compensateParamsPlace  
								,int[] demaParamsPlace  
								, int[] correctParamsPlace 
								,int[] inputParamsPlace  
								, int[] otherParamsPlace 
								) {

				this.compensateParamsPlace = compensateParamsPlace;
				this.demaParamsPlace = demaParamsPlace;
				this.correctParamsPlace = correctParamsPlace;
				this.inputParamsPlace = inputParamsPlace;
				this.otherParamsPlace = otherParamsPlace;
				
			}

			public int[] getCompensateParamsPlace() {
				return compensateParamsPlace;
			}

			public int[] getCorrectParamsPlace() {
				return correctParamsPlace;
			}

			public int[] getDemaParamsPlace() {
				return demaParamsPlace;
			}

			public int[] getInputParamsPlace() {
				return inputParamsPlace;
			}

			public int[] getOtherParamsPlace() {
				return otherParamsPlace;
			}

		}
	}//end of SensorCalParams

}