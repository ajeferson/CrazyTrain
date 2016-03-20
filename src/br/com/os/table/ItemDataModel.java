package br.com.os.table;

import javax.swing.table.DefaultTableModel;

import br.com.os.interfaces.ListViewController;
import br.com.os.interfaces.ListViewControllerDataSource;
import br.com.os.interfaces.ListViewControllerDelegate;

public class ItemDataModel extends DefaultTableModel {

	private static final long serialVersionUID = 7149681284011968966L;

	private String columnNames[] = {"ID", "Excluir"};
	private ListViewControllerDataSource dataSource;
	private ListViewControllerDelegate delegate;
	private ListViewController listViewController;
	
	public ItemDataModel(ListViewControllerDataSource dataSource, ListViewController listViewController) {
		this.dataSource = dataSource;
		this.listViewController = listViewController;
	}
	
	@Override
	public int getRowCount() {
		if(this.dataSource != null) {
			return this.dataSource.getItemCount(this.listViewController);
		}
		return 0;
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return this.columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return this.dataSource.getValueAtIndex(this.listViewController, rowIndex);
		default:
			return "Excluir";
		}
		
	}
	
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		fireTableCellUpdated(row, column);
	}
	
	@Override
	public void removeRow(int row) {
		if(this.delegate != null) {
			this.delegate.didSelectRowAtIndex(row);
		}
	}
	
}
