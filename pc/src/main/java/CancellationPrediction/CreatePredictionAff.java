package main.java.CancellationPrediction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class CreatePredictionAff {
	public static void makeARFF(ArrayList<Job> trainingJobs) {
		try {
			
			BufferedWriter wr = new BufferedWriter(new FileWriter("resources/predictionData.arff"));
			

			wr.write("@relation trainingData\n");
			wr.newLine();
			
			wr.write("@attribute cancelled {0, 1}\n");
			wr.write("@attribute weight numeric\n");
			wr.write("@attribute reward numeric\n");
			wr.write("@attribute itemCount numeric\n");
			
			wr.newLine();
			
			wr.write("@data\n");
			for (Job j : trainingJobs) {
				String line = "?,";
				line += j.getTotalWeight() + "," + j.getTotalReward() + "," + j.getNumberOfItems();
				wr.write(line);
				wr.newLine();
			}
			
			wr.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
