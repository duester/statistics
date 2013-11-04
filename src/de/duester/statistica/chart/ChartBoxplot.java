package de.duester.statistica.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

import de.duester.statistica.Statistica;

// ящик с усами
public class ChartBoxplot extends Chart {
	// показывать среднее значение?
	private JCheckBox showMean;
	// показывать выбросы?
	private JCheckBox showOutliers;
	private JButton borderColor;
	private JButton fillColor;

	public ChartBoxplot() {
		showMean = new JCheckBox();
		showMean.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ExtendedBoxAndWhiskerRenderer renderer = (ExtendedBoxAndWhiskerRenderer) chart
						.getCategoryPlot().getRenderer();
				renderer.setMeanVisible(showMean.isSelected());
			}
		});

		showOutliers = new JCheckBox();
		showOutliers.setSelected(true);
		showOutliers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CategoryPlot plot = chart.getCategoryPlot();

				CategoryDataset dataset = plot.getDataset();

				ValueAxis rangeAxis = plot.getRangeAxis();

				ExtendedBoxAndWhiskerRenderer renderer = (ExtendedBoxAndWhiskerRenderer) (chart
						.getCategoryPlot()).getRenderer();
				renderer.setOutliersVisible(showOutliers.isSelected());
				rangeAxis.setRangeWithMargins(renderer.findRangeBounds(dataset));
			}
		});

		borderColor = new JButton();
		borderColor.setBackground(Color.BLACK);
		borderColor.setPreferredSize(new Dimension(20, 20));
		borderColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(Statistica.getInstance(), null,
						borderColor.getBackground());
				if (color != null)
					setBorderColor(color);
			}
		});

		fillColor = new JButton();
		fillColor.setBackground(Color.YELLOW);
		fillColor.setPreferredSize(new Dimension(20, 20));
		fillColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(Statistica.getInstance(), null,
						fillColor.getBackground());
				if (color != null)
					setFillColor(color);
			}
		});
	}

	private void setBorderColor(Color color) {
		borderColor.setBackground(color);

		ExtendedBoxAndWhiskerRenderer renderer = (ExtendedBoxAndWhiskerRenderer) chart
				.getCategoryPlot().getRenderer();
		renderer.setSeriesOutlinePaint(0, color);
	}

	private void setFillColor(Color color) {
		fillColor.setBackground(color);

		ExtendedBoxAndWhiskerRenderer renderer = (ExtendedBoxAndWhiskerRenderer) chart
				.getCategoryPlot().getRenderer();
		renderer.setSeriesPaint(0, color);
	}

	@Override
	public void createChart() {
		DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

		for (int i = 0; i < data.length; i++) {
			List<Double> xx = new ArrayList<>(data[0].length);
			for (double d : data[i])
				xx.add(d);

			dataset.add(xx, 0, getLabel(i));
		}

		ExtendedBoxAndWhiskerRenderer renderer = new ExtendedBoxAndWhiskerRenderer();
		renderer.setMedianVisible(true);
		renderer.setMeanVisible(showMean.isSelected());
		renderer.setOutliersVisible(showOutliers.isSelected());
		renderer.setSeriesVisibleInLegend(0, false);

		CategoryAxis domainAxis = new CategoryAxis();
		domainAxis.setTickLabelFont(titleFont);

		ValueAxis rangeAxis = new NumberAxis();
		rangeAxis.setRangeWithMargins(renderer.findRangeBounds(dataset));

		CategoryPlot plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer);

		chart = new JFreeChart(plot);

		setBorderColor(borderColor.getBackground());
		setFillColor(fillColor.getBackground());
	}

	@Override
	public void localizeTitles() {
		setChartTitle("txtBoxplot");
	}

	@Override
	public JComponent[] getControls() {
		return new JComponent[] { createLabel("txtBorderColor"), borderColor,
				createLabel("txtFillColor"), fillColor, createLabel("txtShowMean"),
				showMean, createLabel("txtShowOutliers"), showOutliers };
	}

}
