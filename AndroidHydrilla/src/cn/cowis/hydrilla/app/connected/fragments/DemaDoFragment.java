package cn.cowis.hydrilla.app.connected.fragments;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import android.content.Intent;
import android.os.Bundle;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.connected.activities.DemaDoCorrectInputActivity_;
import cn.cowis.hydrilla.app.connected.activities.DemaDoInputActivity_;
import cn.cowis.hydrilla.app.parent.SuperFragment;

@EFragment(R.layout.select_dema_correct)
public class DemaDoFragment extends SuperFragment{
	

	@Click(R.id.elec_correct_layout_1)
	public void elec_correct_layout_1(){
		Intent intent = new Intent(getActivity(),DemaDoInputActivity_.class);
		Bundle bundle = new Bundle();
		bundle.putInt("position", getArguments().getInt("position"));
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	
	@Click(R.id.elec_correct_layout_2)
	public void elec_correct_layout_2(){
		Intent intent = new Intent(getActivity(),DemaDoCorrectInputActivity_.class);
		Bundle bundle = new Bundle();
		bundle.putInt("position", getArguments().getInt("position"));
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
