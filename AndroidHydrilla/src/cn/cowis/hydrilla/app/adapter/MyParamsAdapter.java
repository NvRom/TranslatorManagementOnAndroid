package cn.cowis.hydrilla.app.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.adapter.ChildParamsAdapter.NeedWriter;
import cn.cowis.hydrilla.app.entity.CacheParams;
import cn.cowis.hydrilla.util.ListViewUtil;

public class MyParamsAdapter extends BaseAdapter implements NeedWriter{

	Map<String, CacheParams> map = null;
	List<String> keys = new ArrayList<String>();

	Map<String,Object> needChangerMap=new HashMap<String,Object>();
	Context context;

	public MyParamsAdapter(Map<String, CacheParams> map, Context context) {
		this.map = map;
		this.context = context;
		Set<String> ids = map.keySet();
		for (String id : ids) {
			keys.add(id);
		}

	}

	@Override
	public int getCount() {

		return keys.size();
	}

	@Override
	public Object getItem(int position) {

		return map.get(keys.get(position));
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		
		
		ListView  clv=null;
		if(view ==null){
		
		view = LayoutInflater.from(context).inflate(
				R.layout.detail_params_listview, null);
		
		clv=(ListView) view.findViewById(R.id.detail_listview);
		
		view.setTag(clv);
		}else{
			
			clv=(ListView) view.getTag();
		}
		
		CacheParams cp = map.get(keys.get(position));
		
		ChildParamsAdapter cpa=new ChildParamsAdapter(cp,keys.get(position),this,context);
		
		clv.setAdapter(cpa);
		
		ListViewUtil.setListViewHeightBasedOnChildren(clv);

	   return view;

	}

	@Override
	public void addNeedValue(String id, Object obj) {
		
		needChangerMap.put(id, obj);
	}
	
	public  Map<String,Object> getNeedWriterMap(){
		
		return needChangerMap;
		
	}
	
	public void cleanNeedWriterMap(){
		
		needChangerMap.clear();
	}

	@Override
	public void removeNeedValue(String id) {
		
		if(needChangerMap.containsKey(id)){
			
			needChangerMap.remove(id);
		}
	}


}
