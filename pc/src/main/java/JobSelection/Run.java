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
	private static ArrayList<JobsAssignment> finalList;
    public static ItemSpecifications itemSpecifications = new ItemSpecifications();
    public static Jobs jobs = new Jobs();


    public static void main (){
        ItemReader itemReader = new ItemReader(jobs, itemSpecifications);
        ItemReader.main();
        itemSpecifications = ItemReader.itemSpecifications;
        
        logger.debug("All item infromation stored.");
        
        jobs = ItemReader.jobs;
        
        logger.debug("All job infromation stored.");
        
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
        
        logger.debug("All orders sorted by reward.");
        finalList =new ArrayList<JobsAssignment>();
        for(int i=0;i<allOrders.size();i++) {
        	finalList.add(new JobsAssignment(allOrders.get(i).getID(),allOrders.get(i).getReward()));
        	for(int j=0;j<allOrders.get(i).getAllOrder().size();j++) {
        		finalList.add(new JobsAssignment(allOrders.get(i).getAllOrder().get(j)));
        	}
        }
        GridPose robotPosition1=new GridPose(new Point (1,0),Heading.PLUS_Y);
        GridPose robotPosition2=new GridPose(new Point (3,0),Heading.PLUS_Y);
        GridPose robotPosition3=new GridPose(new Point (2,0),Heading.PLUS_Y);
        int i=0;
        Map<String,Specifications> itemSpecs=itemSpecifications.getItemSpecification();
        PathFinder finder=new PathFinder(MapUtils.createTrainingMap());
        while(finalList.size()>=i+5) {
        	System.out.println("na");
        	int collectedTasks=0;
        	TaskList[] tasks=new TaskList[3];
        	int[]tasksInt=new int[3];
        	while (collectedTasks<3) {
	        	if(finalList.get(i).getTask()!=null) {
	        		tasks[collectedTasks]=finalList.get(i).getTask();
	        		tasksInt[collectedTasks]=i;
	        		collectedTasks++;
	        		i++;
	        	}else i++;
        	}
        	int[][]jobAuctioning=new int[3][3];
        	for(int j=0;j<3;j++) {
        		jobAuctioning[j][0]=finder.FindPath(robotPosition1,new GridPose(itemSpecs.get(tasks[j].getName()).getCoordinates(),Heading.PLUS_X)).path.size();
        		jobAuctioning[j][1]=finder.FindPath(robotPosition2,new GridPose(itemSpecs.get(tasks[j].getName()).getCoordinates(),Heading.PLUS_X)).path.size();
        		jobAuctioning[j][2]=finder.FindPath(robotPosition3,new GridPose(itemSpecs.get(tasks[j].getName()).getCoordinates(),Heading.PLUS_X)).path.size();
        	}
        	boolean foundBest=false;
        	int [][]bestOrder=new int[3][2];
        	while(!foundBest) {
        		for(int j=0;j<3;j++) {
        			for (int y=0;y<3;y++) {
        				System.out.print(jobAuctioning[j][y]+" ");
        			}
        			System.out.print("\n");
        		}
        		System.out.println();
        		int biggest=-1;
        		int biggestJ = -1;
        		int biggestY = -1;
        		for(int j=0;j<3;j++) {
        			for (int y=0;y<3;y++) {
        				if(jobAuctioning[j][y]>biggest) {
        					biggest=jobAuctioning[j][y];
        					biggestJ=j;
        					biggestY=y;
        				}
        			}
        		}
        		int totalNumJ=0;
        		int totalNumY=0;
        		for(int j=0;j<3;j++) {
        			if(jobAuctioning[biggestJ][j]!=-1) {
        				totalNumY++;
        			}
        		}
        		
        		for(int j=0;j<3;j++) {
        			if(jobAuctioning[j][biggestY]!=-1) {
        				totalNumJ++;
        			}
        		}
        		if(totalNumJ==1||totalNumY==1) {
        			for(int j=0;j<3;j++) {
            			if(j!=biggestJ) {
            				jobAuctioning[j][biggestY]=-1;
            			}
            		}
            		for(int y=0;y<3;y++) {
            			if(y!=biggestY) {
            				jobAuctioning[biggestJ][y]=-1;
            			}
            		}
            		int secondJ = 0;
            		int secondY = 0;
            		int second =0;
            		for(int j=0;j<3;j++) {
            			for (int y=0;y<3;y++) {
            				if(j!=biggestJ&&y!=biggestY&&jobAuctioning[j][y]!=-1) {
            					System.out.println("I found second");
            					secondJ=j;
            					secondY=y;
            					second=jobAuctioning[j][y];
            				}
            			}
            		}
            		for(int j=0;j<3;j++) {
            			if(j!=secondJ) {
            				jobAuctioning[j][secondY]=-1;
            			}
            		}
            		for(int y=0;y<3;y++) {
            			if(y!=secondY) {
            				jobAuctioning[secondJ][y]=-1;
            			}
            		}
            		int thirdJ = 0;
            		int thirdY = 0;
            		for(int j=0;j<3;j++) {
            			for (int y=0;y<3;y++) {
            				if(j!=biggestJ&&y!=biggestY&&jobAuctioning[j][y]!=-1&&j!=secondJ&&y!=secondY) {
            					thirdJ=j;
            					thirdY=y;
            					bestOrder[0][0]=biggestJ;
            					bestOrder[0][1]=biggestY;
            					bestOrder[1][0]=secondJ;
            					bestOrder[1][1]=secondY;
            					bestOrder[2][0]=thirdJ;
            					bestOrder[2][1]=thirdY;
            					foundBest=true;
            				}
            			}
            		}
        		} else jobAuctioning[biggestJ][biggestY]=-1;
        	}
        	if(bestOrder[0][0]!=0) {
        		if(bestOrder[2][0]==0) {
        			Collections.swap(finalList, tasksInt[0], tasksInt[2]);
        		}else Collections.swap(finalList, tasksInt[0], tasksInt[1]);
        	}
        	if((bestOrder[0][0]==0&&bestOrder[0][1]==2)||(bestOrder[0][0]==1&&bestOrder[0][1]==2)||(bestOrder[0][0]==2&&bestOrder[0][1]==0)) {
        		Collections.swap(finalList, tasksInt[1],tasksInt[2]);
        		i++;
        	}
        }
       /* GridPose startingPosition=new GridPose(new Point (1,0),Heading.PLUS_X);
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
	        		newPaths.add(new OrderDetail(allOrders.get(i).getDetail().get(j).getName(),pathInfo.path));
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
        	newOrder.add(new Order(allOrders.get(i).getID(),allOrders.get(i).getRate(),allOrders.get(i).getReward(),newPaths,allOrders.get(i).getAllOrder()));
        }
        
        allOrders=newOrder;
       /* for(int i=0;i<allOrders.size();i++) {
        	System.out.println(allOrders.get(i).toString());
        	
        }*/
        
        logger.debug("All job selection tasks completed");
    }


    public static Jobs getJobs() {
        return jobs;
    }
    public static ArrayList<JobsAssignment> getAssignments(){
    	return finalList;
    }
    public static ArrayList<Order> getOrders(){
    	return allOrders;
    	
    }
}
