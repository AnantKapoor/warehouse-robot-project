package controller;

import java.util.ArrayList;

import communication.Connection;
import main.java.JobSelection.*;

public class Main {
	
	public static void main(String[] args) {
		Run.main();
		ArrayList<Order> orders =Run.getOrders();
		Connection.main(orders);
	}
}
