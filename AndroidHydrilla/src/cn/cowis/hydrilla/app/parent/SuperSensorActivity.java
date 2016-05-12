package cn.cowis.hydrilla.app.parent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.cowis.hydrilla.app.MyRoboApplication;
import cn.cowis.hydrilla.app.entity.Constants;
import cn.cowis.hydrilla.app.entity.SensorManager;
import cn.cowis.hydrilla.app.entity.SensorManager.SensorCommonParams;
import cn.cowis.hydrilla.view.CustomLinearLayoutHandInput;
import cn.cowis.hydrilla.view.CustomLinearLayoutReadInput;

/**
 * @author 用到传感器的activity
 */
public class SuperSensorActivity extends SuperActivity {

	public int position = 0;
	public SensorManager sensorManager = null;

	public SensorManager temSensorManager;
	
	public SensorCommonParams scp;
	
	public Bundle bundle=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		
		bundle= getIntent().getExtras();
		position = bundle.getInt("position");

		//当前传感器
		sensorManager = myApp.getSensorManager(position);
		
		scp=sensorManager.scp;
		
		//温度传感器
		temSensorManager = sensorManager.temSlave;

		if (temSensorManager != null) {

			byte state = 0;
			try {
				state = (Byte) temSensorManager
						.getParamValue(Constants.SENSOR_STATE);
			} catch (Exception e) {
				
			}

			if (state == 0) {//不正常了啊
				temSensorManager = null;
			} 

		}
		
		

	}
	
	/**
	 * 建立一个handle
	 * @param view
	 * @return
	 */
	public Handler createHandler(final CustomLinearLayoutReadInput view) {

		return new Handler() {

			public void handleMessage(Message msg) {
				
				view.setValue(msg.getData().getString("back"));
				
			}
		};

	}
	
	
	/**
	 *  建立一个handle
	 * @param view
	 * @return
	 */
	public Handler createHandler(final CustomLinearLayoutHandInput view) {

		return new Handler() {

			public void handleMessage(Message msg) {
				
				view.setValue(msg.getData().getString("back"));
				
			}
		};

	}
	

}
