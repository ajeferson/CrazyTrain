package br.com.os.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.os.interfaces.ItemHandler;
import br.com.os.interfaces.ViewController;
import br.com.os.model.RollerCoaster;

public class RollerCoasterViewController extends JFrame implements ViewController, ChangeListener, ActionListener {

	private static final long serialVersionUID = -914864416118572007L;

	private Container container;
	private JTextField textFieldTravellingtime;

	// Constants
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 130;

	private ItemHandler itemHandler;

	public RollerCoasterViewController() {
		super("Controlador de Montanha Russa");
	}

	@Override
	public void build(ItemHandler itemHandler) {
		this.container = this.getContentPane();
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.itemHandler = itemHandler;
		this.addComponents();
	}

	@Override
	public void open() {
		this.setVisible(true);
	}

	private void addComponents() {

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel topTopPanel = new JPanel();
		topTopPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		topTopPanel.add(new JLabel("Tempo de viagem:"));

		this.textFieldTravellingtime = new JTextField(5);
		this.textFieldTravellingtime.setEditable(false);
		this.textFieldTravellingtime.setText("5s");
		topTopPanel.add(this.textFieldTravellingtime);

		topPanel.add(topTopPanel);

		JSlider slider = new JSlider(5, 60);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(5);
		slider.setMinorTickSpacing(5);
		slider.setPaintLabels(true);
		slider.addChangeListener(this);
		topPanel.add(slider);

		this.container.add(topPanel, BorderLayout.CENTER);

		JButton addButton = new JButton("Criar");
		addButton.addActionListener(this);
		this.container.add(addButton, BorderLayout.SOUTH);

	}

	public static void main(String[] args) {
		new RollerCoasterViewController();
	}

	// Change Listener

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		this.textFieldTravellingtime.setText("" + source.getValue() + "s");
	}

	// Action Listener

	@Override
	public void actionPerformed(ActionEvent e) {
		this.itemHandler.didProduceItem(new RollerCoaster(3,
				1000 * Integer.parseInt(this.textFieldTravellingtime.getText()
						.substring(0, this.textFieldTravellingtime.getText().length() - 1)
						)
				));
	}

}
