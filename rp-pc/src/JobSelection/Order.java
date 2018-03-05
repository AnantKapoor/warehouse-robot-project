package JobSelection;

import java.util.ArrayList;

public class Order {
	private int jobID;
	private float rewardRate;
	private float reward;
	private ArrayList<OrderDetail> detail;
	public Order(int jobID,float rewardRate,float reward,ArrayList<OrderDetail> detail) {
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
			items+=" "+detail.get(i).getAmount()+" items of "+detail.get(i).getName()+";\n";
		}
		return "job ID: "+jobID+";\nReward: "+reward+"\nRequired items :\n"+items;
	}
}
