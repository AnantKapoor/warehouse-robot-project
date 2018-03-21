package main.java.JobSelection;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

import main.java.PathFinding.PathFinder;
import main.java.PathFinding.PathFinder2;
import main.java.PathFinding.PathInfo;

import org.apache.log4j.Logger;

import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;

public class Run {

	private static final Logger logger = Logger.getLogger(Run.class);
	private static ArrayList<Order> allOrders;
	private static ArrayList<JobsAssignment> finalList;
	public static ItemSpecifications itemSpecifications = new ItemSpecifications();
	public static Jobs jobs = new Jobs();

	public static void main() {
		ItemReader itemReader = new ItemReader(jobs, itemSpecifications);
		ItemReader.main();
		itemSpecifications = ItemReader.itemSpecifications;

		logger.debug("All item infromation stored.");

		jobs = ItemReader.jobs;

		logger.debug("All job infromation stored.");

		Iterator itemIterator = itemSpecifications.getItemSpecification().values().iterator();
		allOrders = jobs.calculateRewards(itemSpecifications);

		if (allOrders == null) {
			logger.error("Error calculating job rewards");
		}

		Collections.sort(allOrders, new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				return o2.getRate().compareTo(o1.getRate());
			}
		});

		logger.debug("All orders sorted by reward.");
		finalList = new ArrayList<JobsAssignment>();
		for (int i = 0; i < allOrders.size(); i++) {
			finalList.add(new JobsAssignment(allOrders.get(i).getID(), allOrders.get(i).getReward()));
			for (int j = 0; j < allOrders.get(i).getAllOrder().size(); j++) {
				finalList.add(new JobsAssignment(allOrders.get(i).getAllOrder().get(j)));
			}
		}
		GridPose robotPosition1 = new GridPose(new Point(1, 0), Heading.PLUS_Y);
		GridPose robotPosition2 = new GridPose(new Point(3, 0), Heading.PLUS_Y);
		GridPose robotPosition3 = new GridPose(new Point(2, 0), Heading.PLUS_Y);
		int i = 0;
		Map<String, Specifications> itemSpecs = itemSpecifications.getItemSpecification();
		PathFinder finder = new PathFinder(MapUtils.createTrainingMap());
		PathFinder2 finder2=new PathFinder2(MapUtils.createMarkingWarehouseMap());
		ArrayList<PathInfo> pathInfo=new ArrayList<PathInfo>();
		while (finalList.size() >= i + 5) {
			int collectedTasks = 0;
			TaskList[] tasks = new TaskList[3];
			int[] tasksInt = new int[3];
			while (collectedTasks < 3) {
				if (finalList.get(i).getTask() != null) {
					tasks[collectedTasks] = finalList.get(i).getTask();
					tasksInt[collectedTasks] = i;
					collectedTasks++;
					i++;
				} else
					i++;
			}
			int[][] jobAuctioning = new int[3][3];
			for (int j = 0; j < 3; j++) {
				jobAuctioning[j][0] = finder.FindPath(robotPosition1,
						new GridPose(itemSpecs.get(tasks[j].getName()).getCoordinates(), Heading.PLUS_X)).path.size();
				jobAuctioning[j][1] = finder.FindPath(robotPosition2,
						new GridPose(itemSpecs.get(tasks[j].getName()).getCoordinates(), Heading.PLUS_X)).path.size();
				jobAuctioning[j][2] = finder.FindPath(robotPosition3,
						new GridPose(itemSpecs.get(tasks[j].getName()).getCoordinates(), Heading.PLUS_X)).path.size();
			}
			int[][] jobBackup = new int [3][3];
			for (int q=0;q<3;q++) {
				for (int w=0;w<3;w++) {
					jobBackup[q][w]=jobAuctioning[q][w];
				}
			}
			boolean foundBest = false;
			int[][] bestOrder = new int[3][2];
			while (!foundBest) {
				int biggest = -1;
				int biggestJ = -1;
				int biggestY = -1;
				for (int j = 0; j < 3; j++) {
					for (int y = 0; y < 3; y++) {
						if (jobAuctioning[j][y] > biggest) {
							biggest = jobAuctioning[j][y];
							biggestJ = j;
							biggestY = y;
						}
					}
				}
				int totalNumJ = 0;
				int totalNumY = 0;
				for (int j = 0; j < 3; j++) {
					if (jobAuctioning[biggestJ][j] != -1) {
						totalNumY++;
					}
				}

				for (int j = 0; j < 3; j++) {
					if (jobAuctioning[j][biggestY] != -1) {
						totalNumJ++;
					}
				}
				if (totalNumJ == 1 || totalNumY == 1) {
					boolean carryOn = true;
					for (int j = 0; j < 3; j++) {
						if (j != biggestJ) {
							int qCount = 0;
							for (int q = 0; q < 3; q++) {
								if (jobAuctioning[j][q] != -1 && q != biggestY) {
									qCount++;
								}
							}
							if (qCount == 0) {
								carryOn = false;
								break;
							}
						}
					}
					
					for (int y = 0; y < 3; y++) {
						if (y != biggestY) {
							int qCount = 0;
							for (int q = 0; q < 3; q++) {
								if (jobAuctioning[q][y] != -1 && q != biggestJ) {
									qCount++;
								}
							}
							if (qCount == 0) {
								carryOn = false;
								break;
							}
						}
					}
					if(carryOn==false) {
						jobBackup[biggestJ][biggestY] = -1;
						for (int q=0;q<3;q++) {
							for (int w=0;w<3;w++) {
								jobAuctioning[q][w]=jobBackup[q][w];
							}
						}
						continue;
					}else {
						for(int j=0;j<3;j++) {
							if(j!=biggestJ) {
								jobAuctioning[j][biggestY]=-1;
							}
						}
						for(int y=0;y<3;y++) {
							if(y!=biggestY) {
								jobAuctioning[biggestJ][y]=-1;
							}
						}
					}
					if (carryOn) {
						int secondJ = -1;
						int secondY = -1;
						int numCount = 0;
						int remainingNumbers=0;
						for(int q=0;q<3;q++) {
							for(int w=0;w<3;w++) {
								if(jobAuctioning[q][w]!=-1) {
									remainingNumbers++;
								}
							}
						}
						for (int j = 0; j < 3; j++) {
							for (int y = 0; y < 3; y++) {
								if (j != biggestJ && y != biggestY && jobAuctioning[j][y] != -1) {
									for (int q = 0; q < 3; q++) {
										if (jobAuctioning[j][q] != -1) {
											numCount++;
										}
									}

									for (int q = 0; q < 3; q++) {
										if (jobAuctioning[q][y] != -1) {
											numCount++;
										}
									}
									if (numCount < 4||remainingNumbers==5) {
										secondJ = j;
										secondY = y;
									} else numCount=0;
								}
							}
						}
						for (int j = 0; j < 3; j++) {
							if (j != secondJ) {
								jobAuctioning[j][secondY] = -1;
							}
						}
						for (int y = 0; y < 3; y++) {
							if (y != secondY) {
								jobAuctioning[secondJ][y] = -1;
							}
						}
						int thirdJ = -1;
						int thirdY = -1;
						for (int j = 0; j < 3; j++) {
							for (int y = 0; y < 3; y++) {
								if (j != biggestJ && y != biggestY && jobAuctioning[j][y] != -1 && j != secondJ
										&& y != secondY) {
									thirdJ = j;
									thirdY = y;
									bestOrder[0][0] = biggestJ;
									bestOrder[0][1] = biggestY;
									bestOrder[1][0] = secondJ;
									bestOrder[1][1] = secondY;
									bestOrder[2][0] = thirdJ;
									bestOrder[2][1] = thirdY;
									foundBest = true;
								}
							}
						}
					}
				} else
					jobAuctioning[biggestJ][biggestY] = -1;
			}
			if (bestOrder[0][0] != 0) {
				if (bestOrder[2][0] == 0) {
					Collections.swap(finalList, tasksInt[0], tasksInt[2]);
				} else
					Collections.swap(finalList, tasksInt[0], tasksInt[1]);
			}
			if ((bestOrder[0][0] == 0 && bestOrder[0][1] == 2) || (bestOrder[0][0] == 1 && bestOrder[0][1] == 2)
					|| (bestOrder[0][0] == 2 && bestOrder[0][1] == 0)) {
				Collections.swap(finalList, tasksInt[1], tasksInt[2]);
				i++;
			}
			pathInfo=finder2.FindPath(robotPosition1,new GridPose( itemSpecs.get(tasks[0].getName()).getCoordinates(), Heading.PLUS_X),
				robotPosition2,new GridPose( itemSpecs.get(tasks[1].getName()).getCoordinates(), Heading.PLUS_X),
				robotPosition3, new GridPose (itemSpecs.get(tasks[2].getName()).getCoordinates(), Heading.PLUS_X));
			robotPosition1=pathInfo.get(0).pose;
			robotPosition2=pathInfo.get(1).pose;
			robotPosition3=pathInfo.get(2).pose;
			finalList.get(tasksInt[0]).getTask().addPath(pathInfo.get(0).path);
			finalList.get(tasksInt[1]).getTask().addPath(pathInfo.get(1).path);
			finalList.get(tasksInt[2]).getTask().addPath(pathInfo.get(2).path);
		}
		for (int j=0;j<finalList.size()-10;j++) {
			finalList.get(j).toStrin();
		}
		logger.debug("All job selection tasks completed");
	}

	public static Jobs getJobs() {
		return jobs;
	}

	public static ArrayList<JobsAssignment> getAssignments() {
		return finalList;
	}

	public static ArrayList<Order> getOrders() {
		return allOrders;

	}
}
