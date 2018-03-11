package test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import main.java.JobSelection.ItemSpecifications;
import main.java.JobSelection.Jobs;
import main.java.JobSelection.Order;
import main.java.JobSelection.OrderDetail;
import main.java.JobSelection.Task;

import org.junit.Test;

public class CalculateRewardsTest {

	@Test
	public void testCalculateJobReward1() {

		ItemSpecifications specs = new ItemSpecifications();
		specs.addSpecifications("a", 100, 2344);
		specs.addSpecifications("b", 20, 145);

		specs.getItemSpecification().get("a").addCoordinates(new Point(2, 5));
		specs.getItemSpecification().get("b").addCoordinates(new Point(5, 0));

		Task task1 = new Task();
		task1.addTask("a", 12);
		task1.addTask("b", 2);

		Jobs jobs = new Jobs();
		jobs.addJobs(1000, task1);

		ArrayList<Order> result = jobs.calculateRewards(specs);
		String resultString = "";

		for (int i = 0; i < result.size(); i++) {
			resultString += (result.get(i).toString());
		}

		assertEquals(resultString, "job ID: 1000;" + "\n" + "Reward: 1240.0"  + "\n" + "Required items :"  + 
		"\n" + " 12 items of a;"  + "\n" + " 2 items of b;"   + "\n");
	}
	
	@Test
	public void testCalculateJobReward2() {

		ItemSpecifications specs = new ItemSpecifications();
		specs.addSpecifications("a", 100, 2344);
		specs.addSpecifications("b", 20, 145);
		specs.addSpecifications("c", 45, 98);

		specs.getItemSpecification().get("a").addCoordinates(new Point(2, 5));
		specs.getItemSpecification().get("b").addCoordinates(new Point(5, 0));
		specs.getItemSpecification().get("c").addCoordinates(new Point(1, 5));

		Task task1 = new Task();
		task1.addTask("a", 12);
		task1.addTask("b", 2);
		task1.addTask("c", 7);

		Task task2 = new Task();
		task2.addTask("a", 2);
		task2.addTask("b", 44);
		task2.addTask("c", 34);

		Task task3 = new Task();
		task3.addTask("a", 9);
		task3.addTask("b", 23);
		task3.addTask("c", 1);

		Jobs jobs = new Jobs();
		jobs.addJobs(1000, task1);
		jobs.addJobs(1001, task2);
		jobs.addJobs(1002, task3);

		ArrayList<Order> result = jobs.calculateRewards(specs);
		String resultString = "";

		for (int i = 0; i < result.size(); i++) {
			resultString += (result.get(i).toString());
		}

		assertEquals(resultString, "job ID: 1000;" + "\n" + "Reward: 1555.0"  + "\n" + "Required items :"  + 
		"\n" + " 12 items of a;"  + "\n" + " 2 items of b;"   + "\n" + " 7 items of c;"  + "\n" +"job ID: 1001;"  + "\n" + "Reward: 2610.0"   + "\n" + "Required items :"
		  + "\n" + " 2 items of a;"  + "\n" + " 44 items of b;"   + "\n" + " 34 items of c;"  + "\n" + "job ID: 1002;" + "\n" + "Reward: 1405.0"  + "\n" +
		"Required items :" + "\n" + " 9 items of a;"  + "\n" + " 23 items of b;"  + "\n" + " 1 items of c;" + "\n" );
	}
}
