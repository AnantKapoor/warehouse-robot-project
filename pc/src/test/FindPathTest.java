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

public class FindPathTest {
	
	@Test 
	public void findPathTestDistanceZero1 (){
		GridMap map= MapUtils.createRealWarehouse2016();
		PathFinder find=new PathFinder(map);
		
		PathInfo path= find.FindPath(new GridPose(new Point (0,0),Heading.PLUS_X ), new Point(5,5));
		String result = "";
		for(int i =0;i<path.path.size();i++) {
			result += (path.path.get(i));
		}
		assertEquals(result, "0000010000");
	}
	
	@Test 
	public void findPathTestDistanceZero2 (){
		GridMap map= MapUtils.createRealWarehouse2016();
		PathFinder find=new PathFinder(map);
		
		PathInfo path= find.FindPath(new GridPose(new Point (3,0),Heading.PLUS_X ), new Point(7,0));
		String result = "";
		for(int i =0;i<path.path.size();i++) {
			result += (path.path.get(i));
		}
		assertEquals(result, "0000");
	}
	
	@Test 
	public void findPathTestAlreadyThere (){
		GridMap map= MapUtils.createRealWarehouse2016();
		PathFinder find=new PathFinder(map);
		
		PathInfo path= find.FindPath(new GridPose(new Point (2,0),Heading.PLUS_X ), new Point(2,0));
		String result = "";
		for(int i =0;i<path.path.size();i++) {
			result += (path.path.get(i));
		}
		assertEquals(result, "");
	}
	
	@Test 
	public void findPathTestDistance1 (){
		GridMap map= MapUtils.createRealWarehouse2016();
		PathFinder find=new PathFinder(map);
		
		PathInfo path= find.FindPath(new GridPose(new Point (3,0),Heading.PLUS_X ), new Point(2,5));
		String result = "";
		for(int i =0;i<path.path.size();i++) {
			result += (path.path.get(i));
		}
		assertEquals(result, "2-10000");
	}
	
	@Test 
	public void findPathTestNumberOfStepsZERO (){
		GridMap map= MapUtils.createRealWarehouse2016();
		PathFinder find=new PathFinder(map);
		
		PathInfo path= find.FindPath(new GridPose(new Point (3,7),Heading.PLUS_X ), new Point(3,7));
		
		ArrayList<Integer> listOfSteps= new ArrayList<Integer>();
		
		for(int i =0;i<path.path.size();i++) {
			listOfSteps.add(path.path.get(i));
		}
		assertEquals(listOfSteps.size(), 0);
	}
	
	@Test 
	public void findPathTestNumberOfSteps (){
		GridMap map= MapUtils.createRealWarehouse2016();
		PathFinder find=new PathFinder(map);
		
		PathInfo path= find.FindPath(new GridPose(new Point (3,0),Heading.PLUS_X ), new Point(2,5));
		
		ArrayList<Integer> listOfSteps= new ArrayList<Integer>();
		
		for(int i =0;i<path.path.size();i++) {
			listOfSteps.add(path.path.get(i));
		}
		assertEquals(listOfSteps.size(), 6);
	}
}
