package main.java.JobSelection;

import java.util.ArrayList;

public class plswork {

	public static void main(String[] args) {
		Run.main();
		ArrayList<Order> orders =Run.getOrders();
		for (Order ord : orders) {
		//Order ord=orders.get(0);
			
			
			System.out.println(ord.getID());
    		int n = ord.getDetail().size();
    		for (int i = 0; i < n; i ++) {
    			ArrayList<Integer> steps = ord.getPath(i);
    			if(steps.size()>1){
    				if(steps.get(steps.size()-1)!=5){
    					steps.add(4);
    				}
    			}
    			//if(steps.size()==0){
    				//steps.add(4);
    			//	System.out.println("I was here");
    			//}
    			if(steps.size()>1){
    				if(steps.get(steps.size()-1)!=5){
    					steps.add(4);
    				}
    			}
    			for(int step : steps) {
    				System.out.println(step);
    			}
    			
    		}
		}
	}
}
