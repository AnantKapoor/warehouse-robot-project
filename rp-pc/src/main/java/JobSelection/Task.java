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

public class Task {
	private Map<Character, Integer> tasks = new HashMap<Character, Integer>();
	private float reward;
	private ArrayList<OrderDetail> details = new ArrayList();

	public Map<Character, Integer> getTasks() {
		return tasks;
	}

	public ArrayList<OrderDetail> getDetails() {
		return details;
	}

	public void addTask(char item, int count) {
		tasks.put(item, count);
	}

	public float getReward() {
		return reward;
	}

	public float calculateReward(Map<Character, Integer> tasks, GridMap map,
			GridPose startingPose, ItemSpecifications itemSpecifications) {
		float totalReward = 0.0f;

		ItemSpecifications itemSpecifications1 = itemSpecifications;
		ItemSpecifications itemSpecifications2 = itemSpecifications;
		Map<Character, Specifications> specs = itemSpecifications2
				.getItemSpecification();
		int totalDistance = 0;
		PathFinder finder = new PathFinder(map);
		PathInfo pathInfo = null;
		Iterator it = tasks.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			char item = (Character) pair.getKey();
			int count = (Integer) pair.getValue();
			Iterator it2 = specs.entrySet().iterator();
			GridPose currentPose = startingPose;
			
			while (it2.hasNext()) {
				Map.Entry pair2 = (Map.Entry) it2.next();
				char item2 = (Character) pair2.getKey();
				
				if (item2 == item) {
					Specifications itemData = (Specifications) pair2.getValue();
					double reward = itemData.getReward();
					pathInfo = finder.FindPath(currentPose,
							itemData.getCoordinates());
					totalDistance += pathInfo.path.size();
					currentPose = pathInfo.pose;
					totalReward += ((float) reward) * ((float) count);
					details.add(new OrderDetail(item2, count));
					break;
				}
			}
		
			it2 = specs.entrySet().iterator();
		}
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