package test;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.ArrayList;

import main.java.PathFinding.PathFinder2;
import main.java.PathFinding.PathInfo;

import org.junit.Test;

import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;

public class PathFinder2MoveTest {
	@Test 
	public void findPatMoveTestTEST1 (){
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder2 finder = new PathFinder2(map);
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		PathInfo startingPoint = new PathInfo(new GridPose(new Point (9,0),Heading.MINUS_Y ), path, new GridPose(new Point (1,2),Heading.MINUS_X ));
		finder.allPaths1.add(startingPoint);
		finder.move(2);
		
		ArrayList<Integer> expectedPath = new ArrayList<Integer>();
		
		assertEquals(finder.allPaths1.get(0).path, expectedPath);
		assertEquals(finder.allPaths1.get(0).pose.getX(), 9);
		assertEquals(finder.allPaths1.get(0).pose.getY(), 0);
		assertEquals(finder.allPaths1.get(0).pose.getHeading(), Heading.MINUS_Y );
	}
	
	@Test 
	public void findPatMoveTest11 (){
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder2 finder = new PathFinder2(map);
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		PathInfo startingPoint = new PathInfo(new GridPose(new Point (1,0),Heading.PLUS_X ), path, new GridPose(new Point (1,9),Heading.MINUS_X ));
		finder.allPaths1.add(startingPoint);
		finder.move(2);
		
		ArrayList<Integer> expectedPath = new ArrayList<Integer>();
		expectedPath.add(2);
		
		assertEquals(finder.allPaths1.get(1).path, expectedPath);
		assertEquals(finder.allPaths1.get(1).pose.getX(), 0);
		assertEquals(finder.allPaths1.get(1).pose.getY(), 0);
		assertEquals(finder.allPaths1.get(1).pose.getHeading(), Heading.MINUS_X );
	}
	
	@Test 
	public void findPatMoveTestNegative11 (){
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder2 finder = new PathFinder2(map);
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		PathInfo startingPoint = new PathInfo(new GridPose(new Point (9,0),Heading.MINUS_Y ), path, new GridPose(new Point (1,2),Heading.MINUS_X ));
		finder.allPaths1.add(startingPoint);
		finder.move(-1);
		
		ArrayList<Integer> expectedPath = new ArrayList<Integer>();
		//expectedPath.add(-1);
		
		assertEquals(finder.allPaths1.get(0).path, expectedPath);
		assertEquals(finder.allPaths1.get(0).pose.getX(), 9);
		assertEquals(finder.allPaths1.get(0).pose.getY(), 0);
		assertEquals(finder.allPaths1.get(0).pose.getHeading(), Heading.MINUS_Y );
	}
	
	//SECOND
	
	@Test 
	public void findPatMoveTestTEST2 (){
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder2 finder = new PathFinder2(map);
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		PathInfo startingPoint = new PathInfo(new GridPose(new Point (3,1),Heading.MINUS_X ), path, new GridPose(new Point (1,2),Heading.MINUS_Y ));
		PathInfo secondPoint = new PathInfo(new GridPose(new Point (4,2),Heading.MINUS_X ), path, new GridPose(new Point (4,6),Heading.MINUS_Y ));
		PathInfo thirdPoint = new PathInfo(new GridPose(new Point (2,2),Heading.MINUS_X ), path, new GridPose(new Point (0,1),Heading.MINUS_Y ));
		finder.allPaths1.add(startingPoint);
		finder.allPaths2.add(secondPoint);
		finder.allPaths3.add(startingPoint);
		finder.move3(2);
		finder.move3(2);
		
		ArrayList<Integer> expectedPath = new ArrayList<Integer>();
		
		assertEquals(finder.allPaths2.get(0).path, expectedPath);
		assertEquals(finder.allPaths2.get(0).pose.getX(), 4);
		assertEquals(finder.allPaths2.get(0).pose.getY(), 2);
		assertEquals(finder.allPaths2.get(0).pose.getHeading(), Heading.MINUS_X );
	}
	
	@Test 
	public void findPatMoveTest12 (){
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder2 finder = new PathFinder2(map);
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		PathInfo startingPoint = new PathInfo(new GridPose(new Point (3,1),Heading.PLUS_X ), path, new GridPose(new Point (2,9),Heading.MINUS_X ));
		PathInfo secondPoint = new PathInfo(new GridPose(new Point (5,2),Heading.PLUS_X ), path, new GridPose(new Point (3,8),Heading.MINUS_X ));
		PathInfo thirdPoint = new PathInfo(new GridPose(new Point (2,2),Heading.PLUS_X ), path, new GridPose(new Point (9,8),Heading.MINUS_X ));
		finder.allPaths1.add(startingPoint);
		finder.allPaths2.add(secondPoint);
		finder.allPaths3.add(startingPoint);
		finder.move3(2);
		finder.move3(2);
		
		ArrayList<Integer> expectedPath = new ArrayList<Integer>();
		
		assertEquals(finder.allPaths2.get(0).path, expectedPath);
		assertEquals(finder.allPaths2.get(0).pose.getX(), 5);
		assertEquals(finder.allPaths2.get(0).pose.getY(), 2);
		assertEquals(finder.allPaths2.get(0).pose.getHeading(), Heading.PLUS_X );
	}
	
	@Test 
	public void findPatMoveTestNegative12 (){
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder2 finder = new PathFinder2(map);
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		PathInfo startingPoint = new PathInfo(new GridPose(new Point (5,0),Heading.MINUS_Y ), path, new GridPose(new Point (1,1),Heading.MINUS_X ));
		PathInfo secondPoint = new PathInfo(new GridPose(new Point (0,0),Heading.MINUS_Y ), path, new GridPose(new Point (1,2),Heading.MINUS_X ));
		PathInfo thirdPoint = new PathInfo(new GridPose(new Point (7,7),Heading.MINUS_Y ), path, new GridPose(new Point (0,3),Heading.MINUS_X ));
		finder.allPaths1.add(startingPoint);
		finder.allPaths2.add(secondPoint);
		finder.allPaths3.add(startingPoint);
		finder.move3(2);
		finder.move3(2);
		
		ArrayList<Integer> expectedPath = new ArrayList<Integer>();
		//expectedPath.add(-1);
		
		assertEquals(finder.allPaths2.get(0).path, expectedPath);
		assertEquals(finder.allPaths2.get(0).pose.getX(), 0);
		assertEquals(finder.allPaths2.get(0).pose.getY(), 0);
		assertEquals(finder.allPaths2.get(0).pose.getHeading(), Heading.MINUS_Y );
	}
}
