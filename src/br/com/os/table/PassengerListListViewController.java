package br.com.os.table;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import br.com.os.interfaces.Item;
import br.com.os.model.Passenger;

public class PassengerListListViewController extends JFrame implements ListViewControllerDelegate {

	private static final long serialVersionUID = -2654082844290897483L;
	
	private JTable table;
	private ItemDataModel dataModel;
	
	public PassengerListListViewController(ArrayList<Item> items) {
		super("Lista de passageiros");
		this.dataModel = new ItemDataModel(items);
		this.build();
		this.setSize(250, 250);
		this.setResizable(false);
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
		this.table.getColumn("Excluir").setCellEditor(new ButtonColumnEditor(this));
		
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
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
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
		});
	}

	@Override
	public void didSelectRowAtIndex(int index) {
		((ItemDataModel) this.table.getModel()).removeRow(index);
	}
	
}
