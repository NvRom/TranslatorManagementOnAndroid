package cn.cowis.hydrilla.app.activities;


import java.util.List;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.entity.TransmitterManager;
import cn.cowis.hydrilla.app.parent.SuperActivity;
import cn.cowis.hydrilla.app.service.BackEvents.BackEventsType;
import cn.cowis.hydrilla.app.service.BackEvents.OnBackListener;

/**
 * @author Administrator shuang ge
 * 
 *         启动软件时，进入的flash界面和选择界面
 */
@EActivity(R.layout.flash_main)
// 这个是启动的flash
public class MainActivity extends SuperActivity{
	
	
	private long mExitTime;
	static String flag = "flag";
	
	LinearLayout bluetooth_layout;
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		backThread();
	}
	


	@Background
	void backThread() {// flash停一秒
		showSelection();
	}
	// UI线程
	@UiThread(delay=2000)
	public void showSelection() {
		setTitleName(R.string.connect_method);
		getSupportActionBar().show();
		this.setContentView(R.layout.selection_method);

		//bluetooth_layout= (LinearLayout)findViewById(R.id.bluetooth_layout);
		
	}
 
	@SuppressLint("NewApi")
	@Click(R.id.bluetooth_layout)
	public void selectMethod_bluetooth(View v) {// 按钮事件 选择某个连接方式
		
		//bluetooth_layout.setBackgroundResource(R.drawable.bg_shape_l);
		Intent intent=null;
		
		intent = new Intent(this,BluetoothActivity_.class); // 激活的是哪个activity分别是
		
		startActivity(intent);//激活此activity
	
	}
	
	@Click(R.id.Usb_layout)
	public void selectMethod_usb(View v) {
		// 按钮事件 选择某个连接方式
		Intent intent=null;
		
		intent = new Intent(this,UsbActivity_.class); // 激活的是哪个activity分别是
		
		startActivity(intent);//激活此activity
	}

	@Click(R.id.button1)
	public void onClick(View v) {
		System.exit(0);
	}
	
	
	  public boolean onKeyDown(int keyCode, KeyEvent event) {
	        
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				
				System.exit(0);
				return true;
		   }
			return false;
		}
	
	
}
