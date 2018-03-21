package controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import communication.Connection;
import main.java.CancellationPrediction.MakePredictions;
import main.java.JobSelection.*;
import warehouseInterface.WarehouseInterface;

public class Main {
	
	private static final Logger logger = Logger.getLogger(Run.class);
	
	public static void main(String[] args) {
		MakePredictions.main();
		logger.debug("All predictions made.");
		Run.main();
		ArrayList<Order> orders = Run.getOrders();
//       ArrayList<Order> orders = new ArrayList<>();
		WarehouseInterface.main(orders);
	}
}
