package br.com.os.table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import br.com.os.interfaces.ListViewController;
import br.com.os.interfaces.ListViewControllerDelegate;

/** Represents the editor of the JTable. It's kind of a delegate for when editing the JTable. */
public class ButtonColumnEditor extends DefaultCellEditor implements ActionListener {
	
	private static final long serialVersionUID = -6301739509935019237L;
	
	protected JButton button;
	private String label;
	private boolean isPushed;
	private ListViewControllerDelegate delegate;
	private ListViewController listViewController;
	
	public ButtonColumnEditor(ListViewController listViewController, ListViewControllerDelegate delegate) {
		super(new JCheckBox());
		this.listViewController = listViewController;
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
			if(this.delegate != null) {
				this.delegate.didSelectRowAtIndex(this.listViewController, row);
			}
		} else {
			this.button.setForeground(table.getForeground());
			this.button.setBackground(table.getBackground());
		}
		
		if(value != null) {
			label = value.toString();
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
