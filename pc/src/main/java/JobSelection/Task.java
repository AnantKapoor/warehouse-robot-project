package main.java.JobSelection;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import main.java.PathFinding.PathInfo;
import main.java.PathFinding.PathFinder;
import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPose;
import rp.util.Collections;

public class Task {
	private Map<String, Integer> tasks = new HashMap<String, Integer>();
	private double reward;
	private ArrayList<OrderDetail> details = new ArrayList();
	private Point dropPoint= new Point();
	private ArrayList<ArrayList<Integer>>paths=new ArrayList<ArrayList<Integer>>();

	public Map<String, Integer> getTasks() {
		return tasks;
	}

	public ArrayList<OrderDetail> getDetails() {
		return details;
	}

	public void addTask(String item, int count) {
		tasks.put(item, count);
	}

	public double getReward() {
		return reward;
	}

	public float calculateReward(Map<String, Integer> tasks, GridMap map,
			GridPose startingPose, ItemSpecifications itemSpecifications) {
		float totalReward = 0.0f;

		ItemSpecifications itemSpecifications2 = itemSpecifications;
		Map<String, Specifications> specs = itemSpecifications2
				.getItemSpecification();
		int totalDistance = 0;
		PathFinder finder = new PathFinder(map);
		PathInfo pathInfo = null;
		Iterator it = tasks.entrySet().iterator();
		double totalWeight=0;
		GridPose currentPose = null;
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			String item = (String) pair.getKey();
			int count = (Integer) pair.getValue();
			Iterator it2 = specs.entrySet().iterator();
			currentPose = startingPose;
			
			while (it2.hasNext()) {
				Map.Entry pair2 = (Map.Entry) it2.next();
				String item2 = (String) pair2.getKey();
				
				if (item2.equals(item)) {
					Specifications itemData = (Specifications) pair2.getValue();
					if(itemData.getWeight()<50) {
						pathInfo = finder.FindPath(currentPose,
								itemData.getCoordinates());
						totalDistance += pathInfo.path.size();
						currentPose = pathInfo.pose;
						details.add(new OrderDetail(pathInfo.path));
						for(int i=0;i<count;i++) {
							if(itemData.getWeight()+totalWeight<=50) {
								double reward = itemData.getReward();
								totalReward += (float) reward;
								totalWeight+=itemData.getWeight();
								details.add(new OrderDetail(item2));
							} else {
								pathInfo=finder.FindPath(currentPose, dropPoint);
								totalReward += itemData.getReward();
								paths.add(pathInfo.path);
								totalDistance+=pathInfo.path.size();
								ArrayList<Integer> newPath = (ArrayList<Integer>) pathInfo.path.clone();
								newPath.add(3);
								GridPose newPose=pathInfo.pose;
								pathInfo=finder.FindPath(newPose, new Point((int)currentPose.getPosition().getX(),(int)currentPose.getPosition().getY()));
								newPath.addAll(pathInfo.path);
								totalWeight=itemData.getWeight();
								details.add(new OrderDetail(item2, newPath));
							}
						}
					}else return -1;	
					}
				}		
			it2 = specs.entrySet().iterator();
		}
		
		pathInfo=finder.FindPath(currentPose,dropPoint);
		ArrayList<Integer>finalPath= (ArrayList<Integer>) pathInfo.path.clone();
		finalPath.add(5);
		details.add(new OrderDetail(finalPath));
		it = tasks.entrySet().iterator();
		this.reward = totalReward;
		return totalReward / totalDistance;
	}
	
	/*
	 * public int calculateDistance(Iterator items,GridPose startingPose,GridMap
	 * map) {
	 * 
	 * ItemSpecifications itemSpecifications = Main.getItemSpecifications();
	 * GridPose currentPose=startingPose; int totalLength=0; PathFinder
	 * finder=new PathFinder(map); PathInfo pathInfo=null;
	 * while(items.hasNext()) { pathInfo= finder.FindPath(currentPose,
	 * itemSpecifications
	 * .getItemSpecification().get(items.next()).getCoordinates());
	 * totalLength+=pathInfo.path.size(); currentPose=pathInfo.pose; } return
	 * totalLength; }
	 */
}