package br.com.os.interfaces;

public interface ListViewControllerDataSource {
	
	public int getItemCount();
	public String getValueAtIndex(int index);
	public boolean activatedButtonAtIndex(int index);
}
