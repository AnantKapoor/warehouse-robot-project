package main.java.JobSelection;

import java.util.ArrayList;

public class Order {
	private int jobID;
	private float rewardRate;
	private double reward;
	private ArrayList<OrderDetail> detail;
	public Order(int jobID,float rewardRate,double reward,ArrayList<OrderDetail> detail) {
		this.jobID=jobID;
		this.rewardRate=rewardRate;
		this.reward=reward;
		this.detail=detail;
	}
	public Float getRate() {
		return rewardRate;
	}
	public String toString() {
		String items="";
		for(int i=0;i<detail.size();i++) {
			if(detail.get(i).getPath().size()!=0&&!detail.get(i).getName().equals("")) items+=detail.get(i).getPath().toString()+" take "+detail.get(i).getName()+";\n";
			else if(detail.get(i).getPath().size()==0&&! detail.get(i).getName().equals("")) items+=" take "+detail.get(i).getName()+" \n";
			else items+=" "+detail.get(i).getPath().toString()+" \n";
		}
		return "job ID: "+jobID+";\nReward: "+reward+"\nRequired items :\n"+items;
	}
}
