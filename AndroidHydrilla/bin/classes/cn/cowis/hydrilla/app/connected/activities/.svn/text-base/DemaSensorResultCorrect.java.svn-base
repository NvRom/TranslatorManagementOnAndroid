package cn.cowis.hydrilla.app.connected.activities;

import java.util.HashMap;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.entity.Constants;
import cn.cowis.hydrilla.app.parent.SuperSensorActivity;
import cn.cowis.hydrilla.util.ToolUtil;
import cn.cowis.hydrilla.view.CustomLinearLayoutReadInput;

@EActivity(R.layout.dema_sensor_correct_result)
public class DemaSensorResultCorrect extends SuperSensorActivity{

	@ViewById(R.id.result_param_correct)
	LinearLayout layout;
	
	String[] correctValues=null;
	
	@Click(R.id.dema_correct_button)
	public void click(View view){
		
		Map<String,Object> maps=new HashMap<String,Object>();
		
		maps.put(Constants.CORRECT_PARAMS, ToolUtil.stringsToFloats(correctValues));
		myApp.sensorClient.sensorService.writeCacheParams(this, maps, sensorManager);
	}

	@AfterViews
	public void afterView() {

		Bundle bundle= getIntent().getExtras();
		correctValues=bundle.getStringArray("correct");

		getActionBar().setTitle(sensorManager.scp.getName()+"的校正结果");
		initLayout(correctValues);
	}

	public void initLayout(String[] correctValues) {

		String[] correctNames= (String[]) sensorManager.getParamNames(Constants.CORRECT_PARAMS);
		
		
		for(int i=0;i<correctNames.length;i++){
		
		CustomLinearLayoutReadInput clrs=new CustomLinearLayoutReadInput(this);
		
		clrs.setMarkShow(View.GONE);
		
		clrs.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				 LayoutParams.WRAP_CONTENT));
		clrs.setBackgroundResource(R.drawable.bg_selector_nostroke_layout);
		
		clrs.setLabelName(correctNames[i]);
		clrs.setValue(String.valueOf(correctValues[i]));
		
		layout.addView(clrs);
		

		if (i != correctNames.length - 1) {// 如果不是最后一个还要加一条线分开

			TextView textView = new TextView(this);// 一条细线
			textView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, 2));
			textView.setBackgroundResource(R.color.litter_gray);
			layout.addView(textView);
			//
		}
		}
		
	}


}
