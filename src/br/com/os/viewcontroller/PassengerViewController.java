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

import br.com.os.interfaces.ViewControllerDelegate;
import br.com.os.interfaces.ViewController;
import br.com.os.model.Passenger;

/** ViewController for adding new passengers. */
public class PassengerViewController extends JFrame implements ViewController, ActionListener, ChangeListener {

	private static final long serialVersionUID = -5719443095013144413L;

	// Constants
	private static final int SLIDER_HEIGHT = 45;
	private static final int SLIDER_WIDTH = 500;
	private static final int SPACE_BETWEEN = 20;
	private static final int INITIAL_ENTERING_TIME = 1;
	private static final int INITIAL_LEAVING_TIME = 1;

	// View attrs
	private Container container;
	private JSlider sliderEntering, sliderLeaving;
	private JTextField textFieldId, textFieldEntering, textFieldLeaving;
	private JButton createButton;

	private ViewControllerDelegate intemHandler;

	public PassengerViewController() {
		super("Novo Passageiro");
	}

	@Override
	public void build(ViewControllerDelegate itemHandler) {
		this.intemHandler = itemHandler;
		this.container = this.getContentPane();
		this.addComponents();
		this.pack();
		this.setResizable(false);
	}

	private void addComponents() {

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Id
		JPanel panelFlowId = new JPanel();
		panelFlowId.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelFlowId.add(new JLabel("Id:"));
		this.textFieldId = this.coolTextField();
		panelFlowId.add(this.textFieldId);
		panel.add(panelFlowId);
		
		panel.add(Box.createRigidArea(new Dimension(10, SPACE_BETWEEN/2)));

		// Entering time
		JPanel panelEntering = new JPanel();
		panelEntering.setLayout(new BoxLayout(panelEntering, BoxLayout.Y_AXIS));
		JPanel panelFlowEntering = new JPanel();
		panelFlowEntering.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelFlowEntering.add(new JLabel("Tempo de embarque:"));
		this.textFieldEntering = this.coolTextField();
		panelFlowEntering.add(this.textFieldEntering);
		panelEntering.add(panelFlowEntering);
		this.sliderEntering = this.coolSlider();
		panelEntering.add(this.sliderEntering);
		panel.add(panelEntering);

		panel.add(Box.createRigidArea(new Dimension(10, SPACE_BETWEEN)));

		// Leaving time
		JPanel panelLeaving = new JPanel();
		panelLeaving.setLayout(new BoxLayout(panelLeaving, BoxLayout.Y_AXIS));
		JPanel panelFlowLeaving = new JPanel();
		panelFlowLeaving.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelFlowLeaving.add(new JLabel("Tempo de embarque:"));
		this.textFieldLeaving = this.coolTextField();
		panelFlowLeaving.add(this.textFieldLeaving);
		panelLeaving.add(panelFlowLeaving);
		this.sliderLeaving = this.coolSlider();
		panelLeaving.add(this.sliderLeaving);
		panel.add(panelLeaving);

		panel.add(Box.createRigidArea(new Dimension(10, SPACE_BETWEEN)));
		
		this.container.add(panel, BorderLayout.CENTER);

		// Create Passenger Button
		this.createButton = new JButton("Criar Passageiro");
		this.createButton.addActionListener(this);
		this.container.add(this.createButton, BorderLayout.SOUTH);

	}

	/** Builds a setup slider */
	private JSlider coolSlider() {
		JSlider slider = new JSlider(1, 15, 1);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setPreferredSize(new Dimension(SLIDER_WIDTH, SLIDER_HEIGHT));
		slider.addChangeListener(this);
		return slider;
	}

	/** Builds a formatted JTextField */
	private JTextField coolTextField() {
		JTextField textField = new JTextField(5);
		textField.setEditable(false);
		return textField;
	}

	@Override
	public void open() {
		this.setVisible(true);
	}

	@Override
	public void reset() {
		this.textFieldId.setText("" + Passenger.nextPassengerId());
		this.sliderEntering.setValue(INITIAL_ENTERING_TIME);
		this.textFieldEntering.setText(INITIAL_ENTERING_TIME + "s");
		this.sliderLeaving.setValue(INITIAL_LEAVING_TIME);
		this.textFieldLeaving.setText(INITIAL_LEAVING_TIME + "s");
	}

	@Override
	public JFrame getFrame() {
		return this;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.textFieldId.setText("" + (Passenger.nextPassengerId() + 1));
		this.intemHandler.didProduceItem(this, new Passenger(this.sliderEntering.getValue() * 1000, this.sliderLeaving.getValue() * 1000));
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider slider = (JSlider) e.getSource();
		if(slider == this.sliderEntering) {
			this.textFieldEntering.setText(slider.getValue() + "s");
		} else {
			this.textFieldLeaving.setText(slider.getValue() + "s");
		}
	}

	@Override
	public JButton getActionButton() {
		return this.createButton;
	}

}
