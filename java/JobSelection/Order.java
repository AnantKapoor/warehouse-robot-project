package main.java.JobSelection;

import java.util.ArrayList;

public class Order {
	private int jobID;
	private ArrayList<TaskList> allOrder;
	private float rewardRate;
	private double reward;
	private ArrayList<OrderDetail> detail;
	public Order(int jobID,float rewardRate,double reward,ArrayList<OrderDetail> detail,ArrayList<TaskList> allOrder) {
		this.jobID=jobID;
		this.rewardRate=rewardRate;
		this.reward=reward;
		this.setDetail(detail);
		this.allOrder=allOrder;
	}
	public ArrayList<TaskList> getAllOrder(){
		return allOrder;
	}
	public Float getRate() {
		return rewardRate;
	}
	public int getID(){
		return jobID;
	}
	public ArrayList<Integer> getPath(int i){
		return(getDetail().get(i).getPath());
	}
	public double getReward(){
		return reward;
	}
	public String toString() {
		String items="";
		for(int i=0;i<getDetail().size();i++) {
			items+=" "+getDetail().get(i).getPath().toString()+"  "+getDetail().get(i).getName()+" \n" ;
		}
		return "job ID: "+jobID+";\nReward: "+reward+"\nRequired items :\n"+items;
	}
	public ArrayList<OrderDetail> getDetail() {
		return detail;
	}
	public void setDetail(ArrayList<OrderDetail> detail) {
		this.detail = detail;
	}
}
