package PathFinding;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import rp.robotics.navigation.GridPose;

public class PathInfo {
	public GridPose pose;
	public ArrayList<Integer>path = new ArrayList<Integer>();

	public Point2D goal;
	public PathInfo(GridPose pose,ArrayList<Integer> path,Point2D goal) {
		this.pose=pose;
		this.path=path;
		this.goal=goal;
	}
	public Integer getDistance() {
		return (int) (Math.abs(pose.getX()-goal.getX())+Math.abs(pose.getY()-goal.getY()));
	}
}
