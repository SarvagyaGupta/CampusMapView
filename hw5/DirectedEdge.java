package hw5;

/**
 * <b>DirectedEdge</b> is an immutable representation of unidirectional edge in 
 * <b>MyGraph</b>.
 * 
 * <p>
 * Each DirectedEdge connects two vertices and can be represented as e=&lt;A,B&gt;, where
 * A and B are the two nodes it connects and B is directly reachable from A.
 * </p>
 * 
 * @author Sarvagya Gupta
 */
public final class DirectedEdge<K extends Comparable<K>, V extends Comparable<V>> 
		implements Comparable<DirectedEdge<K, V>> {
	// Abstraction Function:
	//		DirectedEdge, e, represents a edge in the directed labeled multi-graph.
	//		The value/weight of the edge is represented by 'label' and the node to
	//		which the edge is pointing to is represented by 'destinationNode'.
	//
	// Representation Invariant: 
	//		label != null && destinationEdge != null
	
	/** Stores the label of the edge */
	private final K label;
	
	/** Stores the destination of the edge */
	private final V destinationNode;

	/**
	 * @param label The name of the node
	 * @param destinationNode The destination node of the edge
	 * @requires label != null &amp;&amp; destinationNode != null
	 * @effects Constructs a new Edge with the given label, and destinationNode 
	 */	
	public DirectedEdge(K label, V destinationNode) {
		this.label = label;
		this.destinationNode = destinationNode;
	}

	/**
	 * @return Returns the label of this edge
	 */
	public K getLabel() {
		return this.label;
	}

	/**
	 * @return Returns the destination node of this edge
	 */
	public V getDestinationNode() {
		return this.destinationNode;
	}

	/**
	 * @return Returns the String representation of the DirectedEdge
	 */
	@Override
	public String toString() {
		return this.getDestinationNode() + "(" + this.getLabel() + ")";
	}

	/**
	 * Compares the DirectedEdge by the child node first, and then the label
	 * to break any ties
	 * 
	 * @param other The DirectedEdge to which this has to be compared
	 * @return An Integer less than 0 if this &lt; other, 0 if this = other, or
	 * 			greater than 0 if this &gt; other
	 */
	@Override
	public int compareTo(DirectedEdge<K, V> other) {
		int val = this.getDestinationNode().compareTo(other.getDestinationNode());
		if (val == 0) {
			return this.getLabel().compareTo(other.getLabel());
		}
		return val;
	}
	
	/**
	 * @param obj The object to which this has to be compared to.
	 * @return Returns true if obj instanceof DirectedEdge
	 * 			&amp;&amp; this.getLabel().equals(obj.getLabel()) 
	 * 			&amp;&amp; this.getDestinationNode().equals(obj.getDestinationNode()), 
	 * 			false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DirectedEdge<?, ?>)) {
			return false;
		}
		DirectedEdge<?, ?> other = (DirectedEdge<?, ?>) obj;
		return this.getLabel().equals(other.getLabel()) 
				&& this.getDestinationNode().equals(other.getDestinationNode());
	}
	
	/**
	 * @return Returns the hashcode of this
	 */
	@Override
	public int hashCode() {
		return this.getDestinationNode().hashCode() * 19 + this.getLabel().hashCode();
	}
}
