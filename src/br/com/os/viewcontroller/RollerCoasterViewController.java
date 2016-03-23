package br.com.os.viewcontroller;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.os.interfaces.ViewController;
import br.com.os.interfaces.ViewControllerDelegate;
import br.com.os.model.RollerCoaster;

public class RollerCoasterViewController extends JFrame implements ViewController, ChangeListener, ActionListener {

	private static final long serialVersionUID = -914864416118572007L;

	private Container container;
	private JTextField textFieldTravellingTime, textFieldSeats;
	private JSlider sliderTravellingTime, sliderSeats;
	private JButton createButton;

	// Constants
	private static final int INITIAL_TRAVELLING_TIME = 5;
	private static final int INITIAL_SEATS = 2;

	private ViewControllerDelegate itemHandler;

	public RollerCoasterViewController() {
		super("Novo Vagão");
	}

	// ViewController implement
	
	@Override
	public void build(ViewControllerDelegate itemHandler) {
		this.container = this.getContentPane();
		this.itemHandler = itemHandler;
		this.setResizable(false);
		this.addComponents();
		this.pack();
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

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Travelling time
		JPanel panelTravelling = new JPanel();
		panelTravelling.setLayout(new BoxLayout(panelTravelling, BoxLayout.Y_AXIS));
		JPanel panelFlowTravelling = new JPanel();
		panelFlowTravelling.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelFlowTravelling.add(new JLabel("Tempo de viagem:"));
		this.textFieldTravellingTime = this.coolTextField();
		panelFlowTravelling.add(this.textFieldTravellingTime);
		panelTravelling.add(panelFlowTravelling);
		this.sliderTravellingTime = this.coolSlider(5, 60, 5);
		panelTravelling.add(this.sliderTravellingTime);
		panel.add(panelTravelling);

		panel.add(Box.createRigidArea(new Dimension(10, 20)));

		// Leaving time
		JPanel panelSeats = new JPanel();
		panelSeats.setLayout(new BoxLayout(panelSeats, BoxLayout.Y_AXIS));
		JPanel panelFlowSeats = new JPanel();
		panelFlowSeats.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelFlowSeats.add(new JLabel("Número de assentos:"));
		this.textFieldSeats = this.coolTextField();
		panelFlowSeats.add(this.textFieldSeats);
		panelSeats.add(panelFlowSeats);
		this.sliderSeats = this.coolSlider(2, 10, 2);
		panelSeats.add(this.sliderSeats);
		panel.add(panelSeats);

		panel.add(Box.createRigidArea(new Dimension(10, 20)));
		
		this.container.add(panel, BorderLayout.CENTER);

		// Create Passenger Button
		this.createButton = new JButton("Criar Vagão");
		this.createButton.addActionListener(this);
		this.container.add(this.createButton, BorderLayout.SOUTH);
		
		this.reset();


	}
	
	/** Builds a formatted JTextField */
	private JTextField coolTextField() {
		JTextField textField = new JTextField(5);
		textField.setEditable(false);
		return textField;
	}

	/** Builds a setup slider */
	private JSlider coolSlider(int min, int max, int value) {
		JSlider slider = new JSlider(min, max, value);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(value);
		slider.setMinorTickSpacing(value);
		slider.setSnapToTicks(true);
		slider.setPaintLabels(true);
		slider.setPreferredSize(new Dimension(500, 45));
		slider.addChangeListener(this);
		return slider;
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
