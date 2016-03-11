package br.com.os.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import br.com.os.interfaces.Item;
import br.com.os.interfaces.ItemHandler;
import br.com.os.interfaces.SemaphoreController;
import br.com.os.interfaces.ViewController;
import br.com.os.model.RollerCoaster;
import br.com.os.model.amazing.AmazingJMenuItem;
import br.com.os.model.amazing.AmazingSemaphore;

/** Represents the main window of the program. It contains the landscape and the menu items to do things. */
public class MainViewController extends JFrame implements ViewController, ItemHandler, SemaphoreController {

	private static final long serialVersionUID = -598156911230782190L;

	// Semaphores
	private AmazingSemaphore semaphoreLine = new AmazingSemaphore(0);
	private AmazingSemaphore semaphoreRollerCoaster = new AmazingSemaphore(0);
	private AmazingSemaphore semaphorePassengers = new AmazingSemaphore(0);
	private AmazingSemaphore semaphoreMutex = new AmazingSemaphore(1);

	// Control variables
	final int MAX_SEATS = 3;
//	private int line = 0;

	// Train and passengers
	private RollerCoaster rollerCoaster;
//	private ArrayList<Passenger> passengers = new ArrayList<Passenger>();

	// Constants
	public static final int WINDOW_WIDTH = 500;
	public static final int WINDOW_HEIGHT = 500;


	// View attrs
	private Container container;

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
		AmazingJMenuItem itemNewRollerCoaster = new AmazingJMenuItem("Montanha Russa", "RollerCoasterViewController", this);
		itemNewRollerCoaster.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		subMenuNew.add(itemNewRollerCoaster);

		// New Passenger item
		JMenuItem itemNewPassenger = new JMenuItem("Passageiro");
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

	/** Adds a roller coaster to the canvas and sets it as the current roller coaster. */
	private void handleCreationOfRollerCoaster(RollerCoaster rollerCoaster) {
		this.rollerCoaster = rollerCoaster;
		this.rollerCoaster.setController(this);
		this.addComponent(this.rollerCoaster.asView());
		this.rollerCoaster.start();
	}

	/** Adds a component to the main container and repaints the JFrame. */
	private void addComponent(Component component) {
		this.add(component);
		this.repaint();
	}

	// ViewController implement

	@Override
	public void build(ItemHandler itemHandler) {
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.container = this.getContentPane();
		this.container.setLayout(null);
		this.addComponents();
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

	// ItemHandler implement

	@Override
	public void didProduceItem(Item item) {
		if(item instanceof RollerCoaster) {
			this.handleCreationOfRollerCoaster((RollerCoaster) item);
		}
	}

	public static void main(String[] args) {
		MainViewController main = new MainViewController();
		main.build(null);
		main.open();
	}

	@Override
	public void upLine(int permits) {
		this.semaphoreLine.up(permits);
	}

	@Override
	public void upLine() {
		this.semaphoreLine.up();
	}

	@Override
	public void downLine() {
		this.semaphoreLine.down();
	}

	@Override
	public void upPassengers(int permits) {
		this.semaphorePassengers.up(permits);
	}

	@Override
	public void upPassengers() {
		this.semaphorePassengers.up();
	}

	@Override
	public void downPassengers() {
		this.semaphorePassengers.down();
	}

	@Override
	public void upRollerCoaster() {
		this.semaphoreRollerCoaster.up();
	}

	@Override
	public void downRollerCoaster() {
		this.semaphoreRollerCoaster.down();
	}

	@Override
	public void upMutex() {
		this.semaphoreMutex.up();
	}

	@Override
	public void downMutex() {
		this.semaphoreMutex.down();
	}

	@Override
	public boolean isRollerCoasterFull() {
		return this.rollerCoaster.isFull();
	}

	@Override
	public boolean isRollerCoasterMoving() {
		return this.rollerCoaster.isMoving();
	}

	@Override
	public boolean isRollerCoasterEmpty() {
		return this.rollerCoaster.isEmpty();
	}

	@Override
	public void incrementNumberOfPassengersOnRollerCoaster() {
		this.rollerCoaster.incrementOccupiedSeats();
	}

	@Override
	public void decrementNumberOfPassengersOnRollerCoaster() {
		this.rollerCoaster.decrementOccupiedSeats();
	}

}
