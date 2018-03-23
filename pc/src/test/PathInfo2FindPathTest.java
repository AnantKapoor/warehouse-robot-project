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

public class PathInfo2FindPathTest {

	@Test
	public void getDistnaceTest1() {
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder2 finder = new PathFinder2(map);
		
		ArrayList<PathInfo> foundPath = finder.FindPath((new GridPose(new Point(
				2, 1), Heading.PLUS_X)), new GridPose(new Point(2, 3),
				Heading.PLUS_X),
				(new GridPose(new Point(3, 4), Heading.PLUS_X)), new GridPose(
						new Point(0, 0), Heading.PLUS_X), (new GridPose(
						new Point(0, 2), Heading.PLUS_X)), new GridPose(
						new Point(0, 3), Heading.PLUS_X));

		assertEquals(foundPath.get(0).pose.getX(), 2);
		assertEquals(foundPath.get(0).pose.getY(), 3);
		assertEquals(foundPath.get(0).pose.getHeading(), Heading.PLUS_Y);
	}
	
	@Test
	public void getDistnaceTest2() {
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder2 finder = new PathFinder2(map);
		
		ArrayList<PathInfo> foundPath = finder.FindPath((new GridPose(new Point(
				9, 1), Heading.PLUS_X)), new GridPose(new Point(9, 3),
				Heading.PLUS_X),
				(new GridPose(new Point(3, 4), Heading.PLUS_X)), new GridPose(
						new Point(0, 0), Heading.PLUS_X), (new GridPose(
						new Point(0, 2), Heading.PLUS_X)), new GridPose(
						new Point(0, 3), Heading.PLUS_X));

		assertEquals(foundPath.get(0).pose.getX(), 9);
		assertEquals(foundPath.get(0).pose.getY(), 3);
		assertEquals(foundPath.get(0).pose.getHeading(), Heading.PLUS_Y);
	}
	
	@Test
	public void getDistnaceTest3() {
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder2 finder = new PathFinder2(map);
		
		ArrayList<PathInfo> foundPath = finder.FindPath((new GridPose(new Point(
				2, 1), Heading.MINUS_Y)), new GridPose(new Point(5, 3),
				Heading.MINUS_X),
				(new GridPose(new Point(3, 4), Heading.PLUS_X)), new GridPose(
						new Point(0, 0), Heading.PLUS_X), (new GridPose(
						new Point(0, 2), Heading.PLUS_X)), new GridPose(
						new Point(0, 3), Heading.PLUS_X));

		assertEquals(foundPath.get(0).pose.getX(), 5);
		assertEquals(foundPath.get(0).pose.getY(), 3);
		assertEquals(foundPath.get(0).pose.getHeading(), Heading.PLUS_Y);
	}
	
	@Test
	public void getDistnaceTest4() {
		GridMap map = MapUtils.createRealWarehouse2016();
		PathFinder2 finder = new PathFinder2(map);
		
		ArrayList<PathInfo> foundPath = finder.FindPath((new GridPose(new Point(
				0, 1), Heading.PLUS_X)), new GridPose(new Point(3, 3),
				Heading.PLUS_X),
				(new GridPose(new Point(3, 4), Heading.PLUS_X)), new GridPose(
						new Point(0, 0), Heading.PLUS_X), (new GridPose(
						new Point(0, 2), Heading.PLUS_X)), new GridPose(
						new Point(0, 3), Heading.PLUS_X));

		assertEquals(foundPath.get(0).pose.getX(), 3);
		assertEquals(foundPath.get(0).pose.getY(), 3);
		assertEquals(foundPath.get(0).pose.getHeading(), Heading.PLUS_Y);
	}
}
 