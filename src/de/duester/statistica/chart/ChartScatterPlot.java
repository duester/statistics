package de.duester.statistica.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;

import org.jfree.chart.labels.XYSeriesLabelGenerator;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import de.duester.statistica.Statistica;
import de.duester.statistica.caclulation.Statistics;

// диаграмма рассеяния
public class ChartScatterPlot extends ChartPointLine {
	// показать линию регрессии?
	private JCheckBox showRegressionLine;
	private JButton regressionLineColor;

	public ChartScatterPlot() {
		showRegressionLine = new JCheckBox();
		showRegressionLine.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkRegressionLine();
			}
		});

		regressionLineColor = new JButton();
		regressionLineColor.setBackground(Color.RED);
		regressionLineColor.setPreferredSize(new Dimension(20, 20));
		regressionLineColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(Statistica.getInstance(), null,
						regressionLineColor.getBackground());
				if (color != null)
					setRegressionLineColor(color);
			}
		});
	}

	private void setRegressionLineColor(Color color) {
		regressionLineColor.setBackground(color);

		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) chart.getXYPlot()
				.getRenderer();
		renderer.setSeriesPaint(1, color);
	}

	private void checkRegressionLine() {
		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) chart.getXYPlot()
				.getRenderer();
		renderer.setSeriesVisible(1, showRegressionLine.isSelected());

		regressionLineColor.setEnabled(showRegressionLine.isSelected());
	}

	@Override
	public void createChart() {
		super.createChart();

		double[] x = data[0];
		double[] y = data[1];
		// [a,b], где y = a + bx
		final double[] linearFit = Statistics.getLinearFit(x, y);
		double[] xx = Arrays.copyOf(x, x.length);
		Arrays.sort(xx);

		double[][] regXY = new double[2][2];
		regXY[0][0] = xx[0];
		regXY[0][1] = xx[xx.length - 1];
		for (int i = 0; i < regXY[0].length; i++)
			regXY[1][i] = linearFit[0] + linearFit[1] * regXY[0][i];

		DefaultXYDataset dataset = (DefaultXYDataset) chart.getXYPlot().getDataset();
		dataset.addSeries(1, regXY);

		checkRegressionLine();

		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) chart.getXYPlot()
				.getRenderer();
		renderer.setSeriesLinesVisible(0, false);
		renderer.setSeriesShapesVisible(0, true);
		renderer.setSeriesVisibleInLegend(0, false);
		renderer.setSeriesLinesVisible(1, true);
		renderer.setSeriesShapesVisible(1, false);
		renderer.setLegendItemLabelGenerator(new XYSeriesLabelGenerator() {

			@Override
			public String generateLabel(XYDataset dataset, int series) {
				if (series == 1)
					return "y = "
							+ linearFit[0]
							+ " + "
							+ (linearFit[1] < 0 ? "(" + linearFit[1] + ")" : linearFit[1])
							+ " x";
				else
					return null;
			}
		});

		setRegressionLineColor(regressionLineColor.getBackground());
	}

	@Override
	public void localizeTitles() {
		setChartTitle("txtScatterPlot");
	}

	@Override
	public JComponent[] getControls() {
		return new JComponent[] { createLabel("txtPointColor"), color,
				createLabel("txtShowRegressionLine"), showRegressionLine,
				createLabel("txtRegressionLineColor"), regressionLineColor };
	}

}
