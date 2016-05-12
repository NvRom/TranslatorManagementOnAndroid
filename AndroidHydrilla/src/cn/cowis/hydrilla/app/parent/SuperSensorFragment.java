package cn.cowis.hydrilla.app.parent;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import cn.cowis.hydrilla.app.Dialogs;
import cn.cowis.hydrilla.app.activities.AfterConnectedActivity;
import cn.cowis.hydrilla.app.entity.Constants;
import cn.cowis.hydrilla.app.entity.SensorManager;
import cn.cowis.hydrilla.app.entity.SensorManager.SensorCommonParams;
import cn.cowis.hydrilla.view.CustomLinearLayoutHandInput;
import cn.cowis.hydrilla.view.CustomLinearLayoutReadInput;

/**
 * 
 * @author Administrator
 *                   用到传感器了的fragment
 *
 */
public class SuperSensorFragment extends SuperFragment {
	
	public Dialogs dialog;

    public SensorManager sensorManager=null;//本身传感器
    
    public SensorManager temSensorManager=null;//温度传感器
	
	public int position=0;
	
	public SensorCommonParams scp=null;
	
	public AfterConnectedActivity afterActivity=null;
	
	@Override  
    public void onAttach(Activity activity) {  
        super.onAttach(activity);  
          
        try {  
        	afterActivity=(AfterConnectedActivity) activity;
        	
        	dialog=afterActivity.getDialog();
        	
        } catch (ClassCastException e) {  
            throw new ClassCastException(activity.toString()  
                    + " must implement OnHeadlineSelectedListener");  
        }  
        
        System.out.println("position"+position);
        position= getArguments().getInt("position");
        
       
        
        //当前传感器
        sensorManager = myApp.getSensorManager(position);
        scp=sensorManager.scp;
        
       
        //温度传感器
		temSensorManager = sensorManager.temSlave;

		if (temSensorManager != null) {//判断是否有温度传感器

			byte state = 0;
			try {
				state = (Byte) temSensorManager
						.getParamValue(Constants.SENSOR_STATE);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (state == 0) {//不正常了啊
				temSensorManager = null;
			} 

		}
    } 
	
	
	
	
	
}
