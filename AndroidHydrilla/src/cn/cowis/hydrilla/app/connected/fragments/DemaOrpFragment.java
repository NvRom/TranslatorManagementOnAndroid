package cn.cowis.hydrilla.app.connected.fragments;

import java.util.HashMap;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ViewFlipper;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.charts.AnalogChartManager;
import cn.cowis.hydrilla.app.entity.Constants;
import cn.cowis.hydrilla.app.parent.SuperSensorFragment;
import cn.cowis.hydrilla.app.service.BackEvents.BackEventsType;
import cn.cowis.hydrilla.app.service.BackEvents.OnBackListener;
import cn.cowis.hydrilla.util.ToolUtil;

/**
 * ORP的标定界面
 * 
 * @author Administrator
 * 
 */
@EFragment(R.layout.dema_orp_chart)
public class DemaOrpFragment extends SuperSensorFragment implements OnBackListener {

	@ViewById(R.id.dema_orp_chart)
	ViewFlipper analogFlipper; // 模拟量的曲线图

	@ViewById(R.id.dema_orp_input_ag_value)
	RadioButton agSelected; // 选择水银
	
	
	@ViewById(R.id.dema_orp_input_hg_value)
	RadioButton hgSelected;// 选择汞

	private static final float[] ag = new float[] { (float) 204.8,
			(float) -0.684 };
	private static final float[] hg = new float[] { (float) 243.8,
			(float) -0.652 };

	private AnalogChartManager chartManager;
	
	int interval=2,h=0;;
	
	@AfterViews
	public void init(){
		

		// 建立模拟量曲线
		chartManager = new AnalogChartManager(getActivity(), sensorManager);
		
		
		analogFlipper.addView(chartManager.createChart("ORP","ORP曲线图","时间/s",new String[]{"orp模拟量"},1));// 添加曲线
		
		
//		chart.begion();
		
	}
	
//	@Override
//	public void onStart() {
//		super.onStart();
//		//myApp.sensorClient.sensorService.backEvents.addBackListener(this);
//		
//		//myApp.sensorClient.sensorService.readCacheParams(getActivity(),new String[]{Constants.DEMA_PARAMS}, sensorManager);
//		
//		
//	}
//	
//	@Override
//	public void onStop(){
//		super.onStop();
//		
//		
//		
//		//myApp.sensorClient.sensorService.backEvents.removeBackListener(this);
//	}
	
	

	@Click(R.id.sure_selected)
	public void sure() {
		
			Map<String,Object> map=new HashMap<String,Object>();
			if (agSelected.isChecked()) {
				
				map.put(Constants.DEMA_PARAMS, ag);
				myApp.sensorClient.sensorService.writeCacheParams(getActivity(),map, sensorManager);
				
			} else {
				
				map.put(Constants.DEMA_PARAMS, hg);
				myApp.sensorClient.sensorService.writeCacheParams(getActivity(),map, sensorManager);
				
				
			}

	}


	@Override
	public void onDroneEvent(BackEventsType event, Object object) {
		
		switch(event){
	
		case NO_CACHE_VALUE:
			
			if (object == null) {
				Toast.makeText(myApp, "通信有误!", Toast.LENGTH_SHORT).show();
				return;
			}else{
				
				double x=	h++*interval;
				double y= ToolUtil.roundValue((Float) object, sensorManager.scp.analogNum);
				
				chartManager.updateValues(x, y);
			}
			
		}
	}
	
	


	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
	
//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		super.onCreateOptionsMenu(menu, inflater);
//
//		inflater.inflate(R.menu.menu_parameters, menu);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		adapter.clearFocus();
//
//		switch (item.getItemId()) {
//		case R.id.menu_load_parameters:
//			refreshParameters();
//			break;
//
//		case R.id.menu_write_parameters:
//			writeModifiedParametersToDrone();
//			break;
//
//		case R.id.menu_open_parameters:
//			openParametersFromFile();
//			break;
//
//		case R.id.menu_save_parameters:
//			saveParametersToFile();
//			break;
//
//		default:
//			return super.onOptionsItemSelected(item);
//		}
//		return true;
//	}

	


}
