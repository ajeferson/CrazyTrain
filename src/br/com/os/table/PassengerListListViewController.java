package br.com.os.table;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import br.com.os.interfaces.Item;
import br.com.os.model.Passenger;

public class PassengerListListViewController extends JFrame {

	private static final long serialVersionUID = -2654082844290897483L;
	
	private JTable table;
	private ItemDataModel dataModel;
	
	public PassengerListListViewController(ArrayList<Item> items) {
		super("Lista de passageiros");
		this.dataModel = new ItemDataModel(items);
		this.build();
		this.pack();
		this.setVisible(true);
	}
	
	private void build() {
		
		// Building table
		this.table = new JTable(this.dataModel);
		
		// Making id column to have center alignment
		DefaultTableCellRenderer centeteredRowRenderer = new DefaultTableCellRenderer();
		centeteredRowRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.table.getColumn("ID").setCellRenderer(centeteredRowRenderer);
		this.table.getTableHeader().setDefaultRenderer(centeteredRowRenderer);
		
		// Making the deletion column to be rendered as button
		this.table.getColumn("Excluir").setCellRenderer(new ButtonColumnRenderer());
		this.table.getColumn("Excluir").setCellEditor(new ButtonColumnEditor(new JCheckBox()));
		
		this.table.setPreferredScrollableViewportSize(this.table.getPreferredSize());
		
		// Building scrollpane
		JScrollPane scrollPane = new JScrollPane(this.table);
		this.getContentPane().add(scrollPane);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension((int) this.getPreferredSize().getWidth(), 100);
	}
	
	public static void main(String[] args) {
		ArrayList<Item> passengers = new ArrayList<Item>();
		passengers.add(new Passenger(100, 100));
		passengers.add(new Passenger(100, 100));
		passengers.add(new Passenger(100, 100));
		passengers.add(new Passenger(100, 100));
		passengers.add(new Passenger(100, 100));
		passengers.add(new Passenger(100, 100));
		passengers.add(new Passenger(100, 100));
		passengers.add(new Passenger(100, 100));
		passengers.add(new Passenger(100, 100));
		passengers.add(new Passenger(100, 100));
		passengers.add(new Passenger(100, 100));
		passengers.add(new Passenger(100, 100));
		passengers.add(new Passenger(100, 100));
		passengers.add(new Passenger(100, 100));
		passengers.add(new Passenger(100, 100));
		passengers.add(new Passenger(100, 100));
		passengers.add(new Passenger(100, 100));
		new PassengerListListViewController(passengers);
	}
	
}
