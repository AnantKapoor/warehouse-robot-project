package main.java.PathFinding;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import rp.robotics.navigation.GridPose;

public class PathInfo {
	public GridPose pose;
	public ArrayList<Integer>path = new ArrayList<Integer>();
	public ArrayList<Point2D> visitedCoordinates=new ArrayList<Point2D>();
	public GridPose goal;
	
	public PathInfo(GridPose pose,ArrayList<Integer> path,GridPose goal, ArrayList<Point2D> visitedCoordinates) {
		this.pose=pose;
		this.path=path;
		this.goal=goal;
		this.visitedCoordinates=visitedCoordinates;
	}
	public PathInfo(GridPose pose,ArrayList<Integer> path,GridPose goal) {
		this.pose=pose;
		this.path=path;
		this.goal=goal;
	}
	public PathInfo(GridPose pose) {
		this.pose=pose;
	}
	public Integer getDistance() {
		return (int) (Math.abs(pose.getX()-goal.getX())+Math.abs(pose.getY()-goal.getY()));
	}
	public char[] get(int i) {
		// TODO Auto-generated method stub
		return null;
	}
}
