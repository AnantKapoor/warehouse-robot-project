package main.java.CancellationPrediction;

public class Job {
	
	private int id;
	private double totalReward;
	private double totalWeight;
	private boolean wasCancelled;
	private int numberOfItems;
	
	public Job (int id, double totalReward, double totalWeight, boolean wasCancelled, int numberOfItems){
		this.id = id;
		this.totalReward = totalReward;
		this.totalWeight = totalWeight;
		this.wasCancelled = wasCancelled;
		this.numberOfItems = numberOfItems;
	}
	
	public Job (int id, double totalReward, double totalWeight, int numberOfItems){
		this.id = id;
		this.totalReward = totalReward;
		this.totalWeight = totalWeight;
		this.numberOfItems = numberOfItems;
	}

	public double getTotalReward(){
		return totalReward;
	}
	
	public double getTotalWeight(){
		return totalWeight;
	}
	
	public int getNumberOfItems(){
		return numberOfItems;
	}
	
	public boolean getWasCancelled (){
		return wasCancelled;
	}
	
	public int getId (){
		return id;
	}
}
