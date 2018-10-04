package hw6;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import hw5.DirectedEdge;
import hw5.MyGraph;
import hw6.MarvelParser.MalformedDataException;

public class MarvelPaths {
	
	/**
	 * Runs the parser
	 * 
	 * @param args A list of command line arguments
	 */
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		MyGraph<String, String> graph = buildGraph(console);
		findPath(console, graph);
		console.close();
	}
	
	/**
	 * Builds and returns the graph by the file specified by the user
	 * 
	 * @param console Scanner that takes in user input
	 * @return MyGraph
	 */
	private static MyGraph<String, String> buildGraph(Scanner console) {
		System.out.println("Welcome to the Marvel Universe");
		System.out.println();
		System.out.print("Enter the file name: ");
		String name = console.nextLine();
		File file = new File("src/hw6/data/" + name);
		while (!file.exists()) {
			System.err.println("Invalid file...");
			System.out.print("Enter a valid file name: ");
			name = console.nextLine();
			file = new File("src/hw6/data/" + name);
		}
		System.out.println();
		return loadGraph(name);
	}
	
	/**
	 * Finds the shortest path from one character to an another in the graph
	 * 
	 * @param console Scanner that takes in user input
	 * @param graph MyGraph in which the path has to be found
	 */
	private static void findPath(Scanner console, MyGraph<String, String> graph) {
		System.out.println("Graph is now ready for use...");
		System.out.print("Enter the starting character: ");
		String start = console.nextLine();
		System.out.print("Enter the ending character: ");
		String end = console.nextLine();
		System.out.println(bfsPathSearch(graph, start, end));
	}
	
	/**
	 * Loads the graph using the specified file
	 * 
	 * @param fileName The file to be processed
	 * @return MyGraph, graph, such that graph has all the characters and the
	 * 			character that they are linked to.
	 */
	public static MyGraph<String, String> loadGraph(String fileName) {
		MyGraph<String, String> graph = new MyGraph<>();
		try {
			Set<String> characters = new HashSet<>();
			Map<String, List<String>> books = new HashMap<>();
			MarvelParser.parseData("src/hw6/data/" + fileName, characters, books);
			graph.addNodes(characters);
			Set<String> keys = books.keySet();
			for(String key: keys) {
				List<String> nodes = books.get(key);
				for (String node: nodes) {
					graph.addEdges(node, key, nodes);
				}
			}
		} catch (MalformedDataException e) {
			System.err.println("The file is malformed");
			e.printStackTrace();
		}
		return graph;
	}
	
	/**
	 * Searches the path between startNode and endNode in the graph
	 * 
	 * @param graph MyGraph that needs to be searched through
	 * @param startNode The character from which the path needs to start
	 * @param endNode The charcter to which the path need to lead to
	 * @return A list containing the path from the startNode to endNode. Returns
	 * 			null if no path is found.
	 */
	public static List<DirectedEdge<String, String>> bfsPathSearch(MyGraph<String, String> graph, 
			String startNode, String endNode) {
		Map<String, List<DirectedEdge<String, String>>> visited = new HashMap<>();
		Queue<String> nodes = new LinkedList<>();
		nodes.add(startNode);
		visited.put(startNode, new LinkedList<>());
		
		while(!nodes.isEmpty()) {
			String nextNode = nodes.remove();
			if (nextNode.equals(endNode)) {
				return visited.get(endNode);
			}
			
			// Sorts the incident edges of next node.
			Set<DirectedEdge<String, String>> edges = graph.getIncidentEdges(nextNode);
			List<DirectedEdge<String, String>> tempEdges = new ArrayList<>(edges);
			Collections.sort(tempEdges);
			
			// Updates the queue to search all nearby nodes of nextNode
			for (DirectedEdge<String, String> edge: tempEdges) {
				String destination = edge.getDestinationNode();
				if (!visited.containsKey(destination)) {
					List<DirectedEdge<String, String>> currPath = new ArrayList<>();
					for (DirectedEdge<String, String> temp: visited.get(nextNode)) {
						currPath.add(temp);
					}
					currPath.add(edge);
					visited.put(destination, currPath);
					nodes.add(destination);
				}
			}
		}
		
		return null;
	}
}
