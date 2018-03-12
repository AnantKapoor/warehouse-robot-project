package main.java.JobSelection;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

import main.java.PathFinding.PathFinder;
import main.java.PathFinding.PathInfo;

import org.apache.log4j.Logger;

import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;


public class Run {
	
	private static final Logger logger = Logger.getLogger(Run.class);
	private static ArrayList<Order>allOrders;
    public static ItemSpecifications itemSpecifications = new ItemSpecifications();
    public static Jobs jobs = new Jobs();


    public static void main (){
        ItemReader itemReader = new ItemReader(jobs, itemSpecifications);
        ItemReader.main();
        itemSpecifications = ItemReader.itemSpecifications;
        
        logger.info("All item infromation stored.");
        
        jobs = ItemReader.jobs;
        
        logger.info("All job infromation stored.");
        
        Iterator itemIterator= itemSpecifications.getItemSpecification().values().iterator();
        allOrders = jobs.calculateRewards(itemSpecifications);
        
        if (allOrders == null){
        	logger.error("Error calculating job rewards");
        }
        
        Collections.sort(allOrders, new Comparator<Order>() {
		    @Override
		    public int compare(Order o1, Order o2) {
		        return o2.getRate().compareTo(o1.getRate());
		    }
		});
        GridPose startingPosition=new GridPose(new Point (1,0),Heading.PLUS_X);
        PathFinder finder = new PathFinder(MapUtils.createTrainingMap());
        ItemSpecifications itemSpecifications2 = itemSpecifications;
        Map<String, Specifications> specs = itemSpecifications2
				.getItemSpecification();
        ArrayList<Order> newOrder=new ArrayList<Order>();
        PathInfo pathInfo;
        for(int i=0;i<allOrders.size();i++){
        	ArrayList<OrderDetail> newPaths=new ArrayList<OrderDetail>();
        	for(int j=0;j<allOrders.get(i).getDetail().size();j++){
        		if(!allOrders.get(i).getDetail().get(j).getName().equals("")){
	        		Specifications itemSpecs=specs.get(allOrders.get(i).getDetail().get(j).getName());
	        		pathInfo = new PathInfo(startingPosition);
	        		pathInfo= finder.FindPath(startingPosition, new GridPose(itemSpecs.getCoordinates(),Heading.PLUS_X));
	        		startingPosition=pathInfo.pose;
	        		newPaths.add(new OrderDetail(pathInfo.path));
        		} else{
        			newPaths.add(new OrderDetail(allOrders.get(i).getDetail().get(j).getName()));
        		}
        	}
        	pathInfo = new PathInfo(startingPosition);
        	pathInfo=finder.FindPath(pathInfo.pose, new GridPose(new Point(2,2),Heading.PLUS_X));
        	startingPosition=pathInfo.pose;
        	
        	ArrayList<Integer> lastOne=pathInfo.path;
        	lastOne.add(5);
        	
        	newPaths.add(new OrderDetail(lastOne));
        	newOrder.add(new Order(allOrders.get(i).getID(),allOrders.get(i).getRate(),allOrders.get(i).getReward(),newPaths));
        }
        allOrders=newOrder;
        /*for(int i=0;i<allOrders.size();i++) {
        	System.out.println(allOrders.get(i).toString());
        }*/
        
        logger.info("All jobs ranked by reward.");
    }


    public static Jobs getJobs() {
        return jobs;
    }
    
    public static ArrayList<Order> getOrders(){
    	return allOrders;
    	
    }
}
