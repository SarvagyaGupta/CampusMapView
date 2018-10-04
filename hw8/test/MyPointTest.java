package hw8.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hw8.MyPoint;

public class MyPointTest {
	
	private static final MyPoint p1 = new MyPoint(1.0, 1.0);
	private static final MyPoint p2 = new MyPoint(-1.0, 2.0);
	private static final MyPoint p4 = new MyPoint(1.0, 4.0);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNotEquals() {
		assertFalse("nodes are not equal", p1.equals(p2));
	}
	
	@Test
	public void testEquals() {
		assertTrue("nodes are equal", p1.equals(new MyPoint(1.0, 1.0)));
	}
	
	@Test
	public void testCompareToLess() {
		assertTrue("nodes is less than", p1.compareTo(p4) < 0);
	}
	
	@Test
	public void testCompareToEqual() {
		assertEquals("nodes is equal to", p1.compareTo(p1), 0);
	}

	@Test
	public void testCompareToGreater() {
		assertTrue("nodes is equal to", p1.compareTo(p2) > 0);
	}
	
	@Test
	public void testHashCodeEquals() {
		assertEquals("nodes is equal to", p1.hashCode(), new MyPoint(1.0, 1.0).hashCode());
	}
}
