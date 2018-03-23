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
		PathFinder2 find=new PathFinder2(map);

		ArrayList<PathInfo> path=find.FindPath(new GridPose(new Point (6,2),Heading.MINUS_Y ),new GridPose( new Point(6,6),Heading.MINUS_X),
				new GridPose(new Point (3,2),Heading.MINUS_Y ),new GridPose( new Point(6,6),Heading.MINUS_X),
				new GridPose(new Point (8,2),Heading.MINUS_Y ),new GridPose( new Point(6,5),Heading.MINUS_X));
		System.out.println("  k");
		for(int i =0;i<path.size();i++) {
			for (int j =0;j<path.get(i).path.size();j++) {
			System.out.println(path.get(i).path.get(j)+"  k");
		}
	}
	}
}
