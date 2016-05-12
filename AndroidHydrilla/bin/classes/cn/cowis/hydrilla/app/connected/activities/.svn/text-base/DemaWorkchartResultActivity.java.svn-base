package cn.cowis.hydrilla.app.connected.activities;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ViewFlipper;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.charts.FittingChartManager;
import cn.cowis.hydrilla.app.parent.SuperActivity;

@EActivity(R.layout.dema_workchart_result)
public class DemaWorkchartResultActivity extends SuperActivity {

	@ViewById(R.id.dema_workchart_nh4)
	ViewFlipper chartViewFlipper;
	
	@ViewById(R.id.equation)
	TextView equationText;

	@ViewById(R.id.temText)
	TextView temText;
	
	@ViewById(R.id.stcalText)
	TextView stcalTextView;
	
	@ViewById(R.id.stcal)
	TextView stcalText;
	
	@ViewById(R.id.r)
	TextView rText;
	
	@ViewById(R.id.pts)
	TextView ptsText;

	@ViewById(R.id.lod)
	TextView lodText;
	
	private FittingChartManager chart = null;
	List<double[]> xList ;//三条曲线的坐标集合
	List<double[]> yList;

	private double tem;
	private int ionic;
	private double[] params;
	private String[] titleString = new String[]{"pa(NH4+)","pc(NH4+)"};
	
	@AfterViews
	public void init(){
//		chart = new FittingChartManager(this);
//		View chartView = null ;
//		
//		chartView = chart.createChart("NH4+工作曲线图"
//											, titleString[ionic]
//											, "E /mV"
//											, new String[]{"NH4+","拟合曲线","水平曲线"}
//											, xList
//											, yList
//											, new int[]{Color.RED, Color.YELLOW, Color.GREEN}
//											, new  PointStyle[]{PointStyle.CIRCLE,PointStyle.POINT,PointStyle.POINT});
//		
//		chartViewFlipper.addView(chartView);
//		if(params[2]>0)
//			equationText.setText("E = "+Tools.roundValue(params[0], 2)+"x + "+ Tools.roundValue(params[2], 2));
//		else if(params[2]==0)
//			equationText.setText("E = "+Tools.roundValue(params[0], 2)+"x ");
//		else 
//			equationText.setText("E = "+Tools.roundValue(params[0], 2)+"x  "+ Tools.roundValue(params[2], 2));
//		temText.setText(String.valueOf(Tools.roundValue(tem,1)));
//		stcalTextView.setText("|斜率S("+String.valueOf(Tools.roundValue(tem,1))+") |/mV ：");
//		stcalText.setText(String.valueOf(Tools.roundValue(Math.abs(params[0]),2)));
//		rText.setText(String.valueOf(Tools.roundValue(Math.abs(params[1]),4)));
//		ptsText.setText(String.valueOf(Tools.roundValue(Math.abs(params[3]),2)));
//		lodText.setText(String.valueOf(Tools.roundValue(params[4],2)));

	}
	
//	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitleName("NH4+工作曲线标定结果");
		
		xList = (List<double[]>) getIntent().getBundleExtra("bundle").getSerializable("x");
		yList = (List<double[]>) getIntent().getBundleExtra("bundle").getSerializable("y");
		params = getIntent().getBundleExtra("bundle").getDoubleArray("param");
		tem = getIntent().getBundleExtra("bundle").getDouble("tem");
		ionic = getIntent().getBundleExtra("bundle").getInt("ionic");
	}
	
	
	
}
