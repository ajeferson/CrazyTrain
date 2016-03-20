import javax.swing.SwingUtilities;

import br.com.os.viewcontroller.MainViewController;

public class Main {

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
