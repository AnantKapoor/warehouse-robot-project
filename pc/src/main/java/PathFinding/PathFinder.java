package main.java.PathFinding;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import lejos.geom.Point;
import lejos.robotics.mapping.LineMap;
import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPose;

public class PathFinder {
	public LineMap map;
	public GridPose goalCoordinates;
	public ArrayList<PathInfo> allPaths = new ArrayList<PathInfo>();

	public PathFinder(LineMap map2) {
		this.map = map2;
	}

	public PathInfo FindPath(GridPose startingPose, GridPose goalCoordinates) {
		boolean foundPath = false;
		allPaths = new ArrayList<PathInfo>();
		this.goalCoordinates = goalCoordinates;
		ArrayList<Integer> path = new ArrayList<Integer>();
		PathInfo startingPoint = new PathInfo(startingPose, path,
				goalCoordinates);
		
		allPaths.add(startingPoint);
		move(2);
		allPaths.add(startingPoint);
		if(map.inside(new Point((int)goalCoordinates.getX(),(int) goalCoordinates.getY()))&&  map.inside(new Point((int)goalCoordinates.getX(),(int) goalCoordinates.getY()))) {
			while (!foundPath) {
				if (allPaths.get(0).getDistance() == 0) {
					foundPath = true;
				} else {
					this.move(1);
					this.move(-1);
					this.move(0);
					allPaths.remove(0);
				}
				Collections.sort(allPaths, new Comparator<PathInfo>() {
						@Override
						public int compare(PathInfo o1, PathInfo o2) {
							return ((Integer)(o1.path.size()+o1.getDistance())).compareTo(o2.path.size()+o2.getDistance());
						}
					});
			}
			return allPaths.get(0);
		} else {
			return new PathInfo(new GridPose(),allPaths.get(0).path,new GridPose()) ;
		}
	}

	public void move(int direction) {
		GridPose nextLocation = allPaths.get(0).pose.clone();
		nextLocation.rotateUpdate(direction * 90);
		nextLocation.moveUpdate();
		@SuppressWarnings("unchecked")
		ArrayList<Integer> nextPath = (ArrayList<Integer>) allPaths.get(0).path
				.clone();
		nextPath.add(direction);
		if (map.inside(new Point((int)allPaths.get(0).pose.getPosition().getX(),(int)allPaths.get(0).pose.getPosition().getY()))&&
				map.inside(new Point((int)nextLocation.getPosition().getX(),(int)nextLocation.getPosition().getY()))) {
			this.allPaths.add(1, new PathInfo(nextLocation, nextPath,
					this.goalCoordinates));
		} 

		// return new PathInfo(nextLocation, nextPath, this.goalCoordinates);
	}

}
