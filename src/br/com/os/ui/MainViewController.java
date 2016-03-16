package br.com.os.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import br.com.os.interfaces.ViewControllerDelegate;
import br.com.os.interfaces.ViewController;
import br.com.os.model.amazing.AmazingJMenuItem;

/** Represents the main window of the program. It contains the landscape and the menu items to do things. */
public class MainViewController extends JFrame implements ViewController {

	private static final long serialVersionUID = -598156911230782190L;


	// View attrs
	private Container container;
	private Scene scene = new Scene();

	public MainViewController() {
		super("Montanha Russa");
	}

	/** Adds the menu items. */
	private void addComponents() {

		JMenuBar menuBar = new JMenuBar();

		// File menu
		JMenu menuFile = new JMenu("Arquivo");

		// New submenu
		JMenu subMenuNew = new JMenu("Novo");

		// New Roller Coaster item
		AmazingJMenuItem itemNewRollerCoaster = new AmazingJMenuItem("Montanha Russa", "RollerCoasterViewController", this.scene);
		itemNewRollerCoaster.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		subMenuNew.add(itemNewRollerCoaster);

		// New Passenger item
		JMenuItem itemNewPassenger = new AmazingJMenuItem("Passageiro", "PassengerViewController", this.scene);
		itemNewPassenger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		subMenuNew.add(itemNewPassenger);

		menuFile.add(subMenuNew);
		menuBar.add(menuFile);

		// Edit menu
		JMenu menuEdit = new JMenu("Editar");

		// List submenu
		JMenu subMenuList = new JMenu("Lista");

		// List Roller Coaster item
		JMenuItem itemListRollerCoaster = new JMenuItem("Montanhas Russas");
		itemListRollerCoaster.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
		subMenuList.add(itemListRollerCoaster);

		// New Passenger item
		JMenuItem itemListPassenger = new JMenuItem("Passageiros");
		itemListPassenger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.ALT_MASK));
		subMenuList.add(itemListPassenger);

		menuEdit.add(subMenuList);
		menuBar.add(menuEdit);

		// Help menu
		JMenu menuHelp = new JMenu("Ajuda");

		// Help item
		JMenuItem itemHelp = new JMenuItem("Ajuda");
		itemHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.ALT_MASK));
		menuHelp.add(itemHelp);

		// About item
		JMenuItem itemAbout = new JMenuItem("Sobre");
		itemAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, ActionEvent.ALT_MASK));
		menuHelp.add(itemAbout);

		menuBar.add(menuHelp);

		this.setJMenuBar(menuBar);

	}

	// ViewController implement

	@Override
	public void build(ViewControllerDelegate itemHandler) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.container = this.getContentPane();
		this.addComponents();
		this.container.add(this.scene);
		this.pack();
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
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				MainViewController main = new MainViewController();
				main.build(null);
				main.open();
			}
		});
	}

}
