package cn.cowis.modbus.entity;

// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 2013-08-01 12:31:15
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Transmitter.java


import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Date;

import android.annotation.SuppressLint;
import android.util.Log;
import cn.cowis.modbus.MasterProxy;
import cn.cowis.modbus.tool.ConvertData;


/**
 * @author shuang ge
 * 
 */
public class Transmitter {

	private int slaveId;
	private MasterProxy masterProxy;
	private Sensor[] sensors = new Sensor[2];
	private static final int temSensoraddr = 0x10;//AddrConst.SENSOR_TYPE(0x0000)+ sensorAddr，即得传感器类型地址
	private static final int mainSensoraddr = 0x8f;
	

	
	public Transmitter(MasterProxy masterProxy, int slaveId) {
		this.masterProxy = masterProxy;
		this.slaveId = slaveId;
	}

	
	public int getSlaveId() {
		return slaveId;
	}


	/**
	 * 为变送器中初始化两个传感器（tem main）
	 * @return
	 * @throws Exception
	 */
	public Transmitter initSensors()throws Exception{
		sensors[0] = new SensorTem(slaveId, temSensoraddr, masterProxy);
		sensors[1] = new SensorMain(slaveId, mainSensoraddr, masterProxy);
		return this;
	}

	/**
	 * 必须用该类调用writeAddrTableVersion(short)方法向下位机写命令后，这样该方法返回值才不为null
	 * @return
	 */

	public Sensor[] getSensors() {
		return sensors;
	}

	/**
	 * 协议版本
	 * 
	 * @return 协议版本
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public Short readAddrTableVersion() throws Exception {
		
		byte[] values= masterProxy.syncGetValue(slaveId,
				 AddrConst.ADDR_TABLE_VERSION,
				1);
		short addrTableVersion = ConvertData.toShort(values)[0];
		System.out.println("read addr table version={}"+ addrTableVersion);
		
		return addrTableVersion;
	}

	/**
	 * 写版本协议
	 * 
	 * @param t
	 * @param newSlaveId
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeAddrTableVersion(short p) throws Exception {
		masterProxy.syncSetValue(slaveId, 
				AddrConst.ADDR_TABLE_VERSION, p);
		
	}

	/**
	 * 读变送器的通信配置参数
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public Short readComParameters() throws Exception {
		
		byte[] values= masterProxy.syncGetValue(slaveId
												,AddrConst.COM_PARAMETERS
												,1);

		return ConvertData.toShort(values)[0];
	}

	/**
	 * 写变送器的通信配置参数
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public void wirteComParameters(short p) throws Exception {
		masterProxy.syncSetValue(slaveId,
				AddrConst.COM_PARAMETERS,  p);

	}
	
	
	/**
	 * (衍生)读停止位和奇偶位
	 * 
	 * @param p
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public byte readParityStop() throws Exception {

		
		return (byte) (readComParameters() >> 8 & 0x0f);//& 0xf0ff将高字节低四位清空
		
	}
	

	/**
	 * (衍生)写停止位和奇偶位
	 * 
	 * @param p
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeParityStop(byte p) throws Exception {

		
		short value = (short) ((readComParameters() & 0xf0ff) + (p << 8));//& 0xf0ff将高字节低四位清空
		wirteComParameters(value);
	}

	
	/**
	 * (衍生)读波特率
	 * 
	 * @param p
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public byte readBaud() throws Exception {
		
		return (byte) (readComParameters() >> 12 & 0xf);
	}
	
	/**
	 * (衍生)写波特率
	 * 
	 * @param p
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeBaud(byte p) throws Exception {
		short value = (short) ((readComParameters() & 0x0fff) + (p << 12));
		wirteComParameters(value);

	}

	/**
	 * (衍生)写从机ID
	 * 
	 * @param p
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeSlaveId(int p) throws Exception {
	
		short value = (short) ((readComParameters() & 0xff00) +(p&0x00ff));
		wirteComParameters(value);
		writeReStart((byte) 1);
		
		slaveId = p;
		
		for(int i=0;i<sensors.length;i++){
			
			sensors[i].setSlaveId(p);
		}
		
		
		
		
	}

	
	/**
	 * 读 debug 和restart
	 * @param p
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public short readDebugStart() throws Exception{
		byte[] values= masterProxy.syncGetValue(slaveId, 
				AddrConst.SYSTEMRESET, 1);
		
		 return ConvertData.toShort(values)[0];
	}
	
	
	/**
	 * 写 debug 和restart
	 * @param p
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeDebugStart(short p) throws Exception{
		  masterProxy.syncSetValue(slaveId,
				AddrConst.SYSTEMRESET,  p);
		 
	}
	
	
	public byte readReStart() throws Exception{
		
		return (byte)( readDebugStart()&0x00ff);
	}
	
	/**
	 * 写重启
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public void writeReStart(byte p) throws Exception {
		
		short value=(short) ((readDebugStart()&0xff00)+(p&0xff));
		writeDebugStart(value);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public byte readDebugSwitch() throws Exception{
		
		return (byte) ((readDebugStart()>>8)&0xff);
	}
	/**
	 * (衍生)写debug开关盒
	 * @param p
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeDebugSwitch(byte p) throws Exception{
		
		
		short value=(short) ((readDebugStart()&0x00ff)+(p<<8));
		writeDebugStart(value);
	}
	
	

	/**
	 * 读产品型号和变送器ID
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public int readProductTypeId() throws Exception {
		byte[] values= masterProxy.syncGetValue(slaveId,
				 AddrConst.PRODUCT_TYPE_ID,
				2);
		return ConvertData.toInt(values);
	}

	/**
	 * 写 产品型号和变送器ID
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public void wirteProductTypeId(int p) throws Exception {
		masterProxy.syncSetValue(slaveId, 
				AddrConst.PRODUCT_TYPE_ID, p);
	}

	
	/**
	 * @return
	 * 
	 * @throws Exception
	 */
	public byte readProductType() throws Exception{
		
		return (byte) ( readProductTypeId() >> 24);
	}
	/**
	 * (衍生)写1个字节的型号
	 * 
	 * @param str
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeProductType(byte val) throws Exception {
		int p = (readProductTypeId() & 0xffffff) + (val << 24);

		wirteProductTypeId(p);
		
	}

	public int readProductId() throws Exception{
		
		return readProductTypeId() & 0xffffff;
	}
	
	/**
	 * (衍生)写2个字节的字母产品ID
	 * 
	 * @param val
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeProductId(int val) throws Exception {
		int p = (readProductTypeId() & 0xff000000) + val;
		wirteProductTypeId(p);
		
	}

	/**
	 * 读生产日期
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 * @throws ParseException
	 */
	public Date readMfdDate() throws Exception {
		byte[] values= masterProxy.syncGetValue(slaveId,
				 AddrConst.MFD_DATE,
				2);
		return ConvertData.byteToDate(values);
	}

	/**
	 * 写生产日期
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public void writeMfdDate(Date date) throws Exception {

		
		masterProxy.syncSetValue(slaveId, 
				AddrConst.MFD_DATE, date);
	

	}

	/**
	 * 读软件和硬件版本
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public Short readHardSoftVersion() throws Exception {
		byte [] values=masterProxy.syncGetValue(slaveId,
				 AddrConst.HARD_SOFT_VERSION,
				1);
		
		return ConvertData.toShort(values)[0];
	}

	/**
	 * 写软件和硬件版本
	 * 
	 * @throws ErrorResponseException
	 * @throws ModbusTransportException
	 */
	public void writeHardSoftVersion(short p) throws Exception {
		masterProxy.syncSetValue(slaveId,
				AddrConst.HARD_SOFT_VERSION, p);
	
	}

	public byte readHardVersion() throws Exception{
		
		return (byte) (readHardSoftVersion() >> 8 & 0xff);
	}
	
	/**
	 * (衍生)写硬件版本
	 * 
	 * @param val
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeHardVersion(byte val) throws Exception {
		short p = val;

		p = (short) ((readHardSoftVersion() & 0xff) + (p << 8));

		writeHardSoftVersion(p);

	}

	
	public byte readSoftVersion() throws Exception{
		
		return (byte)(readHardSoftVersion() & 0xff);
	}
	
	
	/**
	 * (衍生)写软件版本
	 * 
	 * @param val
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void writeSoftVersion(byte val) throws Exception {

		short p = (short) ((readHardSoftVersion() & 0xff00) + val);

		writeHardSoftVersion(p);

	}

	/**
	 * 读产品信息
	 * 
	 * @return
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public String readProductInfo() throws Exception {

		byte values[] = masterProxy.syncGetValue(slaveId, AddrConst.PRODUCT_INFO, 8);
	
		return ConvertData.byteToString(values);
	}

	@SuppressLint("NewApi")
	public void writeProductInfo(String data) throws Exception {

		byte[] datas = new byte[16];
		byte[] real = data.getBytes(Charset.forName("UTF-8"));
		
		if (datas.length > 16) {
			throw new Exception();
		}
		
		System.arraycopy(real, 0, datas, 0, real.length);
		short[] values = ConvertData.toShort(datas);
		
		masterProxy.syncSetValue(slaveId, AddrConst.PRODUCT_INFO, values);
		
	}

	
}