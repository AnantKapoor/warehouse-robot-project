package main.java.JobSelection;

import java.util.ArrayList;

public class OrderDetail {
	private char itemName;
	private ArrayList<Integer>path=new ArrayList<>();
	public OrderDetail(char itemName) {
		this.itemName=itemName;
	}
	public OrderDetail(char itemName, ArrayList<Integer>path) {
		this.itemName=itemName;
		this.path=path;
	}
	public OrderDetail(ArrayList<Integer> path) {
		this.path=path;
		this.itemName=0;
	}
	public char getName() {
		return itemName;
		
	}
	public ArrayList<Integer> getPath() {
		return path;
	}
}
