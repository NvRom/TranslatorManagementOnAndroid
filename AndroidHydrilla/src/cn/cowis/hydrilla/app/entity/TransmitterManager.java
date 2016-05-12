package cn.cowis.hydrilla.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.cowis.hydrilla.app.entity.CacheParams.ValueType;
import cn.cowis.modbus.entity.Sensor;
import cn.cowis.modbus.entity.Transmitter;

public class TransmitterManager extends Manager {

	private static final long serialVersionUID = 1L;

	static String[] CONNECT_TSMT_NEED = new String[] { Constants.BAUD,
			Constants.PARITY_STOP, Constants.SLAVE_ID, Constants.PRODUCT_ID };// 连接的时候读的变送器参数
	
	static String[] ALL_PARAMS = new String[] { Constants.ADDR_TABLE_VERSION
	, Constants.BAUD, Constants.PARITY_STOP, Constants.SLAVE_ID,
			Constants.DEBUG_SWITCH, Constants.RESTART, Constants.PRODUCT_TYPE,
			Constants.PRODUCT_ID, Constants.MFD_DATE, Constants.SOFT_VERSION,
			Constants.HARD_VERSION };//全部的变送器属性

	Transmitter tsmt = null;

	SensorManager[] sensorAllManagers = null; //里面有正确的类型，也有不正确的类型
	
	List <SensorManager> avalibleSensorManagers=null;//可用的sensorManager 有正确的类型 集合
	
	Sensor[] sensors = null;

	public TsmtCommonParams tcp = new TsmtCommonParams();// 开始已经初始化了的属性

	public TransmitterManager(Transmitter transmitter) {

		this.tsmt = transmitter;

		try {
			sensors = tsmt.initSensors().getSensors();

			initSensorManagers();

		} catch (Exception e) {
			e.printStackTrace();
		}

		initParams();

	}
	
	
	/**
	 * 初始化边送终的传感器
	 */
	public void initSensorManagers(){
		
		sensorAllManagers=SensorManagerFactory.getSensorManager(sensors);
		
		avalibleSensorManagers=new ArrayList<SensorManager>();
		for(int i=0;i<sensorAllManagers.length;i++){
			
			if(sensorAllManagers[i].isAvalibleSensor()){
				
				avalibleSensorManagers.add(sensorAllManagers[i]);
			}
		}
		
	}
	
	public List<SensorManager> getAvalibleSensorManagers(){
		
		return avalibleSensorManagers;
		
	}
	
	public SensorManager[] getAllSensorManagers(){
		
		return sensorAllManagers;
	}
	
	

	public void initParams() {

		paramMaps = new HashMap<String, CacheParams>();

		paramMaps.put(Constants.ADDR_TABLE_VERSION, new CacheParams(
				"协议版本" ,ValueType.SHORT) {

			@Override
			public Object readValue() throws Exception {
				return tsmt.readAddrTableVersion();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				tsmt.writeAddrTableVersion((Short) obj);

			}

		});

		paramMaps.put(Constants.BAUD, new CacheParams("波特率",ValueType.BYTE ) {

			@Override
			public Object readValue() throws Exception {

				return tsmt.readBaud();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				tsmt.writeBaud((Byte) obj);

			}

		});

		paramMaps.put(Constants.PARITY_STOP, new CacheParams(
				 "数据位停止位和奇偶校验" ,ValueType.BYTE) {

			@Override
			public Object readValue() throws Exception {

				return tsmt.readParityStop();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				tsmt.writeParityStop((Byte) obj);

			}

		});

		paramMaps.put(Constants.SLAVE_ID, new CacheParams(
				"从机地址" ,ValueType.INT) {

			@Override
			public Object readValue() throws Exception {

				return tsmt.getSlaveId();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				tsmt.writeSlaveId((Integer) obj);

			}

		});

		paramMaps.put(Constants.DEBUG_SWITCH, new CacheParams(
				 "debug模拟开关" ,ValueType.BYTE, false) {

			@Override
			public Object readValue() throws Exception {

				return tsmt.readDebugSwitch();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				tsmt.writeDebugSwitch((Byte) obj);

			}

		});

		paramMaps.put(Constants.RESTART, new CacheParams(
				"重启标志位",ValueType.BYTE , false) {

			@Override
			public Object readValue() throws Exception {

				return tsmt.readReStart();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				tsmt.writeReStart((Byte) obj);

			}

		});

		paramMaps.put(Constants.PRODUCT_TYPE, new CacheParams(
				"产品型号",ValueType.BYTE ) {

			@Override
			public Object readValue() throws Exception {

				return tsmt.readProductType();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				tsmt.writeProductType((Byte) obj);

			}

		});

		paramMaps.put(Constants.PRODUCT_ID, new CacheParams(
				"产品序列号" ,ValueType.INT) {

			@Override
			public Object readValue() throws Exception {

				return tsmt.readProductId();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				tsmt.writeProductId((Integer) obj);

			}

		});

		paramMaps.put(Constants.MFD_DATE, new CacheParams(
				"生产日期",ValueType.DATE ) {

			@Override
			public Object readValue() throws Exception {

				return tsmt.readMfdDate();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				tsmt.writeMfdDate((Date) obj);

			}

		});

		paramMaps.put(Constants.SOFT_VERSION, new CacheParams(
				"软件版本" ,ValueType.BYTE) {

			@Override
			public Object readValue() throws Exception {

				return tsmt.readSoftVersion();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				tsmt.writeSoftVersion((Byte) obj);

			}

		});

		paramMaps.put(Constants.HARD_VERSION, new CacheParams(
				"硬件版本" ,ValueType.BYTE) {

			@Override
			public Object readValue() throws Exception {

				return tsmt.readHardVersion();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				tsmt.writeHardVersion((Byte) obj);

			}

		});
	}

	/**
	 * @return
	 *       这个是已经初始化的
	 */
//	public SensorManager[] getSensorManagers() {
//		return sensorManagers;
//	}

	/**
	 * 连接的时候初始化部分属性这样就不卡了
	 * 
	 * @param ids
	 * 
	 * @throws Exception
	 */
	@Override
	public Manager initPartParams() throws Exception {

		CacheParams tsmtSensor = null;
		for (String id : CONNECT_TSMT_NEED) {
			tsmtSensor = paramMaps.get(id);
			tsmtSensor.getValue();
		}
		return this;
	}

	/**
	 * @throws Exception
	 *             全部属性初始化
	 */
	public void initAllParams() throws Exception {

		CacheParams tsmtSensor = null;
		for (String id : ALL_PARAMS) {
			tsmtSensor = paramMaps.get(id);
			tsmtSensor.getValue();
		}
	}

	public class TsmtCommonParams {

		public byte getBaud() {
			try {
				return (Byte) paramMaps.get(Constants.BAUD).getValue();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return -2;
		}

		public byte getParityStop() {
			try {
				return (Byte) paramMaps.get(Constants.PARITY_STOP).getValue();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			;
			return -2;
		}

		public int getSlaveId() {
			try {
				return (Integer) paramMaps.get(Constants.SLAVE_ID).getValue();
			} catch (Exception e) {
				e.printStackTrace();
			}
			;
			return -2;
		}

		public int getProductId() {
			try {
				return (Integer) paramMaps.get(Constants.PRODUCT_ID).getValue();
			} catch (Exception e) {
				e.printStackTrace();
			}
			;
			return -2;
		}

	}

}
