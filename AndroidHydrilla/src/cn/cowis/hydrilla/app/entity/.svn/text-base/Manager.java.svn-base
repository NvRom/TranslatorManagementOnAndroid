package cn.cowis.hydrilla.app.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 *     对变送器和传感器的属性操作
 * @author Administrator
 *
 */
public abstract class Manager implements Serializable{

	static final long serialVersionUID = 1L;

	protected Map<String,CacheParams> paramMaps =new HashMap<String,CacheParams>();
	
	
	
	public Map<String, CacheParams> getParamMaps() {
		return paramMaps;
	}
	
	/**
	 * @param id
	 *         属性的id
	 * @param value
	 *         属性设置的值
	 * @throws Exception 
	 */
	public void setParamValue(String id,Object value) throws Exception{
		
		
		CacheParams tsmtSensor= paramMaps.get(id);
		
		tsmtSensor.setValue(value);
	}
	
	
	/**
	 * @param id
	 *        获取属性的值
	 * @return
	 * @throws Exception
	 */
	public Object getParamValue(String id) throws Exception{
		
		CacheParams tsmtSensor= paramMaps.get(id);
		
		return tsmtSensor.getValue();
	}
	
	
	/**
	 * @param id
	 *         获得属性的名称
	 * @return
	 */
	public Object getParamNames(String id){
		
		CacheParams tsmtSensor= paramMaps.get(id);

		return tsmtSensor.getName();
	}
	
	/**
	 * @param id
	 *        获得这个参数
	 * @return
	 */
	public CacheParams getParams(String id){
		
		return  paramMaps.get(id);
	}

	
	/**
	 * @throws Exception
	 *         初始化 所有 配置
	 */
	public void initAllParams() throws Exception{
		
		for(String key:paramMaps.keySet()){
			
			paramMaps.get(key).getValue();
		}
	}
	
	public abstract Manager initPartParams() throws Exception;
	
	
}
