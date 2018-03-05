package PathFinding;

import java.awt.Point;
import java.util.ArrayList;

import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;

public abstract class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GridMap map= MapUtils.createRealWarehouse2016();
		PathFinder find=new PathFinder(map);

		PathInfo path=find.FindPath(new GridPose(new Point (0,0),Heading.PLUS_X ), new Point(5,5));
		for(int i =0;i<path.path.size();i++) {
			System.out.println(path.path.get(i));
		}
	}

}
