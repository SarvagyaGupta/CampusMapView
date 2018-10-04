package hw8.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hw5.DirectedEdge;
import hw8.CampusParser;
import hw8.CampusParser.MalformedDataException;
import hw8.MyPoint;

public class CampusParserTest {
	Map<String, String> buildingNames;
	Map<String, MyPoint> buildingPos;
	Set<MyPoint> allPoints;
	Map<MyPoint, List<DirectedEdge<Double, MyPoint>>> paths;

	@Before
	public void setUp() throws Exception {
		allPoints = new HashSet<>();
		buildingNames = new HashMap<>();
		buildingPos = new HashMap<>();
		paths = new HashMap<>();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuildingsParserAllBuildingsSize() throws MalformedDataException {
		CampusParser.parseCampusBuildings("campus_buildings.dat", buildingNames, buildingPos);
		assertEquals("size should be 51", buildingNames.keySet().size(), 51);
	}

	@Test
	public void testBuildingsParserAllPosition() throws MalformedDataException {
		CampusParser.parseCampusBuildings("campus_buildings.dat", buildingNames, buildingPos);
		assertEquals("size should be 51", buildingPos.keySet().size(), 51);
	}
	
	@Test
	public void testBuildingsParserKeySetEqual() throws MalformedDataException {
		CampusParser.parseCampusBuildings("campus_buildings.dat", buildingNames, buildingPos);
		assertEquals("keySets should be equal", buildingPos.keySet(), buildingNames.keySet());
	}
	
	@Test
	public void testCampusParserAllNodes() throws MalformedDataException {
		CampusParser.parseCampusPathData("campus_paths.dat", allPoints, paths);
		assertEquals("should be 2067", allPoints.size(), 2067);
	}
	
	@Test
	public void testCampusParserMalformed() {
		boolean check = false;
		try {
			CampusParser.parseCampusPathData("MalformedCampus.dat", allPoints, paths);
		} catch (MalformedDataException e) {
			check = true;
		}
		assertTrue(check);
	}
	
	@Test
	public void testBuildingsParserAllNodes() {
		boolean check = false;
		try {
			CampusParser.parseCampusBuildings("MalformedBuildings.dat", buildingNames, buildingPos);
		} catch (MalformedDataException e) {
			check = true;
		}
		assertTrue(check);
	}
	
	
}
