package cn.cowis.hydrilla.app.connected.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.entity.Constants;
import cn.cowis.hydrilla.app.parent.SuperSensorActivity;
import cn.cowis.hydrilla.view.CustomLinearLayoutReadInput;

@EActivity(R.layout.dema_sensor_result)
public class DemaSensorResult extends SuperSensorActivity{

	@ViewById(R.id.result_param)
	LinearLayout layout;

	@AfterViews
	public void afterView() {

		Bundle bundle= getIntent().getExtras();
		float[] demaValues=bundle.getFloatArray("dema");

		getActionBar().setTitle(sensorManager.scp.getName()+"传感器的标定结果");
		initLayout(demaValues);
	}

	public void initLayout(float[] demaValues) {

		String[] demaNames= (String[]) sensorManager.getParamNames(Constants.DEMA_PARAMS);
		
		
		for(int i=0;i<demaNames.length;i++){
		
		CustomLinearLayoutReadInput clrs=new CustomLinearLayoutReadInput(this);
		
		clrs.setMarkShow(View.GONE);
		
		clrs.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				 LayoutParams.WRAP_CONTENT));
		clrs.setBackgroundResource(R.drawable.bg_selector_nostroke_layout);
		
		clrs.setLabelName(demaNames[i]);
		clrs.setValue(String.valueOf(demaValues[i]));
		
		layout.addView(clrs);
		

		if (i != demaNames.length - 1) {// 如果不是最后一个还要加一条线分开

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
