package main.java.JobSelection;

import java.util.ArrayList;

public class OrderDetail {
	private String itemName;
	private ArrayList<Integer>path=new ArrayList<>();
	public OrderDetail(String itemName) {
		this.itemName=itemName;
	}
	public OrderDetail(String itemName, ArrayList<Integer>path) {
		this.itemName=itemName;
		this.path=path;
	}
	public OrderDetail(ArrayList<Integer> path) {
		this.path=path;
		this.itemName="";
	}
	public String getName() {
		return itemName;
		
	}
	public ArrayList<Integer> getPath() {
		return path;
	}
}
