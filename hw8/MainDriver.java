package hw8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import hw5.DirectedEdge;

/**
 * The main driver that allows the user to roam the campus
 * 
 * @author Sarvagya Gupta
 */
public class MainDriver {
	/**
	 * The method that is responsible for the flow of control
	 * 
	 * @param args Takes in arguments from the command line
	 */
	public static void main(String[] args) {
		CampusPaths campus = new CampusPaths();
		menu();
		Scanner console = new Scanner(System.in);
		while (true) {
			System.out.print("Enter an option ('m' to see the menu): ");
			String userInput = console.nextLine();
			while(userInput.startsWith("#") || userInput.isEmpty()) {
				System.out.println(userInput);
				userInput = console.nextLine();
			}
			if (userInput.equals("b")) {
				listBuildings(campus);
			} else if (userInput.equals("r")) {
				findPath(console, campus);
			} else if (userInput.equals("m")) {
				menu();
			} else if (userInput.equals("q")) {
				break;
			} else {
				System.out.println("Unknown option");
				System.out.println();
			}
		}
		console.close();
	}
	
	/**
	 * List all the buildings on the campus
	 * 
	 * @param campus The campus of which the buildings have to printed
	 */
	private static void listBuildings(CampusPaths campus) {
		Set<String> buildings = campus.getBuildings();
		List<String> temp = new ArrayList<>(buildings);
		Collections.sort(temp);
		System.out.println("Buildings: ");
		for (String building: temp) {
			System.out.println("\t" + building);
		}
		System.out.println();
	}

	/**
	 * Prints the menu of the program
	 */
	private static void menu() {
		System.out.println("Menu:");
		System.out.println("\tr to find a route");
		System.out.println("\tb to see a list of all buildings");
		System.out.println("\tq to quit");
		System.out.println();
	}

	/**
	 * Finds the shortest path from one character to an another in the graph
	 * 
	 * @param console Scanner that takes in user input
	 * @param campus The campus in which the path is to be searched
	 * @param graph MyGraph in which the path has to be found
	 */
	private static void findPath(Scanner console, CampusPaths campus) {
		// Get building names from the user
		System.out.print("Abbreviated name of starting building: ");
		String startNode = console.nextLine();
		System.out.print("Abbreviated name of ending building: ");
		String endNode = console.nextLine();
		
		// Check if the buildings exist
		boolean flag = true;
		if (!campus.isBuilding(startNode)) {
			System.out.println("Unknown building: " + startNode);
			flag = false;
		}
		if (!campus.isBuilding(endNode)) {
			System.out.println("Unknown building: " + endNode);
			flag = false;
		}
		if (!flag) {
			System.out.println();
			return;
		}
		
		// Get the path between the nodes
		List<DirectedEdge<Double, MyPoint>> path = campus.dijkstraSearch(startNode, endNode);
		
		// Print it out to the user
		Double totalDist = 0.0;
		MyPoint prev = campus.getPosBuilding(startNode);
		System.out.println("Path from " + campus.getFullName(startNode) + " to "
				+ campus.getFullName(endNode) + ":");
		for (DirectedEdge<Double, MyPoint> edge: path) {
			System.out.println("\tWalk " + round(edge.getLabel()) + " feet " + 
					campus.getDirection(prev, edge.getDestinationNode()) + " to " + 
					edge.getDestinationNode());
			prev = edge.getDestinationNode();
			totalDist += edge.getLabel();
		}
		if (!startNode.equals(endNode))
			System.out.println("Total distance: " + round(totalDist) + " feet");
		System.out.println();
	}
	
	private static long round(double val) {
		return Math.round(val);
	}
}
