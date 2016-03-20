package br.com.os.interfaces;

/** Actions that take place on a ListViewController */
public interface ListViewControllerDelegate {

	public void didSelectRowAtIndex(ListViewController listViewController, int index);
	
}
