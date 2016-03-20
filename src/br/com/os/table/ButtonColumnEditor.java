package br.com.os.table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class ButtonColumnEditor extends DefaultCellEditor implements ActionListener {
	
	private static final long serialVersionUID = -6301739509935019237L;
	
	protected JButton button;
	private String label;
	private boolean isPushed;
	private ListViewControllerDelegate delegate;
	
	public ButtonColumnEditor(ListViewControllerDelegate delegate) {
		super(new JCheckBox());
		this.button = new JButton();
		this.button.setOpaque(true);
		this.button.addActionListener(this);
		this.delegate = delegate;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		
		if(isSelected) {
			this.button.setForeground(table.getSelectionForeground());
			this.button.setBackground(table.getSelectionBackground());
		} else {
			this.button.setForeground(table.getForeground());
			this.button.setBackground(table.getBackground());
		}
		
		if(value != null) {
			label = value.toString();
			this.delegate.didSelectRowAtIndex(row);
		} else {
			label = "";
		}
		
		this.button.setText(label);
		this.isPushed = true;
		
		return this.button;
	}
	
	@Override
	public Object getCellEditorValue() {
		if(this.isPushed) {
		}
		this.isPushed = false;
		return label;
	}
	
	@Override
	public boolean stopCellEditing() {
		this.isPushed = false;
		return super.stopCellEditing();
	}
	
	@Override
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		fireEditingStopped();
	}

}
