package main.java.PathFinding;

import java.awt.Point;
import java.util.ArrayList;

import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;

public abstract class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GridMap map= MapUtils.createRealWarehouse();
		PathFinder find=new PathFinder(map);

		PathInfo path=find.FindPath(new GridPose(new Point (6,2),Heading.MINUS_Y ), new Point(6,5));
		for(int i =0;i<path.path.size();i++) {
			System.out.println(path.path.get(i));
		}
	}

}
