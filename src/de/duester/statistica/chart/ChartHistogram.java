package de.duester.statistica.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.Range;
import org.jfree.data.statistics.HistogramDataset;

import de.duester.statistica.Resource;
import de.duester.statistica.Statistica;

// гистограмма
public class ChartHistogram extends Chart {
	// количество столбцов
	private JSpinner numberOfIntervals;
	private JButton borderColor;
	private JButton columnColor;

	public ChartHistogram() {
		numberOfIntervals = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
		numberOfIntervals.setPreferredSize(new Dimension(45, 20));
		numberOfIntervals.addChangeListener(new ChangeListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void stateChanged(ChangeEvent e) {
				CombinedDomainXYPlot plot = (CombinedDomainXYPlot) chart.getPlot();
				List<XYPlot> subPlots = plot.getSubplots();

				for (int i = 0; i < data.length; i++) {
					HistogramDataset dataset = new HistogramDataset();
					dataset.addSeries(i, data[i], (Integer) numberOfIntervals.getValue());

					subPlots.get(i).setDataset(dataset);
				}
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

		columnColor = new JButton();
		columnColor.setBackground(Color.YELLOW);
		columnColor.setPreferredSize(new Dimension(20, 20));
		columnColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(Statistica.getInstance(), null,
						columnColor.getBackground());
				if (color != null)
					setColumnColor(color);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void setBorderColor(Color color) {
		borderColor.setBackground(color);

		List<XYPlot> subPlots = ((CombinedDomainXYPlot) chart.getPlot()).getSubplots();
		for (int i = 0; i < data.length; i++) {
			XYBarRenderer renderer = (XYBarRenderer) subPlots.get(i).getRenderer();
			renderer.setSeriesOutlinePaint(0, color);
		}
	}

	@SuppressWarnings("unchecked")
	private void setColumnColor(Color color) {
		columnColor.setBackground(color);

		List<XYPlot> subPlots = ((CombinedDomainXYPlot) chart.getPlot()).getSubplots();
		for (int i = 0; i < data.length; i++) {
			XYBarRenderer renderer = (XYBarRenderer) subPlots.get(i).getRenderer();
			renderer.setSeriesPaint(0, color);
		}
	}

	@Override
	public void createChart() {
		ValueAxis domainAxis = new NumberAxis();

		CombinedDomainXYPlot plot = new CombinedDomainXYPlot(domainAxis);
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setDataset(null);

		XYBarRenderer renderer = new XYBarRenderer();
		renderer.setDrawBarOutline(true);
		renderer.setShadowVisible(false);
		renderer.setSeriesVisibleInLegend(0, false);

		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		for (int i = 0; i < data.length; i++) {
			double[] x = data[i];
			Arrays.sort(x);
			min = Math.min(min, x[0]);
			max = Math.max(max, x[x.length - 1]);

			HistogramDataset dataset = new HistogramDataset();
			dataset.addSeries(i, x, (Integer) numberOfIntervals.getValue());

			ValueAxis rangeAxis = new NumberAxis();
			rangeAxis.setLabelFont(titleFont);

			XYPlot subPlot = new XYPlot(dataset, null, rangeAxis, renderer);

			plot.add(subPlot);
		}
		domainAxis.setRangeWithMargins(new Range(min, max));

		chart = new JFreeChart(plot);

		setBorderColor(borderColor.getBackground());
		setColumnColor(columnColor.getBackground());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void localizeTitles() {
		setChartTitle("txtHistogram");

		CombinedDomainXYPlot plot = (CombinedDomainXYPlot) chart.getPlot();
		List<XYPlot> subPlots = plot.getSubplots();

		for (int i = 0; i < data.length; i++) {
			ValueAxis axis = subPlots.get(i).getRangeAxis();
			axis.setLabel(Resource.getString("txtFrequency", getLabel(i)));
		}
	}

	@Override
	public JComponent[] getControls() {
		return new JComponent[] { createLabel("txtIntervalsNumber"), numberOfIntervals,
				createLabel("txtBorderColor"), borderColor,
				createLabel("txtColumnColor"), columnColor };
	}

}
