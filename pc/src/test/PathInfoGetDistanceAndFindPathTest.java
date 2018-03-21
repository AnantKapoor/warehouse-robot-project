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

public class PathInfoGetDistanceAndFindPathTest {

	@Test
	public void getDistnaceTest1() {
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder find = new PathFinder(map);
		PathInfo pathInfo1 = new PathInfo(new GridPose(new Point(7, 9),
				Heading.PLUS_X), new ArrayList<Integer>(), new GridPose(new Point(2, 3),
						Heading.PLUS_Y));
		int result1 = pathInfo1.getDistance();
		assertEquals(result1, 11);
		
		PathInfo pathInfo2 = find.FindPath((new GridPose(new Point(7, 9),
				Heading.PLUS_X)), new GridPose(new Point(2, 3),
						Heading.PLUS_X));
		
		int result2 = pathInfo2.getDistance();
		assertEquals(result2, 0);
	}

	@Test
	public void getDistnaceTest2() {
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder find = new PathFinder(map);
		PathInfo pathInfo1 = new PathInfo(new GridPose(new Point(2, 9),
				Heading.MINUS_X), new ArrayList<Integer>(), new GridPose(new Point(0, 3),
						Heading.PLUS_Y));
		int result1 = pathInfo1.getDistance();
		assertEquals(result1, 8);
		
		PathInfo pathInfo2 = find.FindPath((new GridPose(new Point(2, 9),
				Heading.MINUS_X)), new GridPose(new Point(0, 3),
						Heading.PLUS_X));
		
		int result2 = pathInfo2.getDistance();
		assertEquals(result2, 0);
	}

	@Test
	public void getDistnaceTest3() {
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder find = new PathFinder(map);
		PathInfo pathInfo1 = new PathInfo(new GridPose(new Point(1, 3),
				Heading.MINUS_X), new ArrayList<Integer>(), new GridPose(new Point(9, 2),
						Heading.MINUS_X));
		int result1 = pathInfo1.getDistance();
		assertEquals(result1, 9);
		
		PathInfo pathInfo2 = find.FindPath((new GridPose(new Point(1, 3),
				Heading.MINUS_X)), new GridPose(new Point(9, 2),
						Heading.MINUS_X));
		
		int result2 = pathInfo2.getDistance();
		assertEquals(result2, 0);
	}

	@Test
	public void getDistnaceTest4() {
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder find = new PathFinder(map);
		PathInfo pathInfo1 = new PathInfo(new GridPose(new Point(2, 1),
				Heading.MINUS_Y), new ArrayList<Integer>(), new GridPose(new Point(0, 2),
						Heading.MINUS_X));
		int result1 = pathInfo1.getDistance();
		assertEquals(result1, 3);
		
		PathInfo pathInfo2 = find.FindPath((new GridPose(new Point(2, 1),
				Heading.MINUS_Y)), new GridPose(new Point(0, 2),
						Heading.MINUS_X));
		
		int result2 = pathInfo2.getDistance();
		assertEquals(result2, 0);
	}

	@Test
	public void getDistnaceTest5() {
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder find = new PathFinder(map);
		PathInfo pathInfo1 = new PathInfo(new GridPose(new Point(2, 9),
				Heading.MINUS_Y), new ArrayList<Integer>(), new GridPose(new Point(1, 3),
						Heading.PLUS_X));
		int result1 = pathInfo1.getDistance();
		assertEquals(result1, 7);
		
		PathInfo pathInfo2 = find.FindPath((new GridPose(new Point(2, 9),
				Heading.MINUS_Y)), new GridPose(new Point(1, 3),
						Heading.PLUS_X));
		
		int result2 = pathInfo2.getDistance();
		assertEquals(result2, 0);
	}

	@Test
	public void getDistnaceTest6() {
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder find = new PathFinder(map);
		PathInfo pathInfo1 = new PathInfo(new GridPose(new Point(0, 0),
				Heading.MINUS_Y), new ArrayList<Integer>(), new GridPose(new Point(5, 6),
						Heading.PLUS_X));
		int result1 = pathInfo1.getDistance();
		assertEquals(result1, 11);
		
		PathInfo pathInfo2 = find.FindPath((new GridPose(new Point(0, 0),
				Heading.MINUS_Y)), new GridPose(new Point(5, 6),
						Heading.PLUS_X));
		
		int result2 = pathInfo2.getDistance();
		assertEquals(result2, 0);
	}

	@Test
	public void getDistnaceTest7() {
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder find = new PathFinder(map);
		PathInfo pathInfo1 = new PathInfo(new GridPose(new Point(9, 9),
				Heading.PLUS_Y), new ArrayList<Integer>(), new GridPose(new Point(2, 8),
						Heading.PLUS_X));
		int result1 = pathInfo1.getDistance();
		assertEquals(result1, 8);
		
		PathInfo pathInfo2 = find.FindPath((new GridPose(new Point(9, 9),
				Heading.PLUS_Y)), new GridPose(new Point(2, 8),
						Heading.PLUS_X));
		
		int result2 = pathInfo2.getDistance();
		assertEquals(result2, 0);
	}
}
