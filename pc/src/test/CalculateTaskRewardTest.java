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
			task.addTask("ab", 2);
			task.addTask("bc", 2);
			task.addTask("cd", 7);
			
			Map<String, Integer> taskMap = task.getTasks();
			
			ItemSpecifications specs = new ItemSpecifications ();
			specs.addSpecifications("ab", 10, 12);
			specs.addSpecifications("bc", 20, 25);
			specs.addSpecifications("cd", 100, 8);
			
			specs.getItemSpecification().get("ab").addCoordinates(new Point(2,5));
			specs.getItemSpecification().get("bc").addCoordinates(new Point(5,0));
			specs.getItemSpecification().get("cd").addCoordinates(new Point(1,5));
			
			float result = task.calculateReward(taskMap, MapUtils.createRealWarehouse(), specs);
			assertEquals(result, 320f, 0.0f);
		}
		
		@Test
		public void CalculateReward2(){
			Task task = new Task();
			task.addTask("aa", 1);
			task.addTask("bb", 2);
			
			Map<String, Integer> taskMap = task.getTasks();
			
			ItemSpecifications specs = new ItemSpecifications ();
			specs.addSpecifications("aa", 50, 1);
			specs.addSpecifications("bb", 300, 3);
			
			specs.getItemSpecification().get("aa").addCoordinates(new Point(3,4));
			specs.getItemSpecification().get("bb").addCoordinates(new Point(1,2));
			
			float result = task.calculateReward(taskMap, MapUtils.createRealWarehouse(), specs);
			assertEquals(result, 216.66667f, 0.0f);
		}
}
