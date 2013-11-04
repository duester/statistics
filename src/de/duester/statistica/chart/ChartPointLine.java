package de.duester.statistica.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JColorChooser;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.DefaultXYDataset;

import de.duester.statistica.Statistica;

// базовый класс для линейной диаграммы и диаграммы рассеяния
public abstract class ChartPointLine extends Chart {
	protected JButton color;

	public ChartPointLine() {
		color = new JButton();
		color.setBackground(Color.BLUE);
		color.setPreferredSize(new Dimension(20, 20));
		color.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Color c = JColorChooser.showDialog(Statistica.getInstance(), null,
						color.getBackground());
				if (c != null)
					setColor(c);
			}
		});
	}

	protected void setColor(Color c) {
		color.setBackground(c);

		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) chart.getXYPlot()
				.getRenderer();
		renderer.setSeriesPaint(0, c);
	}

	@Override
	public void createChart() {
		DefaultXYDataset dataset = new DefaultXYDataset();
		dataset.addSeries(0, data);

		double[] x = Arrays.copyOf(data[0], data[0].length);
		Arrays.sort(x);
		double minx = x[0];
		double maxx = x[x.length - 1];

		double[] y = Arrays.copyOf(data[1], data[1].length);
		Arrays.sort(y);
		double miny = y[0];
		double maxy = y[y.length - 1];

		ValueAxis domainAxis = new NumberAxis(getLabel(0));
		domainAxis.setRangeWithMargins(new Range(minx, maxx));

		ValueAxis rangeAxis = new NumberAxis(getLabel(1));
		rangeAxis.setRangeWithMargins(new Range(miny, maxy));

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(false, false);

		XYPlot plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);

		chart = new JFreeChart(plot);

		setColor(color.getBackground());
	}

}
