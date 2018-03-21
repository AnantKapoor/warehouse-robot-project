package warehouse;

import java.util.ArrayList;

import lejos.nxt.Button;
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
	public final double LENGTH = robot.getConfig().getRobotLength();
	private final double DEFAULT_ROTATE_SPEED = 0.7 * this.rPilot.getMaxRotateSpeed();
	private final double DEFAULT_TRAVEL_SPEED = 0.8 * this.rPilot.getMaxTravelSpeed();
	public final LightSensor LEFT = new LightSensor(SensorPort.S3);
	public final LightSensor MIDDLE = new LightSensor(SensorPort.S2);
	public final LightSensor RIGHT = new LightSensor(SensorPort.S1);
	public final OpticalDistanceSensor IR = new OpticalDistanceSensor(SensorPort.S4);
	private boolean atJunction = false;
	private boolean running = true;
	private int[] calibratedVals;

	public Robot() {
		rPilot.setTravelSpeed(DEFAULT_TRAVEL_SPEED);
		rPilot.setRotateSpeed(DEFAULT_ROTATE_SPEED);
		calibratedVals = calibrateLightSensors();
	}

	private void forward() {
		rPilot.forward();
	}

	private void travel(double distance) {
		rPilot.travel(distance, false);
	}

	private void stop() {
		rPilot.stop();
	}

	private void rotate(double angle, boolean immediateReturn) {
		rPilot.rotate(1.0075 * angle, immediateReturn);
	}

	public void executeRoute(ArrayList<Integer> directions) {
		int index = 0;
		forward();
		while (index < directions.size()) {
			if (atJunction) {
				if (directions.get(index) != 0) {
					stop();
					travel(LENGTH / 10);
					rotate(directions.get(index) * 90, false);
				} else {
					Delay.msDelay(75);
				}
				atJunction = false;
				index++;
			}
			running = true;
			run();
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
			// LCD.drawString("max: " + maxL + " min: " + minL, 0, 4);
			// LCD.drawString("max: " + maxM + " min: " + minM, 0, 5);
			// LCD.drawString("max: " + maxR + " min: " + minR, 0, 6);

		}
		return new int[] { maxL, minL, maxM, minM, maxR, minR };
	}

	private void adjustRight(double lV, double rV) {
		while (!isWhite(lV, calibratedVals[1]) && isWhite(rV, calibratedVals[5])) {
			Motor.C.stop(true);
			Motor.A.forward();
			lV = LEFT.getLightValue();
			rV = RIGHT.getLightValue();
		}
	}

	private void adjustLeft(double lV, double rV) {
		while (isWhite(lV, calibratedVals[1]) && !isWhite(rV, calibratedVals[5])) {
			Motor.A.stop(true);
			Motor.C.forward();
			lV = LEFT.getLightValue();
			rV = RIGHT.getLightValue();
		}
	}

	private boolean isWhite(double value, int blackValue) {
		if (value > blackValue + 5) {
			return true;

		}
		return false;
	}

	/*
	 * private boolean findObstacle() {
	 * 
	 * if ( us.getRange() IR.getDistance() < DISTANCE) {
	 * System.out.println("Obstacle Found"); return true; } else return false; }
	 */

	private void run() {
		double lV;
		double mV;
		double rV;
		while (running) {
			lV = LEFT.getLightValue();
			rV = RIGHT.getLightValue();
			mV = MIDDLE.getLightValue();
			// findObstacle();
			if (!isWhite(lV, calibratedVals[1]) && !isWhite(mV, calibratedVals[3]) && !isWhite(rV, calibratedVals[5])) {
				atJunction = true;
				running = false;
			} else if (!isWhite(lV, calibratedVals[1]) && isWhite(rV, calibratedVals[5])) { // needs to adjust to right
				adjustRight(lV, rV);
			} else if (isWhite(lV, calibratedVals[1]) && !isWhite(rV, calibratedVals[5])) { // needs to adjust to left
				adjustLeft(lV, rV);
			} else {
				forward();
			}
		}
	}

	public static void main(String[] args) {
		Button.waitForAnyPress();
		Robot robot = new Robot();
		ArrayList<Integer> directions = new ArrayList<>();
		directions.add(1);
		directions.add(2);
		directions.add(0);
		directions.add(1);
		directions.add(0);
		directions.add(-1);
		directions.add(0);
		directions.add(2);
		directions.add(-1);
		directions.add(0);
		robot.executeRoute(directions);
	}
}