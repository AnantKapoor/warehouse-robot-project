package main.java.JobSelection;

import java.util.ArrayList;

public class TaskList {
	private int amount;
	private String name;
	ArrayList<Integer> path = new ArrayList<Integer>();
	public TaskList(int amount, String name){
		this.amount=amount;
		this.name=name;
	}
	public void addPath(ArrayList<Integer>path) {
		this.path=path;
	}
	public ArrayList<Integer>getPath(){
		return path;
	}
	public int getAmount(){
		return amount;
	}
	public String getName(){
		return name;
	}
}
