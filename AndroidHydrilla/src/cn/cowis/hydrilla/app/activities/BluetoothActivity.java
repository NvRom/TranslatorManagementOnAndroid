package cn.cowis.hydrilla.app.activities;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.RoboGuice;
import org.androidannotations.annotations.ViewById;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import cn.cowis.hydrilla.app.MyRoboApplication;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.adapter.MyBluetoothAdapter;
import cn.cowis.hydrilla.app.entity.TransmitterManager;
import cn.cowis.hydrilla.app.service.BackEvents.BackEventsType;
import cn.cowis.hydrilla.app.service.BackEvents.OnBackListener;
import cn.cowis.hydrilla.app.service.SensorClient;
import cn.cowis.hydrilla.util.ListViewUtil;
import cn.cowis.hydrilla.view.CustomTextViewButton;

import com.actionbarsherlock.app.SherlockActivity;

@EActivity(R.layout.bluetooth_connection)
@RoboGuice
public class BluetoothActivity extends SherlockActivity implements OnBackListener{

	public static final int REQUEST_ENABLE_BT = 111;
	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter
			.getDefaultAdapter();
	
	private BluetoothDevice selectedDevice = null;// 选择的蓝牙设备
	
	 ProgressBar progressBar=null;
	
	 MyRoboApplication myApp=null;
	//已配对设备
	@ViewById(R.id.bond_listview)
	ListView bondListView;// 已配对的设备
	private BaseAdapter bondAdapter;
	private List<BluetoothDevice> bondBluetoothDevices = new ArrayList<BluetoothDevice>();// 已配对蓝牙
					
	// 查找的设备
	@ViewById(R.id.availableDeviceListView)
	ListView availableListview;// 可用设备
	private List<BluetoothDevice> availableBluetoothDevices = new ArrayList<BluetoothDevice>();// 可用蓝牙																								// 设
	private BaseAdapter availableAdapter;

	@ViewById(R.id.custom_view_num_1)
	CustomTextViewButton slaveIdSlaveIdMin;

	@ViewById(R.id.custom_view_num_2)
	CustomTextViewButton slaveIdSlaveIdMax;


	@Override
	public void onResume() {
		super.onResume();

		
		if(availableBluetoothDevices.size()>0){
			return;
		}
		
		if (bluetoothAdapter!=null&&bluetoothAdapter.isEnabled()) {
			
			initDevice();
			findDevice();
			
		} else {
			
			startActivityForResult(new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
		}
	}
	
	
	// 蓝牙广播接收者
	private BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				String action = intent.getAction();
				if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) { // 搜索完毕
					progressBar.setVisibility(View.INVISIBLE);

				} else if (BluetoothDevice.ACTION_FOUND.equals(action)) { // 发现蓝牙设备

					BluetoothDevice bluetoothDevice = intent
							.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					
					
					if(hasDevice(bondBluetoothDevices,bluetoothDevice)==null&&hasDevice(availableBluetoothDevices,bluetoothDevice)==null){//如果找到是新设备
						availableBluetoothDevices.add(bluetoothDevice);
						availableAdapter.notifyDataSetChanged();
						ListViewUtil.setListViewHeightBasedOnChildren(availableListview);
					}
					
				} else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) { // 配对状态改变了
					
					
					BluetoothDevice bluetoothDevice = intent
							.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//
					if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDED) {//原来是未配对设备现在是配对设备
						
						BluetoothDevice aDevice= hasDevice(availableBluetoothDevices,bluetoothDevice);
						
						if(aDevice!=null){
							availableBluetoothDevices.remove(aDevice);//这里面减少
							availableAdapter.notifyDataSetChanged();
							
							bondBluetoothDevices.add(aDevice);
							bondAdapter.notifyDataSetChanged();//这里面加一个
						}
					}
			
				} 
			}

		};
		
		
		
		/**
		 * 查找设备 
		 */
		private void findDevice(){
			if (!bluetoothAdapter.isDiscovering()) {
			   progressBar.setVisibility(View.VISIBLE);
			   availableBluetoothDevices.clear();
			
			   bluetoothAdapter.startDiscovery();
			}
		}
		
		private void initDevice(){
			
			Set<BluetoothDevice> bondDevices=bluetoothAdapter.getBondedDevices();
			
			bondBluetoothDevices.clear();
			
			for(BluetoothDevice device:bondDevices){
				bondBluetoothDevices.add(device);
			}
			
			//已经配对的设备
			bondAdapter=new MyBluetoothAdapter(BluetoothActivity.this,bondBluetoothDevices);
			bondListView.setAdapter(bondAdapter);
			
			ListViewUtil.setListViewHeightBasedOnChildren(bondListView);
			
			
			
			bondListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					selectedDevice = bondBluetoothDevices.get(position);
					
					
					for(int i=0;i<bondBluetoothDevices.size();i++){
						
						CheckedTextView ctv= (CheckedTextView) parent.getChildAt(i).findViewById(R.id.checked_textView);
						
						ctv.setChecked(false);
					}

					CheckedTextView ctv = (CheckedTextView) (view
							.findViewById(R.id.checked_textView));
					ctv.setChecked(true);

				}

			});

			//未配对的设备
			availableAdapter=new MyBluetoothAdapter(BluetoothActivity.this,availableBluetoothDevices);
			availableListview.setAdapter(availableAdapter);
			
			ListViewUtil.setListViewHeightBasedOnChildren(availableListview);
		}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		 View view= LayoutInflater.from(this).inflate(R.layout.bluetooth_title,null);
		 getSupportActionBar().setCustomView(view);
		 getSupportActionBar().setDisplayShowCustomEnabled(false);
		 getSupportActionBar().setDisplayShowHomeEnabled(false);
	     getSupportActionBar().setDisplayShowCustomEnabled(true);
	     
	     
	      myApp= (MyRoboApplication)getApplicationContext();
	      
	      myApp.sensorClient.sensorService.backEvents.addBackListener(this);
	     
	     view.findViewById(R.id.button_search).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				if (!bluetoothAdapter.isDiscovering()) {
					
					progressBar.setVisibility(View.VISIBLE);
					bluetoothAdapter.startDiscovery();// 这个方法就是查找周围的设备,如果周围有设备那么通过蓝牙广播接收
				}
			}
	    	 
	     });
	     
	    progressBar=  (ProgressBar) view.findViewById(R.id.progress_bar);
		
//	    setContentView(R.layout.iprogress);// 声明使用自定义标题
		// 动态建立一个蓝牙广播接收者
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
		intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
		intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
		// 注册一个广播
		this.registerReceiver(bluetoothReceiver, intentFilter);

		// 判断是否有蓝牙或者蓝牙是否打开
		if (bluetoothAdapter == null) {
			Toast.makeText(this, "不支持蓝牙", Toast.LENGTH_LONG).show();
		
		}
		
	}

	/**
	 * 是否有相同的设备
	 * 
	 * @param bluetoothDevice
	 */
	private BluetoothDevice hasDevice(List<BluetoothDevice> list,
			BluetoothDevice bluetoothDevice) {

		for (BluetoothDevice device : list) {
			
			if (device.getAddress().equals(bluetoothDevice.getAddress())) {
				return device;
			}
		}

		return null;
	}

	
	/**
	 * 连接 某个设备的蓝牙
	 */
	@Click(R.id.bluetooth_connect_button)
	public void connect() {

		if (bluetoothAdapter.isDiscovering()) {
//			pb_title.setVisibility(View.INVISIBLE);
			bluetoothAdapter.cancelDiscovery();
		}
		if (selectedDevice == null) {
			Toast.makeText(this, "请选择设备", Toast.LENGTH_SHORT).show();
		} else {

			buildConnect();

		}
	}

	/**
	 * t返回
	 * 
	 * @param view
	 */
	@Click(R.id.back)
	public void exit(View view) {

		finish();
	}
	
	@Click(R.id.layout_title)
	public void back(View view){
		finish();
	}

	@Override
	public void onDestroy() {
		selectedDevice=null;
		//解绑服务
		//myApp.sensorClient.close();
		
		if (bluetoothAdapter != null) {
			bluetoothAdapter.cancelDiscovery();
		}

		this.unregisterReceiver(bluetoothReceiver);
		
		myApp.sensorClient.sensorService.backEvents.removeBackListener(this);
		super.onDestroy();
	}

	
	/**
	 * 建立连接
	 */
	public void buildConnect() {

		try {
			BluetoothSocket bluetoothSocket = selectedDevice
					.createRfcommSocketToServiceRecord(UUID
							.fromString("00001101-0000-1000-8000-00805F9B34FB"));

			int slaveIdMin = Integer.parseInt(slaveIdSlaveIdMin.getCurrentValue()
					.toString());
			int slaveIdMax = Integer.parseInt(slaveIdSlaveIdMax.getCurrentValue());
			
			//绑定服务 并 开始连接
			myApp.sensorClient.sensorService.connectTransmitter(bluetoothSocket, slaveIdMin, slaveIdMax, this);
//			connection
//					.bluetoothConnect(bluetoothSocket, slaveIdMin, slaveIdMax);


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onDroneEvent(BackEventsType event, Object object) {
		switch(event){
		case CONNECTED:
			
			myApp.setTransmitterManagers((List<TransmitterManager>) object);
			myApp.setCurrectMethod(1);
			Intent intent=new Intent(this,AfterConnectedActivity_.class);
			startActivity(intent);
		}
		
	}

	
	


}
