package br.com.os.viewcontroller;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import br.com.os.interfaces.ListViewController;
import br.com.os.interfaces.ListViewControllerDataSource;
import br.com.os.interfaces.ListViewControllerDelegate;
import br.com.os.interfaces.ViewControllerDelegate;
import br.com.os.table.ButtonColumnEditor;
import br.com.os.table.ButtonColumnRenderer;
import br.com.os.table.ItemDataModel;

public class ItemListViewController extends JFrame implements ListViewController {

	private static final long serialVersionUID = -2654082844290897483L;
	private final String identifier;
	
	private JTable table;
	private ItemDataModel dataModel;
	
	public ItemListViewController(String title, String identifier, ListViewControllerDataSource dataSource) {
		super(title);
		this.identifier = identifier;
		this.dataModel = new ItemDataModel(dataSource, this);
	}

	@Override
	public void open() {
		this.setVisible(true);
	}

	@Override
	public void reset() {
	}

	@Override
	public JFrame getFrame() {
		return this;
	}

	@Override
	public JButton getActionButton() {
		return null;
	}
	
	@Override
	public void build(ViewControllerDelegate itemHandler) {
		
		// Building table
		this.table = new JTable(this.dataModel);
		
		// Making id column to have center alignment
		DefaultTableCellRenderer centeteredRowRenderer = new DefaultTableCellRenderer();
		centeteredRowRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.table.getColumn("ID").setCellRenderer(centeteredRowRenderer);
		this.table.getTableHeader().setDefaultRenderer(centeteredRowRenderer);
		
		// Making the deletion column to be rendered as button
		this.table.getColumn("Excluir").setCellRenderer(new ButtonColumnRenderer());
		this.table.getColumn("Excluir").setCellEditor(new ButtonColumnEditor(this, (ListViewControllerDelegate) itemHandler));
		
		this.table.setPreferredScrollableViewportSize(this.table.getPreferredSize());
		
		// Building scrollpane
		JScrollPane scrollPane = new JScrollPane(this.table);
		this.getContentPane().add(scrollPane);
		
		this.setSize(250, 250);
		this.setResizable(false);
		
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension((int) this.getPreferredSize().getWidth(), 100);
	}

	@Override
	public void updateListView() {
		this.table.updateUI();
	}

	
	// Getters and Setters
	@Override
	public String getIdentifier() {
		return this.identifier;
	}
	
}
