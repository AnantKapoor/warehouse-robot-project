package main.java.JobSelection;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class ItemReader {
    public static ItemSpecifications itemSpecifications;
    public static Jobs jobs;
    public static Map <Integer, Boolean> cancellations;
    public static ArrayList <DropLocation> dropLocations;
    
    private static final Logger logger = Logger.getLogger(Run.class);
    
    public static void readCancellationInfo (String filePath){

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
        	logger.error("Error reading job cancellations (predict.csv)");
            e1.printStackTrace();
        }

        try {
            line = reader.readLine();
        } catch (IOException e1) {
        	logger.error("Error reading job cancellations (predict.csv)");
            e1.printStackTrace();
        }
        
        int tempId = 1;


        while ((line != null)) {
            String[] twoParts = line.split(cvsSplitBy);
            if (twoParts[0].equals("0")){
            	cancellations.put(tempId, false);
            } else{
            	cancellations.put(tempId, true);
            }
            tempId ++;
            
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            reader.close();
        } catch (IOException e) {
        	logger.error("Error reading job cancellations (predict.csv)");
            e.printStackTrace();
        }
        
        logger.debug("All job cancellation information has been read from the file (predict.csv)");
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
        	logger.error("Error reading item specifications (specs.csv)");
            e1.printStackTrace();
        }

        try {
            line = reader.readLine();
        } catch (IOException e1) {
        	logger.error("Error reading item specifications (specs.csv)");
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
        	logger.error("Error reading item specifications (specs.csv)");
            e.printStackTrace();
        }
        
        logger.debug("All item specifications have been read from the file (specs.csv)");
    }
    
    public static ArrayList<Item> readLocations(String filePath) {
    	 String cvsSplitBy = ",";
    	 ArrayList<Item> allItems=new ArrayList<Item>();
         FileReader file = null;

         try {
             file = new FileReader(filePath);
         } catch (FileNotFoundException e) {
        	 logger.error("Error reading item locations (locations.csv)");
             e.printStackTrace();
         }
         BufferedReader reader = new BufferedReader(file);

         String line = null;
         try {
             line = reader.readLine();
         } catch (IOException e1) {
        	 logger.error("Error reading item locations (locations.csv)");
             e1.printStackTrace();
         }
         
         try {
             line = reader.readLine();
         } catch (IOException e1) {
        	 logger.error("Error reading item locations (locations.csv)");
             e1.printStackTrace();
         }
         
         while (line != null) {
             String[] allParts = line.split(cvsSplitBy);
             String itemName=allParts[2];
             itemSpecifications.getItemSpecification().get(itemName).addCoordinates(new Point(Integer.parseInt(allParts[0]) , Integer.parseInt(allParts[1])));
             try {
                 line = reader.readLine();
             } catch (IOException e) {
            	 logger.error("Error reading item locations (locations.csv)");
                 e.printStackTrace();
             }
         }
         
         logger.debug("All item locations have been read from the file (locations.csv)");
         
         return allItems;
    }
    public static void readJobs (String filePath){

        String cvsSplitBy = ",";
        FileReader file = null;

        try {
            file = new FileReader(filePath);
        } catch (FileNotFoundException e) {
        	logger.error("Error reading job information (jobs.csv)");
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(file);

        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e1) {
        	logger.error("Error reading job information (jobs.csv)");
            e1.printStackTrace();
        }
        
        int tempId = 1;

        while ((line != null)) {
            String[] allParts = line.split(cvsSplitBy);

            int tasksLeft = (allParts.length - 1) / 2;
            int jobID = Integer.parseInt(allParts[0]);
            int item = 1;
            int count = 2;

            Task task = new Task();
            while (tasksLeft > 0){
                task.addTask(allParts[item], Integer.parseInt(allParts[count]));
                item += 2;
                count += 2;
                --tasksLeft;
            }

            if (cancellations.get(tempId) == false){
            	jobs.addJobs(jobID, task);
            } else {
            	System.out.println(jobID + " cancelled");
            }
            
            tempId ++;
            
            
            //System.out.println(jobs.toString(jobID));
            try {
                line = reader.readLine();
            } catch (IOException e) {
            	logger.error("Error reading job information (jobs.csv)");
                e.printStackTrace();
            }
        }

        try {
            reader.close();
        } catch (IOException e) {
        	logger.error("Error reading job information (jobs.csv)");
            e.printStackTrace();
        }
        
        logger.debug("All item specifications have been read from the file (jobs.csv)");
    }
    
    public static void readDrops (String filePath){

        String cvsSplitBy = ",";
        FileReader file = null;

        try {
            file = new FileReader(filePath);
        } catch (FileNotFoundException e) {
        	logger.error("Error reading job information (jobs.csv)");
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(file);

        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e1) {
        	logger.error("Error reading job information (drops.csv)");
            e1.printStackTrace();
        }

        while ((line != null)) {
            String[] allParts = line.split(cvsSplitBy);

            DropLocation drop = new DropLocation (Integer.parseInt(allParts[0]), Integer.parseInt(allParts[1]));
            dropLocations.add(drop);
            
            try {
                line = reader.readLine();
            } catch (IOException e) {
            	logger.error("Error reading job information (drops.csv)");
                e.printStackTrace();
            }
        }

        try {
            reader.close();
        } catch (IOException e) {
        	logger.error("Error reading job information (drops.csv)");
            e.printStackTrace();
        }
        
        logger.debug("All item specifications have been read from the file (drops.csv)");
    }

    public static ItemSpecifications getItemSpecifications() {
        return itemSpecifications;
    }
    
    public static ArrayList<DropLocation> getDropLocations() {
        return dropLocations;
    }

    public ItemReader (Jobs jobs, ItemSpecifications itemSpecifications) {
        ItemReader.jobs = jobs;
        ItemReader.itemSpecifications = itemSpecifications;
    }
    public static void main (){
    	dropLocations = new ArrayList <DropLocation>();
    	cancellations = new HashMap <Integer, Boolean>();
    	readCancellationInfo ("resources/predict.csv");
        readSpecs ("resources/items.csv");
        readJobs ("resources/jobs.csv");
        readLocations("resources/locations.csv");
        readDrops("resources/drops.csv");
        logger.debug("ItemReader has parsed all of the information found in .csv files");
    }
}
