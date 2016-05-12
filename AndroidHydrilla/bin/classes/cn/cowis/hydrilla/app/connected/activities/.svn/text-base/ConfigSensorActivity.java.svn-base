package cn.cowis.hydrilla.app.connected.activities;

import java.util.HashMap;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.RoboGuice;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import cn.cowis.hydrilla.app.Dialogs;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.entity.Constants;
import cn.cowis.hydrilla.app.parent.SuperSensorActivity;

import com.google.inject.Inject;


/**
 * @author Administrator
 *
 */
@EActivity(R.layout.config_param_2)
@RoboGuice
public class ConfigSensorActivity extends SuperSensorActivity{

	
	@ViewById(R.id.sensortype)
	TextView sensorName; 

	@ViewById(R.id.workingstate_Switcher)
	Switch statueSwitch;
	
	@ViewById(R.id.comp_tem_layout)
	LinearLayout compTemlayout;
	
	@ViewById(R.id.comp_tem_switcher)
	Switch compTemswitch;
	
	@ViewById(R.id.measuring_range_layout)
	LinearLayout rangeLayout;
	
	@ViewById(R.id.min_value)
	TextView minValue;   
	
	@ViewById(R.id.max_value)
	TextView maxValue;
	
	@ViewById(R.id.range_name)
	TextView rangeName;


	@Inject
	Dialogs dialog;

	Handler handler= new Handler() {//handler 操作返回的最大最小数据

		public void handleMessage(Message msg) {
			
			minValue.setText(msg.getData().getString("min"));
			maxValue.setText(msg.getData().getString("max"));

		}
	};
	
	@Override
	protected void onCreate(Bundle arg0) {
		
		super.onCreate(arg0);
		
	}

	
	@AfterViews
	public void init() {
		setTitle(scp.getName()+"传感器的设定");
		
		if(temSensorManager!=null){//温度有温度补偿
			
			compTemlayout.setVisibility(View.VISIBLE);
			
			if(scp.getTemSwitch()==1)
				compTemswitch.setChecked(true);
			else  compTemswitch.setChecked(false);
			
		}
		
		sensorName.setText(scp.getName()+"类型");
		
		if(scp.getState()==1){//工作状态
			
			statueSwitch.setChecked(true);
			rangeLayout.setClickable(true);//可以设置上下线
			rangeLayout.setAlpha(1);
			
		}else{
			statueSwitch.setChecked(false);
			rangeLayout.setClickable(false);//不可以上下线
			rangeLayout.setAlpha(0.5f);
		}
		
		//=================设置单位
		 String unit= scp.unit[0];
		 if(!"".equals(unit)) rangeName.setText(" /"+unit);
		 
		 //==============设置范围
		 minValue.setText(String.valueOf(scp.getSensorLeftValue()));
		 maxValue.setText(String.valueOf(scp.getSensorRightValue()));
		
		
	}//end of init
	
	/**
	 * 工作状态开关
	 */
	@CheckedChange (R.id.workingstate_Switcher)
	public void sensorStatue(CompoundButton buttonView, boolean isChecked) {
		
				if (isChecked) {
					rangeLayout.setClickable(true);
					rangeLayout.setAlpha(1);

				} else {
					rangeLayout.setClickable(false);//测量范围变灰色
					rangeLayout.setAlpha(0.5f);
				}
	}

	/**
	 * 输入测量范围值
	 * @param view
	 */
	@Click(R.id.measuring_range_layout)
	public void setRangeValue(View view){
		
		dialog.inputDialogMinMax("请输入电极测定上下限"+scp.unit[0],handler,scp.getSensorLeftValue(),scp.getSensorRightValue());
	}
	
	@Click(R.id.save)
	public void saveParams(){
		
		Map<String,Object> map=new HashMap<String,Object>(); 
		
		map.put(Constants.ANALOG_LEFT_VALUE, Float.parseFloat(minValue.getText().toString()));
		map.put(Constants.ANALOG_RIGHT_VALUE, Float.parseFloat(maxValue.getText().toString()));
		
		map.put(Constants.SENSOR_STATE, (byte)(statueSwitch.isChecked()?1:0));
		
		map.put(Constants.TEM_SWITCH, (byte)(compTemswitch.isChecked()?1:0));
		
		myApp.sensorClient.sensorService.writeCacheParams(this, map, sensorManager);
		
	}
		
	
}
