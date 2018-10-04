package hw8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hw5.DirectedEdge;
import hw5.MyGraph;
import hw7.MarvelPaths2;
import hw8.CampusParser.MalformedDataException;

/**
 * CampusPaths, model, process and stores the data given from the CampusParser so
 * that it can be used to find the path between two buidings and the direction of
 * travel
 * 
 * @author Sarvagya Gupta
 */
public class CampusPaths {
	// Representation Invariant:
	// 		graph != null && buildingNames != null && buildingPos != null
	//		The key entries in buildingNames and buildingPos should be the same
	
	// Abstraction Function:
	//		AF(this): 
	//			A location on the campus - this.graph.keySet()
	//			Name of buildings (abbr.) - this.buildingNames.keySet()
	//			Name of buildings (full) - this.buildingNames.get(name (abbr.))
	// 			Position of building, b - this.buildingPos.get(b)
	
	/** MyGraph that stores all the reachable points on campus */
	private final MyGraph<MyPoint, Double> graph;
	
	/** Map that associates the abbreviated names of the building to its full name */
	private final Map<String, String> buildingNames;
	
	/** Map that associates the abbreviated names of the building to its position on campus */
	private final Map<String, MyPoint> buildingPos;
	
	/**
	 * Constructs a virtual campus based on the campus_buildings.dat and campus_paths.dat
	 * data files.
	 */
	public CampusPaths() {
		graph = new MyGraph<>();
		buildingNames = new HashMap<>();
		buildingPos = new HashMap<>();
		Set<MyPoint> nodes = new HashSet<>();
		Map<MyPoint, List<DirectedEdge<Double, MyPoint>>> paths = new HashMap<>();
		try {
			CampusParser.parseCampusPathData("campus_paths.dat", nodes, paths);
			CampusParser.parseCampusBuildings("campus_buildings.dat", buildingNames, buildingPos);
		} catch (MalformedDataException e) {
			System.err.println("Data file is malformed.");
			e.printStackTrace();
		}
		buildGraph(graph, paths, nodes);
		checkRep();
	}
	

	/**
	 * Builds the MyGraph, graph, with the list of paths and set of nodes
	 * 
	 * @param graph The graph to be filled
	 * @param path The list of paths to be in the graph
	 * @param nodes The set of nodes to be present in the MyGraph
	 */
	private static void buildGraph(MyGraph<MyPoint, Double> graph, Map<MyPoint, 
			List<DirectedEdge<Double, MyPoint>>> path, Set<MyPoint> nodes) {
		graph.addNodes(nodes);
		for (MyPoint edge: path.keySet()) {
			List<DirectedEdge<Double, MyPoint>> neighbors = path.get(edge);
			for (DirectedEdge<Double, MyPoint> neighbor: neighbors) {
				graph.addEdge(edge, neighbor);
			}
		}		
	}
	
	/**
	 * Finds the path from building, start, to another building, end that costs the least.
	 * 
	 * @param start The current building in the path
	 * @param end The destination building in the path
	 * @return A list containng the path to be taken to reach from start to end in the least
	 * 			cost
	 */
	public List<DirectedEdge<Double, MyPoint>> dijkstraSearch(String start, String end) {
		if (!buildingNames.containsKey(start) || !buildingNames.containsKey(end)) {
			throw new IllegalArgumentException("One/Both of building doesn't exist!");
		}
		MyPoint startNode = buildingPos.get(start);
		MyPoint endNode = buildingPos.get(end);
		return MarvelPaths2.dijkstraSearch(graph, startNode, endNode);
	}
	
	/**
	 * @return Returns the buildings present on the campus in alphabetic order.
	 */
	public Set<String> getBuildings() {
		Set<String> res = new HashSet<>();
		for (String shortName: buildingNames.keySet()) {
			res.add(shortName + ": " + buildingNames.get(shortName));
		}
		return res;
	}
	
	/**
	 * Checks to see if a building with the shortName is present on campus
	 * 
	 * @param shortName Building name to be checked for
	 * @return True if the building is on campus, false otherwise
	 */
	public boolean isBuilding(String shortName) {
		return buildingNames.containsKey(shortName);
	}
	
	/**
	 * Returns the full name of the building based on its abbreviated name, shortName
	 *  
	 * @param shortName Abbreviated name of the building 
	 * @throws IllegalArgumentException Throws the argument if the building doesn't exist
	 * @return The string that contains the full name of the building with the abbreviated name
	 */
	public String getFullName(String shortName) {
		if (!buildingNames.containsKey(shortName)) {
			throw new IllegalArgumentException("Building doesn't exist");
		}
		return buildingNames.get(shortName);
	}
	
	/**
	 * Returns the cardinal directions from the start point to the end point
	 * 
	 * @param start The current point from which the direction is to be found
	 * @param end The end point to which the direction is to be found
	 * @return A String that represents the direction that the user needs to move in
	 */
	public String getDirection(MyPoint start, MyPoint end) {
		double xCoordinate = -start.getX() + end.getX();
		double yCoordinate = start.getY() - end.getY();
		double angle = Math.atan2(yCoordinate, xCoordinate) / Math.PI;
		
		if (angle > (7.0 / 8.0) || angle < (-7.0 / 8.0)) {
			return "W";
		} else if (angle < -5.0 / 8.0) {
			return "SW";
		} else if (angle < -3.0 / 8.0) {
			return "S";
		} else if (angle < -1.0 / 8.0) {
			return "SE";
		} else if (angle < 1.0 / 8.0) {
			return "E";
		} else if (angle < 3.0 / 8.0) {
			return "NE";
		} else if (angle < 5.0 / 8.0) {
			return "N";
		} else {
			return "NW";
		}
	}

	/**
	 * Returns the positon of the building
	 *  
	 * @param node The building whose position needs to be returned
	 * @throws IllegalArgumentException Throws the argument if the building doesn't exist
	 * @return The MyPoint that contains the location of the building
	 */
	public MyPoint getPosBuilding(String node) {
		if (!buildingNames.containsKey(node)) {
			throw new IllegalArgumentException("Building doesn't exist");
		}
		return buildingPos.get(node);
	}
	
	/**
	 * Checks the invariants on CampusParser
	 */
	private void checkRep() {
		assert(graph != null && buildingNames != null && buildingPos != null);
		assert(this.buildingNames.keySet().size() == this.buildingPos.keySet().size());
	}
}
