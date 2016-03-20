package br.com.os.viewcontroller;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import br.com.os.interfaces.ViewControllerDelegate;
import br.com.os.interfaces.ViewController;
import br.com.os.model.amazing.AmazingJMenuItem;
import br.com.os.ui.Scene;

/** Represents the main window of the program. It contains the landscape and the menu items to do things. */
public class MainViewController extends JFrame implements ViewController {

	private static final long serialVersionUID = -598156911230782190L;


	// View attrs
	private Container container;
	private Scene scene = new Scene();

	public MainViewController() {
		super("Vagão Maluco");
	}

	/** Adds the menu items. */
	private void addComponents() {

		JMenuBar menuBar = new JMenuBar();

		// File menu
		JMenu menuFile = new JMenu("Arquivo");

		// New submenu
		JMenu subMenuNew = new JMenu("Novo");

		// New Roller Coaster item
		RollerCoasterViewController rollerCoasterViewController = new RollerCoasterViewController();
		rollerCoasterViewController.build(this.scene);
		AmazingJMenuItem itemNewRollerCoaster = new AmazingJMenuItem("Vagão", rollerCoasterViewController);
		itemNewRollerCoaster.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		subMenuNew.add(itemNewRollerCoaster);
		rollerCoasterViewController = null;

		// New Passenger item
		PassengerViewController passengerViewController = new PassengerViewController();
		passengerViewController.build(this.scene);
		JMenuItem itemNewPassenger = new AmazingJMenuItem("Passageiro", passengerViewController);
		itemNewPassenger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		subMenuNew.add(itemNewPassenger);
		passengerViewController = null;

		menuFile.add(subMenuNew);
		menuBar.add(menuFile);

		// Edit menu
		JMenu menuEdit = new JMenu("Editar");

		// List submenu
		JMenu subMenuList = new JMenu("Lista");

		// List Roller Coaster item
		ItemListViewController rollerCoasterListViewController = new ItemListViewController("Lista de vagões", "RollerCoasterList", this.scene);
		this.scene.setRollerCoasterListViewController(rollerCoasterListViewController);
		rollerCoasterListViewController.build(this.scene);
		JMenuItem itemListRollerCoaster = new AmazingJMenuItem("Vagões", rollerCoasterListViewController);
		itemListRollerCoaster.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
		subMenuList.add(itemListRollerCoaster);
		rollerCoasterListViewController = null;

		// New Passenger item
		ItemListViewController passengerListViewController = new ItemListViewController("Lista de passageiros", "PassengersList", this.scene);
		this.scene.setPassengerListViewController(passengerListViewController);
		passengerListViewController.build(this.scene);
		JMenuItem itemListPassenger = new AmazingJMenuItem("Passageiros", passengerListViewController);
		itemListPassenger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.ALT_MASK));
		subMenuList.add(itemListPassenger);
		passengerListViewController = null;

		menuEdit.add(subMenuList);
		menuBar.add(menuEdit);

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

}
