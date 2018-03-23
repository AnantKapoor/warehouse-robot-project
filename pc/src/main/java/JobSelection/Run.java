package main.java.JobSelection;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

import main.java.PathFinding.PathFinder;
import main.java.PathFinding.PathFinder2;
import main.java.PathFinding.PathInfo;

import org.apache.log4j.Logger;

import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.robotics.testing.TestMaps;



public class Run {
	
	private static final Logger logger = Logger.getLogger(Run.class);
	private static ArrayList<Order> allOrders;
	public static ItemSpecifications itemSpecifications = new ItemSpecifications();
	public static Jobs jobs = new Jobs();
	private static ArrayList<ArrayList<Integer>> allPaths1 = new ArrayList<ArrayList<Integer>>();
	private static ArrayList<ArrayList<Integer>> allPaths2 = new ArrayList<ArrayList<Integer>>();
	private static ArrayList<ArrayList<Integer>> allPaths3 = new ArrayList<ArrayList<Integer>>();

	public static void main() {
		ItemReader itemReader = new ItemReader(jobs, itemSpecifications);
		ItemReader.main();
		itemSpecifications = ItemReader.itemSpecifications;

		logger.debug("All item infromation stored.");

		jobs = ItemReader.jobs;

		logger.debug("All job infromation stored.");

		Iterator itemIterator = itemSpecifications.getItemSpecification().values().iterator();
		allOrders = jobs.calculateRewards(itemSpecifications);

		if (allOrders == null) {
			logger.error("Error calculating job rewards");
		}

		Collections.sort(allOrders, new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				return o2.getRate().compareTo(o1.getRate());
			}
		});

		logger.debug("All orders sorted by reward.");
		ArrayList<Order> finalList1 = new ArrayList<Order>();
		ArrayList<Order> finalList2 = new ArrayList<Order>();
		ArrayList<Order> finalList3 = new ArrayList<Order>();
		
		for (int i = 0; i < allOrders.size()-3; i+=3) {
			finalList1.add(allOrders.get(i));
			finalList2.add(allOrders.get(i+1));
			finalList3.add(allOrders.get(i+2));
		}
		GridPose robotPosition1 = new GridPose(new Point(1, 0), Heading.PLUS_Y);
		GridPose robotPosition2 = new GridPose(new Point(3, 0), Heading.PLUS_Y);
		GridPose robotPosition3 = new GridPose(new Point(2, 0), Heading.PLUS_Y);
		Map<String, Specifications> itemSpecs = itemSpecifications.getItemSpecification();
		Specifications dropPoint1=new Specifications(1,0);
		Specifications dropPoint2=new Specifications(2,0);
		Specifications dropPoint3=new Specifications(3,0);
		dropPoint1.addCoordinates(new Point (0,1));
		dropPoint2.addCoordinates(new Point (0,2));
		dropPoint3.addCoordinates(new Point (0,3));
		itemSpecs.put("dp1",dropPoint1);
		itemSpecs.put("dp2",dropPoint2);
		itemSpecs.put("dp3",dropPoint3);
		PathFinder finder = new PathFinder(TestMaps.warehouseMap());
		PathFinder2 finder2=new PathFinder2(TestMaps.warehouseMap());
		ArrayList<TaskList> allTasks1=new ArrayList<TaskList>();
		ArrayList<TaskList> allTasks2=new ArrayList<TaskList>();
		ArrayList<TaskList> allTasks3=new ArrayList<TaskList>();
		double carriedWeight=0;
		for(int i = 0; i<finalList1.size();i++) {
			int closestItem=Integer.MAX_VALUE;
			int closestInt=-1;
			for(int j=0;j<finalList1.get(i).getAllOrder().size();j++) {
				for(int y=j;y<finalList1.get(i).getAllOrder().size();y++) {
					int distance=finder.FindPath(robotPosition1, new GridPose(itemSpecs.get(finalList1.get(i).getAllOrder().get(y).getName()).getCoordinates(),
							Heading.MINUS_X)).path.size();
					if(distance<closestItem) {
						closestInt=j;
					}
				}
				Collections.swap(finalList1.get(i).getAllOrder(), closestInt, j);
				Specifications specs=itemSpecs.get(finalList1.get(i).getAllOrder().get(j).getName());
				carriedWeight+=specs.getWeight()*finalList1.get(i).getAllOrder().get(j).getAmount();
				if(carriedWeight<50) {
				robotPosition1=finder.FindPath(robotPosition1, new GridPose(itemSpecs.get(finalList1.get(i).getAllOrder().get(j).getName()).getCoordinates(),
						Heading.MINUS_X)).pose;
				} else {
					robotPosition1=finder.FindPath(robotPosition1, new GridPose(itemSpecs.get("dp1").getCoordinates(),Heading.MINUS_X)).pose;
					finalList1.get(i).getAllOrder().add(j,new TaskList(0,"dp1"));
					j++;
				}
			}
			allTasks1.addAll(finalList1.get(i).getAllOrder());
		}
		carriedWeight=0;
		for(int i = 0; i<finalList2.size();i++) {
			int closestItem=Integer.MAX_VALUE;
			int closestInt=-1;
			for(int j=0;j<finalList2.get(i).getAllOrder().size();j++) {
				for(int y=j;y<finalList2.get(i).getAllOrder().size();y++) {
					int distance=finder.FindPath(robotPosition2, new GridPose(itemSpecs.get(finalList2.get(i).getAllOrder().get(y).getName()).getCoordinates(),
							Heading.MINUS_X)).path.size();
					if(distance<closestItem) {
						closestInt=j;
					}
				}
				
				Collections.swap(finalList2.get(i).getAllOrder(), closestInt, j);
				Specifications specs=itemSpecs.get(finalList2.get(i).getAllOrder().get(j).getName());
				carriedWeight+=specs.getWeight()*finalList2.get(i).getAllOrder().get(j).getAmount();
				if(carriedWeight<50) {
				robotPosition2=finder.FindPath(robotPosition2, new GridPose(itemSpecs.get(finalList2.get(i).getAllOrder().get(j).getName()).getCoordinates(),
						Heading.MINUS_X)).pose;
				} else {
					robotPosition2=finder.FindPath(robotPosition2, new GridPose(itemSpecs.get("dp2").getCoordinates(),Heading.MINUS_X)).pose;
					finalList2.get(i).getAllOrder().add(j,new TaskList(0,"dp2"));
					j++;
					carriedWeight=0;
				}
			}
			allTasks2.addAll(finalList2.get(i).getAllOrder());
		}	
		carriedWeight=0;
		for(int i = 0; i<finalList3.size();i++) {
			int closestItem=Integer.MAX_VALUE;
			int closestInt=-1;
			for(int j=0;j<finalList3.get(i).getAllOrder().size();j++) {
				for(int y=j;y<finalList3.get(i).getAllOrder().size();y++) {
					int distance=finder.FindPath(robotPosition3, new GridPose(itemSpecs.get(finalList3.get(i).getAllOrder().get(y).getName()).getCoordinates(),
							Heading.MINUS_X)).path.size();
					if(distance<closestItem) {
						closestInt=j;
					}
				}
				Collections.swap(finalList3.get(i).getAllOrder(), closestInt, j);
				Specifications specs=itemSpecs.get(finalList3.get(i).getAllOrder().get(j).getName());
				carriedWeight+=specs.getWeight()*finalList3.get(i).getAllOrder().get(j).getAmount();
				if(carriedWeight<50) {
				robotPosition3=finder.FindPath(robotPosition3, new GridPose(itemSpecs.get(finalList3.get(i).getAllOrder().get(j).getName()).getCoordinates(),
						Heading.MINUS_X)).pose;
				} else {
					robotPosition3=finder.FindPath(robotPosition3, new GridPose(itemSpecs.get("dp3").getCoordinates(),Heading.MINUS_X)).pose;
					finalList3.get(i).getAllOrder().add(j,new TaskList(0,"dp3"));
					j++;
				}
			}
			allTasks3.addAll(finalList3.get(i).getAllOrder());
		}
		System.out.println("done");
		robotPosition1 = new GridPose(new Point(1, 0), Heading.PLUS_Y);
		robotPosition2 = new GridPose(new Point(3, 0), Heading.PLUS_Y);
		robotPosition3 = new GridPose(new Point(2, 0), Heading.PLUS_Y);
		int smallestAll=0;
		if(allTasks1.size()<allTasks2.size()&&allTasks1.size()<allTasks3.size()) {
			smallestAll=allTasks1.size();
		}else if(allTasks2.size()<allTasks1.size()&&allTasks2.size()<allTasks3.size()) {
			smallestAll=allTasks2.size();
		}else if(allTasks3.size()<allTasks2.size()&&allTasks3.size()<allTasks1.size()) {
			smallestAll=allTasks3.size();
		}

		ArrayList<PathInfo> finderPaths=new ArrayList<PathInfo>();
		for(int i=0;i<smallestAll;i++) {
			if(itemSpecs.get(allTasks1.get(i).getName()).getCoordinates().equals(itemSpecs.get(allTasks2.get(i).getName()).getCoordinates())){
				Collections.swap(allTasks2, i, i+2);
			}
			if(itemSpecs.get(allTasks1.get(i).getName()).getCoordinates().equals(itemSpecs.get(allTasks3.get(i).getName()).getCoordinates())) {
				Collections.swap(allTasks3, i, i+2);
			}
			if (itemSpecs.get(allTasks2.get(i).getName()).getCoordinates().equals(itemSpecs.get(allTasks3.get(i).getName()).getCoordinates())) {
				Collections.swap(allTasks3, i, i+2);
			}
			finderPaths=finder2.FindPath(robotPosition1, new GridPose(itemSpecs.get(allTasks1.get(i).getName()).getCoordinates(),Heading.MINUS_X)
					, robotPosition2,  new GridPose(itemSpecs.get(allTasks2.get(i).getName()).getCoordinates(),Heading.MINUS_X),
					robotPosition3,  new GridPose(itemSpecs.get(allTasks3.get(i).getName()).getCoordinates(),Heading.MINUS_X));
			if(allTasks1.get(i).getName().equals("dp1")){
				finderPaths.get(0).path.add(3);
			}
			allPaths1.add(finderPaths.get(0).path);
			robotPosition1=finderPaths.get(0).pose;
			if(allTasks2.get(i).getName().equals("dp2")){
				finderPaths.get(1).path.add(3);
			}
			allPaths2.add(finderPaths.get(1).path);
			robotPosition2=finderPaths.get(1).pose;
			if(allTasks3.get(i).getName().equals("dp3")){
				finderPaths.get(2).path.add(3);
			}
			allPaths3.add(finderPaths.get(2).path);
			robotPosition3=finderPaths.get(2).pose;
			
			//finder2.FindPath(robotPosition1, goalCoordinates1, robotPosition2, goalCoordinates2, robotPosition3, goalCoordinates3)
		}
		System.out.println(allPaths1.toString());
		System.out.println(allPaths2.toString());
		System.out.println(allPaths3.toString());
			
		logger.debug("All job selection tasks completed");
	}

	public static Jobs getJobs() {
		return jobs;
	}
	public static ArrayList<ArrayList<Integer>> getPaths1(){
		return allPaths1;
	}
	public static ArrayList<ArrayList<Integer>> getPaths2(){
		return allPaths2;
	}
	public static ArrayList<ArrayList<Integer>> getPaths3(){
		return allPaths3;
	}
	/*public static ArrayList<JobsAssignment> getAssignments() {
//		return finalList;
	}*/
	public static ArrayList<Order> getOrders() {
		return allOrders;

	}

}
