package test;

import static org.junit.Assert.*;

import java.awt.Point;

import main.java.PathFinding.PathFinder;
import main.java.PathFinding.PathInfo;

import org.junit.Test;

import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;

public class PathInfoGetDistanceTest {
	
	static GridMap map= MapUtils.createRealWarehouse2016();
	static PathFinder find=new PathFinder(map);
	
	@Test
	public void getDistnaceTestZERO1PLUSX() {
		PathInfo pathInfo=find.FindPath(new GridPose(new Point (0,0),Heading.PLUS_X ), new Point(5,5));
		int result = pathInfo.getDistance();
		assertEquals(result,0);
	}
	
	@Test
	public void getDistnaceTestZERO2PLUSX() {
		PathInfo pathInfo=find.FindPath(new GridPose(new Point (11,0),Heading.PLUS_X ), new Point(0,0));
		int result = pathInfo.getDistance();
		assertEquals(result,0);
	}
	
	@Test
	public void getDistnaceTest1PLUSX() {
		PathInfo pathInfo=find.FindPath(new GridPose(new Point (11,0),Heading.PLUS_X ), new Point(7,5));
		int result = pathInfo.getDistance();
		assertEquals(result,3);
	}
	
	@Test
	public void getDistnaceTest2PLUSX() {
		PathInfo pathInfo=find.FindPath(new GridPose(new Point (4,0),Heading.PLUS_Y ), new Point(7,5));
		int result = pathInfo.getDistance();
		assertEquals(result,3);
	}	
	
	@Test
	public void getDistnaceTestZERO1PLUSY() {
		PathInfo pathInfo=find.FindPath(new GridPose(new Point (0,0),Heading.PLUS_Y ), new Point(5,5));
		int result = pathInfo.getDistance();
		assertEquals(result,0);
	}
	
	@Test
	public void getDistnaceTestZERO2PLUSY() {
		PathInfo pathInfo=find.FindPath(new GridPose(new Point (11,0),Heading.PLUS_Y ), new Point(0,0));
		int result = pathInfo.getDistance();
		assertEquals(result,0);
	}
	
	@Test
	public void getDistnaceTest1PLUSY() {
		PathInfo pathInfo=find.FindPath(new GridPose(new Point (11,0),Heading.PLUS_Y ), new Point(7,5));
		int result = pathInfo.getDistance();
		assertEquals(result,3);
	}
	
	@Test
	public void getDistnaceTest2PLUSY() {
		PathInfo pathInfo=find.FindPath(new GridPose(new Point (4,0),Heading.PLUS_Y ), new Point(7,5));
		int result = pathInfo.getDistance();
		assertEquals(result,3);
	}	
}
