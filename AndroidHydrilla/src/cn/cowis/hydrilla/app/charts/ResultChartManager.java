package cn.cowis.hydrilla.app.charts;

import java.util.Timer;
import java.util.TimerTask;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;
import cn.cowis.hydrilla.app.charts.SuperChartManager.XYData;
import cn.cowis.hydrilla.app.entity.Constants;
import cn.cowis.hydrilla.app.entity.SensorManager;
import cn.cowis.hydrilla.app.service.BackEvents.BackEventsType;
import cn.cowis.hydrilla.app.service.BackEvents.OnBackListener;
import cn.cowis.hydrilla.app.service.SensorService.ReadNoCacheParamCycle;
import cn.cowis.hydrilla.util.ToolUtil;
import cn.cowis.modbus.exception.ErrorCodeException;

/**
 * @author Administrator
 * 
 *         读结果值的曲线
 */
public class ResultChartManager extends SuperChartManager{

	
	
	
	public ResultChartManager(Context context, SensorManager sensorManager
			) {
		super(context, sensorManager);
	}
	
	
	public void upDateChart(double x,double real,double avg){
		
		XYSeries realSeries = dataset.getSeriesAt(0);
		
		XYSeries avgSeries = dataset.getSeriesAt(1);
		
		updateSeries(realSeries, new XYData(x, real));
		
		updateSeries(avgSeries, new XYData(x, avg));
	}
	
	/**
	 * 建立渲染
	 * 
	 * @return
	 */
	
	@Override
	protected XYMultipleSeriesRenderer getRenderer(String title,String xTitle,String yTitle,int num) {

		XYMultipleSeriesRenderer renderer =super.getRenderer(title, xTitle,
				yTitle,num);

		renderer.setYAxisMin(sensorManager.scp.getSensorLeftValue());
		renderer.setYAxisMax(sensorManager.scp.getSensorRightValue());

		return renderer;
	}

	/**
	 * 建立chart
	 * 
	 * @return
	 */

}
