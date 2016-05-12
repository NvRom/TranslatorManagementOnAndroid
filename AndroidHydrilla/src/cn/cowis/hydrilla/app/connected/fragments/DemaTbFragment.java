package cn.cowis.hydrilla.app.connected.fragments;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import android.content.Intent;
import android.os.Bundle;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.connected.activities.DemaTbInputActivity1_;
import cn.cowis.hydrilla.app.connected.activities.DemaTbInputActivity2_;
import cn.cowis.hydrilla.app.parent.SuperFragment;


@EFragment(R.layout.select_tb_dema_method)

public class DemaTbFragment extends SuperFragment{

	
	@Click(R.id.two_layout)
	public void elec_correct_layout_1(){
		Intent intent = new Intent(getActivity(),DemaTbInputActivity2_.class);
		Bundle bundle = new Bundle();
		bundle.putInt("position", getArguments().getInt("position"));
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	
	@Click(R.id.one_layout)
	public void elec_correct_layout_2(){
		Intent intent = new Intent(getActivity(),DemaTbInputActivity1_.class);
		Bundle bundle = new Bundle();
		bundle.putInt("position", getArguments().getInt("position"));
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
}
