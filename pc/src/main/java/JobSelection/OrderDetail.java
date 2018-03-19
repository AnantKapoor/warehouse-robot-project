package main.java.JobSelection;

import java.util.ArrayList;

public class OrderDetail {
	private String itemName;
	private ArrayList<Integer>path=new ArrayList<Integer>();
	public OrderDetail(String itemName) {
		this.itemName=itemName;
		//path.add(4);
	}
	public OrderDetail(String itemName, ArrayList<Integer>path) {
		this.itemName=itemName;
		this.path=path;
		/*if(path.get(path.size()-1)!=5&&path.get(path.size()-1)!=4&&path.get(0)!=4){
			path.add(4);
		}*/
	}
	public OrderDetail(ArrayList<Integer> path) {
		this.path=path;
		//if(path.get(path.size()-1)!=5&&path.get(path.size()-1)!=4&&path.get(0)!=4){
		//	path.add(4);
		//}
		this.itemName="";
	}
	public String getName() {
		return itemName;
		
	}
	public ArrayList<Integer> getPath() {
		return path;
	}
}
