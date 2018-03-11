package warehouse;

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
	public final double LENGTH = robot.getConfig().getRobotLength();
	private final double DEFAULT_ROTATE_SPEED = 0.7 * this.rPilot.getMaxRotateSpeed();
	private final double DEFAULT_TRAVEL_SPEED = 0.8 * this.rPilot.getMaxTravelSpeed();
	public final LightSensor LEFT = new LightSensor(SensorPort.S3);
	public final LightSensor MIDDLE = new LightSensor(SensorPort.S2);
	public final LightSensor RIGHT = new LightSensor(SensorPort.S1);
	public final OpticalDistanceSensor IR = new OpticalDistanceSensor(SensorPort.S4);
	private boolean atJunction = false;

	public Robot() {
		rPilot.setTravelSpeed(DEFAULT_TRAVEL_SPEED);
		rPilot.setRotateSpeed(DEFAULT_ROTATE_SPEED);
	}

	public void forward() {
		rPilot.forward();
	}

	public void travel(double distance) {
		rPilot.travel(distance, false);
	}

	/*
	 * public float getDistance() { return rPilot.getMovementIncrement(); }
	 * 
	 * public void resetDistance() { rPilot.reset(); }
	 */

	/*
	 * public void backward() { rPilot.backward(); }
	 */

	public void stop() {
		rPilot.stop();
	}

	public void rotate(double angle, boolean immediateReturn) {
		rPilot.steer(-200, 1.008 * angle, immediateReturn);
	}

	private boolean isMoving() {
		return this.rPilot.isMoving();
	}

	public int[] calibrateLightSensors() {
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
			LCD.drawString("max: " + maxL + "  min: " + minL, 0, 4);
			LCD.drawString("max: " + maxM + "  min: " + minM, 0, 5);
			LCD.drawString("max: " + maxR + "  min: " + minR, 0, 6);

		}
		return new int[] { maxL, minL, maxM, minM, maxR, minR };
	}

	private void adjustRight(double lV, double rV, int lMin, int rMin) {

		while (!isWhite(lV, lMin) && isWhite(rV, rMin)) {

			Motor.C.stop(true);
			Motor.A.forward();
			lV = LEFT.getLightValue();
			rV = RIGHT.getLightValue();

		}
	}

	private void adjustLeft(double lV, double rV, int lMin, int rMin) {
		while (isWhite(lV, lMin) && !isWhite(rV, rMin)) {
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

	public void run() {
		LCD.drawString("Calibrating", 3, 3);
		LCD.drawString("Light Sensors", 2, 4);
		int[] bounds = calibrateLightSensors();

		int lMin = bounds[1] + 3;
		int mMin = bounds[3] + 3;
		int rMin = bounds[5] + 3;
		// System.out.println("start");

		// rPilot.setTravelSpeed(15f);
		double lV;
		double mV;
		double rV;
		forward();
		LCD.drawInt(lMin, 0, 0);
		LCD.drawInt(mMin, 0, 1);
		LCD.drawInt(rMin, 0, 2);
		while (true) {
			lV = LEFT.getLightValue();
			rV = RIGHT.getLightValue();
			mV = MIDDLE.getLightValue();

			// findObstacle();
			if (!isWhite(lV, lMin) && !isWhite(mV, mMin) && !isWhite(rV, rMin)) {
				System.out.println("Junction Detect");
				atJunction = true;
				stop();
				// add motion when it meets junction
			} else if (!isWhite(lV, lMin) && isWhite(rV, rMin)) { // needs to adjust to right

				adjustRight(lV, rV, lMin, rMin);

				System.out.println("adjust to right");
			} else if (isWhite(lV, lMin) && !isWhite(rV, rMin)) { // needs to adjust to left

				adjustLeft(lV, rV, lMin, rMin);
				System.out.println("adjust to left");
			} /*
				 * else if (isWhite(lV, lMin) && isWhite(mV, mMin) && isWhite(rV, rMin)) {
				 * System.out.println("System Error Off the road"); LCD.drawString(lV + " " + mV
				 * + " " + rV, 0, 5); stop(); } else if (isWhite(mV, mMin) && isWhite(rV, rMin))
				 * { System.out.println("Going Straight"); }
				 */
			forward();
			Delay.msDelay(1);
		}
	}

	public boolean isAtJunction() {
		return atJunction;
	}

	public static void main(String[] args) {
		Robot robot = new Robot();
		Button.waitForAnyPress();
		robot.run();
	}
}