package main.java.JobSelection;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;

public class Jobs {
	private static final Logger logger = Logger.getLogger(Run.class);
	
    private Map<Integer, Task> availableJobs  = new HashMap<Integer, Task>();
    private Map<Integer, Float> rewards  = new HashMap<Integer, Float>();

    public void addJobs (int id, Task tasks) {
        availableJobs.put(id, tasks);
    }

    public String toString (int id){
        Task itemData = availableJobs.get(id);
        Map<String, Integer> tasks = itemData.getTasks();
        String text = id + "";

        Iterator it = tasks.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            text += (", " +pair.getKey() + ", " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }

        return text;
    }

    public ArrayList<Order> calculateRewards (ItemSpecifications itemSpecifications){
    	ArrayList<Order> allOrders=new ArrayList();
        Iterator it = availableJobs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int jobID = (Integer) pair.getKey();
            Task tasks = (Task) pair.getValue();
            Map<String, Integer> taskMap = tasks.getTasks();
            float calculation = tasks.calculateReward(taskMap,MapUtils.createTrainingMap(), itemSpecifications);
            rewards.put(jobID, calculation);
           // System.out.println("reward of job " + jobID + "---->" + calculation);
            allOrders.add(new Order(jobID,calculation,tasks.getReward(),tasks.getDetails()));
        }
        logger.debug("Individual rewards for each jobs caluclated");
        return allOrders;
    }
}

