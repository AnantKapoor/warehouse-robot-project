package warehouse;

import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;

public class Robot {
	private WheeledRobotSystem robot = new WheeledRobotSystem(
			new WheeledRobotConfiguration(0.056f, 0.120f, 0.218f, Motor.C, Motor.A));
	private DifferentialPilot rPilot = robot.getPilot();
	private final double DEFAULT_ROTATE_SPEED = 0.6 * this.rPilot.getMaxRotateSpeed();
	private final int DEFAULT_TRAVEL_SPEED = 540; // 540
	public final double LENGTH = robot.getConfig().getRobotLength();
	public final LightSensor LEFT = new LightSensor(SensorPort.S3);
	public final LightSensor MIDDLE = new LightSensor(SensorPort.S2);
	public final LightSensor RIGHT = new LightSensor(SensorPort.S1);
	public final OpticalDistanceSensor IR = new OpticalDistanceSensor(SensorPort.S4);
	private boolean atJunction = true;
	private boolean running = true;
	private int[] calibratedVals;
	private int direction = 0; // assume facing north from beginning
	private int[] coordinates = { 0, 0 };

	public Robot() {
		rPilot.setRotateSpeed(DEFAULT_ROTATE_SPEED);
		calibratedVals = calibrateLightSensors();
		coordinates = Interface.getCoordinates(); // manually enter coordinates
		Interface.printPose(coordinates, direction);
	}

	private void setDefaultSpd() {
		Motor.C.setSpeed(DEFAULT_TRAVEL_SPEED);
		Motor.A.setSpeed(DEFAULT_TRAVEL_SPEED);
	}

	/*
	 * private int getInitialDir(int dir) { switch (dir) { case Button.ID_RIGHT:
	 * return 3; case Button.ID_ESCAPE: return 2; case Button.ID_LEFT: return 1; }
	 * return 0; }
	 */

	private void forward() {
		setDefaultSpd();
		Motor.C.forward();
		Motor.A.forward();
	}

	private void stop() {
		Motor.C.stop(true);
		Motor.A.stop();
	}

	private void rotate(double angle, boolean immediateReturn) {
		rPilot.rotate(1.014 * angle, immediateReturn);
	}

	private void updateCoord() {
		switch (direction) {
		case 0:
			coordinates[1] += 1;
			break;
		case 1:
			coordinates[0] += 1;
			break;
		case 2:
			coordinates[1] -= 1;
			break;
		case 3:
			coordinates[0] -= 1;
			break;
		}
	}

	private boolean isMoving() {
		return this.rPilot.isMoving();
	}

	private int[] calibrateLightSensors() {
		int maxL = 0;
		int minL = 100;
		int maxM = 0;
		int minM = 100;
		int maxR = 0;
		int minR = 100;

		rotate(360, true);
		while (isMoving()) {

			int lightValueL = LEFT.getLightValue();
			int lightValueM = MIDDLE.getLightValue();
			int lightValueR = RIGHT.getLightValue();

			if (lightValueL < minL) {
				minL = lightValueL;
			}
			if (lightValueL > maxL) {
				maxL = lightValueL;
			}

			if (lightValueM < minM) {
				minM = lightValueM;
			}
			if (lightValueM > maxM) {
				maxM = lightValueM;
			}

			if (lightValueR < minR) {
				minR = lightValueR;
			}
			if (lightValueR > maxR) {
				maxR = lightValueR;
			}
		}
		return new int[] { maxL, minL, maxM, minM, maxR, minR };
	}

	private void adjustRight(double lV, double rV) {
		while (isWhite(lV, calibratedVals[1]) && !isWhite(rV, calibratedVals[5])) {
			lV = LEFT.getLightValue();
			rV = RIGHT.getLightValue();
			Motor.C.setSpeed(DEFAULT_TRAVEL_SPEED);
			Motor.A.setSpeed(13 * DEFAULT_TRAVEL_SPEED / 20);
		}
	}

	private void adjustLeft(double lV, double rV) {
		while (!isWhite(lV, calibratedVals[1]) && isWhite(rV, calibratedVals[5])) {
			lV = LEFT.getLightValue();
			rV = RIGHT.getLightValue();
			Motor.A.setSpeed(DEFAULT_TRAVEL_SPEED);
			Motor.C.setSpeed(13 * DEFAULT_TRAVEL_SPEED / 20);
		}
	}

	private boolean isWhite(double value, int blackValue) {
		if (value > blackValue + 5) {
			return true;
		}
		return false;
	}

	private void run() {
		forward();
		while (running) {
			double lV = LEFT.getLightValue();
			double rV = RIGHT.getLightValue();
			double mV = MIDDLE.getLightValue();
			if (!isWhite(lV, calibratedVals[1]) && !isWhite(mV, calibratedVals[3]) && !isWhite(rV, calibratedVals[5])) {
				atJunction = true;
				running = false;
				updateCoord();
				Interface.printPose(coordinates, direction);
			} else if (!isWhite(lV, calibratedVals[1]) && isWhite(rV, calibratedVals[5])) {
				adjustLeft(lV, rV); // needs to adjust to the left
			} else if (isWhite(lV, calibratedVals[1]) && !isWhite(rV, calibratedVals[5])) {
				adjustRight(lV, rV); // needs to adjust to the right
			}
			forward();
		}
	}

	private void rotateAtJunction(int rotationMultiple) {
		if (rotationMultiple < 0) {
			rotate(-360, true);
			Delay.msDelay(200);
			while (isMoving()) {
				int rV = RIGHT.getLightValue();
				if (!isWhite(rV, calibratedVals[5])) {
					direction = (direction + 1) % 4;
					Interface.printPose(coordinates, direction);
					stop();
				}
			}
		} else {
			rotate(360, true);
			Delay.msDelay(200);
			int i = 1;
			while (i++ < rotationMultiple) {
				while (true) {
					int lV = LEFT.getLightValue();
					if (!isWhite(lV, calibratedVals[1])) {
						break;
					}
				}
				direction = (direction + 3) % 4;
				Interface.printPose(coordinates, direction);
				Delay.msDelay(250);
			}
			while (isMoving()) {
				int lV = LEFT.getLightValue();
				if (!isWhite(lV, calibratedVals[1])) {
					direction = (direction + 3) % 4;
					Interface.printPose(coordinates, direction);
					stop();
				}
			}
		}
	}

	public void executeRoute(ArrayList<Integer> directions) {
		LCD.clear(3);
		LCD.clear(4);
		LCD.clear(5);
		LCD.clear(6);
		int index = 0;
		while (index < directions.size()) {
			if (atJunction) {
				atJunction = false;
				Delay.msDelay(100);
				int instr = directions.get(index++);
				if (instr == 0) {
					Delay.msDelay(75);
				} else {
					stop();
					switch (instr) {
					case -1:
					case 1:
					case 2:
						rotateAtJunction(instr);
						break;
					case 3:
						Interface.print(instr);
						Delay.msDelay(250);
						atJunction = true;
						continue;
					case 4:
						Interface.print(instr);
						Delay.msDelay(250);
						atJunction = true;
						continue;
					case 5:
						Interface.print(instr);
						continue;
					}
				}
			}
			running = true;
			run();
		}
		stop();
	}

	/*
	 * private static int[] simplify(float[] ranges) { float min = Float.MAX_VALUE;
	 * int[] simplifiedArr = new int[4]; for (float f : ranges) { if (f < min) { min
	 * = f; } } for (int i = 0; i < 4; i++) { if (Math.abs(ranges[i] - min) <= 5) {
	 * simplifiedArr[i] = 1; } else if (ranges[i] < 2 * min) { simplifiedArr[i] = 2;
	 * } else { simplifiedArr[i] = 0; } } return simplifiedArr; }
	 */

	public static void main(String[] args) {
		Button.waitForAnyPress();
		Robot robot = new Robot();
		Button.waitForAnyPress();

		ArrayList<Integer> directions = new ArrayList<>();

		int[] dir3 = { -1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 3 };
		for (int i : dir3) {
			directions.add(i);
		}
		robot.executeRoute(directions);

		/*
		 * directions.clear(); int[] dir2 = { 2, 0, 0, -1, 0, 0, 3, 2, 0, 0, -1, 0, -1,
		 * 4, 2, 5 }; for (int i : dir2) { directions.add(i); }
		 * robot.executeRoute(directions);
		 * 
		 * directions.clear(); int[] dir = { 0, -1, 0, 0, 0, 4, 2, 0, 0, 3, 0, 1, 0, 0,
		 * 0, 1, 5 }; for (int i : dir) { directions.add(i); }
		 * robot.executeRoute(directions);
		 */

		
		  ArrayList<int[]> values = new ArrayList<>(); boolean foundLoc = false;
		  float[] ranges = new float[4]; for (int i = 0; i < 4; i++) { float range =
		  robot.IR.getRange(); ranges[i] = range; robot.rotateAtJunction(1); } String s
		  = ""; for (float f : ranges) { s += f; }
		 
		/*
		 * ArrayList<int[]> values = new ArrayList<>(); boolean foundLoc = false; int
		 * count = 0; while (!foundLoc && count < 8) { if (atJunction) {
		 * Delay.msDelay(100); robot.stop(); float[] ranges = new float[4]; for (int i =
		 * 0; i < 4; i++) { float range = robot.IR.getRange(); ranges[i] = range;
		 * robot.rotateAtJunction(1); robot.stop(); } values.add(simplify(ranges));
		 * count++; } if (count < 8) { running = true; robot.run(); } } robot.stop();
		 * LCD.clear(); for (int[] element : values) { for (int val : element) {
		 * System.out.print(val + " "); } System.out.println(); }
		 * Button.waitForAnyPress();
		 */
	}
}