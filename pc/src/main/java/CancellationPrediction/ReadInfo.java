package main.java.CancellationPrediction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.java.JobSelection.ItemSpecifications;
import main.java.JobSelection.Specifications;

public class ReadInfo {
    private static ItemSpecifications itemSpecifications;
    private static Map<String, Specifications> specs;
    private static Map<Integer, Boolean> cancellations;
    private static ArrayList<Job> trainingJobs;
    private static ArrayList<Job> allJobs;
    
    public static void readCancellations (String filePath){
    	String cvsSplitBy = ",";
        FileReader file = null;

        try {
            file = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(file);

        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        while ((line != null)) {
            String[] allParts = line.split(cvsSplitBy);

            boolean cancelled;
            if (allParts[1].equals("1")){
            	cancelled = true;
            }else {
            	cancelled = false;
            }
            
            cancellations.put(Integer.parseInt(allParts[0]), cancelled);

            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public static void readTrainingJobs (String filePath){

        String cvsSplitBy = ",";
        FileReader file = null;

        try {
            file = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(file);

        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        //stores text found in the cipher file in two arrays (one for letters, one for their frequencies) encryptedText
        while ((line != null)) {
            String[] allParts = line.split(cvsSplitBy);

            int tasksLeft = (allParts.length - 1) / 2;
            int jobID = Integer.parseInt(allParts[0]);
            int item = 1;
            int count = 2;
            double totalReward = 0;
            double totalWeight = 0;
            int numberOfItems = 0;

            while (tasksLeft > 0){
            	//task.addTask(allParts[item], Integer.parseInt(allParts[count]));
            	Specifications s = specs.get(allParts[item]);
            	int amount = Integer.parseInt(allParts[count]);
            	totalReward += (s.getReward() * ((double) amount));
            	totalWeight += (s.getWeight() * ((double) amount));
            	numberOfItems += amount;
            	item += 2;
                count += 2;
                --tasksLeft;
            }

            Job j = new Job (jobID, totalReward, totalWeight, cancellations.get(jobID), numberOfItems);
            trainingJobs.add(j);
            
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void readJobs (String filePath){

        String cvsSplitBy = ",";
        FileReader file = null;

        try {
            file = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(file);

        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        //stores text found in the cipher file in two arrays (one for letters, one for their frequencies) encryptedText
        while ((line != null)) {
            String[] allParts = line.split(cvsSplitBy);

            int tasksLeft = (allParts.length - 1) / 2;
            int jobID = Integer.parseInt(allParts[0]);
            int item = 1;
            int count = 2;
            double totalReward = 0;
            double totalWeight = 0;
            int numberOfItems = 0;

            while (tasksLeft > 0){
            	//task.addTask(allParts[item], Integer.parseInt(allParts[count]));
            	Specifications s = specs.get(allParts[item]);
            	int amount = Integer.parseInt(allParts[count]);
            	totalReward += (s.getReward() * ((double) amount));
            	totalWeight += (s.getWeight() * ((double) amount));
            	numberOfItems += amount;
            	item += 2;
                count += 2;
                --tasksLeft;
            }

            Job j = new Job (jobID, totalReward, totalWeight, numberOfItems);
            allJobs.add(j);
            
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	 public static void readSpecs (String filePath){

	        String cvsSplitBy = ",";
	        FileReader file = null;

	        try {
	            file = new FileReader(filePath);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	        BufferedReader reader = new BufferedReader(file);

	        String line = null;
	        try {
	            line = reader.readLine();
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }

	        //stores text found in the cipher file in two arrays (one for letters, one for their frequencies) encryptedText
	        while ((line != null)) {
	            String[] threeParts = line.split(cvsSplitBy);
	     
	            itemSpecifications.addSpecifications(threeParts[0], Double.parseDouble(threeParts[1]), Double.parseDouble(threeParts[2]));

	            try {
	                line = reader.readLine();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        
	        try {
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        specs = itemSpecifications.getItemSpecification();
	    }

    public static ArrayList<Job> getJobTrainingData() {
        return trainingJobs;
    }
    
    public static ArrayList<Job> getAllJobs() {
        return allJobs;
    }
    
    public ReadInfo(){
    	specs = new HashMap<String, Specifications>();
    	trainingJobs = new ArrayList<Job>();
    	allJobs = new ArrayList<Job>();
    	cancellations = new HashMap<Integer, Boolean>();
    	itemSpecifications = new ItemSpecifications();
    }

    public static void main (){
    	readCancellations ("resources/cancellations.csv");
    	readSpecs ("resources/items.csv");
    	readTrainingJobs ("resources/training_jobs.csv");
    	readJobs ("resources/jobs.csv");
    }
}

