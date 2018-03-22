package main.java.PathFinding;

import java.awt.Point;
import java.util.ArrayList;

import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;

public abstract class test {

	public static void main(String[] args) {
		for (int i = 0; i < 20; i+=3) {
			System.out.println(i);
			System.out.println(i+1);
			System.out.println(i+2);
		}
		/*int[][] row= {{0,1,2,3},{11,12,13,14}};
		int[][] row2=row.clone();
		row2[0][0]=5;
		System.out.println(row[0][0]);*/
		// TODO Auto-generated method stub
//		GridMap map= MapUtils.createRealWarehouse();
//		PathFinder find=new PathFinder(map);
//
//		PathInfo path=find.FindPath(new GridPose(new Point (6,2),Heading.MINUS_Y ), new Point(6,5));
//		for(int i =0;i<path.path.size();i++) {
//			System.out.println(path.path.get(i));
//		}
	}

}
