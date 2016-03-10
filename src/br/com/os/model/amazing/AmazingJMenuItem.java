package br.com.os.model.amazing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import br.com.os.interfaces.ViewController;

public class AmazingJMenuItem extends JMenuItem implements ActionListener {

	private static final long serialVersionUID = 3247851999765489253L;

	private String targetClassName;

	public AmazingJMenuItem(String text) {
		super(text);
		this.addActionListener(this);
	}

	public String getTargetClassName() {
		return targetClassName;
	}

	public void setTargetClassName(String targetClassName) {
		this.targetClassName = targetClassName;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.targetClassName != null) {
			Class<?> klass;
			try {
				klass = Class.forName("br.com.os.ui." + this.targetClassName);
				ViewController m = (ViewController) klass.newInstance();
				m.init();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
		} else {
			System.err.println("No target class name provided");
		}
	}

}
