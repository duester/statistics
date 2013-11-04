package de.duester.statistica.chart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;

// линейная диаграмма
public class ChartLineChart extends ChartPointLine {
	// сплайн вместо ломаной?
	private JCheckBox showAsSpline;

	public ChartLineChart() {
		showAsSpline = new JCheckBox();
		showAsSpline.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLineType();
			}
		});
	}

	private void setLineType() {
		XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer renderer;

		if (showAsSpline.isSelected()) {
			renderer = new XYSplineRenderer(10);
			renderer.setSeriesShapesVisible(0, false);
		} else
			renderer = new XYLineAndShapeRenderer(true, false);

		renderer.setSeriesVisibleInLegend(0, false);
		plot.setRenderer(renderer);
		setColor(color.getBackground());
	}

	@Override
	public void createChart() {
		super.createChart();

		setLineType();
	}

	@Override
	public void localizeTitles() {
		setChartTitle("txtLineChart");
	}

	@Override
	public JComponent[] getControls() {
		return new JComponent[] { createLabel("txtLineColor"), color,
				createLabel("txtShowAsSpline"), showAsSpline };
	}

}
