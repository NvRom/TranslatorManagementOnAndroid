package cn.cowis.hydrilla.app.activities;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.RoboGuice;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import cn.cowis.hydrilla.app.MyRoboApplication;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.entity.TransmitterManager;
import cn.cowis.hydrilla.app.parent.SuperActivity;
import cn.cowis.hydrilla.app.service.BackEvents.BackEventsType;
import cn.cowis.hydrilla.app.service.BackEvents.OnBackListener;
import cn.cowis.hydrilla.app.service.SensorClient;
import cn.cowis.hydrilla.view.CustomTextViewButton;

import com.google.inject.Inject;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.HexDump;

@EActivity(R.layout.usb_connection)
@RoboGuice
public class UsbActivity extends SuperActivity implements OnBackListener{

	
	 private ActionBar actionBar;
	
	private UsbManager mUsbManager;
	
	private UsbDevice selectedUsbDevice;// usb驱动
	
	@ViewById(R.id.usb_spinner_parity)
	Spinner spinnerParity;// 基偶校验位

	@ViewById(R.id.usb_spinner_stop)
	Spinner spinnerStop;// 停止位

	@ViewById(R.id.usb_spinner_boud)
	Spinner spinnerBaud;// 波特率

	@ViewById(R.id.usb_listview)
	ListView listDevice;// 设备列表

	@ViewById(R.id.custom_view_num_1)
	CustomTextViewButton slaveIdSlaveIdMin;

	@ViewById(R.id.custom_view_num_2)
	CustomTextViewButton slaveIdSlaveIdMax;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().addFlags(WindowManager
				.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		actionBar= getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		myApp.sensorClient.sensorService.backEvents.addBackListener(this);
		
	}


//	private final BroadcastReceiver mUsbPermissionActionReceiver = new BroadcastReceiver() {
//	        public void onReceive(Context context, Intent intent) {
//	            String action = intent.getAction();
//	            if (ACTION_USB_PERMISSION.equals(action)) {
//	                synchronized (this) {
//	                    UsbDevice usbDevice = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
//	                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//	                    	
//	                        if(null != usbDevice){
//	                        	List<UsbDevice> devicesList = initDeviceUsb();
//	                        	
//	                    		if(devicesList!=null){
//	                    			reflashDevice(devicesList);
//	                    		}
//	                       }
//	                    }
//	                    else {
//	                       
//	                        Toast.makeText(context, String.valueOf("Permission denied for device" + usbDevice), Toast.LENGTH_LONG).show();
//	                    }
//	                }
//	            }
//	        }
//	    };
//	
	
	
	@SuppressLint("InlinedApi")
	@AfterViews
	public void init() {
		
		setTitle("OTG连接");
		//打开USB的权限
//		 IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);                 //注册广播接受者
//	     registerReceiver(mUsbPermissionActionReceiver, filter);
	     
//	     mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
		
	     
		mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);             //获得USB管理器
		
		// 数据位的适配器
		ArrayAdapter<CharSequence> adapterParity = ArrayAdapter.createFromResource(this, R.array.usb_parity,
						android.R.layout.simple_spinner_item);
		adapterParity.setDropDownViewResource(R.layout.sherlock_spinner_item);

		spinnerParity.setAdapter(adapterParity);
		spinnerParity.setSelection(0);

		// 停止位的适配器
		ArrayAdapter<CharSequence> adapterStop = ArrayAdapter
				.createFromResource(this, R.array.usb_stop,
						android.R.layout.simple_spinner_item);
		adapterStop.setDropDownViewResource(R.layout.sherlock_spinner_item);

		spinnerStop.setAdapter(adapterStop);
		spinnerStop.setSelection(0);

//		// 奇偶的适配器
//		ArrayAdapter<CharSequence> adapterParity = ArrayAdapter
//				.createFromResource(this, R.array.usb_parity,
//						android.R.layout.simple_spinner_item);
//		adapterParity.setDropDownViewResource(R.layout.sherlock_spinner_item);
//		spinnerParity.setAdapter(adapterParity);
//		spinnerParity.setSelection(0);

		// 波特率的适配器
		ArrayAdapter<CharSequence> adapterBaud = ArrayAdapter
				.createFromResource(this, R.array.usb_baud,
						android.R.layout.simple_spinner_item);
		adapterBaud.setDropDownViewResource(R.layout.sherlock_spinner_item);
		spinnerBaud.setAdapter(adapterBaud);
		spinnerBaud.setSelection(2);

		// 设备额listView
		List<UsbDevice> devices = initDeviceUsb();
		
		if(devices!=null){
			reflashDevice(devices);
		}
	}

	/**
	 * 获得 设备 和驱动的集合
	 * @return
	 */
	@SuppressLint("NewApi")
	public List<UsbDevice> initDeviceUsb() {
		
		List<UsbDevice> usbDevicesList = new ArrayList<UsbDevice>();                    //    在此新建吗？

		for (final UsbDevice device : mUsbManager.getDeviceList().values()) { //getDeviceList();
																				//Returns a HashMap containing all USB devices currently attached.																	//HashMap<String, UsbDevice>																	//values();返回Collection<T>对象，这里Collection<UsbDevice>
			 if(mUsbManager.hasPermission(device)){
				 usbDevicesList.add(device);
			 }
			
			
		}

		return usbDevicesList;
	}
	
	
	/**
	 * 刷新 设备列表
	 */
	public void reflashDevice(final List<UsbDevice> deviceslist){
		
		
		ArrayAdapter<UsbDevice> listDeviceAdapter = new ArrayAdapter<UsbDevice>(this, android.R.layout.simple_list_item_checked, deviceslist) {

			@Override
			@SuppressLint("NewApi")
			public View getView(int position, View convertView, ViewGroup parent) {

				final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(android.R.layout.simple_list_item_checked, null);

				final UsbDevice device = deviceslist.get(position);
				final String title = String.format("Vendor %s Product %s",
								HexDump.toHexString((short) device.getVendorId()), HexDump.toHexString((short)device.getProductId())
								);
				CheckedTextView deviceText = (CheckedTextView) view.findViewById(android.R.id.text1);
				deviceText.setText(title);

				return view;
			}

		};
		

		listDevice.setAdapter(listDeviceAdapter);

		listDevice.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				
				selectedUsbDevice= (UsbDevice) listDevice.getItemAtPosition(position);
				
				((CheckedTextView)view).setChecked(true);
				
			}

		});
		
		
	}
	
	

	/**
	 * t返回
	 * @param view
	 */
	@Click(R.id.back)
	public void exit(View view){
		finish();
	}
	
	
	@Override
	public void onDestroy(){
		//解绑服务
		
//		mUsbPermissionActionReceiver.clearAbortBroadcast();
//		mPermissionIntent.cancel();
		
		myApp.sensorClient.sensorService.backEvents.removeBackListener(this);
		super.onDestroy();
	}
	
	
	/**
	 * 连接按钮
	 */
	@Click(R.id.usb_connect_button)
	public void connect() {
		try {
			int baudRate = Integer.parseInt(spinnerBaud.getSelectedItem()
					.toString());
			int parityBits = spinnerParity.getSelectedItemPosition();
			int stopBits = Integer.parseInt(spinnerStop.getSelectedItem()
					.toString());
			
			
			
			if(selectedUsbDevice==null){
				Toast.makeText(this, "请选择设备", Toast.LENGTH_SHORT).show();
				return;
			}
			List<UsbSerialDriver> usbSerialDrivers = UsbSerialProber.probeSingleDevice(mUsbManager, selectedUsbDevice);
			
			if(usbSerialDrivers.size()==0){
				Toast.makeText(this, "没有此USB驱动",Toast.LENGTH_SHORT).show();
				return;
			}
			
			usbSerialDrivers.get(0).setParameters(baudRate, 8, stopBits, parityBits);
			
			int slaveIdMin = Integer.parseInt(slaveIdSlaveIdMin.getCurrentValue().toString());
			int slaveIdMax = Integer.parseInt(slaveIdSlaveIdMax.getCurrentValue().toString());
			
			//绑定服务 并 开始连接
			
			myApp.sensorClient.sensorService.connectTransmitter(usbSerialDrivers.get(0), slaveIdMin, slaveIdMax, this);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:finish();
			//Toast.makeText(this, "home", 1).show(); 
//			Intent	intent = new Intent(Intent.ACTION_MAIN); 
//			intent.addCategory(Intent.CATEGORY_HOME);
//			startActivity(intent);
			break;
		}
		return true;
		}

	
	@SuppressWarnings("unchecked")
	@Override
	public void onDroneEvent(BackEventsType event, Object object) {
		switch(event){
		case CONNECTED:
		
			myApp.setTransmitterManagers((List<TransmitterManager>) object);
			myApp.setCurrectMethod(0);
			Intent intent=new Intent(this,AfterConnectedActivity_.class);
			startActivity(intent);
		}
		
	}
	
	

}
