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
	private final double DEFAULT_ROTATE_SPEED = 0.7 * this.rPilot.getMaxRotateSpeed();
	private final int DEFAULT_TRAVEL_SPEED = 700;
	public final double LENGTH = robot.getConfig().getRobotLength();
	public final LightSensor LEFT = new LightSensor(SensorPort.S3);
	public final LightSensor MIDDLE = new LightSensor(SensorPort.S2);
	public final LightSensor RIGHT = new LightSensor(SensorPort.S1);
	public final OpticalDistanceSensor IR = new OpticalDistanceSensor(SensorPort.S4);
	private boolean atJunction = false;
	private boolean running = true;
	private int[] calibratedVals;
	private int direction = 2; // assume facing south from beginning
	private int[] coordinates; // assume starting point

	public Robot() {
		rPilot.setRotateSpeed(DEFAULT_ROTATE_SPEED);
		calibratedVals = calibrateLightSensors();
		int initialDir = Button.waitForAnyPress();
		faceSouth(getInitialDir(initialDir));
		coordinates = Interface.getCoordinates();
	}

	private void setDefaultSpd() {
		Motor.C.setSpeed(DEFAULT_TRAVEL_SPEED);
		Motor.A.setSpeed(DEFAULT_TRAVEL_SPEED);
	}

	private int getInitialDir(int dir) {
		switch (dir) {
		case Button.ID_RIGHT:
			return 3;
		case Button.ID_ESCAPE:
			return 2;
		case Button.ID_LEFT:
			return 1;
		}
		return 0;
	}

	private void forward() {
		setDefaultSpd();
		Motor.C.forward();
		Motor.A.forward();
	}

	private void travel(double distance) {
		rPilot.travel(distance, false);
	}

	private void stop() {
		Motor.C.stop(true);
		Motor.A.stop();
	}

	private void rotate(double angle, boolean immediateReturn) {
		rPilot.rotate(1.005 * angle, immediateReturn);
	}

	/*
	 * private void faceSouth() {//// float[] distances = new float[4]; for (int i =
	 * 0; i < 4; i++) { float distance = IR.getRange(); distances[i] = distance;
	 * rotate(90, false); Delay.msDelay(500); } float highest = 0f; for (float f :
	 * distances) { if (f > highest) { highest = f; } } float secondHighest = 0f;
	 * for (float f : distances) { if (f > secondHighest && f < highest) {
	 * secondHighest = f; } } String dist = ""; for (float f : distances) { if (f <
	 * secondHighest) { dist += 1; } else { dist += 0; } } System.out.print("[ ");
	 * for (float f : distances) { System.out.print(f + " "); }
	 * System.out.println("]"); System.out.println(dist);
	 * System.out.println(highest); System.out.println(secondHighest); switch (dist)
	 * { case "0011": break; case "0110": rotate(-90, false); break; case "1100":
	 * rotate(180, false); break; case "1001": rotate(90, false); break; } stop();
	 * Button.waitForAnyPress(); }
	 */

	private void faceSouth(int currentDir) {
		switch (currentDir) {
		case 0:
			rotate(180, false);
			break; // north
		case 1:
			rotate(-90, false); // east
			break;
		case 2:
			break; // south
		case 3:
			rotate(90, false); // west
			break;
		}
		stop();
	}

	private void updateCoord(int reverse) {
		switch (direction) {
		case 0:
			coordinates[1] -= reverse * 1;
			break;
		case 1:
			coordinates[0] += reverse * 1;
			break;
		case 2:
			coordinates[1] += reverse * 1;
			break;
		case 3:
			coordinates[0] -= reverse * 1;
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
		while (!isWhite(lV, calibratedVals[1]) && isWhite(rV, calibratedVals[5])) {
			lV = LEFT.getLightValue();
			rV = RIGHT.getLightValue();

			Motor.C.setSpeed(DEFAULT_TRAVEL_SPEED / 2);
		}
	}

	private void adjustLeft(double lV, double rV) {
		while (isWhite(lV, calibratedVals[1]) && !isWhite(rV, calibratedVals[5])) {
			lV = LEFT.getLightValue();
			rV = RIGHT.getLightValue();

			Motor.A.setSpeed(DEFAULT_TRAVEL_SPEED / 2);
		}
	}

	/*private boolean isAverage(double value, int blackValue) {
		return false;
	}*/

	private boolean isWhite(double value, int blackValue) {
		if (value > blackValue + 5) {
			return true;
		}
		return false;
	}

	private void run() {
		double lV;
		double mV;
		double rV;
		forward();
		while (running) {
			lV = LEFT.getLightValue();
			rV = RIGHT.getLightValue();
			mV = MIDDLE.getLightValue();
			// findObstacle
			if (!isWhite(lV, calibratedVals[1]) && !isWhite(mV, calibratedVals[3]) && !isWhite(rV, calibratedVals[5])) {
				atJunction = true;
				running = false;
				updateCoord(1);
			} else if (!isWhite(lV, calibratedVals[1]) && isWhite(rV, calibratedVals[5])) { // needs to adjust right
																							// wheel speed
				adjustRight(lV, rV);
			} else if (isWhite(lV, calibratedVals[1]) && !isWhite(rV, calibratedVals[5])) { // needs to adjust left
																							// wheel speed
				adjustLeft(lV, rV);
			}
			forward();
		}
	}

	private void rotateAtJunction(int rotationMultiple) {
		if (rotationMultiple < 0) {
			rotate(-360, true);
			while (true) {
				int rV = RIGHT.getLightValue();
				if (!isWhite(rV, calibratedVals[5])) {
					break;
				}
			}
			while (isMoving()) {
				int rV = RIGHT.getLightValue();
				if (!isWhite(rV, calibratedVals[5])) {
					stop();
				}
			}
		} else {
			rotate(360, true);
			int i = 1;
			while (i < rotationMultiple) {
				while (true) {
					int lV = LEFT.getLightValue();
					if (!isWhite(lV, calibratedVals[1])) {
						break;
					}
				}
				Delay.msDelay(250);
			}
			while (isMoving()) {
				int lV = LEFT.getLightValue();
				if (!isWhite(lV, calibratedVals[1])) {
					stop();
				}
			}
		}
	}

	public void executeRoute(ArrayList<Integer> directions) {
		int index = 0;
		forward();
		while (index < directions.size()) {
			if (atJunction) {
				atJunction = false;
				int instr = directions.get(index++);
				if (instr == 0) {
				} else {
					stop();
					switch (instr) {
					case -1:
					case 1:
					case 2:
						rotateAtJunction(instr);
						direction = (direction - instr + 4) % 4;
						continue;
					case 3:
						System.out.println("Dropping item!");
						Button.ENTER.waitForPressAndRelease();
						Delay.msDelay(250);
						atJunction = true;
						continue;
					case 4:
						System.out.println("Picking up item!");
						Button.ENTER.waitForPressAndRelease();
						Delay.msDelay(250);
						atJunction = true;
						continue;
					case 5:
						System.out.println("Finished job!");
						travel(-LENGTH / 2);
						updateCoord(-1);
						continue;
					}
				}
			}
			running = true;
			run();
		}
		stop();
		LCD.clear();
		LCD.drawString(coordinates[0] + ", " + coordinates[1] + ", " + direction, 5, 4);
		Button.waitForAnyPress();
	}

	public static void main(String[] args) {
		Button.waitForAnyPress();
		Robot robot = new Robot();
		// robot.run();

		ArrayList<Integer> directions = new ArrayList<>();

		int[] dir3 = { 1, -1, 1, -1, 2, 0, 0, 1, 2, -1, 5 };
		for (int i : dir3) {
			directions.add(i);
		}
		robot.executeRoute(directions);

		directions.clear();
		int[] dir4 = { 0, 0, 1, 4, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 3, 1, 0, 5 };
		for (int i : dir4) {
			directions.add(i);
		}
		robot.executeRoute(directions);
	}
}