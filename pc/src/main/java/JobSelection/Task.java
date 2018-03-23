package main.java.JobSelection;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import lejos.robotics.mapping.LineMap;
import main.java.PathFinding.PathInfo;
import main.java.PathFinding.PathFinder;
import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.util.Collections;

public class Task {
	private Map<String, Integer> tasks = new HashMap<String, Integer>();
	private double reward;
	private ArrayList<OrderDetail> details;
	private static final GridPose dropPoint= new GridPose(new Point(1,0),Heading.PLUS_X);
	private static final GridPose startingPose= new GridPose(new Point(1,0),Heading.PLUS_X);
	private ArrayList<ArrayList<Integer>>paths=new ArrayList<ArrayList<Integer>>();
	private ArrayList<TaskList> stealStuff=new ArrayList();
	private ArrayList<OrderDetail> allOrder= new ArrayList();
	
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
	public ArrayList<TaskList> getTaskList(){
		return stealStuff;
	}
	public float calculateReward(Map<String, Integer> tasks, LineMap map,
			 ItemSpecifications itemSpecifications) {
		
		float totalReward = 0.0f;
		details = new ArrayList<OrderDetail>();
		ItemSpecifications itemSpecifications2 = itemSpecifications;
		Map<String, Specifications> specs = itemSpecifications2
				.getItemSpecification();
		int totalDistance = 0;
		PathFinder finder = new PathFinder(map);
		PathInfo pathInfo = new PathInfo(startingPose);
		Iterator it = tasks.entrySet().iterator();
		double totalWeight=0;
		ArrayList<TaskList> allOrder = new ArrayList();
		GridPose currentPose = startingPose;
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			String item = (String) pair.getKey();
			int count = (Integer) pair.getValue();
			Iterator it2 = specs.entrySet().iterator();
			
			
			while (it2.hasNext()) {
				Map.Entry pair2 = (Map.Entry) it2.next();
				String item2 = (String) pair2.getKey();
				if (item2.equals(item)) {
					
					Specifications itemData = (Specifications) pair2.getValue();
					if(itemData.getWeight()<=50) {
						currentPose = pathInfo.pose;
						pathInfo = finder.FindPath(currentPose,
								new GridPose(itemData.getCoordinates(), Heading.PLUS_X));
						totalDistance += pathInfo.path.size();
						currentPose = pathInfo.pose;
						details.add(new OrderDetail(pathInfo.path));
						stealStuff.add(new TaskList(count,item2));
						for(int i=0;i<count;i++) {
							if(itemData.getWeight()+totalWeight<=50) {
								double reward = itemData.getReward();
								totalReward += (float) reward;
								totalWeight+=itemData.getWeight();
								details.add(new OrderDetail(item2));
							} else {
								pathInfo=finder.FindPath(currentPose, dropPoint);
								//totalReward += itemData.getReward();
								paths.add(pathInfo.path);
								totalDistance+=pathInfo.path.size();
								ArrayList<Integer> newPath = (ArrayList<Integer>) pathInfo.path.clone();
								newPath.add(3);
								GridPose newPose=pathInfo.pose;
								pathInfo=finder.FindPath(newPose, currentPose);
								newPath.addAll(pathInfo.path);
								totalWeight=itemData.getWeight();
								//System.out.println(newPath.toString());
								details.add(new OrderDetail(item2, newPath));
							}
						}
						
					}else return -1;	
					}
				}		
			it2 = specs.entrySet().iterator();
		}
		pathInfo=finder.FindPath(currentPose,new GridPose());
		ArrayList<Integer>finalPath= (ArrayList<Integer>) pathInfo.path.clone();
		finalPath.add(5);
		currentPose=dropPoint;
		/*pathInfo=finder.FindPath(currentPose,startingPose);
		System.out.println(pathInfo.path.size()+"wooooot");
		finalPath.addAll(pathInfo.path);
		finalPath.add(6);*/
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
