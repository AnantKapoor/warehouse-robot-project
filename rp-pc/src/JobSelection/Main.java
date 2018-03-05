package JobSelection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import PathFinding.PathInfo;

public class Main {
    public static ItemSpecifications itemSpecifications = new ItemSpecifications();
    public static Jobs jobs = new Jobs();

    public static void main (String args[]){
        ItemReader itemReader = new ItemReader(jobs, itemSpecifications);
        ItemReader.main();
        itemSpecifications = ItemReader.itemSpecifications;
        jobs = ItemReader.jobs;
        Iterator plsWork= itemSpecifications.getItemSpecification().values().iterator();
        ArrayList<Order>allOrders=jobs.calculateRewards();
        Collections.sort(allOrders, new Comparator<Order>() {
		    @Override
		    public int compare(Order o1, Order o2) {
		        return o2.getRate().compareTo(o1.getRate());
		    }
		});
        for(int i=0;i<allOrders.size();i++) {
        	System.out.println(allOrders.get(i).toString());
        }
    }

    public static ItemSpecifications getItemSpecifications() {
        return itemSpecifications;
    }

    public static Jobs getJobs() {
        return jobs;
    }
}
