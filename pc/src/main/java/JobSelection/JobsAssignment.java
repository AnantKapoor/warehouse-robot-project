package main.java.JobSelection;

import java.util.ArrayList;

public class JobsAssignment {
	private TaskList task;
	private int ID;
	private String message;
	private double reward;
	public JobsAssignment(TaskList task) {
		this.task=task;
	}
	public JobsAssignment(int ID,double reward) {
		this.ID=ID;
		this.reward=reward;
		task=null;
	}
	public TaskList getTask() {
		return task;
	}
	public int getID() {
		return ID;
	}
	public void toStrin (){
		if(task!=null) {
			System.out.println(task.getName()+" "+task.getAmount());
			if(task.getPath().size()!=0) {
			for(int i=0;i<task.getPath().size();i++) {
				System.out.print(" "+task.getPath().get(i));
			}}
			System.out.println();
		}else System.out.println(ID);
	}
}
	
