import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.RangeFinder;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.config.WheeledRobotConfiguration;
import rp.robotics.DifferentialDriveRobot;
import rp.util.Rate;


public class DetectJunctions2 {
	
	private final DifferentialDriveRobot robot;
	private final DifferentialPilot pilot;
	private final LightSensor lightSensorLeft;
	private final LightSensor lightSensorRight;
	private final LightSensor lightSensorFront;
	private final int ROTATE_DEGREE = 10;
	private final int BLACK = 45;
	private final int ROBOT_LENGTH = 16;
	private final int MAX_DANCING = 5;
	private boolean running = false;
	
	
	public DetectJunctions2(DifferentialDriveRobot robot, SensorPort lsl, SensorPort lsr, SensorPort lsf) {
		this.robot = robot;
		this.pilot = this.robot.getDifferentialPilot();
		this.lightSensorLeft = new LightSensor(lsl);
		this.lightSensorRight = new LightSensor(lsr);
		this.lightSensorFront = new LightSensor(lsf);
	}
	
	public void run(){

		running = true;
		Rate r = new Rate(40);
		double reflectedLightLeft;
		double reflectedLightRight;
		double reflectedLightFront;
		String lastTurn = new String();
		
		pilot.setTravelSpeed(10f);
		pilot.setRotateSpeed(120);
		
		int c = 0;
		
		while (running) {
			//0 is dark
			//100 is light
			
			reflectedLightLeft = lightSensorLeft.getLightValue();
			reflectedLightRight = lightSensorRight.getLightValue();
			reflectedLightFront = lightSensorFront.getLightValue();
			
			++c;
			
			// modify for the caster bot. V1
			// caster bot has three light sensor 
			// If left and right light sensor detect white and centre sensor detect black then it should go straight.
			// if left, right, centre light sensor detect black that means the robot is on the junction.
			// if all the sensor detects white then it means off the track
			// if 
			System.out.println(reflectedLightLeft + ", " + reflectedLightRight + ", " + reflectedLightFront);
			
			
			if(!isWhite(reflectedLightLeft) && !isWhite(reflectedLightRight)){
				if(!isWhite(reflectedLightFront)){
					//The robot meet a junction so add something when the robot meet junction
					
					
				}
			}
			
			
			else if(isWhite(reflectedLightLeft) && isWhite(reflectedLightRight)){
				if(!isWhite(reflectedLightFront)){
				pilot.forward();
				}
				else{
					// add command when all three sensor didn't detect any line
				}
				
			}
			
			
			else if (!isWhite(reflectedLightLeft) && isWhite(reflectedLightRight)) // adjust to left
			{
				turnLeft(reflectedLightLeft, reflectedLightRight);
				lastTurn = "left";
				System.out.println("1 - " + c%10);
			}
			
			else if (!isWhite(reflectedLightRight) && isWhite(reflectedLightLeft)) // adjust to right
			{
				turnRight(reflectedLightLeft, reflectedLightRight);
				lastTurn = "right";
				System.out.println("2 - " + c%10);
			}
			
			
			
			
			
			else if (isWhite(reflectedLightLeft) && isWhite(reflectedLightRight))
			{
				System.out.println("3 - " + c%10);
				
				if (isWhite(reflectedLightFront)) {
					System.out.println("3.1 - " + c%10);
					pilot.rotate(180);
					System.out.println("3.2 - " + c%10);
				}
				
				else {
					System.out.println("3.3 - " + c%10);
					pilot.forward();
				}
			}
			
			
			
			
			
			else {	// both black?
				
				//if (!isWhite(reflectedLightFront)) {
					randomRotation();
				//}

			}
			
			r.sleep();
		}
	}

	public static void main(String[] args) {
		
		Button.waitForAnyPress();
		
		DetectJunctions2 dj2 = new DetectJunctions2(
				new DifferentialDriveRobot(new WheeledRobotConfiguration(5.6f, 11f, 22f, Motor.A, Motor.C)),
				SensorPort.S1,
				SensorPort.S3,
				SensorPort.S2
				);
		dj2.run();
	}
	
	private boolean isWhite(double value) {
		if (value > BLACK)
			return true;
		return false;
	}
	
	private void turnLeft(double ls, double rs) {
		while (!isWhite(ls) && isWhite(rs)) {
			robot.getLeftWheel().stop();
			robot.getRightWheel().forward();
			ls = lightSensorLeft.getLightValue();
			rs = lightSensorRight.getLightValue();
		}
	}

	private void turnRight(double ls, double rs) {
		while (isWhite(ls) && !isWhite(rs)) {
			robot.getRightWheel().stop();
			robot.getLeftWheel().forward();
			ls = lightSensorLeft.getLightValue();
			rs = lightSensorRight.getLightValue();
		}
	}
	
	public void randomRotation() {
		pilot.stop();
		pilot.travel(ROBOT_LENGTH/2);
		
		Random r = new Random();
		int randNum = r.nextInt(3);
		
		if (randNum == 0) {
			//rotate the robot left
			pilot.rotate(88);

		}
		
		else if (randNum == 1) {
			//rotate the robot right
			pilot.rotate(-88);
		}
		
		else if (randNum == 2) {
			pilot.forward();

		}

	}
}