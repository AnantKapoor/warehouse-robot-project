package test;
import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

import main.java.JobSelection.ItemSpecifications;
import main.java.JobSelection.Task;
import main.java.PathFinding.PathFinder;
import main.java.PathFinding.PathInfo;

import org.junit.Test;

import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;

public class CalculateTaskRewardTest {
		
		@Test
		public void CalculateReward1(){
			Task task = new Task();
			task.addTask('a', 12);
			task.addTask('b', 2);
			task.addTask('c', 7);
			
			Map<Character, Integer> taskMap = task.getTasks();
			
			ItemSpecifications specs = new ItemSpecifications ();
			specs.addSpecifications('a', 100, 2344);
			specs.addSpecifications('b', 20, 145);
			specs.addSpecifications('c', 45, 98);
			
			specs.getItemSpecification().get('a').addCoordinates(new Point(2,5));
			specs.getItemSpecification().get('b').addCoordinates(new Point(5,0));
			specs.getItemSpecification().get('c').addCoordinates(new Point(1,5));
			
			float result = task.calculateReward(taskMap, MapUtils.createRealWarehouse(),new GridPose(), specs);
			assertEquals(result, 74.04762f, 0.0f);
		}
		
		@Test
		public void CalculateReward2(){
			Task task = new Task();
			task.addTask('a', 1);
			task.addTask('b', 2);
			
			Map<Character, Integer> taskMap = task.getTasks();
			
			ItemSpecifications specs = new ItemSpecifications ();
			specs.addSpecifications('a', 50000, 10);
			specs.addSpecifications('b', 2000, 145);
			
			specs.getItemSpecification().get('a').addCoordinates(new Point(4,5));
			specs.getItemSpecification().get('b').addCoordinates(new Point(5,9));
			
			float result = task.calculateReward(taskMap, MapUtils.createRealWarehouse(),new GridPose(), specs);
			assertEquals(result, 88.091354f, 0.0f);
		}
}
