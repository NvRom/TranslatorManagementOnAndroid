package cn.cowis.hydrilla.app.parent;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import cn.cowis.hydrilla.app.connected.activities.DemaSensorResult_;
import cn.cowis.hydrilla.app.entity.Constants;
import cn.cowis.hydrilla.app.service.BackEvents.BackEventsType;
import cn.cowis.hydrilla.app.service.BackEvents.OnBackListener;
import cn.cowis.hydrilla.view.CustomLinearLayoutHandInput;
import cn.cowis.hydrilla.view.CustomLinearLayoutReadInput;

/**
 *                     双哥
 * @author Administrator
 *                      标定(输入参数)
 *
 */

public class SuperDemaInputActivity extends SuperSensorActivity implements OnBackListener{

	
	
	
	@Override
	public void onStart() {
		super.onStart();
		myApp.sensorClient.sensorService.backEvents.addBackListener(this);
	}
	
	@Override
	public void onStop(){
		super.onStop();
		myApp.sensorClient.sensorService.backEvents.removeBackListener(this);
	}

	@Override
	public void onDroneEvent(BackEventsType event, Object object) {
		
		switch(event){
		case CACHE_READ_VALUE:
			
			Map<String,Object> values= (Map<String, Object>) object;
			
			float[] demaValue= (float[]) values.get(Constants.DEMA_PARAMS);
			
			Intent intent=new Intent(this,DemaSensorResult_.class);
			
			Bundle bundle=new Bundle();
			bundle.putFloatArray("dema", (float[]) demaValue);
			bundle.putInt("position", position);
			intent.putExtras(bundle);
			startActivity(intent);
			
			
		case CACHE_WRITE_VALUE:
			
			myApp.sensorClient.sensorService.readCacheParams(this,new String[]{Constants.DEMA_PARAMS}, sensorManager);
		}
		
	}
	
	public void beginDema(float[] inputValues){
		
		 if(inputValues==null){
			 Toast.makeText(this, "输入有误", Toast.LENGTH_LONG).show();
			 return;
		 }
		 
		 Map<String,Object> map=new HashMap<String,Object>();
		 map.put(Constants.INPUT_PARAMS, inputValues);
		 
		 myApp.sensorClient.sensorService.writeCacheParams(this,map, sensorManager);//写标定参数
	}
	
	
	
	
}
