package cn.cowis.modbus.entity;

import cn.cowis.modbus.MasterProxy;
import cn.cowis.modbus.tool.ConvertData;






/**
 * @author Administrator
 */
public class SensorTem extends Sensor {

	

	public SensorTem(int slaveId, int sensorAddr,MasterProxy masterProxy) throws Exception{
		
		super(slaveId, sensorAddr, masterProxy);
		
	}
	
	
	/**
	 * 读自动采样间隔和输出模拟信号方式
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public short readAutoIntervalAnalogType() throws Exception {

		byte[] values= masterProxy.syncGetValue(slaveId	
				,AddrConst.AUTO_ANALOG_INTERVAL_TYPE + sensorAddr
				,1);

		return ConvertData.toShort(values)[0];
	}

	/**
	 * 写自动采样间隔和输出模拟信号方式
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeAutoIntervalAnalogType(short p)throws Exception {
		masterProxy.syncSetValue(slaveId,
				AddrConst.AUTO_ANALOG_INTERVAL_TYPE + sensorAddr,
				 p);
	}

	


	/**
	 * 读输出模拟量左检测值
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public float readAnalogOutputLeftValue() throws Exception {
		byte[] values= masterProxy.syncGetValue(slaveId,
				
				AddrConst.ANALOG_OUTPUT_LEFT_VALUE + sensorAddr,
				2);
	
		return ConvertData.toFloat(values)[0];
	}

	/**
	 * 读输出模拟量右检测值
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public float readAnalogOutputRightValue() throws Exception {
		byte[] values= masterProxy.syncGetValue(slaveId,
				
				AddrConst.ANALOG_OUTPUT_RIGHT_VALUE + sensorAddr,
				2);
		
		return ConvertData.toFloat(values)[0];
	}

	@Override
	public void writeAnalogOutputLeftValue(float val)
			throws Exception {

		
		masterProxy.syncSetValue(slaveId, AddrConst.ANALOG_OUTPUT_LEFT_VALUE
				+ sensorAddr, val);
	
	}

	@Override
	public void writeAnalogOutputRightValue(float val)
			throws Exception {
		
		masterProxy.syncSetValue(slaveId, AddrConst.ANALOG_OUTPUT_RIGHT_VALUE
				+ sensorAddr, val);
		
	}

	/**
	 * 读模拟量计算结果
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public float readDbAnalogResult() throws Exception {

		byte [] values =  masterProxy.syncGetValue(slaveId,
				AddrConst.DB_ANALOG_RESULT
				+ sensorAddr, 2);
	
		return ConvertData.toFloat(values)[0];
	}

	/**
	 * 读检测计算结果
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public float readSensorResultR1() throws Exception {

		byte [] results =  masterProxy.syncGetValue(slaveId
													, AddrConst.SENSOR_RESULT+ sensorAddr
													, 2);
		
		float result1= ConvertData.toFloat(results)[0];
		
		return result1;
	}
	
	/* (non-Javadoc)
	 * 读取id的值
	 * @see cn.cowis.transmittor.entity.Sensor#readDbSampleResults()
	 */
	public short[] readDbSampleResults() throws Exception {

		 int count= readSampleCount();
		 
		 byte[] values=new byte[count*2];
		 
		 if(count<=24){
			 
			 byte[] values1 = masterProxy.syncGetValue(slaveId, AddrConst.DB_SAMPLE_RESULTS
						+ sensorAddr, count);
			 System.arraycopy(values1, 0, values, 0, values.length);
			 
		 }else if(count>24&&count<=44){
			 byte[] values1 = masterProxy.syncGetValue(slaveId, AddrConst.DB_SAMPLE_RESULTS
						+ sensorAddr, 24);
			 System.arraycopy(values1, 0, values, 0, values1.length);
			 
			 byte[] values2 = masterProxy.syncGetValue(slaveId, AddrConst.DB_SAMPLE_RESULTS
						+ sensorAddr+24, count-24);
			 System.arraycopy(values2, 0, values, 48, values2.length);
		}else if(count>44&&count<=64){
			
			 byte[] values1 = masterProxy.syncGetValue(slaveId, AddrConst.DB_SAMPLE_RESULTS
						+ sensorAddr, 24);
			 System.arraycopy(values1, 0, values, 0, values1.length);
			 
			 byte[] values2 = masterProxy.syncGetValue(slaveId, AddrConst.DB_SAMPLE_RESULTS
						+ sensorAddr+24, 20);
			 System.arraycopy(values2, 0, values, 48, values2.length);
			 
			 byte[] values3 = masterProxy.syncGetValue(slaveId, AddrConst.DB_SAMPLE_RESULTS
						+ sensorAddr+44, count-24-20);
			 System.arraycopy(values3, 0, values, 88, values3.length);
			
		}
		 
		
		return ConvertData.toShort(values);
	}
	
	
	@Override
	public float[] readSensorDemaAllParams() throws Exception {
		byte datas[] = masterProxy.syncGetValue(slaveId
				,AddrConst.D00+ sensorAddr,16);
		
		return ConvertData.toFloat(datas);
		
	}
	
	/* (non-Javadoc)
	 * 工作状态
	 * @see cn.cowis.transmittor.entity.Sensor#writeSensorStatue(byte)
	 */
	@Override
	public void writeSensorStatue(byte val) throws Exception {
		
		short p = (short) ((readSensorStatueSwitch() & 0xff00) +  (val&0xff));
		writeSensorStatueSwitch(p);
		
	}

	@Override
	public void writeSensorAnalogSwitch(byte val)
			throws Exception {
		
		
	}

	@Override
	public void writeSensorTemSwitch(byte val) throws Exception {
		
	}

	/* 
	 * 标定开关
	 * @see cn.cowis.transmittor.entity.Sensor#writeSensorDemaStatue(byte)
	 */
	@Override
	public void writeSensorDemaStatue(byte val)
			throws Exception {
		
		short p = (short) ((readSensorStatueSwitch() & 0x00ff) +  (val<<8));
		writeSensorStatueSwitch(p);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

		}
		
	}
	
	
	@Override
	public byte readSensorStatue() throws Exception {
		
		return (byte) (readSensorStatueSwitch() & 0x00ff);
		
	}

	@Override
	public byte readSensorAnalogSwitch()
			throws Exception {
		
		return -2;
	}

	@Override
	public byte readSensorTemSwitch() throws Exception {
		
		return -2;
	}

	@Override
	public byte readSensorDemaStatue()
			throws Exception {
		
		return (byte) (readSensorStatueSwitch() >> 8 & 0xff);

	}

	/**
	 *读取 保存位数和 单位 
	 * @return
	 * @throws Exception 
	 */
	public short readResultUnitCount() throws Exception{
		
		byte [] results =  masterProxy.syncGetValue(slaveId
				, AddrConst.SENSOR_RESULT_UNIT+ sensorAddr
				, 1);
		
		return ConvertData.toShort(results)[0];
	}
	
	
	/**
	 * @param value
	 *       写保存位数和单位
	 * @throws Exception 
	 */
	public void writeResultUnitCount(short value) throws Exception{
		
		 masterProxy.syncSetValue(slaveId
					, AddrConst.SENSOR_RESULT_UNIT+ sensorAddr
					, value);
	}
	

	@Override
	public byte[] readResultUnit() throws Exception {
		
		return new byte[]{ (byte) (readResultUnitCount()&0x00ff)};
	}


	@Override
	public void writeResultUnit(byte[] values) throws Exception {
		
		short v= (short) ((readResultUnitCount()&0xff00)+(values[0]&0xff));
		
		writeResultUnitCount(v);
	}


	@Override
	public byte[] readResultCount() throws Exception {
		
		byte[] bs= new byte[]{(byte) (readResultUnitCount()>>8)};
		
		System.out.println("tem sensor num="+bs[0]);
		
		return bs;
	}


	@Override
	public void writeResultCount(byte[] values) throws Exception {
		
		 short v= (short) (( readResultUnitCount()&0x00ff)+(values[0]<<8));
			
		 writeResultUnitCount(v);
	}
	
	
	
	

//	@Override
//	public short readResultCountUnit() throws Exception {
//		
//		
//		byte[] values= masterProxy.syncGetValue(slaveId
//					 ,AddrConst.SENSOR_RESULT_UNIT+ sensorAddr
//					 , 1);
//		sensorResultCountUnit=ConvertData.toShort(values)[0];
//
//			partResultCountUnit();	
//			return sensorResultCountUnit;
//		
//		
//	}
//	
//	
//	@Override
//	public void writeResultCountUnit(short value) throws Exception {
//		
//			 masterProxy.syncSetValue(slaveId,
//					 AddrConst.SENSOR_RESULT_UNIT
//							+ sensorAddr, value);
//			 
//			 sensorResultCountUnit=value;
//		
//	}
	
	
	
	
	
	
	

	

}
