package br.com.os.interfaces;

public interface ViewController {

	public void build(ItemHandler itemHandler);
	
	/** Must show the UI */
	public void open();
	
}
