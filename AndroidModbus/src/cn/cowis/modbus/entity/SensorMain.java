package cn.cowis.modbus.entity;

import cn.cowis.modbus.MasterProxy;
import cn.cowis.modbus.tool.ConvertData;

/**
 * @author Administrator
 *                     shuangge
 */
public final class SensorMain extends Sensor {

	public SensorMain(int slaveId, int sensorAddr, MasterProxy masterProxy)throws Exception {
		super(slaveId, sensorAddr, masterProxy);
	}

	@Override
	public byte readSensorStatue() throws Exception {

		return (byte) (readSensorStatueSwitch() & 0xf);
	}

	@Override
	public byte readSensorAnalogSwitch() throws Exception {

		return (byte) (readSensorStatueSwitch() >> 4 & 0x0f);
	}

	@Override
	public byte readSensorTemSwitch() throws Exception {

		return (byte) (readSensorStatueSwitch() >> 12 & 0xf);
	}

	@Override
	public byte readSensorDemaStatue() throws Exception {

		return (byte) (readSensorStatueSwitch() >> 8 & 0x0f);
	}

	/**
	 * @param p写状态
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	@Override
	public void writeSensorStatue(byte val) throws Exception {

		short p = (short) ((readSensorStatueSwitch() & 0xfff0) + (0xf & val));
		writeSensorStatueSwitch(p);
	}

	/**
	 * 模拟量补偿开关
	 * 
	 * @param val
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	@Override
	public void writeSensorAnalogSwitch(byte val) throws Exception {

		short p = (short) ((readSensorStatueSwitch() & 0xff0f) + (val << 4));
		writeSensorStatueSwitch(p);
	}

	/**
	 * 写 温度开关
	 * 
	 * @param val
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	@Override
	public void writeSensorTemSwitch(byte val) throws Exception {

		short p = (short) ((readSensorStatueSwitch() & 0xfff) + (val << 12));
		writeSensorStatueSwitch(p);

	}

	/**
	 * 开始标定
	 * 
	 * @param val
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	@Override
	public void writeSensorDemaStatue(byte val) throws Exception {

		short p = (short) ((readSensorStatueSwitch() & 0xf0ff) + (val << 8));
		writeSensorStatueSwitch(p);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

		}
	}

	/**
	 * 读自动采样间隔和输出模拟信号方式
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public short readAutoIntervalAnalogType() throws Exception {

		byte[] values = masterProxy.syncGetValue(slaveId,

		AddrConst.OTHER_AUTO_ANALOG_INTERVAL_TYPE + sensorAddr, 1);

		return ConvertData.toShort(values)[0];
	}

	/**
	 * 写自动采样间隔和输出模拟信号方式
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeAutoIntervalAnalogType(short p) throws Exception {
		masterProxy.syncSetValue(slaveId,
				AddrConst.OTHER_AUTO_ANALOG_INTERVAL_TYPE + sensorAddr, p);
	}

	/**
	 * 读输出模拟量左检测值
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public float readAnalogOutputLeftValue() throws Exception {
		byte[] values = masterProxy.syncGetValue(slaveId,

		AddrConst.OTHER_ANALOG_OUTPUT_LEFT_VALUE + sensorAddr, 2);

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
		byte[] values = masterProxy.syncGetValue(slaveId,

		AddrConst.OTHER_ANALOG_OUTPUT_RIGHT_VALUE + sensorAddr, 2);

		return ConvertData.toFloat(values)[0];
	}

	/**
	 * 读模拟量计算结果
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public float readDbAnalogResult() throws Exception {

		byte[] results = masterProxy.syncGetValue(slaveId,
				AddrConst.OTHER_DB_ANALOG_RESULT + sensorAddr, 2);

		return ConvertData.toFloat(results)[0];
	}

	/**
	 * 读检测计算结果R1
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public float readSensorResultR1() throws Exception {

		byte[] results = masterProxy.syncGetValue(slaveId,
				AddrConst.OTHER_SENSOR_R1 + sensorAddr, 2);

		return ConvertData.toFloat(results)[0];
	}

	/**
	 * 读检测计算结果R2
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public float readSensorResultR2() throws Exception {
		byte[] values = masterProxy.syncGetValue(slaveId,
				AddrConst.OTHER_SENSOR_R2 + sensorAddr, 2);

		return ConvertData.toFloat(values)[0];
	}

	/**
	 * 读检测计算结果R3
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public float readSensorResultR3() throws Exception {
		byte[] values = masterProxy.syncGetValue(slaveId,
				AddrConst.OTHER_SENSOR_R3 + sensorAddr, 2);

		return ConvertData.toFloat(values)[0];
	}

	/**
	 * 读检测计算结果R1
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public float readSensorResultR4() throws Exception {
		byte[] values = masterProxy.syncGetValue(slaveId,
				AddrConst.OTHER_SENSOR_R4 + sensorAddr, 2);

		return ConvertData.toFloat(values)[0];
	}

	/*
	 * (non-Javadoc) 读AD采样
	 * 
	 * @see cn.cowis.transmittor.entity.Sensor#readDbSampleResults()
	 */
	public short[] readDbSampleResults() throws Exception {

		int count = readSampleCount();

		byte[] values = new byte[count * 2];

		if (count <= 24) {

			byte[] values1 = masterProxy.syncGetValue(slaveId,
					AddrConst.OTHER_DB_SAMPLE_RESULTS + sensorAddr, count);
			System.arraycopy(values1, 0, values, 0, values.length);

		} else if (count > 24 && count <= 44) {
			byte[] values1 = masterProxy.syncGetValue(slaveId,
					AddrConst.OTHER_DB_SAMPLE_RESULTS + sensorAddr, 24);
			System.arraycopy(values1, 0, values, 0, values1.length);

			byte[] values2 = masterProxy.syncGetValue(slaveId,
					AddrConst.OTHER_DB_SAMPLE_RESULTS + sensorAddr + 24,
					count - 24);
			System.arraycopy(values2, 0, values, 48, values2.length);
		} else if (count > 44 && count <= 64) {

			byte[] values1 = masterProxy.syncGetValue(slaveId,
					AddrConst.OTHER_DB_SAMPLE_RESULTS + sensorAddr, 24);
			System.arraycopy(values1, 0, values, 0, values1.length);

			byte[] values2 = masterProxy.syncGetValue(slaveId,
					AddrConst.OTHER_DB_SAMPLE_RESULTS + sensorAddr + 24, 20);
			System.arraycopy(values2, 0, values, 48, values2.length);

			byte[] values3 = masterProxy.syncGetValue(slaveId,
					AddrConst.OTHER_DB_SAMPLE_RESULTS + sensorAddr + 44,
					count - 24 - 20);
			System.arraycopy(values3, 0, values, 88, values3.length);

		}

		return ConvertData.toShort(values);
	}

	/*
	 * (non-Javadoc) 读左检测值
	 * 
	 * @see cn.cowis.transmittor.entity.Sensor#writeAnalogOutputLeftValue(float)
	 */
	@Override
	public void writeAnalogOutputLeftValue(float val) throws Exception {

		masterProxy.syncSetValue(slaveId,
				AddrConst.OTHER_ANALOG_OUTPUT_LEFT_VALUE + sensorAddr, val);

	}

	/*
	 * (non-Javadoc) 读右检测值
	 * 
	 * @see
	 * cn.cowis.transmittor.entity.Sensor#writeAnalogOutputRightValue(float)
	 */
	@Override
	public void writeAnalogOutputRightValue(float val) throws Exception {

		masterProxy.syncSetValue(slaveId,
				AddrConst.OTHER_ANALOG_OUTPUT_RIGHT_VALUE + sensorAddr, val);

	}

	@Override
	public float[] readSensorDemaAllParams() throws Exception {

		byte[] datas = new byte[288];
		for (int i = 0; i < 12; i++) {
			byte data[] = masterProxy.syncGetValue(slaveId, AddrConst.D00
					+ sensorAddr + i * 12, 12);
			System.arraycopy(data, 0, datas, i * data.length, data.length);
		}

		return ConvertData.toFloat(datas);

	}

	public short[] readResultCountUnit() throws Exception {

		byte[] values = masterProxy.syncGetValue(slaveId,
				AddrConst.OTHER_SENSOR_RESULT_UNIT + sensorAddr, 4);

		return ConvertData.toShort(values);

	}

	public void writeResultCountUnit(short[] value) throws Exception {

		masterProxy.syncSetValue(slaveId, AddrConst.OTHER_SENSOR_RESULT_UNIT
				+ sensorAddr, value);

	}

	@Override
	public byte[] readResultUnit() throws Exception {

		short[] values = readResultCountUnit();

		byte[] units = new byte[values.length];

		for (int i = 0; i < values.length; i++) {
			units[i] = (byte) (values[i] & 0x00ff);
		}

		return units;
	}

	@Override
	public void writeResultUnit(byte[] values) throws Exception {

		short[] datas = readResultCountUnit();

		for (int i = 0; i < values.length; i++) {

			datas[i] = (short) ((datas[i] & 0xff00) + (values[0] & 0xff));
		}
		writeResultCountUnit(datas);
	}

	@Override
	public byte[] readResultCount() throws Exception {

		short[] values = readResultCountUnit();

		byte[] counts = new byte[values.length];

		for (int i = 0; i < values.length; i++) {
			counts[i] = (byte) (values[i] >> 8);
			
			System.out.println("main result count="+counts[i]);
		}

		return counts;
	}

	@Override
	public void writeResultCount(byte[] values) throws Exception {

		short[] datas = readResultCountUnit();

		for (int i = 0; i < values.length; i++) {

			datas[i] = (short) ((datas[i] & 0x00ff) + (values[0] << 8));
			
			System.out.println("write result count="+datas[i]);
		}
		writeResultCountUnit(datas);
	}

}
