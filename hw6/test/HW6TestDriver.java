package hw6.test;

import java.io.*;
import java.util.*;

import hw5.DirectedEdge;
import hw5.MyGraph;
import hw5.test.HW5TestDriver;
import hw6.MarvelPaths;

/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph.
 **/
public class HW6TestDriver extends HW5TestDriver {

    public static void main(String args[]) {
        try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            HW6TestDriver td;

            if (args.length == 0) {
                td = new HW6TestDriver(new InputStreamReader(System.in),
                                       new OutputStreamWriter(System.out));
            } else {

                String fileName = args[0];
                File tests = new File (fileName);

                if (tests.exists() || tests.canRead()) {
                    td = new HW6TestDriver(new FileReader(tests),
                                           new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + tests.toString());
                    printUsage();
                    return;
                }
            }

            td.runTests();

        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        }
    }

    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new HW5TestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
	public HW6TestDriver(Reader r, Writer w) {
		super(r, w);
	}

    @Override
    protected void executeCommand(String command, List<String> arguments) {
        try {
            if (command.equals("LoadGraph")) {
                loadGraph(arguments);
            } else if (command.equals("FindPath")) {
                findPath(arguments);
            } else if (command.equals("CreateGraph")) {
                createGraph(arguments);
            } else if (command.equals("AddNode")) {
                addNode(arguments);
            } else if (command.equals("AddEdge")) {
                addEdge(arguments);
            } else if (command.equals("ListNodes")) {
                listNodes(arguments);
            } else if (command.equals("ListChildren")) {
                listChildren(arguments);
            } else {
                output.println("Unrecognized command: " + command);
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }
    
    private void findPath(List<String> arguments) {
    	if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }
    	
    	String graphName = arguments.get(0);
    	String start = arguments.get(1);
    	String end = arguments.get(2);
    	
    	findPath(graphName, start, end);
	}

	private void findPath(String graphName, String startNode, String endNode) {
		MyGraph<String, String> graph = graphs.get(graphName);
		startNode = startNode.replace("_", " ");
		endNode = endNode.replace("_", " ");
		
		boolean flag = checkNode(graph, startNode);
		flag = checkNode(graph, endNode) || flag;
		if (flag) return;
		
		List<DirectedEdge<String, String>> path = 
				MarvelPaths.bfsPathSearch(graph, startNode, endNode);
		output.println("path from " + startNode + " to " + endNode + ":");
		
		if (path == null) {
			output.println("no path found");
		} else {
			String prev = startNode;
			for (DirectedEdge<String, String> edge: path) {
				output.println(prev + " to " + edge.getDestinationNode() + " via " + edge.getLabel());
				prev = edge.getDestinationNode();
			}
		}
	}
	
	private boolean checkNode(MyGraph<String, String> graph, String node) {
		if (!graph.containsNode(node)) {
			output.println("unknown character " + node);
			return true;
		}
		return false;
	}

	private void loadGraph(List<String> arguments) {
		if (arguments.size() != 2) {
			throw new CommandException("Bad arguments to CreateGraph: " + arguments);
		}
	
		String graphName = arguments.get(0);
		String fileName = arguments.get(1);
		
		loadGraph(graphName, fileName);
	}

	private void loadGraph(String graphName, String fileName) {
		graphs.put(graphName, MarvelPaths.loadGraph(fileName));
		output.println("loaded graph " + graphName);
	}

	/**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }
        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
