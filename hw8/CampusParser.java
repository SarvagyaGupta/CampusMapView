package hw8;

import java.io.*;
import java.util.*;

import hw5.DirectedEdge;

/**
 * Parser utility to load the Campus dataset
 */
public class CampusParser {
	/**
     * A checked exception class for bad data files
     */
    @SuppressWarnings("serial")
    public static class MalformedDataException extends Exception {
        public MalformedDataException() { }

        public MalformedDataException(String message) {
            super(message);
        }

        public MalformedDataException(Throwable cause) {
            super(cause);
        }

        public MalformedDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
	/**
     * Reads the Campus Paths dataset.
     * 
     * @param nodes list in which all MyPoints will be stored;
     *          typically empty when the routine is called
     * @param paths map from MyPoints to a set of DirectedEdges from that point to another;
     * 			typically empty when the routine is called
	 * @throws MalformedDataException If the data is malformed
     * @modifies nodes, paths
     * @effects fills nodes with a list of all unique MyPoints
     * @effects fills paths with a map from each MyPoint to all MyPoints it can reach
     */
    public static void parseCampusPathData(String fileName, Set<MyPoint> nodes, 
    		Map<MyPoint, List<DirectedEdge<Double, MyPoint>>> paths) throws MalformedDataException {
        // Why does this method accept the Collections to be filled as
        // parameters rather than making them a return value? To allows us to
        // "return" two different Collections. If only one or neither Collection
        // needs to be returned to the caller, feel free to rewrite this method
        // without the parameters. Generally this is better style.
        BufferedReader reader = null;
        try {
        	reader = new BufferedReader(new FileReader("src/hw8/data/" + fileName));
          
        	// Construct the collections of characters and books, one
        	// <character, book> pair at a time.
        	String inputLine = reader.readLine();
        	while (inputLine != null) {

        		// Ignore comment lines.
        		if (inputLine.startsWith("#")) {
        			continue;
        		}

        		// Add the point to the set of reachable nodes
        		MyPoint basePoint = makePoint(inputLine);
        		nodes.add(basePoint);
        		
        		// Goes through all the lines indented with a tab
        		while ((inputLine = reader.readLine()) != null && inputLine.startsWith("\t")) {
        			String edge = inputLine.split("\t")[1];
        			String[] splitEdge = edge.split(": ");
        			MyPoint destNode = makePoint(splitEdge[0]);
        			nodes.add(destNode);
        			if (!paths.containsKey(basePoint)) {
        				paths.put(basePoint, new ArrayList<>());
        			}
        			paths.get(basePoint).add(new DirectedEdge
        					<Double, MyPoint>(Double.parseDouble(splitEdge[1]), destNode));
        		}
        	}
        } catch (IOException e) {
        	System.err.println(e.toString());
        	e.printStackTrace(System.err);
      	} finally {
      		if (reader != null) {
    		  	try {
    			  	reader.close();
    		  	} catch (IOException e) {
    			  	System.err.println(e.toString());
    			  	e.printStackTrace(System.err);
    		  	}
    	  	}
      	}
    }
    
    /**
     * Helper method that makes a MyPoint based on the string
     * 
     * @param toPoint String to be converted to a MyPoint
     * @return MyPoint
     * @throws MalformedDataException If toPoint doesn't have only the x and y coordinates of the
     * 			MyPoint
     */
    private static MyPoint makePoint(String toPoint) throws MalformedDataException {
    	String[] coordinates = toPoint.split(",");
    	if (coordinates.length != 2) {
    		throw new MalformedDataException();
    	}
		return new MyPoint(Double.parseDouble(coordinates[0]), 
				Double.parseDouble(coordinates[1]));
    }

    /**
     * 
     * @param buildingNames
     * @param buildingPos
     * @throws MalformedDataException
     */
	public static void parseCampusBuildings(String fileName, Map<String, String> buildingNames, 
			Map<String, MyPoint> buildingPos) throws MalformedDataException {
		// Why does this method accept the Collections to be filled as
        // parameters rather than making them a return value? To allows us to
        // "return" two different Collections. If only one or neither Collection
        // needs to be returned to the caller, feel free to rewrite this method
        // without the parameters. Generally this is better style.
		BufferedReader reader = null;
        try {
        	reader = new BufferedReader(new FileReader("src/hw8/data/" + fileName));
          
        	// Construct the collections of characters and books, one
        	// <character, book> pair at a time.
        	String inputLine;
        	while ((inputLine = reader.readLine()) != null) {

        		// Ignore comment lines.
        		if (inputLine.startsWith("#")) {
        			continue;
        		}

        		// Process the data and throw MalformedDataException if the data is malformed
        		String[] buildingInfo = inputLine.split("\t");
        		if (buildingInfo.length != 4) {
        			throw new MalformedDataException("The file should have four pieces of "
        					+ "information sperated by tabs");
        		}
        		
        		// Associate the abbreviated name of the building with its full name
        		buildingNames.put(buildingInfo[0], buildingInfo[1]);
        		
        		// Associate the abbreviated name with the position of the building
        		buildingPos.put(buildingInfo[0], new MyPoint(Double.parseDouble(buildingInfo[2]),
        				Double.parseDouble(buildingInfo[3])));
        	}
        } catch (IOException e) {
        	System.err.println(e.toString());
        	e.printStackTrace(System.err);
      	} finally {
      		if (reader != null) {
    		  	try {
    			  	reader.close();
    		  	} catch (IOException e) {
    			  	System.err.println(e.toString());
    			  	e.printStackTrace(System.err);
    		  	}
    	  	}
      	}
	}
}