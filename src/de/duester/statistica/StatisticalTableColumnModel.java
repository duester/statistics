package de.duester.statistica;

import javax.swing.event.TableColumnModelEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

// модель столбцов таблицы
@SuppressWarnings("serial")
public class StatisticalTableColumnModel extends DefaultTableColumnModel {

	@Override
	public void addColumn(TableColumn column) {
		tableColumns.add(column);
		fireColumnAdded(new TableColumnModelEvent(this, 0, getColumnCount() - 1));
	}

	@Override
	public void removeColumn(TableColumn column) {
		int index = column.getModelIndex();
		tableColumns.remove(column);
		fireColumnRemoved(new TableColumnModelEvent(this, index, 0));
	}

	public int[] getSelectedColumns() {
		if (selectionModel != null) {
			int iMin = selectionModel.getMinSelectionIndex();
			int iMax = selectionModel.getMaxSelectionIndex();

			if ((iMin == -1) || (iMax == -1)) {
				return new int[0];
			}

			int[] rvTmp = new int[1 + (iMax - iMin)];
			int n = 0;
			for (int i = iMin; i <= iMax; i++) {
				if (i > 0 && selectionModel.isSelectedIndex(i)) {
					rvTmp[n++] = i;
				}
			}
			int[] rv = new int[n];
			System.arraycopy(rvTmp, 0, rv, 0, n);
			return rv;
		}
		return new int[0];
	}

	public int getSelectedColumnCount() {
		return getSelectedColumns().length;
	}
}
