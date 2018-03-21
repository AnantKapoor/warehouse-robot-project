package main.java.PathFinding;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import lejos.geom.Point;
import lejos.robotics.mapping.LineMap;
import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPose;

public class PathFinder2 {
		public GridMap map;
		public ArrayList<PathInfo> allPaths1 = new ArrayList<PathInfo>();
		public ArrayList<PathInfo> allPaths2 = new ArrayList<PathInfo>();
		public ArrayList<PathInfo> allPaths3 = new ArrayList<PathInfo>();
		
		public PathFinder2(GridMap map2) {
			this.map = map2;
		}
		public ArrayList<PathInfo> FindPath(GridPose startingPose1, GridPose goalCoordinates1,GridPose startingPose2,
				GridPose goalCoordinates2,GridPose startingPose3, GridPose goalCoordinates3) {
			allPaths1 = new ArrayList<PathInfo>();
			allPaths2 = new ArrayList<PathInfo>();
			allPaths3 = new ArrayList<PathInfo>();
			ArrayList<Integer> path = new ArrayList<Integer>();
			ArrayList<Point2D> points1 = new ArrayList<Point2D>();
			ArrayList<Point2D> points2 = new ArrayList<Point2D>();
			ArrayList<Point2D> points3 = new ArrayList<Point2D>();
			points1.add(startingPose1.getPosition());
			points2.add(startingPose2.getPosition());
			points3.add(startingPose3.getPosition());
			PathInfo startingPoint1 = new PathInfo(startingPose1, path,
					goalCoordinates1,points1);
			PathInfo startingPoint2 = new PathInfo(startingPose2, path,
					goalCoordinates2,points2);
			PathInfo startingPoint3 = new PathInfo(startingPose3, path,
					goalCoordinates3,points3);
			allPaths1.add(startingPoint1);
			allPaths2.add(startingPoint2);
			allPaths3.add(startingPoint3);
			//move(2);
			boolean foundPath=false;
			while (!foundPath) {
				if (allPaths1.get(0).getDistance() == 0) {
					foundPath = true;
				} else {
					move(1);
					move(-1);
					move(0);
					allPaths1.remove(0);
					Collections.sort(allPaths1, new Comparator<PathInfo>() {
						@Override
						public int compare(PathInfo o1, PathInfo o2) {
							return ((Integer)(o1.path.size()+o1.getDistance())).compareTo(o2.path.size()+o2.getDistance());
						}
					});
				}
			}
			foundPath=false;
			move2(2,allPaths2);
			while(!foundPath) {
				if (allPaths2.get(0).getDistance() == 0) {
					foundPath = true;
				} else {
					move2(1,allPaths2);
					move2(-1,allPaths2);
					move2(0,allPaths2);
					allPaths2.remove(0);
					Collections.sort(allPaths2, new Comparator<PathInfo>() {
						@Override
						public int compare(PathInfo o1, PathInfo o2) {
							return ((Integer)(o1.path.size()+o1.getDistance())).compareTo(o2.path.size()+o2.getDistance());
						}
					});
				}
			}
			foundPath=false;
			while(!foundPath) {
				if (allPaths3.get(0).getDistance() == 0) {
					foundPath = true;
				} else {
					move3(1);
					move3(-1);
					move3(0);
					allPaths3.remove(0);
					Collections.sort(allPaths3, new Comparator<PathInfo>() {
						@Override
						public int compare(PathInfo o1, PathInfo o2) {
							return ((Integer)(o1.path.size()+o1.getDistance())).compareTo(o2.path.size()+o2.getDistance());
						}
					});
				}
			}
			ArrayList<PathInfo>finalPaths=new ArrayList<PathInfo>();
			finalPaths.add(allPaths1.get(0));
			finalPaths.add(allPaths2.get(0));
			finalPaths.add(allPaths3.get(0));
			return finalPaths;
		}
		public void move(int direction) {
			GridPose nextLocation = allPaths1.get(0).pose.clone();
			nextLocation.rotateUpdate(direction * 90);
			nextLocation.moveUpdate();
			if (map.isValidTransition(allPaths1.get(0).pose.getPosition(),nextLocation.getPosition())) {
				ArrayList<Integer> nextPath = (ArrayList<Integer>) allPaths1.get(0).path.clone();
				ArrayList<Point2D> nextPoint = (ArrayList<Point2D>) allPaths1.get(0).visitedCoordinates.clone();
				nextPath.add(direction);
				nextPoint.add(nextLocation.getPosition());
				allPaths1.add(1, new PathInfo(nextLocation, nextPath,allPaths1.get(0).goal,nextPoint));
			}
		}
		public void move2(int direction,ArrayList<PathInfo> allPaths) {
			GridPose nextLocation = allPaths.get(0).pose.clone();
			nextLocation.rotateUpdate(direction * 90);
			nextLocation.moveUpdate();
			if (map.isValidTransition(allPaths.get(0).pose.getPosition(),nextLocation.getPosition())) {
				Point2D point=nextLocation.getPosition();
				ArrayList<Integer> nextPath = (ArrayList<Integer>) allPaths.get(0).path.clone();
				ArrayList<Point2D> nextPoint = (ArrayList<Point2D>) allPaths.get(0).visitedCoordinates.clone();
				nextPath.add(direction);
				nextPoint.add(nextLocation.getPosition());
				if(allPaths1.get(0).visitedCoordinates.contains(point)) {
					int pointIndex =allPaths1.get(0).visitedCoordinates.indexOf(point);
					int distance = Math.abs(pointIndex-allPaths.get(0).visitedCoordinates.size());
					if(distance>1) {
						if(allPaths1.get(0).visitedCoordinates.size()-1!=pointIndex||allPaths.get(0).visitedCoordinates.size()+1<pointIndex) {
							allPaths.add(1, new PathInfo(nextLocation, nextPath,allPaths.get(0).goal,nextPoint));
						}
					}
				} else allPaths.add(1, new PathInfo(nextLocation, nextPath,allPaths.get(0).goal,nextPoint));
			}
		}
		public void move3(int direction) {
			GridPose nextLocation = allPaths3.get(0).pose.clone();
			nextLocation.rotateUpdate(direction * 90);
			nextLocation.moveUpdate();
			if (map.isValidTransition(allPaths3.get(0).pose.getPosition(),nextLocation.getPosition())) {
				Point2D point=nextLocation.getPosition();
				ArrayList<Integer> nextPath = (ArrayList<Integer>) allPaths3.get(0).path.clone();
				ArrayList<Point2D> nextPoint = (ArrayList<Point2D>) allPaths3.get(0).visitedCoordinates.clone();
				nextPath.add(direction);
				nextPoint.add(nextLocation.getPosition());
				if(allPaths2.get(0).visitedCoordinates.contains(point)) {
					int pointIndex =allPaths2.get(0).visitedCoordinates.indexOf(point);
					int distance = Math.abs(pointIndex-allPaths3.get(0).visitedCoordinates.size());
					if(distance>1) {
						if(allPaths2.get(0).visitedCoordinates.size()-1!=pointIndex||allPaths3.get(0).visitedCoordinates.size()+1<pointIndex) {
							move2(direction,allPaths3);
						}
					}
				} else move2(direction,allPaths3);
			}
		}
}

