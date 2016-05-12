package cn.cowis.hydrilla.app.connected.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.adapter.MyParamsAdapter;
import cn.cowis.hydrilla.app.entity.Manager;
import cn.cowis.hydrilla.app.entity.SensorManager;
import cn.cowis.hydrilla.app.entity.TransmitterManager;
import cn.cowis.hydrilla.app.parent.SuperActivity;
import cn.cowis.hydrilla.app.service.BackEvents.BackEventsType;
import cn.cowis.hydrilla.app.service.BackEvents.OnBackListener;
import cn.cowis.hydrilla.util.ListViewUtil;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * @author Administrator 详细信息
 * 
 */
@EActivity(R.layout.detail_params)
public class DetailParamsActivity extends SuperActivity implements
		OnBackListener {

	@ViewById(R.id.param_layout)
	LinearLayout layout;

	@ViewById(R.id.tsmt_listview)
	ListView tsmtListView;
	MyParamsAdapter myAdapterTsmt;

	@ViewById(R.id.sensor_listview_0)
	ListView sensorListView0;
	MyParamsAdapter myAdapterSensor0;

	@ViewById(R.id.sensor_listview_1)
	ListView sensorListView1;
	MyParamsAdapter myAdapterSensor1;

	SensorManager[] sensorManagers = null;

	TransmitterManager tsmtManager = null;

	@AfterViews
	public void afterView() {

		layout.setVisibility(View.GONE);

		tsmtManager = myApp.currectTsmtManager;
		sensorManagers = tsmtManager.getAllSensorManagers();

		myApp.sensorClient.sensorService.backEvents.addBackListener(this);

		myApp.sensorClient.sensorService.initAllParams(this,
				myApp.currectTsmtManager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getSupportMenuInflater().inflate(R.menu.detail_params, menu);
		return true;
	}

	/**
	     * 
	     */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {

		case R.id.save_param:

			// myApp.sensorClient.sensorService.writeAllParams(context,
			// managers, maps)
			
			
			List<Manager> listManager = new ArrayList<Manager>();

			List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();

			Map<String,Object> tsmtMap= myAdapterTsmt.getNeedWriterMap();
			
			Map<String,Object> sensorMap0= myAdapterSensor0.getNeedWriterMap();
			
			Map<String,Object> sensorMap1= myAdapterSensor1.getNeedWriterMap();
			
			if(tsmtMap.size()>0){
				listManager.add(tsmtManager);
				listMaps.add(tsmtMap);
			}
			if(sensorMap0.size()>0){
				listManager.add(sensorManagers[0]);
				listMaps.add(sensorMap0);
			}
			if(sensorMap1.size()>0){
				listManager.add(sensorManagers[1]);
				listMaps.add(sensorMap1);
			}

			if(listMaps.size()>0)
			
			myApp.sensorClient.sensorService.writeAllParams(this, listManager, listMaps);
			
			else Toast.makeText(this, "未修改数据!", Toast.LENGTH_SHORT).show();
			break;

		}

		return true;
	}

	@Override
	public void onDroneEvent(BackEventsType event, Object object) {

		switch (event) {

		case CACHE_READ_ALL:// 读成功了

			myAdapterTsmt = new MyParamsAdapter(
					myApp.currectTsmtManager.getParamMaps(), this);
			tsmtListView.setAdapter(myAdapterTsmt);

			myAdapterSensor0 = new MyParamsAdapter(
					sensorManagers[0].getParamMaps(), this);
			sensorListView0.setAdapter(myAdapterSensor0);

			myAdapterSensor1 = new MyParamsAdapter(
					sensorManagers[1].getParamMaps(), this);
			sensorListView1.setAdapter(myAdapterSensor1);

			ListViewUtil.setListViewHeightBasedOnChildren(tsmtListView);
			ListViewUtil.setListViewHeightBasedOnChildren(sensorListView0);
			ListViewUtil.setListViewHeightBasedOnChildren(sensorListView1);
			layout.setVisibility(View.VISIBLE);
			break;
		case CACHE_WRITE_ALL:

			myAdapterTsmt.cleanNeedWriterMap();

			if (myAdapterSensor0 != null) {

				myAdapterSensor0.cleanNeedWriterMap();
			}

			if (myAdapterSensor1 != null) {

				myAdapterSensor1.cleanNeedWriterMap();
			}
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		myApp.sensorClient.sensorService.backEvents.removeBackListener(this);
	}

}
