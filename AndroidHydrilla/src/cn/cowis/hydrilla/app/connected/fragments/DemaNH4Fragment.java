package cn.cowis.hydrilla.app.connected.fragments;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.connected.activities.DemaNH4BasicDemaActivity1_;
import cn.cowis.hydrilla.app.connected.activities.DemaNH4WorkChartActivity_;
import cn.cowis.hydrilla.app.parent.SuperFragment;
import cn.cowis.hydrilla.app.parent.SuperSensorFragment;


@EFragment(R.layout.select_nh4_dema_method)
public class DemaNH4Fragment extends SuperSensorFragment{
	
	
	@Click(R.id.dema_nh4_work_chart)
	public void dema_WorkChart(){
		Intent intent = new Intent(getActivity(),DemaNH4WorkChartActivity_.class);
		Bundle bundle = new Bundle();
		bundle.putInt("position", getArguments().getInt("position"));
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	@Click(R.id.dema_nh4_base_orientation)
	public void dema_BaseValueOrientation(){ 
		
		if(temSensorManager==null){
			Toast.makeText(getActivity(), "必须带有温度传感器!", Toast.LENGTH_SHORT).show();
		}
		
		Intent intent = new Intent(getActivity(),DemaNH4BasicDemaActivity1_.class);
		Bundle bundle = new Bundle();
		bundle.putInt("position", getArguments().getInt("position"));
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	@Click(R.id.dema_nh4_selective_coefficient)
	public void dema_SelectiveCoefficient(){
//		Intent intent = new Intent(getActivity(),DemaDoCorrectInputActivity_.class);
//		Bundle bundle = new Bundle();
//		bundle.putInt("key", getArguments().getInt("position"));
//		intent.putExtras(bundle);
//		startActivity(intent);
	}
}