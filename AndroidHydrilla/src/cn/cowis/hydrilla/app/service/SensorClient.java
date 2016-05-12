package cn.cowis.hydrilla.app.service;

import cn.cowis.hydrilla.app.MyRoboApplication;
import cn.cowis.hydrilla.app.service.SensorService.MyBinder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * 与服务交互的东西
 * 
 * @author Administrator
 *         麻痹 写的劳资 想吐 有没有
 * 
 */
public class SensorClient{

	private Context context;
	public SensorService sensorService;

	
	public SensorClient(MyRoboApplication context) {
		this.context = context;
		
		
	}

	/**
	 * 开始绑定
	 * @return
	 */
	public SensorClient init() {
		context.bindService(new Intent(context, SensorService.class),
				mConnection, Context.BIND_AUTO_CREATE);

		return this;
	}

	/**
	 * 解绑
	 */
	public void close() {

		context.unbindService(mConnection);

	}

	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {

			sensorService = ((MyBinder) service).getService();
			
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {

		}
	};

}
