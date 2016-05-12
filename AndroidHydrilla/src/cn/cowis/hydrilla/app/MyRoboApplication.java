package cn.cowis.hydrilla.app;

import java.util.List;

import roboguice.application.RoboApplication;
import cn.cowis.hydrilla.app.entity.SensorManager;
import cn.cowis.hydrilla.app.entity.TransmitterManager;
import cn.cowis.hydrilla.app.service.BackEvents.BackEventsType;
import cn.cowis.hydrilla.app.service.BackEvents.OnBackListener;
import cn.cowis.hydrilla.app.service.BackEvents;
import cn.cowis.hydrilla.app.service.SensorClient;

import com.google.inject.Module;

public class MyRoboApplication extends RoboApplication{
	
	private static String[] connectMethods=new String[]{"USB","BLUETOOTH"}; 
	private String currectConnectMethod=null;

	public SensorClient sensorClient=null;//这个可以与服务 init()绑定 于服务
	
	public List<TransmitterManager> listTsmt = null; // 总共变送器的集合

	public TransmitterManager currectTsmtManager = null;// 当前使用的变送器
	
	
	
	@Override
	protected void addApplicationModules(List<Module> modules) { 
		modules.add(new RoboModule());
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		sensorClient=new SensorClient(this);//绑定sensorClient
		
		sensorClient.init();//开始绑定service
	}
	
	public void setTransmitterManagers(List<TransmitterManager> listTsmt){
		
		this.listTsmt=listTsmt;
		
		currectTsmtManager=listTsmt.get(0);//默认当前的变送器
		
	}
	
	/**
	 * @param position
	 *        设置当前的变送器
	 */
	public void setCurrectTransmitterManager(int position){
		
		currectTsmtManager=listTsmt.get(position);
	}
	
	
	public void setCurrectMethod(int position){
		
		currectConnectMethod=connectMethods[position];
	}
	
	
	public SensorManager getSensorManager(int position){
		
		return currectTsmtManager.getAvalibleSensorManagers().get(position);
		
	}
	
	public boolean isBlueToothConnect(){
		
		if(connectMethods[1].equals(currectConnectMethod)){
			return true;
		}
		return false;
		
	}
	@Override
	public void onTerminate() {
	
		super.onTerminate();
		sensorClient.close();
	}
	
	

}
