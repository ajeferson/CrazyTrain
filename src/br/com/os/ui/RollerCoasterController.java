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

public class RollerCoasterController extends JFrame implements ChangeListener, ActionListener {

	private static final long serialVersionUID = -914864416118572007L;

	private Container container;
	private JTextField textFieldTravellingtime;

	// Constants
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 130;

	public RollerCoasterController() {
		this.setup();
	}

	private void setup() {
		this.container = this.getContentPane();
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Controlador de Montanha Russa");
		this.addElements();
		this.setVisible(true);
	}

	private void addElements() {

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
		new RollerCoasterController();
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
		System.out.println("Create roller coaster here...");
	}

}
