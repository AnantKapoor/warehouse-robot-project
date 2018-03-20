package main.java.JobSelection;

import java.util.ArrayList;

public class plswork {

	public static void main(String[] args) {
		Run.main();
		ArrayList<Order> orders =Run.getOrders();
		for (Order ord : orders) {
		//Order ord=orders.get(0);
			
			
			int n = ord.getDetail().size();
    		int counter=ord.getDetail().size()-2;
    		for (int i = 0; i < ord.getDetail().size(); i++) {
    			ArrayList<Integer> steps = ord.getPath(i);
    			//if(ord.getPath(i).size()==0){
    				//counter--;
    			//	steps.add(4);
    				//System.out.println(  "   "+4 );

    			//}
    			//if(steps.size()==0){
    				//steps.add(4);
    			//}
    			
    			if(ord.getPath(i).size()==0||steps.size()>0&&steps.get(steps.size()-1)!=4||steps.get(steps.size()-1)!=5){
    				
    					steps.add(4);
    				
    			}
    			for(int step : steps) {
    				//System.out.println(step);
    				System.out.println(step);
    			}
    			
    		}
		}
	}
}
