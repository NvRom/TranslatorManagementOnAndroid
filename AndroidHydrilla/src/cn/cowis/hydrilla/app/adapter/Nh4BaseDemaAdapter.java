package cn.cowis.hydrilla.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.connected.activities.DemaNH4BasicDemaActivity2.Nh4BaseEntity;

public class Nh4BaseDemaAdapter extends BaseAdapter {

	Context context;
	
	List<Nh4BaseEntity> list;
	
	int num;
	
	public Nh4BaseDemaAdapter(Context context,
			List<Nh4BaseEntity> list) {

		this.context = context;
		this.list = list;

	}
	//@+id/
	
	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		
		MyTag tag=null;
		
		if(view==null){
			
			tag=new MyTag();
			
			view=LayoutInflater.from(context).inflate(
					R.layout.dema_nh4_basic_dema_2_listview, null);
			
			tag.viewNum=(TextView) view.findViewById(R.id.nh4_base_num);
			
			tag.viewTem=(TextView) view.findViewById(R.id.nh4_base_tem);
			
			tag.viewAnalog=(TextView) view.findViewById(R.id.nh4_base_analog);
			
			view.setTag(tag);
		}else{
			tag=(MyTag) view.getTag();
		}
		num++;
		tag.viewNum.setText(String.valueOf(num));
		
		Nh4BaseEntity nbe= list.get(arg0);
		tag.viewTem.setText(String.valueOf(nbe.temValue));
		tag.viewAnalog.setText(String.valueOf(nbe.analogValue));
		
		return view;
	}
	
	
	
	
	public class MyTag{
		
		TextView viewNum;
		
		TextView viewTem;
		
		TextView viewAnalog;
		
	}

}
