package br.com.os.table;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/** Renders the button column on the JTable. */
public class ButtonColumnRenderer extends JButton implements TableCellRenderer {

	private static final long serialVersionUID = -8593043358660067333L;

	public ButtonColumnRenderer() {
		this.setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		if(isSelected) {
			this.setForeground(table.getSelectionForeground());
			this.setBackground(table.getSelectionBackground());
		} else {
			this.setForeground(table.getForeground());
			this.setBackground(table.getBackground());
		}
		
		if(value != null) {
			this.setText(value.toString());
		} else {
			this.setText("");
		}
		
		return this;
	}
}
