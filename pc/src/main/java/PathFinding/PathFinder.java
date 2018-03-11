package main.java.PathFinding;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPose;

public class PathFinder {
	public GridMap map;
	public Point goalCoordinates;
	public ArrayList<PathInfo> allPaths = new ArrayList<PathInfo>();

	public PathFinder(GridMap map) {
		this.map = map;
	}

	public PathInfo FindPath(GridPose startingPose, Point goalCoordinates) {
		boolean foundPath = false;
		allPaths = new ArrayList<PathInfo>();
		this.goalCoordinates = goalCoordinates;
		ArrayList<Integer> path = new ArrayList<Integer>();
		PathInfo startingPoint = new PathInfo(startingPose, path,
				goalCoordinates);
		
		allPaths.add(startingPoint);
		move(2);
		allPaths.add(startingPoint);
		int counter = 0;
		if(!map.isObstructed((int)goalCoordinates.getX(),(int) goalCoordinates.getY())&& map.isValidGridPosition((int)goalCoordinates.getX(),(int) goalCoordinates.getY())) {
			while (!foundPath&&allPaths.size()<20000) {
				System.out.println(allPaths.get(0).path.size());
				counter++;
				Collections.sort(allPaths, new Comparator<PathInfo>() {
					@Override
					public int compare(PathInfo o1, PathInfo o2) {
						return ((Integer)(o1.path.size())).compareTo(o2.path.size());
					}
				});
				if (allPaths.get(0).getDistance() == 0) {
					foundPath = true;
				} else {
					this.move(1);
					this.move(-1);
					this.move(0);
					allPaths.remove(0);
				}
			}
			//System.out.print(allPaths.get(0).path.toString());
			return allPaths.get(0);
		} else {
			return new PathInfo(new GridPose(),allPaths.get(0).path,new Point()) ;
		}
	}

	public void move(int direction) {
		GridPose nextLocation = allPaths.get(0).pose.clone();
		nextLocation.rotateUpdate(direction * 90);
		nextLocation.moveUpdate();
		ArrayList<Integer> nextPath = (ArrayList<Integer>) allPaths.get(0).path
				.clone();
		nextPath.add(direction);
		if (map.isValidTransition(allPaths.get(0).pose.getPosition(),
				nextLocation.getPosition())) {
			this.allPaths.add(1, new PathInfo(nextLocation, nextPath,
					this.goalCoordinates));
		}

		// return new PathInfo(nextLocation, nextPath, this.goalCoordinates);
	}

}
