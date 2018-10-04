package hw5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <b>MyGraph</b> is a representation of a directed graph. It is a 
 * finite collection of vertices and set of edges, ordered pairs of vertices.
 * 
 * K: The type of nodes in the MyGraph
 * V: The type of label of the DirectedEdges connecting nodes in the MyGraph
 * 
 * @author Sarvagya Gupta
 */
public class MyGraph<K extends Comparable<K>, V extends Comparable<V>> {
	// Abstraction Function:
	//		MyGraph, m, represents a directed labeled multi-graph containing
	//		nodes, and unidirectional edges that connect these nodes. Each node
	//		is a key of the 'nodes' and each edge that originates from the node
	//		is part of the set of values for the node in 'nodes'.
	//
	// Representation Invariant: 
	//		this.nodes != null &&
	//		forall (node in nodes) node != null &&
	//      forall ((node in nodes) forall (edge in node.get(node)) edge != null)
	
	/** Stores the Vertex and its children */
	private final Map<K, Set<DirectedEdge<V, K>>> nodes;
	
	/** Check rep bulky part switch */
	private final boolean checkRep = false;
	
	/**
	 * @effects Creates a new MyGraph that has no vertices nor edges 
	 */
	public MyGraph() {
		this(new HashMap<>());
	}
	
	/**
	 * @param nodes The map of all vertices to be present in the MyGraph
	 * @effects Created a new MyGraph, map, that has all the nodes from vertices.
	 */
	private MyGraph(Map<K, Set<DirectedEdge<V, K>>> nodes) {
		this.nodes = nodes;
	}

	/**
	 * Addition of vertex to already existing set of vertices present in this MyGraph
	 * 
	 * @param node The node to be added
	 * @return Returns this
	 * @requires node != null
	 * @modifies this
	 * @effects this_post = this_pre U {node}. In other words, the updated set
	 * 			of nodes contains all the vertices contained in the previous set and the node.
	 * 			If node exists already, this.nodes_post = this.nodes_pre
	 */
	public MyGraph<K, V> addNode(K node) {
		this.nodes.putIfAbsent(node, new HashSet<>());
		checkRep();
		return this;
	}
	
	/**
	 * Addition of vertices to already existing set of vertices present in this MyGraph
	 * 
	 * @param nodes The set of nodes to be added
	 * @return Returns this
	 * @requires nodes != null
	 * @modifies this
	 * @effects this_post = this_pre U {nodes}. In other words, the updated set
	 * 			of nodes contains all the vertices contained in the previous set and the all 
	 * 			the nodes in the list, nodes. If any node exists, it is not added again to
	 * 			this MyGraph.
	 */
	public MyGraph<K, V> addNodes(Set<K> nodes) {
		List<K> nodesCopy = new ArrayList<>();
		for (K str: nodes) {
			nodesCopy.add(str);
		}
		for (K str: nodesCopy) {
			this.addNode(str);
		}
		checkRep();
		return this;
	}
	
	/**
	 * Removal of a node from the set of vertices present in this MyGraph
	 * 
	 * @param node The vertex to be removed
	 * @requires node != null
	 * @return Returns this
	 * @modifies this
	 * @effects this_post = this_pre - {node}. In other words, the updated set of 
	 * 			nodes contains all previous vertices, except node. If the node doesn't exist,
	 * 			there is no change.
	 */
	public MyGraph<K, V> removeNode(K node) {
		this.nodes.remove(node);
		checkRep();
		return this;
	}
	
	/**
	 * Removal of vertices to already existing set of vertices present in this MyGraph
	 * 
	 * @param nodes The set of nodes to be removed
	 * @return Returns this
	 * @requires nodes != null
	 * @modifies this
	 * @effects this_post = this_pre - {nodes}. In other words, the updated set
	 * 			of nodes contains all the vertices contained in the previous set, except all 
	 * 			the nodes in the list, nodes. If any node doesn't exists, it is skipped.
	 */
	public MyGraph<K, V> removeNodes(Set<K> nodes) {
		List<K> nodesCopy = new ArrayList<>();
		for (K str: nodes) {
			nodesCopy.add(str);
		}
		for (K str: nodesCopy) {
			this.removeNode(str);
		}
		checkRep();
		return this;
	}
	
	/**
	 * Addition of edge to already existing set of incident edges of parentNode
	 * present in this MyGraph
	 * 
	 * @param parentNode The node at which the edge to be added is originating
	 * @param edge The edge to be added to the parentNode
	 * @throws IllegalArgumentException Throws the exception if parentNode or 
	 * 			edge.getDestinationNode() is not in this MyGraph
	 * @return Returns this
	 * @requires edge != null &amp;&amp; parentNode != null
	 * @modifies this
	 * @effects The updated set of all incident edges of the node, parentNode,
	 * 			contains all the incident edges it contained previously and the DirectedEdge, 
	 * 			edge. 
	 * 			If edge is already present, then there is no change
	 */
	public MyGraph<K, V> addEdge(K parentNode, DirectedEdge<V, K> edge) {
		if (!this.containsNode(parentNode)
				|| !this.containsNode(edge.getDestinationNode())) {
			throw new IllegalArgumentException(parentNode + " or " + 
				edge.getDestinationNode() + " doesn't exist in this MyGraph");
		}
		this.nodes.get(parentNode).add(edge);
		checkRep();
		return this;
	}
	
	/**
	 * Addition of incident edges to already existing set of incident edges on the parentNode 
	 * present in this MyGraph
	 * 
	 * @param parentNode The node at which the edge to be added is originating
	 * @param edges The list of edges to be added to the parentNode
	 * @param label The label with which the list of edges is associated with
	 * @throws IllegalArgumentException Throws the exception if parentNode or 
	 * 			edge.getDestinationNode() is not in this MyGraph
	 * @return Returns this
	 * @requires edged != null &amp;&amp; parentNode != null
	 * @modifies this
	 * @effects The updated set of all incident edges of the node, parentNode,
	 * 			contains all the incident edges it contained previously and the list of edges
	 * 			If a edge is already present, then it is skipped.
	 */
	public MyGraph<K, V> addEdges(K parentNode, V label, List<K> edges) {
		List<K> edgesCopy = new ArrayList<>();
		for (K str: edges) {
			edgesCopy.add(str);
		}
		for (K str: edgesCopy) {
			if (!str.equals(parentNode)) {
				this.addEdge(parentNode, new DirectedEdge<V, K>(label, str));
				this.addEdge(str, new DirectedEdge<V, K>(label, parentNode));
			}
		}
		checkRep();
		return this;
	}

	/**
	 * Removal of edge from the set of incident edges of parentNode present in this MyGraph
	 * 
	 * @param edge The edge to be removed
	 * @param parentNode The node at which the edge to be removed is originating
	 * @throws IllegalArgumentException Throws the exception if parentNode is not present in
	 * 			this MyGraph
	 * @return Returns this
	 * @requires parentNode != null &amp;&amp; edge != null
	 * @modifies this
	 * @effects The updated set of all incident edges of the node, parentNode, 
	 * 			no longer contains the DirectedEdge, edge, as an incident edge. If the edge
	 * 			doesn't exist, there is no change
	 */
	public MyGraph<K, V> removeEdge(K parentNode, DirectedEdge<?, K> edge) {
		if (!this.containsNode(parentNode)) {
			throw new IllegalArgumentException(parentNode + "deosn't exist in this MyGraph");
		}
		this.nodes.get(parentNode).remove(edge);
		checkRep();
		return this;
	}
	
	/**
	 * Returns an unmodifiable set of vertices present in this MyGraph
	 * 
	 * @return An unmodifiable set containing all the vertices present in this MyGraph
	 */
	public Set<K> getAllNodes() {
		return Collections.unmodifiableSet(this.nodes.keySet());
	}
	
	/**
	 * Returns an unmodifiable set containing all the edges incident on the node
	 *  
	 * @param node The vertex whose incident edges need to be returned
	 * @return A set, s, such that it contains all the edges that are incident on node. 
	 * 			If the node doesn't exist in the MyGraph, then it returns an empty set.
	 * @requires node != null
	 */
	public Set<DirectedEdge<V, K>> getIncidentEdges(K node) {
		if (!this.containsNode(node))
			return new HashSet<>();
		return Collections.unmodifiableSet(this.nodes.get(node));
	}
	
	/**
	 * Checks if there is a edge from start vertex to end vertex
	 * 
	 * @param start The starting vertex
	 * @param end The destination vertex
	 * @throws IllegalArgumentException start and end not present in the graph
	 * @return If there exists an edge, e, such that e=&lt;start,end&gt;, then return true,
	 * 		   else return false.
	 * @requires start != null &amp;&amp; end != null
	 */
	public boolean isAdjacent(K start, K end) {
		if (!this.containsNode(start) || !this.containsNode(end)) {
			throw new IllegalArgumentException("start or end vertex not present");
		}
		Set<DirectedEdge<V, K>> edges = this.nodes.get(start);
		for (DirectedEdge<V, K> edge: edges) {
			if (edge.getDestinationNode().equals(end)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if the node is present in this MyGraph
	 * @param node The node to be checked
	 * @return Returns true if this.nodes.containsKey(node), false otherwise
	 */
	public boolean containsNode(K node) {
		return this.nodes.containsKey(node);
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		Set<K> keys = nodes.keySet();
		for(K key: keys) {
			res.append(key + ": {");
			Set<DirectedEdge<V, K>> edges = nodes.get(key);
			for (DirectedEdge<V, K> edge: edges) {
				res.append(edge.getDestinationNode() + "; ");
			}
			res.setLength(res.length() - 2);
			res.append("}\n\n");
		}
		return res.toString();
	}
	
	/**
	 * Checks the representative invariant of this MyGraph
	 */
	private void checkRep() {
		assert(this.nodes != null);
		if (this.checkRep) {
			Set<K> nodes = this.nodes.keySet();
			for(K node: nodes) {
				assert(node != null);
				Set<DirectedEdge<V, K>> edges = this.nodes.get(node);
				for (DirectedEdge<V, K> edge: edges) {
					assert(edge != null);
				}
			}
		}
	}
}