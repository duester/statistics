package de.duester.statistica;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

// модель данных таблицы
@SuppressWarnings("serial")
public class StatisticalTableModel extends AbstractTableModel {
	private List<List<Double>> data = new ArrayList<>();
	private List<String> columnNames = new ArrayList<>();

	@Override
	public int getRowCount() {
		return data.size() + 1;
	}

	@Override
	public int getColumnCount() {
		return columnNames.size() + 1;
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0)
			return "";
		else
			return columnIndex + ". " + columnNames.get(columnIndex - 1);
	}

	public String getColumnInternalName(int columnIndex) {
		return columnNames.get(columnIndex - 1);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0)
			return String.class;
		else
			return Double.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (columnIndex > 0);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0)
			return (rowIndex + 1) + ".";
		else if (rowIndex < data.size())
			return data.get(rowIndex).get(columnIndex - 1);
		else
			return null;
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (rowIndex == data.size())
			addRow();

		data.get(rowIndex).set(columnIndex - 1, (Double) value);
		setEmptyCellToZero();
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	public void addRow() {
		data.add(createEmptyRow());
		fireTableRowsInserted(data.size() - 1, data.size() - 1);
	}

	public void removeRow(int index) {
		data.remove(index);

		fireTableRowsDeleted(data.size() - 1, data.size() - 1);
	}

	public void addColumn() {
		for (List<Double> row : data)
			row.add(null);

		columnNames.add("");
	}

	public void removeColumn(int index) {
		for (List<Double> row : data)
			row.remove(index);

		columnNames.remove(index);

		if (columnNames.size() == 0)
			data.clear();

		fireTableStructureChanged();
	}

	public void setColumnName(int columnIndex, String name) {
		columnNames.set(columnIndex - 1, name);
	}

	private List<Double> createEmptyRow() {
		List<Double> list = new ArrayList<>();
		for (int i = 0; i < columnNames.size(); i++)
			list.add(null);
		return list;
	}

	// проставл€ет в незаполненные €чейки 0
	public void setEmptyCellToZero() {
		for (int i = 0; i < data.size(); i++)
			for (int j = 0; j < columnNames.size(); j++)
				if (data.get(i).get(j) == null)
					data.get(i).set(j, 0.0);
	}

}
