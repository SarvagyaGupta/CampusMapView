package hw7.test;

import java.io.*;
import java.util.*;

import hw5.DirectedEdge;
import hw5.MyGraph;
import hw7.MarvelPaths2;

/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph.
 **/
public class HW7TestDriver {

    public static void main(String args[]) {
        try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            HW7TestDriver td;

            if (args.length == 0) {
                td = new HW7TestDriver(new InputStreamReader(System.in),
                                       new OutputStreamWriter(System.out));
            } else {

                String fileName = args[0];
                File tests = new File (fileName);

                if (tests.exists() || tests.canRead()) {
                    td = new HW7TestDriver(new FileReader(tests),
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

    protected static void printUsage() {
        System.err.println("Usage:");
        System.err.println("to read from a file: java hw5.test.HW5TestDriver <name of input script>");
        System.err.println("to read from standard in: java hw5.test.HW5TestDriver");
    }

    /** String -> Graph: maps the names of graphs to the actual graph **/
    private final Map<String, MyGraph<String, Double>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new HW5TestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
    public HW7TestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @effects Executes the commands read from the input and writes results to the output
     * @throws IOException if the input or output sources encounter an IOException
     **/
    public void runTests()
        throws IOException
    {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            }
            else
            {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

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
		MyGraph<String, Double> graph = graphs.get(graphName);
		startNode = startNode.replace("_", " ");
		endNode = endNode.replace("_", " ");
		
		boolean flag = checkNode(graph, startNode);
		flag = checkNode(graph, endNode) || flag;
		if (flag) return;
		
		List<DirectedEdge<Double, String>> path = 
				MarvelPaths2.dijkstraSearch(graph, startNode, endNode);
		output.println("path from " + startNode + " to " + endNode + ":");
		
		double cost = 0.0;		
		if (path == null) {
			output.println("no path found");
		} else {
			String prev = startNode;
			for (DirectedEdge<Double, String> edge: path) {
				output.printf(prev + " to " + edge.getDestinationNode() + 
						" with weight %.3f\n", edge.getLabel());
				prev = edge.getDestinationNode();
				cost += edge.getLabel();
			}
			output.printf("total cost: %.3f\n", cost);
		}
	}
	
	private boolean checkNode(MyGraph<String, Double> graph, String node) {
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
		graphs.put(graphName, MarvelPaths2.loadGraph(fileName));
		output.println("loaded graph " + graphName);
	}

    protected void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new MyGraph<String, Double>());
        output.println("created graph " + graphName);
    }

    protected void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to addNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        graphs.get(graphName).addNode(nodeName);
        output.println("added node " + nodeName + " to " + graphName);
    }

    protected void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to addEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        Double edgeLabel = Double.valueOf(arguments.get(3));

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
            Double edgeLabel) {
        graphs.get(graphName).addEdge(parentName, new DirectedEdge<Double, String>(edgeLabel, childName));
        output.printf("added edge %.3f from " + parentName + " to " 
        		+ childName + " in " + graphName + "\n", edgeLabel);
    }

    protected void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to listNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        Set<String> nodes = graphs.get(graphName).getAllNodes();
        output.print(graphName + " contains: ");
        for (String node: nodes) {
        	output.print(node + " ");
        }
        output.println();
    }

    protected void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to listChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        Set<DirectedEdge<Double, String>> edges = graphs.get(graphName).getIncidentEdges(parentName);
        output.print("the children of " + parentName + " in " + graphName + " are: ");
		List<DirectedEdge<Double, String>> tempEdges = new ArrayList<>(edges);
		Collections.sort(tempEdges);
        for (DirectedEdge<Double, String> edge: tempEdges) {
        	output.print(edge + " ");
        }
        output.println();
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
