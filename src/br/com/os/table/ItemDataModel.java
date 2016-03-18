package br.com.os.table;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

import br.com.os.interfaces.Item;

public class ItemDataModel extends DefaultTableModel {

	private static final long serialVersionUID = 7149681284011968966L;

	private String columnNames[] = {"ID", "Excluir"};
	private ArrayList<Item> items;
	
	public ItemDataModel(ArrayList<Item> items) {
		this.items = items;
	}
	
	@Override
	public int getRowCount() {
		if(this.items != null) {
			return this.items.size();
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
			return this.items.get(rowIndex).getItemId();
		default:
			return "Excluir";
		}
		
	}
	
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
	
}
