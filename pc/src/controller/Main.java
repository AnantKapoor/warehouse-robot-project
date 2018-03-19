package controller;

import java.util.ArrayList;

import communication.Connection;
import main.java.JobSelection.*;
import warehouseInterface.WarehouseInterface;

public class Main {
	
	public static void main(String[] args) {
		Run.main();
		ArrayList<Order> orders = Run.getOrders();
//        ArrayList<Order> allOrders = new ArrayList<>();
		WarehouseInterface.main(orders);
	}
}
