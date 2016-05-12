package cn.cowis.hydrilla.app.adapter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.activities.BluetoothActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 蓝牙的适配器
 * 
 * @author Administrator
 * 
 */
public class MyBluetoothAdapter extends BaseAdapter {

	Context context = null;

	List<BluetoothDevice> arrayList = null;

	// boolean isBond=true;

	/**
	 * @param context
	 * @param arrayList
	 * @param isBond
	 *            是已经配对还是未配对的
	 */
	public MyBluetoothAdapter(Context context,
			List<BluetoothDevice> arrayList) {

		this.context = context;
		this.arrayList = arrayList;
		// this.isBond=isBond;

	}
	

	private View getShowView(final BluetoothDevice device) {

		View view = null;
		boolean isBond = device.getBondState() == BluetoothDevice.BOND_BONDED;
		if (isBond) {// 如果是已经绑定的设备

			view = LayoutInflater.from(context).inflate(
					R.layout.bluetooth_bond_listview, null);
			
			TextView viewName= (TextView) view.findViewById(R.id.b_device_name);
			viewName.setText(device.getName());
			
			TextView viewMac =(TextView) view.findViewById(R.id.b_device_mac);
			viewMac.setText(device.getAddress());
			
		} else {//未绑定的设备
			view = LayoutInflater.from(context).inflate(
					R.layout.bluetooth_available_listview, null);
			
			TextView viewName= (TextView) view.findViewById(R.id.a_device_name);
			viewName.setText(device.getName());
			
			TextView viewMac =(TextView) view.findViewById(R.id.a_device_mac);
			viewMac.setText(device.getAddress());

			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					System.out.println("anle o ?");
					try {
						Method createBondMethod = BluetoothDevice.class
								.getMethod("createBond");
						createBondMethod.invoke(device);

					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
				}

			});
		}

		return view;
	}

	@Override
	public int getCount() {

		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {

		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		return getShowView(arrayList.get(position));
		 
	}

}
