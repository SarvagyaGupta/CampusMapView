package hw8.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hw8.CampusPaths;
import hw8.MyPoint;

public class CampusPathsTest {
	CampusPaths campus;

	@Before
	public void setUp() throws Exception {
		campus = new CampusPaths();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAllBuildings() {
		assertEquals("51 buildings on campus", campus.getBuildings().size(), 51);
	}
	
	@Test
	public void testBuildingsExists() {
		assertTrue("Building exists", campus.isBuilding("CSE"));
	}
	
	@Test
	public void testBuildingsNotExists() {
		assertFalse("Building not exists", campus.isBuilding("ECSE"));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetFullNameBuildingNotExist() {
		campus.getFullName("ECSE");
	}
	
	@Test
	public void testGetFullNameBuildingExist() {
		assertEquals("You are wrong", campus.getFullName("CSE"), 
				"Paul G. Allen Center for Computer Science & Engineering");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetPosBuildingNotExist() {
		campus.getPosBuilding("ECSE");
	}
	
	@Test
	public void testGetPosBuildingExist() {
		assertEquals("You are wrong", campus.getPosBuilding("PAB"),
				new MyPoint(1560.6467, 1698.3767));
	}
	
	@Test
	public void testNorth() {
		MyPoint a = new MyPoint(1.0, 1.0);
		MyPoint b = new MyPoint(1.0, 0.0);
		assertEquals("Direction should be N", "N", campus.getDirection(a,b));
	}
	
	@Test
	public void testSouth() {
		MyPoint a = new MyPoint(1.0, 1.0);
		MyPoint b = new MyPoint(1.0, 2.0);
		assertEquals("Direction should be S", "S", campus.getDirection(a,b));
	}
	
	@Test
	public void testEast() {
		MyPoint a = new MyPoint(1.0, 1.0);
		MyPoint b = new MyPoint(2.0, 1.0);
		assertEquals("Direction should be E", "E", campus.getDirection(a,b));
	}
	
	@Test
	public void testWest() {
		MyPoint a = new MyPoint(1.0, 1.0);
		MyPoint b = new MyPoint(0.0, 1.0);
		assertEquals("Direction should be W", "W", campus.getDirection(a,b));
	}
	
	@Test
	public void testNorthEast() {
		MyPoint a = new MyPoint(1.0, 1.0);
		MyPoint b = new MyPoint(2.0, 0.0);
		assertEquals("Direction should be NE", "NE", campus.getDirection(a,b));
	}
	
	@Test
	public void testSouthEast() {
		MyPoint a = new MyPoint(1.0, 1.0);
		MyPoint b = new MyPoint(2.0, 2.0);
		assertEquals("Direction should be SE", "SE", campus.getDirection(a,b));
	}
	
	@Test
	public void testNorthWest() {
		MyPoint a = new MyPoint(1.0, 1.0);
		MyPoint b = new MyPoint(0.0, 0.0);
		assertEquals("Direction should be NW", "NW", campus.getDirection(a,b));
	}
	
	@Test
	public void testSouthWest() {
		MyPoint a = new MyPoint(1.0, 1.0);
		MyPoint b = new MyPoint(0.0, 2.0);
		assertEquals("Direction should be SW", "SW", campus.getDirection(a,b));
	}
}
