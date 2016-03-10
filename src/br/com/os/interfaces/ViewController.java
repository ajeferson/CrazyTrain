package br.com.os.interfaces;

public interface ViewController {

	/** Sets up the ViewController. Must be called only once. */
	public void build(ItemHandler itemHandler);
	
	/** Must show the UI */
	public void open();
	
	/** Must reset the UI to its first state. (Usage: when opening a ViewController after the first time). */
	public void reset();
	
}
