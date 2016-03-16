package br.com.os.interfaces;

import javax.swing.JButton;
import javax.swing.JFrame;

public interface ViewController {

	/** Sets up the ViewController. Must be called only once. */
	public void build(ViewControllerDelegate itemHandler);
	
	/** Must show the UI. */
	public void open();
	
	/** Must reset the UI to its first state. (Usage: when opening a ViewController after the first time). */
	public void reset();
	
	/** Returns the JFrame associated with the ViewController. */
	public JFrame getFrame();
	
	/** Returns the button that does all the action on the ViewController. */
	public JButton getActionButton();
	
}
