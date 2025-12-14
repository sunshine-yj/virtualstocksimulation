package clientGUI;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class StockChartFrame extends JFrame {
	
	// 주식 차트
	public StockChartFrame(ArrayList<Integer> list, String _StockName) {
		setTitle("주식 정보");
		setSize(800, 400);
		JFreeChart chart = getChart(list, _StockName);
		ChartPanel chartPanel = new ChartPanel(chart);
		add(chartPanel);
		
		setVisible(true);
	}
	
	// https://androphil.tistory.com/441를 참고하여 그래프를 추가하였습니다.
	// 주식 차트 구성
	public JFreeChart getChart(ArrayList<Integer> list, String _StockName) {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		//데이터 입력
		Collections.reverse(list); // 데이터 값 뒤집기
		int month = 1;
	    for (int price : list) {
	        dataset.addValue(price, _StockName, month + "월");
	        month ++;
	    }

		LineAndShapeRenderer renderer = new LineAndShapeRenderer();
	
	    // plot 기본 설정
		CategoryPlot plot = new CategoryPlot();
	    plot.setDataset(dataset);
	    plot.setRenderer(renderer);
	    plot.setOrientation(PlotOrientation.VERTICAL);
	    plot.setRangeGridlinesVisible(true);
	    plot.setDomainGridlinesVisible(true);
	    
	    // x축 세팅
	    CategoryAxis domainAxis = new CategoryAxis();
	    plot.setDomainAxis(domainAxis);
		
	    // y축 세팅
	    NumberAxis rangeAxis = new NumberAxis();
	    plot.setRangeAxis(rangeAxis);
	    
	    // chart 생성
	    JFreeChart chart = new JFreeChart(plot);
	    
	    
		return chart;
		
	}
}
