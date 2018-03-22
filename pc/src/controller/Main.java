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
		//MakePredictions.main();
		logger.debug("All predictions made.");
		Run.main();
		ArrayList<ArrayList<Integer>> orders1 = Run.getPaths1();
		ArrayList<ArrayList<Integer>> orders2 = Run.getPaths2();
		ArrayList<ArrayList<Integer>> orders3 = Run.getPaths3();
//       ArrayList<Order> orders = new ArrayList<>();
		//WarehouseInterface.main(orders1, orders2, orders3);
        Connection.main(orders1, orders2, orders3);
	}
}
