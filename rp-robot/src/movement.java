

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.config.WheeledRobotConfiguration;
import rp.robotics.DifferentialDriveRobot;
import rp.util.Rate;
//import lejos.nxt.UltrasonicSensor;


public class movement {

	private final DifferentialDriveRobot robot;
	private final DifferentialPilot pilot;
	public final LightSensor LEFT = new LightSensor(SensorPort.S3);
	public final LightSensor MIDDLE = new LightSensor(SensorPort.S2);
	public final LightSensor RIGHT = new LightSensor(SensorPort.S1);
	public final int DISTANCE = 10;
	public final OpticalDistanceSensor os =new OpticalDistanceSensor(SensorPort.S4);
	//public final UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
	
	private final int ROTATE_DEGREE = 10;
	private final int BLACK = 44;
	private final int ROBOT_LENGTH = 18;
	private final int MAX_DANCING = 5;
	private boolean running = false;
	private int arr[][];
	
	
	
	
	
	
	
	
	public movement(DifferentialDriveRobot robot) {
		this.robot = robot;
		this.pilot = this.robot.getDifferentialPilot();
	}
	
	private boolean isWhite(double value) {
		if (value > BLACK){
			return true;
			
		}
		return false;
	}
	
	private String map(){
		
		
		
		return null;
		
	}
	
	
	
	private boolean findObstacle(){
		
		if(/*us.getRange()*/os.getDistance() < DISTANCE){
			System.out.println("Obstacle Found");
			return true;
		}
		else
			return false;
	}
	
	
	private void adjustRight(double Lv, double Rv){
		
		while(isWhite(Lv) && !isWhite(Rv)){
			
			robot.getLeftWheel().stop();
			robot.getRightWheel().forward();
			Lv = LEFT.getLightValue();
			Rv = RIGHT.getLightValue();
			
		}
	}
	
	private void adjustLeft(double Lv, double Rv){
		while(!isWhite(Lv) && isWhite(Rv)){
			robot.getRightWheel().stop();
			robot.getLeftWheel().forward();
			Lv = LEFT.getLightValue();
			Rv = RIGHT.getLightValue();
		}
	}
	
	
	
	
	
	
	public void run(){
		
		running =true;
		Rate r = new Rate(120);
		double Lv;
		double Rv;
		double Fv;
		pilot.setTravelSpeed(10f);
		pilot.setRotateSpeed(120);
	
		
		System.out.println("start");
		
		
		
		while (running) {
	
			Lv = LEFT.getLightValue();
			Rv = RIGHT.getLightValue();
			Fv = MIDDLE.getLightValue();
			
			
			pilot.forward();
		
			findObstacle();
			
			if(!isWhite(Lv) && !isWhite(Rv)){
				System.out.println("Junction Detect");
			}
			else if(!isWhite(Lv) && isWhite(Rv)){ // needs to adjust to right
				pilot.stop();
				//adjustLeft(Lv,Rv);
				while(!isWhite(Lv) && isWhite(Rv)){
					robot.getRightWheel().stop();
					robot.getLeftWheel().forward();
					Lv = LEFT.getLightValue();
					Rv = RIGHT.getLightValue();
				}
				
				System.out.println("adjust to right");
			}
			else if(isWhite(Lv) && !isWhite(Rv)){ // needs to adjust to left
				pilot.stop();
				//adjustRight(Lv, Rv);
				while(isWhite(Lv) && !isWhite(Rv)){
			
					robot.getLeftWheel().stop();
					robot.getRightWheel().forward();
					Lv = LEFT.getLightValue();
					Rv = RIGHT.getLightValue();
					
				}
				System.out.println("adjust to left");
			}
			else if(isWhite(Lv) && isWhite(Rv)){
			System.out.println("Going Straight");
					pilot.forward();
					
				}
			else{
				pilot.stop();
			}
		}
		r.sleep();
	}
	
	
	public static void main(String[] args) {
		
		Button.waitForAnyPress();
		
		movement a = new movement(
				new DifferentialDriveRobot(new WheeledRobotConfiguration(5.6f, 12f, 22f, Motor.A, Motor.C))
				
				);
		a.run();
	}
	
	
	
	
	
	
}
