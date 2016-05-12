package cn.cowis.hydrilla.app.charts;

import java.util.Timer;
import java.util.TimerTask;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import cn.cowis.hydrilla.app.charts.SuperChartManager.XYData;
import cn.cowis.hydrilla.app.entity.Constants;
import cn.cowis.hydrilla.app.entity.SensorManager;
import cn.cowis.hydrilla.app.service.BackEvents.BackEventsType;
import cn.cowis.hydrilla.app.service.BackEvents.OnBackListener;
import cn.cowis.hydrilla.app.service.SensorService.ReadNoCacheParamCycle;
import cn.cowis.hydrilla.util.ToolUtil;

/**
 * @author Administrator
 *
 *读模拟量的曲线
 */
public class AnalogChartManager extends SuperChartManager{
	
//	ReadNoCacheParamCycle rncpc=null;
	
	public AnalogChartManager(Context context,SensorManager sensorManager){
		super(context,sensorManager);
	}

	
	public void updateValues(double x,double y){
		
		XYSeries realSeries= dataset.getSeriesAt(0);
		
		updateSeries(realSeries,new XYData(x, y));
	}


	

}
