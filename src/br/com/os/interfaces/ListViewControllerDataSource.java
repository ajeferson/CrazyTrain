package br.com.os.interfaces;

/**  The data source for list view controllers. */
public interface ListViewControllerDataSource {
	
	public int getItemCount(ListViewController listViewController);
	public String getValueAtIndex(ListViewController listViewController, int index);
	
}
