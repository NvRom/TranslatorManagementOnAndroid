package cn.cowis.hydrilla.app.connected.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.adapter.Nh4BaseDemaAdapter;
import cn.cowis.hydrilla.app.entity.Constants;
import cn.cowis.hydrilla.app.parent.SuperSensorActivity;
import cn.cowis.hydrilla.app.service.BackEvents.BackEventsType;
import cn.cowis.hydrilla.app.service.BackEvents.OnBackListener;
import cn.cowis.hydrilla.util.ToolUtil;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

public class DemaNH4BasicDemaActivity2 extends SuperSensorActivity implements
		OnBackListener {

	ListView listView = null;

	List<Nh4BaseEntity> list = new ArrayList<Nh4BaseEntity>();

	Nh4BaseDemaAdapter nbda=null;
	
	int intervalRead, intervalTem;

	float nh4Value;

	Float currectValue = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dema_nh4_basic_dema_2);

		listView = (ListView) findViewById(R.id.list_view_params);

		nbda=new Nh4BaseDemaAdapter(this, list);
		listView.setAdapter(nbda);

		intervalTem = bundle.getInt("intervalTem");
		intervalRead = bundle.getInt("intervalRead");
		nh4Value = bundle.getFloat("nh4Value");
	}

	public class Nh4BaseEntity {

		public float temValue;

		public float analogValue;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getSupportMenuInflater().inflate(R.menu.nh4_basic_dema_2, menu);
		return true;
	}

	/**
	     * 
	     */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {

		case R.id.save:
			
			if(list.size()<2){
				Toast.makeText(this, "标定数据最少2组", Toast.LENGTH_SHORT).show();
				return true;
			}
			float [] values=new float[17];
			for(int j=0;j<values.length;j++){
				
				values[j]=ToolUtil.getNaN();
			}
			values[0]=nh4Value;
			
			int m=0;
			for(int i=0;i<list.size();i++){
				m++;
				values[m]=list.get(i).analogValue;
				m++;
				values[m]=list.get(i).temValue;
			}
			
			Map<String,Object> map= new HashMap<String,Object>();
			
			map.put(Constants.DEMA_PARAMS, values);
			
			myApp.sensorClient.sensorService.writeCacheParams(this,map , sensorManager);
			
			break;

		case R.id.begion:
			
			currectValue=null;
			list.clear();
			nbda.notifyDataSetChanged();

			setProgressBarIndeterminateVisibility(true);
			
			myApp.sensorClient.sensorService.startCycle(
					Constants.NO_CACHE_RESULT1, temSensorManager, intervalRead*1000,
					-1);

			break;

		case R.id.end:

			myApp.sensorClient.sensorService.destoryCycle();
			setProgressBarIndeterminateVisibility(false);
			

		}
		return true;
	}

	@Override
	public void onDroneEvent(BackEventsType event, Object object) {

		switch (event) {
		case NO_CACHE_VALUE:
			
			if(list.size()==8){
				
				myApp.sensorClient.sensorService.destoryCycle();
				setProgressBarIndeterminateVisibility(false);
				return;
			}
			
			float temValue;
			try {
				temValue = Float.parseFloat(object.toString());
			} catch (Exception e) {
				Toast.makeText(this, "请检查上下限!", Toast.LENGTH_SHORT).show();

				return;
			}
			if (currectValue == null) {
				currectValue = temValue;
				
				myApp.sensorClient.sensorService.startNoCycle(
						Constants.NO_CACHE_ANALOG, sensorManager);
			}
			if (Math.abs(currectValue - temValue) >= intervalTem) {

				currectValue = temValue;

				myApp.sensorClient.sensorService.startNoCycle(
						Constants.NO_CACHE_ANALOG, sensorManager);
			}

			break;

		case NO_CACHE_VALUE_ONE:

			float analogValue;
			try {
				analogValue = Float.parseFloat(object.toString());
			} catch (Exception e) {
				return;
			}
			Nh4BaseEntity nbe= new Nh4BaseEntity();
			nbe.analogValue=analogValue;
			nbe.temValue=currectValue;
			list.add(nbe);
			
			nbda.notifyDataSetChanged();
		}

	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		myApp.sensorClient.sensorService.destoryCycle();
		
	}
	
	@Override
	public void onStart(){
		myApp.sensorClient.sensorService.backEvents.addBackListener(this);
		super.onStart();
	}
	
	@Override
	public void onStop(){
		myApp.sensorClient.sensorService.backEvents.removeBackListener(this);
		super.onStop();
	}

}
