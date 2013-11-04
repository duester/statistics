package de.duester.statistica;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;

import de.duester.statistica.caclulation.Calculation;
import de.duester.statistica.caclulation.Calculation1Variable;
import de.duester.statistica.caclulation.Calculation2Variables;
import de.duester.statistica.chart.Chart;
import de.duester.statistica.chart.ChartBoxplot;
import de.duester.statistica.chart.ChartHistogram;
import de.duester.statistica.chart.ChartLineChart;
import de.duester.statistica.chart.ChartScatterPlot;
import de.duester.statistica.sequence.Sequence;
import de.duester.statistica.sequence.SequenceArithmetic;
import de.duester.statistica.sequence.SequenceBinomialDistribution;
import de.duester.statistica.sequence.SequenceConst;
import de.duester.statistica.sequence.SequenceExpression;
import de.duester.statistica.sequence.SequenceNormalDistribution;
import de.duester.statistica.sequence.SequenceUniformDistribution;

@SuppressWarnings("serial")
// главный класс приложения
public class Statistica extends JFrame implements ActionListener, ListSelectionListener {
	private static Statistica me;

	// элементы меню
	private JMenu mnuColumn, mnuSequence, mnuCalculation, mnuChart, mnuLanguage;
	private JMenuItem mnuColumnAdd, mnuColumnRemove, mnuColumnClear, mnuColumnCopy,
			mnuColumnLoad, mnuColumnSaveSelected, mnuColumnSaveAll;
	private JMenuItem mnuSequenceArithmetic, mnuSequenceConst,
			mnuSequenceUniformDistribution, mnuSequenceBinomialDistribution,
			mnuSequenceNormalDistribution, mnuSequenceExpression;
	private JMenuItem mnuCalculation1Variable, mnuCalculation2Variables,
			mnuCalculationSaveResults;
	private JMenuItem mnuChartHistogram, mnuChartBoxplot, mnuChartScatterPlot,
			mnuChartLineChart, mnuChartSaveImage;
	private JRadioButtonMenuItem mnuLanguageRussian, mnuLanguageEnglish,
			mnuLanguageGerman;
	private JPopupMenu ctxtMnu;
	private JMenuItem ctxtMnuDeleteRow;
	// элементы интерфейса
	private JSplitPane splitPane;
	private JTable dataTable;
	private JTabbedPane tabbedPane;
	private JPanel textPanel;
	private JSplitPane graphicsPanel;
	private ChartPanel diagramPanel;
	private JPanel diagramControls;
	private JFileChooser fileChooser;

	// последние сохранённые экземпляры классов Calculation и Chart
	// (для локализации текстовых надписей)
	private Calculation calculation;
	private Chart chart;

	private Statistica() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		initMenu();
		initContent();

		changeLanguage("ru");

		setTitle("Statistica 1.0");
		setPreferredSize(new Dimension(800, 600));
		// setExtendedState(MAXIMIZED_BOTH);
		pack();
		splitPane.setDividerLocation(0.5);
		graphicsPanel.setDividerLocation(0.9);
	}

	public static Statistica getInstance() {
		if (me == null)
			me = new Statistica();
		return me;
	}

	// инициализация элементов меню
	private void initMenu() {
		JMenuBar mb = new JMenuBar();
		mb.add(mnuColumn = new JMenu());
		mb.add(mnuSequence = new JMenu());
		mb.add(mnuCalculation = new JMenu());
		mb.add(mnuChart = new JMenu());
		mb.add(mnuLanguage = new JMenu());

		mnuColumn.add(mnuColumnAdd = createMenuItem());
		mnuColumn.add(mnuColumnRemove = createMenuItem());
		mnuColumnRemove.setEnabled(false);
		mnuColumn.add(mnuColumnClear = createMenuItem());
		mnuColumnClear.setEnabled(false);
		mnuColumn.addSeparator();
		mnuColumn.add(mnuColumnCopy = createMenuItem());
		mnuColumnCopy.setEnabled(false);
		mnuColumn.addSeparator();
		mnuColumn.add(mnuColumnLoad = createMenuItem());
		mnuColumn.add(mnuColumnSaveSelected = createMenuItem());
		mnuColumnSaveSelected.setEnabled(false);
		mnuColumn.add(mnuColumnSaveAll = createMenuItem());
		mnuColumnSaveAll.setEnabled(false);

		mnuSequence.add(mnuSequenceConst = createMenuItem());
		mnuSequence.add(mnuSequenceArithmetic = createMenuItem());
		mnuSequence.addSeparator();
		mnuSequence.add(mnuSequenceUniformDistribution = createMenuItem());
		mnuSequence.add(mnuSequenceBinomialDistribution = createMenuItem());
		mnuSequence.add(mnuSequenceNormalDistribution = createMenuItem());
		mnuSequence.addSeparator();
		mnuSequence.add(mnuSequenceExpression = createMenuItem());

		mnuCalculation.add(mnuCalculation1Variable = createMenuItem());
		mnuCalculation1Variable.setEnabled(false);
		mnuCalculation.add(mnuCalculation2Variables = createMenuItem());
		mnuCalculation2Variables.setEnabled(false);
		mnuCalculation.addSeparator();
		mnuCalculation.add(mnuCalculationSaveResults = createMenuItem());
		mnuCalculationSaveResults.setEnabled(false);

		mnuChart.add(mnuChartHistogram = createMenuItem());
		mnuChartHistogram.setEnabled(false);
		mnuChart.add(mnuChartBoxplot = createMenuItem());
		mnuChartBoxplot.setEnabled(false);
		mnuChart.addSeparator();
		mnuChart.add(mnuChartScatterPlot = createMenuItem());
		mnuChartScatterPlot.setEnabled(false);
		mnuChart.add(mnuChartLineChart = createMenuItem());
		mnuChartLineChart.setEnabled(false);
		mnuChart.addSeparator();
		mnuChart.add(mnuChartSaveImage = createMenuItem());
		mnuChartSaveImage.setEnabled(false);

		ButtonGroup bg = new ButtonGroup();
		mnuLanguage.add(mnuLanguageRussian = createRadioButtonMenuItem(bg));
		mnuLanguage.add(mnuLanguageEnglish = createRadioButtonMenuItem(bg));
		mnuLanguage.add(mnuLanguageGerman = createRadioButtonMenuItem(bg));
		mnuLanguageRussian.setSelected(true);

		ctxtMnu = new JPopupMenu();
		ctxtMnu.add(ctxtMnuDeleteRow = createMenuItem());

		setJMenuBar(mb);
	}

	private JMenuItem createMenuItem() {
		JMenuItem mi = new JMenuItem();
		mi.addActionListener(this);
		return mi;
	}

	private JRadioButtonMenuItem createRadioButtonMenuItem(ButtonGroup bg) {
		JRadioButtonMenuItem rbmi = new JRadioButtonMenuItem();
		rbmi.addActionListener(this);
		bg.add(rbmi);
		return rbmi;
	}

	// инициализация элементов интерфейса
	private void initContent() {
		dataTable = new JTable();
		dataTable.setModel(new StatisticalTableModel());
		dataTable.setColumnModel(new StatisticalTableColumnModel());
		dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		dataTable.setCellSelectionEnabled(true);
		dataTable.getTableHeader().setReorderingAllowed(false);
		dataTable.getTableHeader().setResizingAllowed(false);
		dataTable.getSelectionModel().addListSelectionListener(this);
		dataTable.getColumnModel().getSelectionModel().addListSelectionListener(this);
		dataTable.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		dataTable.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StatisticalTableColumnModel columnModel = (StatisticalTableColumnModel) dataTable
						.getColumnModel();
				ListSelectionModel selection = columnModel.getSelectionModel();
				int column = columnModel.getColumnIndexAtX(e.getX());
				if (column == 0)
					return;

				switch (e.getClickCount()) {
				case 1:
					// выделение столбцов
					if ((!e.isControlDown() && !e.isShiftDown())
							|| (e.isControlDown() && e.isShiftDown())) {
						selection.setSelectionInterval(column, column);
						selection.setAnchorSelectionIndex(column);
					} else if (e.isShiftDown())
						selection.setSelectionInterval(
								selection.getAnchorSelectionIndex(), column);
					else // isCtrlDown()
					if (columnModel.getSelectionModel().isSelectedIndex(column))
						selection.removeSelectionInterval(column, column);
					else
						selection.addSelectionInterval(column, column);

					dataTable.setRowSelectionInterval(0, dataTable.getRowCount() - 1);

					checkSelection();
					break;
				case 2:
					// смена имени столбца
					changeColumnName(column);
					break;
				}
			}
		});
		// контекстное меню
		dataTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3)
					ctxtMnu.show(e.getComponent(), e.getX(), e.getY());
			}
		});

		JScrollPane scrollPane = new JScrollPane(dataTable);
		JPanel p = new JPanel(new BorderLayout());
		p.add(scrollPane, BorderLayout.WEST);

		textPanel = new JPanel(new GridBagLayout());

		diagramPanel = new ChartPanel(null);
		diagramPanel.setPopupMenu(null);

		diagramControls = new JPanel(new FlowLayout(FlowLayout.CENTER));

		graphicsPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, diagramPanel,
				diagramControls);
		graphicsPanel.setDividerSize(3);

		tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.addTab("", textPanel);
		tabbedPane.addTab("", graphicsPanel);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, scrollPane,
				tabbedPane);
		splitPane.setDividerSize(3);

		getContentPane().add(splitPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mnuColumnAdd)
			addColumn();
		else if (e.getSource() == mnuColumnRemove)
			removeColumn();
		else if (e.getSource() == mnuColumnClear)
			clearColumns();
		else if (e.getSource() == mnuColumnCopy)
			copyColumns();
		else if (e.getSource() == mnuColumnLoad)
			loadColumns();
		else if (e.getSource() == mnuColumnSaveSelected)
			saveColumns(dataTable.getSelectedColumns());
		else if (e.getSource() == mnuColumnSaveAll) {
			int[] columns = new int[dataTable.getColumnCount() - 1];
			for (int i = 0; i < columns.length; i++)
				columns[i] = i + 1;
			saveColumns(columns);
		} else if (e.getSource() == mnuSequenceConst)
			generateFromSequence(new SequenceConst());
		else if (e.getSource() == mnuSequenceArithmetic)
			generateFromSequence(new SequenceArithmetic());
		else if (e.getSource() == mnuSequenceUniformDistribution)
			generateFromSequence(new SequenceUniformDistribution());
		else if (e.getSource() == mnuSequenceBinomialDistribution)
			generateFromSequence(new SequenceBinomialDistribution());
		else if (e.getSource() == mnuSequenceNormalDistribution)
			generateFromSequence(new SequenceNormalDistribution());
		else if (e.getSource() == mnuSequenceExpression)
			generateFromSequence(new SequenceExpression());
		else if (e.getSource() == mnuCalculation1Variable)
			calculate(new Calculation1Variable());
		else if (e.getSource() == mnuCalculation2Variables)
			calculate(new Calculation2Variables());
		else if (e.getSource() == mnuCalculationSaveResults)
			saveCalculation();
		else if (e.getSource() == mnuChartHistogram)
			drawChart(new ChartHistogram());
		else if (e.getSource() == mnuChartBoxplot)
			drawChart(new ChartBoxplot());
		else if (e.getSource() == mnuChartScatterPlot)
			drawChart(new ChartScatterPlot());
		else if (e.getSource() == mnuChartLineChart)
			drawChart(new ChartLineChart());
		else if (e.getSource() == mnuChartSaveImage)
			saveChart();
		else if (e.getSource() == mnuLanguageRussian)
			changeLanguage("ru");
		else if (e.getSource() == mnuLanguageEnglish)
			changeLanguage("en");
		else if (e.getSource() == mnuLanguageGerman)
			changeLanguage("de");
		else if (e.getSource() == ctxtMnuDeleteRow)
			removeRow();
	}

	private void addColumn() {
		((StatisticalTableModel) dataTable.getModel()).addColumn();

		if (dataTable.getColumnCount() == 0)
			dataTable.addColumn(new TableColumn());

		TableColumn column = new TableColumn();
		column.setModelIndex(dataTable.getColumnCount());
		dataTable.addColumn(column);

		((StatisticalTableModel) dataTable.getModel()).setEmptyCellToZero();

		mnuColumnSaveAll.setEnabled(true);
	}

	private void removeColumn() {
		int[] columnIndices = dataTable.getSelectedColumns();

		for (int i = columnIndices.length - 1; i >= 0; i--)
			dataTable
					.removeColumn(dataTable.getColumnModel().getColumn(columnIndices[i]));

		for (int i = columnIndices.length - 1; i >= 0; i--)
			((StatisticalTableModel) dataTable.getModel())
					.removeColumn(columnIndices[i] - 1);

		if (dataTable.getColumnCount() == 1)
			dataTable.removeColumn(dataTable.getColumnModel().getColumn(0));

		dataTable.getSelectionModel().clearSelection();
		dataTable.getColumnModel().getSelectionModel().clearSelection();

		if (dataTable.getColumnCount() == 0)
			mnuColumnSaveAll.setEnabled(false);
	}

	private void clearColumns() {
		for (int column : dataTable.getSelectedColumns())
			for (int i = 0; i < dataTable.getRowCount() - 1; i++)
				dataTable.setValueAt(0.0, i, column);
	}

	private void copyColumns() {
		int[] columnIndices = dataTable.getSelectedColumns();
		int count = columnIndices.length;
		int columnsSoFar = dataTable.getColumnCount() - 1;
		StatisticalTableModel model = (StatisticalTableModel) dataTable.getModel();

		// добавляем столбцы и копируем заголовки
		for (int i = 0; i < count; i++) {
			addColumn();
			model.setColumnName(columnsSoFar + i + 1,
					model.getColumnInternalName(columnIndices[i]));
		}

		// копируем данные
		for (int i = 0; i < dataTable.getRowCount() - 1; i++)
			for (int j = 0; j < count; j++)
				dataTable.setValueAt(dataTable.getValueAt(i, columnIndices[j]), i,
						columnsSoFar + j + 1);

		model.fireTableStructureChanged();
	}

	private void loadColumns() {
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				Resource.getString("txtFileDialogFilterCSV"), "csv");
		fileChooser.setFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(fileChooser.getSelectedFile())));

				String line = br.readLine();
				int count = line.split(";").length;
				int columnsSoFar = dataTable.getColumnCount() > 0 ? dataTable
						.getColumnCount() - 1 : 0;
				for (int i = 0; i < count; i++)
					addColumn();

				int row = 0;
				while (line != null) {
					// при необходимости добавляем новую строку
					if (row == dataTable.getRowCount() - 1)
						((StatisticalTableModel) dataTable.getModel()).addRow();

					String[] values = line.split(";");
					for (int i = 0; i < count; i++)
						dataTable.setValueAt(Double.parseDouble(values[i]), row,
								columnsSoFar + i + 1);

					line = br.readLine();
					row++;
				}

				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private void saveColumns(int[] columns) {
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				Resource.getString("txtFileDialogFilterCSV"), "csv");
		fileChooser.setFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
			try {
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(fileChooser.getSelectedFile())));

				for (int i = 0; i < dataTable.getRowCount() - 1; i++) {
					for (int j = 0; j < columns.length - 1; j++)
						bw.write(dataTable.getValueAt(i, columns[j]) + ";");
					bw.write(dataTable.getValueAt(i, columns[columns.length - 1]) + "\n");
				}

				bw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	// добавление в таблицу данных согласно выбранной схеме последовательности
	private void generateFromSequence(Sequence sequence) {
		double[] list;
		try {
			list = sequence.execute();
		} catch (CancelPressedException e) {
			return;
		}

		addColumn();
		// при необходимости добавляем новые строки
		for (int i = 0; i < list.length - dataTable.getRowCount() + 1; i++)
			((StatisticalTableModel) dataTable.getModel()).addRow();

		for (int i = 0; i < list.length; i++)
			dataTable.setValueAt(list[i], i, dataTable.getColumnCount() - 1);
	}

	// вычисление основных характеристик числовых рядов
	private void calculate(Calculation calculation) {
		this.calculation = calculation;
		calculation.calculate(getDoubleArrayFromSelectedColumns());
		displayCalculationResults();

		mnuCalculationSaveResults.setEnabled(textPanel.getComponentCount() > 0);
	}

	private void saveCalculation() {
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				Resource.getString("txtFileDialogFilterTextFiles"), "txt");
		fileChooser.setFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
			try {
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(fileChooser.getSelectedFile()), "UTF-8"));

				for (Component component : textPanel.getComponents())
					bw.write(((JLabel) component).getText() + "\n");

				bw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	// визуализация данных
	private void drawChart(Chart chart) {
		this.chart = chart;
		chart.setData(getDoubleArrayFromSelectedColumns(),
				getStringArrayFromSelectedColumnsHeaders());
		if (chart.hasValidData()) {
			chart.createChart();
			diagramPanel.setChart(chart.getChart());
			diagramPanel.setMouseZoomable(false);
			localizeChart();
		}

		mnuChartSaveImage.setEnabled(chart.hasValidData());
	}

	private void saveChart() {
		fileChooser = new JFileChooser();

		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG", "jpg");
		fileChooser.addChoosableFileFilter(filter);

		filter = new FileNameExtensionFilter("PNG", "png");
		fileChooser.setFileFilter(filter);

		fileChooser.setAcceptAllFileFilterUsed(false);
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
			try {
				if (fileChooser.getFileFilter().getDescription().equals("JPG"))
					ChartUtilities.saveChartAsJPEG(fileChooser.getSelectedFile(),
							chart.getChart(), 1024, 1024);
				else
					ChartUtilities.saveChartAsPNG(fileChooser.getSelectedFile(),
							chart.getChart(), 1024, 1024);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private void changeLanguage(String id) {
		Resource.init(id);

		mnuColumn.setText(Resource.getString("mnuColumn"));
		mnuColumnAdd.setText(Resource.getString("mnuColumnAdd"));
		mnuColumnRemove.setText(Resource.getString("mnuColumnRemove"));
		mnuColumnClear.setText(Resource.getString("mnuColumnClear"));
		mnuColumnCopy.setText(Resource.getString("mnuColumnCopy"));
		mnuColumnLoad.setText(Resource.getString("mnuColumnLoad"));
		mnuColumnSaveSelected.setText(Resource.getString("mnuColumnSaveSelected"));
		mnuColumnSaveAll.setText(Resource.getString("mnuColumnSaveAll"));

		mnuSequence.setText(Resource.getString("mnuSequence"));
		mnuSequenceConst.setText(Resource.getString("mnuSequenceConst"));
		mnuSequenceArithmetic.setText(Resource.getString("mnuSequenceArithmetic"));
		mnuSequenceUniformDistribution.setText(Resource
				.getString("mnuSequenceUniformDistribution"));
		mnuSequenceBinomialDistribution.setText(Resource
				.getString("mnuSequenceBinomialDistribution"));
		mnuSequenceNormalDistribution.setText(Resource
				.getString("mnuSequenceNormalDistribution"));
		mnuSequenceExpression.setText(Resource.getString("mnuSequenceExpression"));

		mnuCalculation.setText(Resource.getString("mnuCalculation"));
		mnuCalculation1Variable.setText(Resource.getString("mnuCalculation1Variable"));
		mnuCalculation2Variables.setText(Resource.getString("mnuCalculation2Variables"));
		mnuCalculationSaveResults
				.setText(Resource.getString("mnuCalculationSaveResults"));

		mnuChart.setText(Resource.getString("mnuChart"));
		mnuChartHistogram.setText(Resource.getString("mnuChartHistogram"));
		mnuChartBoxplot.setText(Resource.getString("mnuChartBoxplot"));
		mnuChartScatterPlot.setText(Resource.getString("mnuChartScatterPlot"));
		mnuChartLineChart.setText(Resource.getString("mnuChartLineChart"));
		mnuChartSaveImage.setText(Resource.getString("mnuChartSaveImage"));

		mnuLanguage.setText(Resource.getString("mnuLanguage"));
		mnuLanguageRussian.setText(Resource.getString("mnuLanguageRussian"));
		mnuLanguageEnglish.setText(Resource.getString("mnuLanguageEnglish"));
		mnuLanguageGerman.setText(Resource.getString("mnuLanguageGerman"));

		ctxtMnuDeleteRow.setText(Resource.getString("ctxtMnuDeleteRow"));

		tabbedPane.setTitleAt(0, Resource.getString("txtText"));
		tabbedPane.setTitleAt(1, Resource.getString("txtGraphics"));

		if (calculation != null)
			displayCalculationResults();

		if (chart != null)
			localizeChart();
	}

	private void removeRow() {
		int[] selectedRows = dataTable.getSelectedRows();
		for (int i = selectedRows.length - 1; i >= 0; i--)
			if (selectedRows[i] < dataTable.getRowCount() - 1)
				((StatisticalTableModel) dataTable.getModel()).removeRow(selectedRows[i]);
		dataTable.getSelectionModel().clearSelection();
	}

	private void changeColumnName(int column) {
		dataTable.getColumnModel().getSelectionModel().clearSelection();
		InputForm form = new InputForm();
		form.addTextBox("txtEnterColumnName", ((StatisticalTableModel) dataTable
				.getModel()).getColumnInternalName(column));
		if (form.showDialog() == 0) {
			StatisticalTableModel model = (StatisticalTableModel) dataTable.getModel();
			model.setColumnName(column, form.getTextBoxValue("txtEnterColumnName"));
			model.fireTableStructureChanged();
		}
	}

	// данные выделенных столбцов в виде 2-мерного массива
	private double[][] getDoubleArrayFromSelectedColumns() {
		double[][] data = new double[dataTable.getSelectedColumnCount()][dataTable
				.getRowCount() - 1];
		int[] columnIndices = dataTable.getSelectedColumns();

		for (int j = 0; j < columnIndices.length; j++)
			for (int i = 0; i < dataTable.getRowCount() - 1; i++)
				data[j][i] = (double) dataTable.getValueAt(i, columnIndices[j]);

		return data;
	}

	// имена выделенных столбцов
	private String[] getStringArrayFromSelectedColumnsHeaders() {
		String[] headers = new String[dataTable.getSelectedColumnCount()];
		int[] columnIndices = dataTable.getSelectedColumns();

		for (int i = 0; i < columnIndices.length; i++)
			headers[i] = ((StatisticalTableModel) dataTable.getModel())
					.getColumnInternalName(columnIndices[i]);

		return headers;
	}

	// вывод локализованных результатов вычисления характеристик числовых рядов
	private void displayCalculationResults() {
		textPanel.removeAll();

		String[] list = calculation.getStrings();
		for (int i = 0; i < list.length / 2; i++) {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 20, 0, 0);
			gbc.weightx = 1;
			gbc.weighty = 1;
			boolean isHeader = list[2 * i + 1].equals("");
			if (isHeader)
				gbc.gridwidth = 2;
			textPanel.add(createLabelForTableLayoutPanel(list[2 * i], isHeader), gbc);
			if (!isHeader) {
				gbc = (GridBagConstraints) gbc.clone();
				gbc.gridx = 1;
				textPanel
						.add(createLabelForTableLayoutPanel(list[2 * i + 1], false), gbc);
			}
		}

		textPanel.repaint();
	}

	private JLabel createLabelForTableLayoutPanel(String text, boolean isHeader) {
		JLabel label = new JLabel(text);
		label.setFont(new Font(label.getFont().getName(), isHeader ? Font.BOLD
				: Font.PLAIN, 12));
		return label;
	}

	// локализация текстовых надписей диаграммы
	private void localizeChart() {
		diagramControls.removeAll();

		if (chart.hasValidData()) {
			chart.localizeTitles();

			JComponent[] controls = chart.getControls();
			for (int i = 0; i < controls.length / 2; i++) {
				JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
				JComponent component1 = controls[2 * i];
				JComponent component2 = controls[2 * i + 1];
				panel.add(component1);
				panel.add(component2);
				panel.setPreferredSize(new Dimension(component1.getPreferredSize().width
						+ component2.getPreferredSize().width + 15, Math.max(
						component1.getPreferredSize().height,
						component2.getPreferredSize().height)));
				diagramControls.add(panel);
			}
		}

		diagramControls.repaint();
	}

	// разрешение/запрещение определённых элементов меню
	// в зависимости от кол-ва выделенных столбцов
	private void checkSelection() {
		mnuColumnRemove.setEnabled(dataTable.getSelectedColumnCount() > 0);
		mnuColumnClear.setEnabled(dataTable.getSelectedColumnCount() > 0);
		mnuColumnCopy.setEnabled(dataTable.getSelectedColumnCount() > 0);
		mnuColumnSaveSelected.setEnabled(dataTable.getSelectedColumnCount() > 0);

		mnuCalculation1Variable.setEnabled(dataTable.getSelectedColumnCount() == 1);
		mnuCalculation2Variables.setEnabled(dataTable.getSelectedColumnCount() == 2);

		mnuChartHistogram.setEnabled(dataTable.getSelectedColumnCount() > 0);
		mnuChartBoxplot.setEnabled(dataTable.getSelectedColumnCount() > 0);
		mnuChartScatterPlot.setEnabled(dataTable.getSelectedColumnCount() == 2);
		mnuChartLineChart.setEnabled(dataTable.getSelectedColumnCount() == 2);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		checkSelection();
	}

	public static void main(String[] args) {
		Statistica.getInstance().setVisible(true);
	}

}
