package hw9;

import javax.swing.JFrame;

import hw8.CampusPaths;

/**
 * The driver for the GUI
 * 
 * @author Sarvagya Gupta
 */
public class CampusPathsMain {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Campus Map");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CampusMapControl controller = new CampusMapControl(new CampusPaths());
		frame.add(controller);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(true);
	}
}
