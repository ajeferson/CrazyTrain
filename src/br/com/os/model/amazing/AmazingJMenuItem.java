package br.com.os.model.amazing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import br.com.os.interfaces.ViewControllerDelegate;
import br.com.os.interfaces.ViewController;

/** Encapsulates a JMenuItem to a menu that opens a ViewController with an ItemHandler.
 * @see ViewController
 * @see ViewControllerDelegate */
public class AmazingJMenuItem extends JMenuItem implements ActionListener {

	private static final long serialVersionUID = 3247851999765489253L;

	private ViewController viewController;
	private String viewControllerName;
	private ViewControllerDelegate itemHandler;

	/** Builds an AmazingJMenuItem.
	 * @param text The text that is going to appear on the menu.
	 * @param viewControllerName The name of the ViewController that is going to open when a click happen on this menu.
	 * @param itemHandler The ItemHandler that the ViewController will use. */
	public AmazingJMenuItem(String text, String viewControllerName, ViewControllerDelegate itemHandler) {
		super(text);
		this.addActionListener(this);
		this.viewControllerName = viewControllerName;
		this.itemHandler = itemHandler;
	}
	
	/** Builds the ViewController associated with this menu.
	 * @param name The ViewController name to instantiate.
	 * @param itemHandler The ItemHandler associated to the ViewController. */
	private void buildTargetClassWithName(String name, ViewControllerDelegate itemHandler) {
		Class<?> klass;
		try {
			klass = Class.forName("br.com.os.ui." + name);
			this.viewController = (ViewController) klass.newInstance();
			this.viewController.build(itemHandler);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}

	
	// ActionListener implement
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.viewController == null) {
			this.buildTargetClassWithName(this.viewControllerName, this.itemHandler);
			this.viewControllerName = null;
			this.itemHandler = null;
		}
		if(this.viewController.getFrame().isVisible()) {
			this.viewController.getFrame().requestFocus();
		} else {
			this.viewController.reset();
		}
		this.viewController.open();
	}
	
	public ViewController getViewController() {
		return viewController;
	}

}
