package main.java.JobSelection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import org.apache.log4j.Logger;


public class Main {
	
	private static final Logger logger = Logger.getLogger(Main.class);
	
    public static ItemSpecifications itemSpecifications = new ItemSpecifications();
    public static Jobs jobs = new Jobs();

    public static void main (String args[]){
        ItemReader itemReader = new ItemReader(jobs, itemSpecifications);
        ItemReader.main();
        itemSpecifications = ItemReader.itemSpecifications;
        
        logger.info("All item infromation stored.");
        
        jobs = ItemReader.jobs;
        
        logger.info("All job infromation stored.");
        
        Iterator itemIterator= itemSpecifications.getItemSpecification().values().iterator();
        ArrayList<Order>allOrders = jobs.calculateRewards(itemSpecifications);
        
        if (allOrders == null){
        	logger.error("Error calculating job rewards");
        }
        
        Collections.sort(allOrders, new Comparator<Order>() {
		    @Override
		    public int compare(Order o1, Order o2) {
		        return o2.getRate().compareTo(o1.getRate());
		    }
		});
        
        for(int i=0;i<allOrders.size();i++) {
        	System.out.println(allOrders.get(i).toString());
        }
        
        logger.info("All jobs ranked by reward.");
    }

    public static Jobs getJobs() {
        return jobs;
    }
}
