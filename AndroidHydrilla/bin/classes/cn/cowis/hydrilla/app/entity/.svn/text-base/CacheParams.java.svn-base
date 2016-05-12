package cn.cowis.hydrilla.app.entity;

import java.util.Arrays;

/**
 * 变送器中每一个属性(除了一些不可以缓存的)
 * 
 * @author Administrator
 * 
 */
public abstract class CacheParams {

	public Object names=null;

	private boolean flag=true;//判断其是否可以缓存(有些东西是缓存不住的 下面自动变)
	
	private Object value = null;
	
	private ParamType[] paramTypes=new ParamType[0];
	
	private ValueType type=null;

	public CacheParams(Object names,ValueType type) {

		this.names = names;
		this.type=type;
		
	}
	
	public Object getHasInitValue(){
		
		return value;
	}
	
	public CacheParams(Object names,ValueType type,boolean flag){
		this(names,type);
		this.flag=flag;
	}
	
	public CacheParams(Object names,ValueType type,ParamType[]paramTypes,boolean flag){
		this(names,type,flag);
		this.paramTypes=paramTypes;
	};
	
	public Object getName() {
		return names;
	}
	
	public ParamType[] getParamTypes() {
		return paramTypes;
	}
	
	public ValueType getValueType() {
		return type;
	}

	public Object getValue() throws Exception {

		if (value == null||!flag) {
			value = readValue();
		}
		return value;
	}
	
	/**
//	 * @return
//	 *        判断其值 是否为数组
//	 */
//	public boolean isValueArray(){
//		
//		if(value!=null&& value.getClass().isArray()){
//			
//			return true;
//		}
//		return false;
//		
//	}
//	


	public void setValue(Object obj) throws Exception {

		// 判断 写入的数据 与原来的数据 是否相等
		
		if(value!=null){
		
		if (obj.toString().equals(value.toString())&&flag)
			return;

		if (obj instanceof float[]) {
			
			if(Arrays.equals((float[])obj, (float[])value)&&flag)return;
		}
		if(obj instanceof byte[]){
			if(Arrays.equals((byte[])obj, (byte[])value)&&flag)return;
		}
		}
		writeValue(obj);
		value = obj;
	}

	public abstract Object readValue() throws Exception;

	public abstract void writeValue(Object obj) throws Exception;

	
	public enum ParamType{
		T,PH,EC,ORP,DO,TB,OTHER,P
	}
	
	public enum ValueType{
		BYTE,INT,SHORT,BYTES,FLOATS,FLOAT,STRING,DATE
	}
	
}
