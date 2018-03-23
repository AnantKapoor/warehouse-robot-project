package main.java.JobSelection;

public class TaskList {
	private int amount;
	private String name;
	public TaskList(int amount, String name){
		this.amount=amount;
		this.name=name;
	}
	public int getAmount(){
		return amount;
	}
	public String getName(){
		return name;
	}
}
