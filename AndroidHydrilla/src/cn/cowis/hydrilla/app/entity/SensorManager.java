package cn.cowis.hydrilla.app.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.cowis.hydrilla.app.entity.CacheParams.ValueType;
import cn.cowis.hydrilla.app.entity.SensorManagerFactory.OtherInfo;
import cn.cowis.hydrilla.app.entity.SensorManagerFactory.SensorParamsNameType;
import cn.cowis.modbus.entity.Sensor;

public class SensorManager extends Manager {

	
	private static final long serialVersionUID = 222L;
	static String[] CONNECT_SENSOR_NEED=new String[]{Constants.SENSOR_TYPE,Constants.ANALOG_LEFT_VALUE,Constants.ANALOG_RIGHT_VALUE,Constants.SENSOR_STATE,Constants.TEM_SWITCH,Constants.COMPENSATE_PARAMS,Constants.SENSOR_RESULT_COUNT,Constants.SENSOR_RESULT_UNIT}; //连接时候读的传感器参数

	public Sensor sensor = null;
	
	private SensorParamsNameType paramsNameType;
	
	public SensorManager temSlave;
	
	private OtherInfo oi;
	
	public SensorCommonParams scp=new SensorCommonParams();
	
	public int resultProject=0;//这个是保存的是结果的项目

	public SensorManager(Sensor mySensor, SensorParamsNameType paramNames,OtherInfo oi) {
		
		this.sensor = mySensor;
		this.paramsNameType = paramNames;
		this.oi=oi;
		
		initParams();
	}
	
	/**
	 * 判断其是否是个有用的传感器(判断有没有这个类型)
	 */
	public boolean isAvalibleSensor(){
		if(oi.name==null)return false;
		
		return true;
	}
	
	/**
	 * 设置温度的从传感器
	 */
	public void setTemSlave(SensorManager temSlave){
		
		this.temSlave=temSlave;
		
	}
	
	public Map<String,CacheParams> getParamMaps() {
		return paramMaps;
	}

	
	public void initParams() {
		
		
		paramMaps = new HashMap<String,CacheParams>();

		paramMaps.put(Constants.SENSOR_TYPE,new CacheParams( "传感器类型" ,ValueType.SHORT
				) {

			@Override
			public Object readValue() throws Exception {

				return sensor.readSensorType();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeSensorType((Short) obj);

			}

		});

		paramMaps.put(Constants.SENSOR_INFO,new CacheParams( "传感器信息" ,ValueType.STRING
				) {

			@Override
			public Object readValue() throws Exception {

				return sensor.readSensorInfo();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				System.out.println(obj.toString());
				sensor.writeSensorInfo((String) obj);
			}

		});

		paramMaps.put(Constants.SENSOR_MFD_DATE,new CacheParams("出厂日期" ,ValueType.DATE
				) {

			@Override
			public Object readValue() throws Exception {

				return sensor.readSensorMfdDate();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeSensorMfdDate((Date) obj);

			}

		});

		paramMaps.put(Constants.SENSOR_ENABLE_DATE,new CacheParams("启用日期" ,ValueType.DATE
				) {

			@Override
			public Object readValue() throws Exception {

				return sensor.readSensorEnableDate();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeSensorEnableDate((Date) obj);

			}

		});

		paramMaps.put(Constants.SENSOR_AGEOUT_DATE,new CacheParams( "过期日期" ,ValueType.DATE
				) {

			@Override
			public Object readValue() throws Exception {

				return sensor.readSensorAgeOutDate();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeSensorAgeOutDate((Date) obj);

			}

		});

		paramMaps.put(Constants.SENSOR_LASTCALL_DATE,new CacheParams("最近一次标定日期" ,ValueType.DATE
				) {

			@Override
			public Object readValue() throws Exception {

				return sensor.readSensorLastCallDate();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeSensorLastCallDate((Date) obj);

			}

		});

		paramMaps.put(Constants.SENSOR_NEXTCALL_DATE,new CacheParams("建议重新标定日期" ,ValueType.DATE
				) {

			@Override
			public Object readValue() throws Exception {

				return sensor.readSensorNextCallDate();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeSensorNextCallDate((Date) obj);

			}

		});

		paramMaps.put(Constants.SENSOR_CALL_INFO,new CacheParams("标定信息",ValueType.STRING 
				) {

			@Override
			public Object readValue() throws Exception {

				return sensor.readSensorCallInfo();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeSensorCallInfo((String) obj);

			}

		});

		paramMaps.put(Constants.SAMPLE_INTERVAL,new CacheParams("传感器采样间隔",ValueType.SHORT
				) {

			@Override
			public Object readValue() throws Exception {

				return sensor.readSampleInterval();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeSampleInterval((Short) obj);

			}

		});

		paramMaps.put(Constants.SAMPLE_COUNT,new CacheParams("传感器采样数量",ValueType.SHORT 
				) {

			@Override
			public Object readValue() throws Exception {

				return sensor.readSampleCount();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeSampleCount((Short) obj);

			}

		});

		paramMaps.put(Constants.DEMA_SWITCH,new CacheParams("标定开关",ValueType.BYTE ,false
				) {

			@Override
			public Object readValue() throws Exception {

				return sensor.readSensorDemaStatue();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeSensorDemaStatue((Byte) obj);

			}

		});

		paramMaps.put(Constants.SENSOR_STATE,new CacheParams("传感器状态" ,ValueType.BYTE
				) {

			@Override
			public Object readValue() throws Exception {

				return sensor.readSensorStatue();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeSensorStatue((Byte) obj);

			}

		});

		paramMaps.put(Constants.TEM_SWITCH,new CacheParams( "温度开关" ,ValueType.BYTE
				) {

			@Override
			public Object readValue() throws Exception {

				return sensor.readSensorTemSwitch();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeSensorTemSwitch((Byte) obj);

			}

		});

		paramMaps.put(Constants.SENSOR_ANALOG_SWITCH,new CacheParams( "模拟量补偿开关",ValueType.BYTE 
				) {

			@Override
			public Object readValue() throws Exception {

				return sensor.readSensorAnalogSwitch();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeSensorAnalogSwitch((Byte) obj);

			}

		});

		paramMaps.put(Constants.COMPENSATE_PARAMS,new CacheParams(paramsNameType.compensateParamsName,ValueType.FLOATS,paramsNameType.compensateParamsType,true
				) {

			@Override
			public Object readValue() throws Exception {

				return sensor.getSensorParams().readCompensateParams();

			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.getSensorParams().writeCompensateParams((float[]) obj);

			}

		});

		paramMaps.put(Constants.CORRECT_PARAMS,new CacheParams(paramsNameType.correctParamsName,ValueType.FLOATS
				) {

			@Override
			public Object readValue() throws Exception {

				return sensor.getSensorParams().readCorrectParams();

			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.getSensorParams().writeCorrectParams((float[]) obj);

			}
		});

		
		paramMaps.put(Constants.INPUT_PARAMS,new CacheParams(paramsNameType.inputParamsName,ValueType.FLOATS,false
				) {
			@Override
			public Object readValue() throws Exception {
				return sensor.getSensorParams().readInputParams();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.getSensorParams().writeInputParams((float[]) obj);
			}
		});
		
		paramMaps.put(Constants.DEMA_PARAMS,new CacheParams(paramsNameType.demaParamsName,ValueType.FLOATS,false) {
			@Override
			public Object readValue() throws Exception {
				return sensor.getSensorParams().readDemaParams();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.getSensorParams().writeDemaParams((float[]) obj);
			}
		});

		paramMaps.put(Constants.OTHER_PARAMS,new CacheParams(paramsNameType.otherParamsName,ValueType.FLOATS
				) {
			@Override
			public Object readValue() throws Exception {
				return sensor.getSensorParams().readOtherParams();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.getSensorParams().writeOtherParams((float[]) obj);
			}
		});

		paramMaps.put(Constants.AUTO_INTERVAL,new CacheParams( "自动采样间隔" ,ValueType.SHORT
				) {
			@Override
			public Object readValue() throws Exception {
				return sensor.readAutoInterval();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeAutoInterval((Short) obj);
			}
		});

		paramMaps.put(Constants.ANALOG_TYPE,new CacheParams("输出模拟信号方式" ,ValueType.BYTE
				) {
			@Override
			public Object readValue() throws Exception {
				return sensor.readAnalogType();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeAnalogType((Byte) obj);
			}
		});

		paramMaps.put(Constants.ANALOG_LEFT_VALUE,new CacheParams("输出模拟信号左检测值" ,ValueType.FLOAT
				) {
			@Override
			public Object readValue() throws Exception {
				return sensor.readAnalogOutputLeftValue();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeAnalogOutputLeftValue((Float) obj);
			}
		});

		paramMaps.put(Constants.ANALOG_RIGHT_VALUE,new CacheParams( "输出模拟信号右检测值" ,ValueType.FLOAT
				) {
			@Override
			public Object readValue() throws Exception {
				return sensor.readAnalogOutputRightValue();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeAnalogOutputRightValue((Float) obj);
			}
		});

		//String[] paramNames= new String[oi.units.length];
		
		paramMaps.put(Constants.SENSOR_RESULT_COUNT,new CacheParams(new String[]{"检测结果保存的位数"},ValueType.BYTES 
				) {
			@Override
			public Object readValue() throws Exception {
				return sensor.readResultCount();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeResultCount((byte[]) obj);
			}
		});

		paramMaps.put(Constants.SENSOR_RESULT_UNIT,new CacheParams( new String[]{"结果值单位"},ValueType.BYTES
				) {
			@Override
			public Object readValue() throws Exception {
				return sensor.readResultUnit();
			}

			@Override
			public void writeValue(Object obj) throws Exception {
				sensor.writeResultUnit((byte[]) obj);
			}
		});

	}

	public Sensor getSensor() {
		return sensor;
	}
	
	/**
	 * 连接的时候初始化部分属性这样就不卡了
	 * @param ids
	 *             
	 * @throws Exception
	 */
	@Override
	public Manager initPartParams() throws Exception{
		
		CacheParams tsmtSensor=null;
		for(String id:CONNECT_SENSOR_NEED){
			tsmtSensor= paramMaps.get(id);
			tsmtSensor.getValue();
		}
		
		scp.name=oi.name;
		scp.analogNum=(byte) oi.analogNum;
		scp.unit=oi.units;
		scp.analogUnit=oi.analogUnit;
		return this;
	}
	
	
	
	
	
	
	
	/**
	 * @return 模拟量值
	 * @throws Exception
	 */
	public float readAnalogResult() throws Exception {

		return sensor.readDbAnalogResult();
	}

	/**
	 * @return 结果值1
	 * @throws Exception
	 */
	public float readResult1() throws Exception {

		return sensor.readSensorResultR1();
	}

	/**
	 * @return 结果值2
	 * @throws Exception
	 */
	public float readResult2() throws Exception {

		return sensor.readSensorResultR2();
	}

	/**
	 * @return 结果值3
	 * @throws Exception
	 */
	public float readResult3() throws Exception {

		return sensor.readSensorResultR3();
	}

	/**
	 * @return 结果值4
	 * @throws Exception
	 */
	public float readResult4() throws Exception {

		return sensor.readSensorResultR4();
	}
	
	
	/**
	 * @author Administrator
	 *                   传感器的常用属性，在开始连接的时候已经把数据初始化了（用起来方便）
	 */
	public class SensorCommonParams{
		
		public String name;//名称
		
		public String []unit;//单位
		
		public byte analogNum;
		
		public String analogUnit;
		
		
		public String getName() {
			return name;
		}
		
		public String getResultId(){
			
			switch(resultProject){
			case 0:
				return Constants.NO_CACHE_RESULT1;
			case 1:
				return Constants.NO_CACHE_RESULT2;
			case 2:
				return Constants.NO_CACHE_RESULT3;
				
			case 3:
				return Constants.NO_CACHE_RESULT4;
			}
			return Constants.NO_CACHE_RESULT1;
			
			
		}

		public short getType() {
			try {
				return (Short) paramMaps.get(Constants.SENSOR_TYPE).getValue();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return -1;
		}

		public byte getState() {
			try {
				return (Byte) paramMaps.get(Constants.SENSOR_STATE).getValue();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return -1;
		}

		/**
		 * @return
		 *       获得当前结果单位
		 */
		public String getCurrentUnit() {
			return unit[resultProject];
		}

		/**
		 * @return
		 *      当前结果保留位数
		 */
		public byte getCurrentResultNum() {
			try {
				byte[] resultNum= (byte[]) paramMaps.get(Constants.SENSOR_RESULT_COUNT).getValue();
				return resultNum[resultProject];
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return -1;
		}

		/**
		 * @return
		 *       当前的模拟量保留位数
		 */
		public byte getAnalogNum() {
			return analogNum;
		}//模拟量的保留位数
		
		
		/**
		 * @return
		 *      获得传感器的右检测值
		 */
		public float getSensorRightValue(){
			
			try {
				return (Float) paramMaps.get(Constants.ANALOG_RIGHT_VALUE).getValue();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return -2;
		}
		
		
		/**
		 * @return
		 *     获得传感器的左检测值
		 */
		public float getSensorLeftValue(){
			
			try {
				return (Float) paramMaps.get(Constants.ANALOG_LEFT_VALUE).getValue();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			return -2;
		}
		
		
		/**
		 * @return
		 *       获得温度补偿开关
		 */
		public byte getTemSwitch(){
			
			try {
				return (Byte) paramMaps.get(Constants.TEM_SWITCH).getValue();
			} catch (Exception e) {
				return -2;
			}
		}

	}
	
	
}
