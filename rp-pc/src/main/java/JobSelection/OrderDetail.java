package main.java.JobSelection;

public class OrderDetail {
	private char itemName;
	private int itemAmount;
	public OrderDetail(char itemName,int itemAmount) {
		this.itemName=itemName;
		this.itemAmount=itemAmount;
	}
	public char getName() {
		return itemName;
	}
	public int getAmount() {
		return itemAmount;
	}
}
