package cn.cowis.hydrilla.app.charts;

import java.io.Serializable;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import cn.cowis.hydrilla.app.MyRoboApplication;
import cn.cowis.hydrilla.app.entity.SensorManager;
import android.R.menu;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Handler;
import android.view.View;


public class SuperChartManager {
	/*默认3秒 时间间隔*/
//	protected int interval = 2; // 间隔
//	
//	protected int num = -1; // 显示点个数(如果是-1 那么不停止 ,无限个点)

	protected Context context;

	protected XYMultipleSeriesDataset dataset;
	
	protected SensorManager sensorManager;
	
	
	protected View chart=null;
	
	

	static int[] colors = new int[]{Color.RED,Color.YELLOW};//点的颜色
	static PointStyle[] styles = new PointStyle[] { PointStyle.DIAMOND,PointStyle.CIRCLE};//点的类型 
	
	
	protected SuperChartManager(Context context,SensorManager sensorManager) {
		this.context = context;
		this.sensorManager=sensorManager;
		
		
	}


	/**
	 * 创建曲线（动态的）
	 * 
	 * @param seriaName
	 * @return
	 */
	public View createChart(String title,String xTitle, String yTitle,String[] seriaNames,int numSeria) {
		
		chart= ChartFactory.getLineChartView(context, getDataset(seriaNames),getRenderer(title, xTitle, yTitle,numSeria));

		return chart;

	}
	
//	/**
//	 * 创建曲线（静态的）
//	 * 
//	 * @param seriaName
//	 * @return
//	 */
	public View createChart(String title,String xTitle, String yTitle,String[] seriaName
										,List<double[]> x,List<double[]> y
										,int[] colors,PointStyle[] styles) {
									
//		chart= ChartFactory.getLineChartView(context, getDataset(seriaName, x, y),getRenderer(title, xTitle, yTitle,colors,styles));

	return chart;

	}
//	
	
	
	/**
	 * 更新曲线
	 */
	public void updateSeries(XYSeries xySeries,XYData xyData){
		
		dataset.removeSeries(xySeries);
		
		xySeries.add(xyData.x, xyData.y);
		
		dataset.addSeries(xySeries);
		
		chart.invalidate();
	}
	

	/**
	 * 建立数据源	 * 
	 * @param seriaName
	 * @return
	 */
	protected XYMultipleSeriesDataset getDataset(String[] seriaNames) {

		dataset = new XYMultipleSeriesDataset();
		
		for(int i=0;i < seriaNames.length;i++){
			XYSeries xySeries = new XYSeries(seriaNames[i]);
			dataset.addSeries(xySeries);
		}
		
		return dataset;
	}
	

	/**
	 * 渲染曲线图
	 * 
	 * @return
	 */
	protected XYMultipleSeriesRenderer getRenderer(String title,String xTitle,String yTitle,int numSeria) {
			
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

		renderer.setChartTitle(title);// 图的标题
		renderer.setXTitle(xTitle);// x轴的标题
		renderer.setYTitle(yTitle);// y轴的标题
		
		//设置x轴和y轴标题的颜色  
		renderer.setLabelsColor(Color.CYAN);
		 //设置x轴和y轴标签的颜色  
        renderer.setXLabelsColor(Color.WHITE);  
        renderer.setYLabelsColor(0,Color.WHITE);

		 //设置x轴和y轴的标签对齐方式  
        renderer.setXLabelsAlign(Align.CENTER);  
        renderer.setYLabelsAlign(Align.RIGHT); 
        
        //设置图表背景色
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.BLACK);
        // 设置显示网格  
        renderer.setShowGrid(true); 
 
		renderer.setChartTitleTextSize(30);// 设置图表标题文本大小
		renderer.setAxisTitleTextSize(30);// 设置坐标轴标题文本大小
		renderer.setLabelsTextSize(30); // 设置轴标签文本大小
		renderer.setPointSize(5.0f); // 设置点的大小
		//renderer.set
		renderer.setLegendTextSize(30); // 设置图例文本大小
		renderer.setLegendHeight(60); // 设置图例高度
		
		renderer.setMargins(new int[] { 60,100,80,10 });// 设置4边留白,/上,左,下,右
		
		for(int i=0;i<numSeria;i++){
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			
			r.setDisplayChartValues(true);
			r.setChartValuesTextSize(30);
			r.setFillPoints(false);
			r.setPointStyle(styles[i]);
			r.setLineWidth(2.0f);//设置曲线宽度
			
			if(i==1||i==2)	
				r.setDisplayChartValues(false);
		
			renderer.addSeriesRenderer(r);
		}
		
		return renderer;

	}
	
	
	public class XYData implements Serializable{
		
		
		private static final long serialVersionUID = 1L;
		public double x;
		public double y;
		
		public XYData(double x,double y){
			
			this.x=x;
			
			this.y=y;
		}
		
	}

}
