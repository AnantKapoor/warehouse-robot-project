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
		
		PathInfo startingPoint = new PathInfo(new GridPose(new Point (9,0),Heading.MINUS_Y ), path, new Point(2,4));
		finder.allPaths.add(startingPoint);
		finder.move(1);

//		ArrayList<PathInfo> allPathsExpected = new ArrayList<PathInfo>();
//		allPathsExpected.add(startingPoint);
//		
//		GridPose nextLocation = allPathsExpected.get(0).pose.clone();
//		nextLocation.rotateUpdate(1 * 90);
//		nextLocation.moveUpdate();
//		ArrayList<Integer> nextPath = (ArrayList<Integer>) allPathsExpected.get(0).path.clone();
//		nextPath.add(1);
//		if (map.isValidTransition(allPathsExpected.get(0).pose.getPosition(),
//				nextLocation.getPosition())) {
//			allPathsExpected.add(1, new PathInfo(nextLocation, nextPath, new Point(2,4)));
//		}
		
		ArrayList<Integer> expectedPath = new ArrayList<Integer>();
		expectedPath.add(1);
		
		assertEquals(finder.allPaths.get(1).path, expectedPath);
		assertEquals(finder.allPaths.get(1).goal, null);
		assertEquals(finder.allPaths.get(1).pose.getX(), 10);
		assertEquals(finder.allPaths.get(1).pose.getY(), 0);
		assertEquals(finder.allPaths.get(1).pose.getHeading(), Heading.PLUS_X );
	}
	
	@Test 
	public void findPatMoveTest1 (){
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder finder = new PathFinder(map);
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		PathInfo startingPoint = new PathInfo(new GridPose(new Point (0,0),Heading.PLUS_X ), path, new Point(2,4));
		finder.allPaths.add(startingPoint);
		finder.move(1);
		
		ArrayList<Integer> expectedPath = new ArrayList<Integer>();
		expectedPath.add(1);
		
		assertEquals(finder.allPaths.get(1).path, expectedPath);
		assertEquals(finder.allPaths.get(1).goal, null);
		assertEquals(finder.allPaths.get(1).pose.getX(), 0);
		assertEquals(finder.allPaths.get(1).pose.getY(), 1);
		assertEquals(finder.allPaths.get(1).pose.getHeading(), Heading.PLUS_Y );
	}
	
	@Test 
	public void findPatMoveTestNegative1 (){
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder finder = new PathFinder(map);
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		PathInfo startingPoint = new PathInfo(new GridPose(new Point (2,4),Heading.PLUS_X ), path, new Point(2,4));
		finder.allPaths.add(startingPoint);
		finder.move(-1);
		
		ArrayList<Integer> expectedPath = new ArrayList<Integer>();
		expectedPath.add(-1);
		
		assertEquals(finder.allPaths.get(1).path, expectedPath);
		assertEquals(finder.allPaths.get(1).goal, null);
		assertEquals(finder.allPaths.get(1).pose.getX(), 2);
		assertEquals(finder.allPaths.get(1).pose.getY(), 3);
		assertEquals(finder.allPaths.get(1).pose.getHeading(), Heading.MINUS_Y );
	}
}
