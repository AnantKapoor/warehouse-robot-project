package warehouse;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;

public class Robot {
	private WheeledRobotSystem robot = new WheeledRobotSystem(
			new WheeledRobotConfiguration(0.056f, 0.120f, 0.218f, Motor.C, Motor.A));
	private DifferentialPilot rPilot = robot.getPilot();
	public final double LENGTH = robot.getConfig().getRobotLength();
	private final double DEFAULT_ROTATE_SPEED = 0.8 * this.rPilot.getMaxRotateSpeed();
	private final double DEFAULT_TRAVEL_SPEED = 0.8 * this.rPilot.getMaxTravelSpeed();
	public final LightSensor LEFT = new LightSensor(SensorPort.S3);
	public final LightSensor MIDDLE = new LightSensor(SensorPort.S2);
	public final LightSensor RIGHT = new LightSensor(SensorPort.S1);
	public final OpticalDistanceSensor IR = new OpticalDistanceSensor(SensorPort.S4);

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

	public float getDistance() {
		return rPilot.getMovementIncrement();
	}

	public void backward() {
		rPilot.backward();
	}

	public void stop() {
		rPilot.stop();
	}

	public void rotate(double angle, boolean immediateReturn) {
		rPilot.rotate(1.007 * angle, immediateReturn);
	}

	public boolean isMoving() {
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
}