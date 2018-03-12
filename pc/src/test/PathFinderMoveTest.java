package test;
import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import main.java.PathFinding.PathFinder;
import main.java.PathFinding.PathInfo;

import org.junit.Test;

import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;

public class PathFinderMoveTest {
	@Test 
	public void findPatMoveTestTEST (){
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder finder = new PathFinder(map);
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		PathInfo startingPoint = new PathInfo(new GridPose(new Point (9,0),Heading.MINUS_Y ), path, new GridPose(new Point (1,2),Heading.MINUS_X ));
		finder.allPaths.add(startingPoint);
		finder.move(2);
		
		ArrayList<Integer> expectedPath = new ArrayList<Integer>();
		
		assertEquals(finder.allPaths.get(0).path, expectedPath);
		assertEquals(finder.allPaths.get(0).pose.getX(), 9);
		assertEquals(finder.allPaths.get(0).pose.getY(), 0);
		assertEquals(finder.allPaths.get(0).pose.getHeading(), Heading.MINUS_Y );
	}
	
	@Test 
	public void findPatMoveTest1 (){
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder finder = new PathFinder(map);
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		PathInfo startingPoint = new PathInfo(new GridPose(new Point (1,0),Heading.PLUS_X ), path, new GridPose(new Point (1,9),Heading.MINUS_X ));
		finder.allPaths.add(startingPoint);
		finder.move(2);
		
		ArrayList<Integer> expectedPath = new ArrayList<Integer>();
		expectedPath.add(2);
		
		assertEquals(finder.allPaths.get(1).path, expectedPath);
		assertEquals(finder.allPaths.get(1).goal, null);
		assertEquals(finder.allPaths.get(1).pose.getX(), 0);
		assertEquals(finder.allPaths.get(1).pose.getY(), 0);
		assertEquals(finder.allPaths.get(1).pose.getHeading(), Heading.MINUS_X );
	}
	
	@Test 
	public void findPatMoveTestNegative1 (){
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder finder = new PathFinder(map);
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		PathInfo startingPoint = new PathInfo(new GridPose(new Point (9,0),Heading.MINUS_Y ), path, new GridPose(new Point (1,2),Heading.MINUS_X ));
		finder.allPaths.add(startingPoint);
		finder.move(-1);
		
		ArrayList<Integer> expectedPath = new ArrayList<Integer>();
		//expectedPath.add(-1);
		
		assertEquals(finder.allPaths.get(0).path, expectedPath);
		assertEquals(finder.allPaths.get(0).pose.getX(), 9);
		assertEquals(finder.allPaths.get(0).pose.getY(), 0);
		assertEquals(finder.allPaths.get(0).pose.getHeading(), Heading.MINUS_Y );
	}
}
