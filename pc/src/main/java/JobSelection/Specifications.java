package main.java.JobSelection;

import java.awt.Point;

public class Specifications {
    private double reward;
    private double weight;
    private Point coordinates;

    public Specifications(double reward, double weight){
        this.reward = reward;
        this.weight = weight;
    }
    public void addCoordinates(Point coordinates) {
    	this.coordinates=coordinates;
    }
    public double getReward() {
        return reward;
    }

    public double getWeight() {
        return weight;
    }
    public Point getCoordinates() {
    	return coordinates;
    }
}
