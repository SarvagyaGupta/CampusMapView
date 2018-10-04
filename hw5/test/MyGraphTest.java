package hw5.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hw5.DirectedEdge;
import hw5.MyGraph;

public class MyGraphTest {
	private MyGraph<String, String> emptyGraph;
	private MyGraph<String, String> threeGraph;
	
	// Make a graph of specified number of nodes
	private static MyGraph<String, String> graph(int index) {
		MyGraph<String, String> graph = new MyGraph<>();
		for (int i = 1; i <= index; i++) {
			graph.addNode("node " + i);
		}
		return graph;
	}
	
	// Make a TreeSet of specified number of nodes
	private Set<String> tree(int index) {
		Set<String> set = new HashSet<>();
		for (int i = 1; i <= index; i++) {
			set.add("node " + i);
		}
		return set;
	}
	
	@Before
	public void setUp() throws Exception {
		emptyGraph = graph(0);
		threeGraph = graph(3);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddNodeWhenEmptyGraph() {
		assertEquals("emptyGraph node not added", tree(1), 
				emptyGraph.addNode("node 1").getAllNodes());
	}
	
	@Test
	public void testAddNodeGeneralCase() {
		assertEquals("nodes not appended", tree(4), 
				threeGraph.addNode("node 4").getAllNodes());
	}
	
	@Test
	public void testAddNodeAlreadyExist() {
		assertEquals("nodes not appended", tree(3), 
				threeGraph.addNode("node 2").getAllNodes());
	}
	
	@Test
	public void testAddListNodesWhenGraphemptyGraph() {
		assertEquals("node not added", tree(2), 
				emptyGraph.addNodes(new HashSet<>(
						Arrays.asList("node 1", "node 2"))).getAllNodes());
	}
	
	@Test
	public void testAddListNodesAlreadyExist() {
		assertEquals("node not added", tree(4), 
				threeGraph.addNodes(new HashSet<>(
						Arrays.asList("node 2", "node 4"))).getAllNodes());
	}
	
	@Test
	public void testRemoveNodeWhenGraphEmpty() {
		assertEquals("node still exists", tree(0), 
				emptyGraph.removeNode("node 4").getAllNodes());
	}
	
	@Test
	public void testRemoveNodeNoNodeExist() {
		assertEquals("node still exists", tree(3), 
				threeGraph.removeNode("node 4").getAllNodes());
	}
	
	@Test
	public void testRemoveNodeExists() {
		assertEquals("node still exists", tree(2), 
				threeGraph.removeNode("node 3").getAllNodes());
	}
	
	@Test
	public void testRemoveListNodesWhenGraphemptyGraph() {
		assertEquals("node not added", tree(0), 
				emptyGraph.removeNodes(new HashSet<>(
						Arrays.asList("node 1", "node 2"))).getAllNodes());
	}
	
	@Test
	public void testRemoveListNodesAlreadyExist() {
		assertEquals("node not added", tree(2), 
				threeGraph.removeNodes(new HashSet<>(
						Arrays.asList("node 3", "node 4"))).getAllNodes());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddEdgeParentNodeDoesNotExist() {
		emptyGraph.addEdge("node 1", new DirectedEdge<String, String>("edge 1", "node 2"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddEdgesDestinationNodeDoesNotExists() {
		threeGraph.addEdge("node 1", new DirectedEdge<String, String>("edge 1", "node 4"));
	}
	
	@Test
	public void testAddEdgesNodesPresent() {
		assertTrue("edge not added correctly", threeGraph.addEdge("node 1", 
				new DirectedEdge<String, String>("edge 1", "node 2")).isAdjacent("node 1", "node 2"));
	}

	@Test
	public void testAddEdgesAlreadyPresent() {
		threeGraph.addEdge("node 1", new DirectedEdge<String, String>("edge 1", "node 2"));
		threeGraph.addEdge("node 1", new DirectedEdge<String, String>("edge 1", "node 2"));
		assertTrue("edge not there", threeGraph.isAdjacent("node 1", "node 2"));
	}
	
	@Test
	public void testAddEdgeSameLabelButNotNode() {
		threeGraph.addEdge("node 1", new DirectedEdge<String, String>("edge 1", "node 2"));
		threeGraph.addEdge("node 1", new DirectedEdge<String, String>("edge 1", "node 3"));
		assertTrue("edge 12 not there", threeGraph.isAdjacent("node 1", "node 2"));
		assertTrue("edge 13 not there", threeGraph.isAdjacent("node 1", "node 3"));
	}
	
	@Test
	public void testAddEdgeSameDestinationButNotLabel() {
		threeGraph.addEdge("node 1", new DirectedEdge<String, String>("edge 1", "node 2"));
		threeGraph.addEdge("node 1", new DirectedEdge<String, String>("edge 2", "node 2"));
		assertEquals("size != 2", threeGraph.getIncidentEdges("node 1").size(), 2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveEdgesNodeDoesNotExists() {
		threeGraph.removeEdge("node 4", new DirectedEdge<String, String>("edge 1", "node 4"));
	}
	
	@Test
	public void testRemoveEdgeExists() {
		DirectedEdge<String, String> addRemove = new DirectedEdge<>("edge 1", "node 2");
		threeGraph.addEdge("node 1", addRemove).removeEdge("node 1", addRemove);
		assertEquals("edge not removed", threeGraph.getIncidentEdges("node 1").size(), 0);
	}
	
	@Test
	public void testRemoveEdgesDoesNotExists() {
		threeGraph.removeEdge("node 1", new DirectedEdge<String, String>("edge 1", "node 3"));
		assertEquals("edge magically appeared", threeGraph.getIncidentEdges("node 1").size(), 0);
	}
	
	@Test
	public void testIsAdjacentEdgeUnidirection() {
		DirectedEdge<String, String> edge = new DirectedEdge<String, String>("edge 1", "node 2");
		assertTrue("wrong behaviour 1", 
				threeGraph.addEdge("node 1", edge).isAdjacent("node 1", "node 2"));
		assertFalse("wrong behaviour 2", 
				threeGraph.addEdge("node 1", edge).isAdjacent("node 2", "node 1"));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void clientAddMapKeySet() {
		threeGraph.getAllNodes().add("Yo");
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void clientRemoveMapKeySet() {
		threeGraph.getAllNodes().remove("node 1");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testcheckParentNodeDoesNotExist() {
		threeGraph.isAdjacent("node 4", "node 1");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testcheckDestinationNodeDoesNotExist() {
		threeGraph.isAdjacent("node 1", "node 4");
	}
	
	@Test
	public void testGetIncidentEdgesNoNode() {
		assertEquals("set not empty", threeGraph.getIncidentEdges("node 1"), tree(0));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void clientAddValue() {
		threeGraph.getIncidentEdges("node 1").add(new DirectedEdge<String, String>("Rip", "ahaha"));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void clientRemoveValue() {
		threeGraph.getIncidentEdges("node 1").remove(new DirectedEdge<String, String>("Rip", "ahaha"));
	}
}
