package main.java.CancellationPrediction;

import java.util.ArrayList;
import java.util.Map;

import main.java.JobSelection.Run;

import org.apache.log4j.Logger;

public class MakePredictions {
	
	private static final Logger logger = Logger.getLogger(Run.class);
	
	public static void main (){
		ReadInfo reader = new ReadInfo();
		reader.main();
		logger.debug("Read training data.");
		ArrayList<Job> trainingData = reader.getJobTrainingData();
		ArrayList<Job> jobs = reader.getAllJobs();
		CreateTrainingAff.makeARFF(trainingData);
		logger.debug("Made ARFF file for training data.");
		CreatePredictionAff.makeARFF(jobs);
		logger.debug("Made ARFF file for actual job data.");
		try {
			Train.main();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.debug("Training data analysed.");
		
		try {
			Predict.main();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.debug("Prediction file created (.csv)");
		
//		try {
//			Verify.main();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
