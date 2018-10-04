package hw5.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hw5.DirectedEdge;

public class EdgeTest {
	public static final DirectedEdge<String, String> EDGE_ONE = new DirectedEdge<>("edge one", "node one");
	public static final DirectedEdge<String, String> EDGE_ONE_P = new DirectedEdge<>("edge one", "node one");
	
	public static final DirectedEdge<String, String> EDGE_TWO = new DirectedEdge<>("edge two", "node one");
	public static final DirectedEdge<String, String> EDGE_TWO_P = new DirectedEdge<>("edge two", "node two");

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEquals() {
		assertEquals("equal RIP same", EDGE_ONE, EDGE_ONE_P);
		assertNotEquals("equal RIP diff", EDGE_ONE, EDGE_TWO);
	}

	@Test
	public void testHashCode() {
		assertEquals("hash RIP same", EDGE_ONE.hashCode(), EDGE_ONE_P.hashCode());
	}
	
	@Test
	public void testCompareToEqual() {
		assertEquals("compare RIP same", EDGE_ONE.compareTo(EDGE_ONE_P), 0);
	}
	
	@Test
	public void testCompareToLess() {
		assertTrue("compare RIP less", EDGE_TWO.compareTo(EDGE_TWO_P) < 0);
	}
	
	@Test
	public void testCompareToMore() {
		assertTrue("compare RIP more", EDGE_TWO.compareTo(EDGE_ONE) > 0);
	}
}
