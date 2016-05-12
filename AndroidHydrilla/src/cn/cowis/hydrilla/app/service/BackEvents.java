package cn.cowis.hydrilla.app.service;

import java.util.ArrayList;
import java.util.List;

public class BackEvents{

	public interface OnBackListener {
		public void onDroneEvent(BackEventsType event,Object object);
	}
	
	public enum BackEventsType {
		CONNECTED,CACHE_READ_VALUE,CACHE_WRITE_VALUE,NO_CACHE_VALUE,CACHE_READ_ALL,CACHE_WRITE_ALL,NO_CACHE_VALUE_ONE
	}


	private List<OnBackListener> droneListeners = new ArrayList<OnBackListener>();

	public void addBackListener(OnBackListener listener) {
		if (listener != null & !droneListeners.contains(listener))
			droneListeners.add(listener);
	}

	public void removeBackListener(OnBackListener listener) {
		if (listener != null && droneListeners.contains(listener))
			droneListeners.remove(listener);
	}

	public void notifyBackEvent(BackEventsType event,Object object) {
		
	
       for(int i=0;i<droneListeners.size();i++){
			
    	   System.out.println( droneListeners.get(i).getClass());
    	   droneListeners.get(i).onDroneEvent(event,object);
			
		}
		
	}
}
