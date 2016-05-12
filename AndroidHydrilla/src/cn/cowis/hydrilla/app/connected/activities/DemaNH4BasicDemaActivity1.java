package cn.cowis.hydrilla.app.connected.activities;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.parent.SuperActivity;
import cn.cowis.hydrilla.view.CustomLinearLayoutHandInput;
import cn.cowis.hydrilla.view.CustomTextViewButton;


@EActivity(R.layout.dema_nh4_basic_dema_1)
public class DemaNH4BasicDemaActivity1 extends SuperActivity{
	
	
	@ViewById(R.id.nh4_base_dema_1_value_1)
	CustomLinearLayoutHandInput cllhiNh4;
	
	@ViewById(R.id.nh4_base_dema_1_value_2)
	CustomTextViewButton ctvbTimes;
	
	@ViewById(R.id.nh4_base_dema_1_value_3)
	CustomTextViewButton ctvbInterval;
	
//	@ViewById(R.id.dema_begin_button)
	
	
	@Click(R.id.dema_begin_button)
	public void clickBegin(View view){
		
		Intent intent=new Intent(this,DemaNH4BasicDemaActivity2.class);
		
		Bundle bundle = new Bundle();
		bundle.putInt("intervalTem", Integer.parseInt(ctvbInterval.getCurrentValue()));
		
		bundle.putInt("intervalRead", Integer.parseInt(ctvbTimes.getCurrentValue()));
		
		bundle.putInt("position", getIntent().getExtras().getInt("position"));
		
		try{
		bundle.putFloat("nh4Value", Float.parseFloat(cllhiNh4.getValue()));
		}catch(Exception e){
			
			Toast.makeText(this, "请输入正确的数据", Toast.LENGTH_SHORT).show();
			return;
		}
		intent.putExtras(bundle);
		startActivity(intent);
		
	}

}
