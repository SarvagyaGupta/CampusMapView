package hw9;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hw8.CampusPaths;

/**
 * The CampusMapControl is the controller of the GUI
 * 
 * @author Sarvagya Gupta
 */
public class CampusMapControl extends JPanel {
	private static final long serialVersionUID = 1L;

	/** Stores the View of the GUI */
	private CampusGraphicsView viewPane;
	/** The campus to be loaded on the GUI */
	private CampusPaths campus;

	/** The drop down box for the current location */
	private JComboBox<String> startBox;
	/** The drop down box for the destination location */
	private JComboBox<String> endBox;
	
	/**
	 * The listener for the JButtons on the GUI
	 * 
	 * @author Sarvagya Gupta
	 */
	class ButtonListener implements ActionListener {

		/**
		 * Performs different actions based on the event, e. It either searches for a 
		 * path between the two buildings or clears the GUI for reuse
		 * 
		 * @param e The event that has occurred
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case "Search Path":
					String start = startBox.getSelectedItem().toString().split(":")[0];
					String end = endBox.getSelectedItem().toString().split(":")[0];
					viewPane.dijkstraSearch(start, end);
					break;
				case "Clear":
					viewPane.clear();
					break;
			}
		}
		
	}
	
	/**
	 * Generates the JPanel on which the GUI is built on
	 * 
	 * @param campus The campus to be loaded on the JPanel
	 */
	public CampusMapControl(CampusPaths campus) {
		// Save reference to the model
		this.campus = campus;
		
		// Change the layout of the panel to the BorderLayout
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Create panel with the map and display it
		viewPane = new CampusGraphicsView(this.campus);
		viewPane.setPreferredSize(new Dimension(1024 * 2, 768 * 2));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		this.add(viewPane, c);
		
		// Make both panels
		JPanel directionPanel = new JPanel(new GridLayout(2, 2));
		JPanel buttonPanel = new JPanel();
		
		// Create starting and ending location labels
		JLabel startLocationLabel = labelMaker("Current Location: ");
		JLabel endLocationLabel = labelMaker("Destination location: ");
		
		// Create drop downs for building
		Set<String> buildings = campus.getBuildings();
		startBox = boxMaker(buildings);
		endBox = boxMaker(buildings);
		
		// Create buttons for user to use
		JButton goButton = buttonMaker("Search Path", new Dimension(250, 75));
		JButton clearButton = buttonMaker("Clear", new Dimension(150, 75));
		
		// Add listeners to the button
		goButton.addActionListener(new ButtonListener());
		clearButton.addActionListener(new ButtonListener());
		
		// Add components to the directionPanel
		directionPanel.add(startLocationLabel);
		directionPanel.add(startBox);
		directionPanel.add(endLocationLabel);
		directionPanel.add(endBox);
		
		// Add components to the buttonPanel
		buttonPanel.add(goButton);
		addSpacing(buttonPanel);
		buttonPanel.add(clearButton);
		
		// Add both panels to the main panel
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 3;
		c.gridwidth = 1;
		c.ipady = 30;
		c.fill = GridBagConstraints.NONE;
		this.add(directionPanel, c);
		
		c.gridx = 1;
		c.ipadx = 50;
		c.weightx = 2;
		this.add(buttonPanel, c);
	}
	
	/**
	 * Makes a custom JLabel for the GUI
	 * 
	 * @param name The name of the label
	 * @return A JLabel, label, such that <code>label.getName().equals(name)</code>
	 */
	private JLabel labelMaker(String name) {
		JLabel label = new JLabel(name);
		label.setFont(new Font("Arial", Font.PLAIN, 30));
		return label;
	}
	
	/**
	 * Makes a custom JButton for the GUI
	 * 
	 * @param name The name of the button
	 * @param dim The dimensions of the button
	 * @return A JButton, button, such that 
	 * 			<code>button.getName().equals(name) && button.getSize().equals(dim)</code>
	 */
	private JButton buttonMaker(String name, Dimension dim) {
		JButton button = new JButton(name);
		button.setPreferredSize(dim);
		button.setFont(new Font("Arial", Font.PLAIN, 30));
		return button;
	}
	
	/**
	 * Makes a custom JComboBox for the GUI
	 * 
	 * @param buildings The set of values in the JComboBox
	 * @return A JComboBox, box
	 */
	private JComboBox<String> boxMaker(Set<String> buildings) {
		String[] list = convert(buildings);
		JComboBox<String> box = new JComboBox<>(list);
		box.setPreferredSize(new Dimension(450, 75));
		box.setFont(new Font("Arial", Font.PLAIN, 30));
		return box;
	}
	
	/**
	 * Converts the set of buildings to a list of buildings
	 * 
	 * @param buildings The set to be converted
	 * @return The list, l, that contains all the things in the buildings in sorted
	 * 			order.
	 */
	private String[] convert(Set<String> buildings) {
		String[] arr = new String[buildings.size()];
		int index = 0;
		for (String building: buildings) {
			arr[index++] = building;
		}
		Arrays.sort(arr);
		return arr;
	}

	/**
	 * Adds spacing to the JPanel
	 * 
	 * @param panel The JPanel to which the spacing is to be added
	 */
	private void addSpacing(JPanel panel) {
		panel.add(Box.createRigidArea(new Dimension(20, 0)));
	}
}
