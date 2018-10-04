package hw7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import hw5.DirectedEdge;
import hw5.MyGraph;
import hw7.MarvelParser2.MalformedDataException;

public class MarvelPaths2 {
	
	/**
	 * Builds and returns a MyGraph by using the data in the given file with name, fileName
	 * 
	 * @param fileName The name of the file in which the data is stored
	 * @return Returns a MyGraph, m, such that m contains all the characters in 
	 * 			the file with weighted edges connecting them
	 * @requires fileName != null && <code>new File(fileName).exists()</code>
	 */
	public static MyGraph<String, Double> loadGraph(String fileName) {
		MyGraph<String, Double> graph = new MyGraph<>();
		try {
			Set<String> characters = new HashSet<>();
			Map<String, Map<String, Double>> characterLinks = new HashMap<>();
			MarvelParser2.parseData("src/hw7/data/" + fileName, characters, characterLinks);
			buildGraph(graph, characterLinks, characters);
		} catch (MalformedDataException e) {
			System.err.println("The file is malformed");
			e.printStackTrace();
		}
		return graph;
	}
	
	/**
	 * Builds the MyGraph, graph, from the given set of characters and a map of the number
	 * times a character has been linked to another character.
	 * 
	 * @param graph The MyGraph to be loaded
	 * @param count The map that contains the data of the number of times a character has appeared
	 * 			in the same book as another character.
	 * @param characters The set of characters in the universe.
	 * @requires graph != null && count != null && characters != null
	 */
	private static void buildGraph(MyGraph<String, Double> graph, Map<String, 
			Map<String, Double>> count, Set<String> characters) {
		graph.addNodes(characters);
		for (String character: count.keySet()) {
			Map<String, Double> neighbors = count.get(character);
			for (String neighbor: neighbors.keySet()) {
				graph.addEdge(character, 
						new DirectedEdge<Double, String>(1 / neighbors.get(neighbor), 
								neighbor));
			}
		}		
	}
	
	/**
	 * Searches a path from the character, start, to the character, end, with the least amount
	 * of cost in the MyGraph, graph.
	 * 
	 * @param graph The MyGraph in which the path has to be found
	 * @param start The starting character for the path
	 * @param end The destination character for the path
	 * @return A list containing the path from start to end. Returns null if there 
	 * 			is no path found.
	 * @requires grpah != null && start != null && end != null
	 */
	public static <K extends Comparable<K>> List<DirectedEdge<Double, K>> 
			dijkstraSearch(MyGraph<K, Double> graph, K start, K end) {
		Set<K> finished = new HashSet<>();
		Queue<List<DirectedEdge<Double, K>>> active = new PriorityQueue<>(
				new Comparator<List<DirectedEdge<Double, K>>>() {

					@Override
					public int compare(List<DirectedEdge<Double, K>> path1, 
							List<DirectedEdge<Double, K>> path2) {
						double diff = 0.0;
						for (DirectedEdge<Double, K> edge: path1) {
							diff += edge.getLabel();
						}
						for (DirectedEdge<Double, K> edge: path2) {
							diff -= edge.getLabel();
						}
						if (diff == 0.0) {
							return path1.size() - path2.size();
						} else if (diff > 0.0) {
							return 1;
						} else {
							return -1;
						}
					}
					
				});
		
		// Adds the start character to the active queue
		List<DirectedEdge<Double, K>> pathStartFromStart = new ArrayList<>();
		pathStartFromStart.add(new DirectedEdge<Double, K>(0.0, start));
		active.add(pathStartFromStart);
		
		while(!active.isEmpty()) {
			List<DirectedEdge<Double, K>> minPath = active.remove();
			K minDest = minPath.get(minPath.size() - 1).getDestinationNode();
			
			// If the end character has been reached
			if (minDest.equals(end)) {
				// The path should not include the first character
				return minPath.subList(1, minPath.size());
			}
			
			// If the minimum cost to that node has already been found
			if (finished.contains(minDest)) {
				continue;
			}
			
			// Add neigbors of the current minDest to the active queue
			for (DirectedEdge<Double, K> edge: graph.getIncidentEdges(minDest)) {
				if (!finished.contains(edge.getDestinationNode())) {
					List<DirectedEdge<Double, K>> newPath = copy(minPath);
					newPath.add(edge);
					active.add(newPath);
				}
			}
			
			// Done searching minDest
			finished.add(minDest);
		}
		
		// If no path is found
		return null; 
	}

	/**
	 * Returns a copy of the contents of the list, minPath
	 * 
	 * @param minPath The list to be copied 
	 * @return A list containing all the elements of minPath
	 */
	private static <K extends Comparable<K>> List<DirectedEdge<Double, K>> 
			copy(List<DirectedEdge<Double, K>> minPath) {
		List<DirectedEdge<Double, K>> res = new ArrayList<>();
		for (DirectedEdge<Double, K> member: minPath) {
			res.add(member);
		}
		return res;
	}
}
