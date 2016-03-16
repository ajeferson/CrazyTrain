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

import br.com.os.interfaces.ViewControllerDelegate;
import br.com.os.interfaces.ViewController;
import br.com.os.model.RollerCoaster;

public class RollerCoasterViewController extends JFrame implements ViewController, ChangeListener, ActionListener {

	private static final long serialVersionUID = -914864416118572007L;

	private Container container;
	private JTextField textFieldTravellingTime, textFieldSeats;
	private JSlider sliderTravellingTime, sliderSeats;
	private JButton createButton;

	// Constants
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 230;
	private static final int INITIAL_TRAVELLING_TIME = 5;
	private static final int INITIAL_SEATS = 2;

	private ViewControllerDelegate itemHandler;

	public RollerCoasterViewController() {
		super("Nova Montanha Russa");
	}

	// ViewController implement
	
	@Override
	public void build(ViewControllerDelegate itemHandler) {
		this.container = this.getContentPane();
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.itemHandler = itemHandler;
		this.setResizable(false);
		this.addComponents();
	}

	@Override
	public void open() {
		this.setVisible(true);
	}
	
	@Override
	public void reset() {
		this.textFieldTravellingTime.setText(INITIAL_TRAVELLING_TIME + "s");
		this.sliderTravellingTime.setValue(INITIAL_TRAVELLING_TIME);
		this.textFieldSeats.setText("" + INITIAL_SEATS);
		this.sliderSeats.setValue(INITIAL_SEATS);
	}
	
	@Override
	public JFrame getFrame() {
		return this;
	}

	/** Addss all the components to the JFrame. */
	private void addComponents() {
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 1, 0, 20));
		
		// Panel travelling time
		JPanel panelTravellingTime = new JPanel();
		panelTravellingTime.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panelTopTravellingTime = new JPanel();
		panelTopTravellingTime.setLayout(new FlowLayout(FlowLayout.CENTER));

		panelTopTravellingTime.add(new JLabel("Tempo de viagem:"));

		this.textFieldTravellingTime = new JTextField(5);
		this.textFieldTravellingTime.setEditable(false);
		panelTopTravellingTime.add(this.textFieldTravellingTime);

		panelTravellingTime.add(panelTopTravellingTime);

		this.sliderTravellingTime = new JSlider(5, 60);
		this.sliderTravellingTime.setPaintTicks(true);
		this.sliderTravellingTime.setMajorTickSpacing(5);
		this.sliderTravellingTime.setMinorTickSpacing(5);
		this.sliderTravellingTime.setPaintLabels(true);
		this.sliderTravellingTime.addChangeListener(this);
		panelTravellingTime.add(this.sliderTravellingTime);
		
		// Panel Seats
		JPanel panelSeats = new JPanel();
		panelSeats.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panelSeatsTop = new JPanel();
		panelSeatsTop.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		panelSeatsTop.add(new JLabel("Quantidade de assentos:"));
		
		this.textFieldSeats = new JTextField(5);
		this.textFieldSeats.setEditable(false);
		panelSeatsTop.add(this.textFieldSeats);
		
		panelSeats.add(panelSeatsTop);
		
		this.sliderSeats = new JSlider(2, 10);
		this.sliderSeats.setPaintTicks(true);
		this.sliderSeats.setMajorTickSpacing(2);
		this.sliderSeats.setMinorTickSpacing(2);
		this.sliderSeats.setSnapToTicks(true);
		this.sliderSeats.setPaintLabels(true);
		this.sliderSeats.addChangeListener(this);
		panelSeats.add(this.sliderSeats);
		
		centerPanel.add(panelTravellingTime);
		centerPanel.add(panelSeats);
		
		this.container.add(centerPanel, BorderLayout.CENTER);

		this.createButton = new JButton("Criar");
		this.createButton.addActionListener(this);
		this.container.add(this.createButton, BorderLayout.SOUTH);
		
		this.reset();

	}

	public static void main(String[] args) {
		new RollerCoasterViewController();
	}

	// Change Listener

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if(source == this.sliderTravellingTime) {
			this.textFieldTravellingTime.setText("" + source.getValue() + "s");
		} else {
			this.textFieldSeats.setText("" + source.getValue());
		}
	}

	// Action Listener

	@Override
	public void actionPerformed(ActionEvent e) {
		this.itemHandler.didProduceItem(this, new RollerCoaster(Integer.parseInt(this.textFieldSeats.getText()),
				1000 * Integer.parseInt(this.textFieldTravellingTime.getText()
						.substring(0, this.textFieldTravellingTime.getText().length() - 1)
						)
				));
	}

	@Override
	public JButton getActionButton() {
		return this.createButton;
	}

}
