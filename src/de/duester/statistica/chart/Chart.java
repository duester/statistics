package de.duester.statistica.chart;

import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;

import de.duester.statistica.Resource;

// базовый класс для построения диаграмм
public abstract class Chart {
	protected JFreeChart chart;
	protected double[][] data;
	protected String[] columnHeaders;
	protected boolean valid;
	protected Font titleFont;

	public Chart() {
		titleFont = new Font("Times", Font.PLAIN, 11);
	}

	// создание объекта chart
	public abstract void createChart();

	public JFreeChart getChart() {
		return chart;
	}

	// создание метки для элементов настройки диаграмм
	// (вызывается из getControls)
	protected JLabel createLabel(String key) {
		JLabel label = new JLabel(Resource.getString(key));
		label.setSize(label.getPreferredSize());
		return label;
	}

	// установка заголовка диаграммы
	protected void setChartTitle(String key) {
		TextTitle title = new TextTitle(Resource.getString(key), new Font("Times",
				Font.BOLD, 14));
		chart.setTitle(title);
	}

	// установка данных и имён столбцов
	public void setData(double[][] data, String[] columnHeaders) {
		this.data = data;
		this.columnHeaders = columnHeaders;
		valid = (data[0].length > 0);
	}

	public boolean hasValidData() {
		return valid;
	}

	// локализация текстовых надписей
	public abstract void localizeTitles();

	// возвращает список элементов настройки диаграмм (включая метки),
	// которые выводятся под диаграммой
	public abstract JComponent[] getControls();

	// надпись, выводимая на осях диаграммы: имя столбца либо номер ряда данных
	protected String getLabel(int index) {
		return columnHeaders[index].equals("") ? "[" + index + "]" : columnHeaders[index];
	}
}
