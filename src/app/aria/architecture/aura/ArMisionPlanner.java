/**
 * 
 * ArMisionPlanner.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.aria.architecture.aura;

import java.io.BufferedReader;
import java.io.FileReader;

import app.aria.robot.ArRobotMobile;
import app.map.Goal;
import app.map.Map;
import app.map.Start;

public class ArMisionPlanner {

	public static final String numberLinesLabel = "NumLines: ";
	public static final String robotLabel = "Cairn: RobotHome ";
	public static final String linesLabel = "LINES";
	public static final String lineMinPosLabel = "LineMinPos: ";
	public static final String lineMaxPosLabel = "LineMaxPos: ";
	public static final String goalLabel = "Cairn: Goal ";
	private String path;
	private Goal goal;
	private Start start;
	private Map map;
	private double robotMaxSpeed;
	private int robotSleepTime;
	private double robotErrorDistance;
	private double robotErrorAngle;
	private int robotStopDistance;
	private int robotSonarAngle;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public Start getStart() {
		return start;
	}

	public void setStart(Start start) {
		this.start = start;
	}

	public void load(String path) throws Exception {
		this.path = path;
		map = new Map();

		boolean linesSector = false;
		int countLine = 0;
		int totalLines = 0;

		String stringRead;
		BufferedReader br = new BufferedReader(new FileReader(path));

		while ((stringRead = br.readLine()) != null) {
			if (stringRead.startsWith(numberLinesLabel)) {
				totalLines = Integer.parseInt(stringRead.substring(numberLinesLabel.length()));
			} else if (stringRead.startsWith(lineMinPosLabel)) {
				String[] tokens = stringRead.substring(lineMinPosLabel.length()).split(" ");
				map.setMinX(Integer.parseInt(tokens[0]));
				map.setMinY(Integer.parseInt(tokens[1]));
			} else if (stringRead.startsWith(lineMaxPosLabel)) {
				String[] tokens = stringRead.substring(lineMaxPosLabel.length()).split(" ");
				map.setMaxX(Integer.parseInt(tokens[0]));
				map.setMaxY(Integer.parseInt(tokens[1]));
			} else if (stringRead.startsWith(robotLabel)) {
				String[] tokens = stringRead.substring(robotLabel.length()).split(" ");
				start = new Start(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2]));
				start.setMap(map);
			} else if (stringRead.startsWith(goalLabel)) {
				String[] tokens = stringRead.substring(goalLabel.length()).split(" ");
				goal = new Goal(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2]));
				goal.setMap(map);
			} else if (stringRead.startsWith(linesLabel)) {
				linesSector = true;
			} else if (linesSector) {
				String[] tokens = stringRead.split(" ");
				map.addLine(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
				if (++countLine == totalLines) {
					linesSector = false;
				}
			}
		}
		br.close();
		if (goal == null && start != null) {
			goal = new Goal(start.getX() + ArRobotMobile.LONG * 2, start.getY(), start.getAngle());
			goal.setMap(map);
		}
	}

	public void addLine(int x1, int y1, int x2, int y2) {
		map.addLine(x1, y1, x2, y2);
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void setRobotMaxSpeed(double robotMaxSpeed) {
		this.robotMaxSpeed = robotMaxSpeed;
	}

	public double getRobotMaxSpeed() {
		return robotMaxSpeed;
	}

	public int getRobotSleepTime() {
		return robotSleepTime;
	}

	public void setRobotSleepTime(int robotSleepTime) {
		this.robotSleepTime = robotSleepTime;
	}

	public double getRobotErrorDistance() {
		return robotErrorDistance;
	}

	public void setRobotErrorDistance(double robotErrorDistance) {
		this.robotErrorDistance = robotErrorDistance;
	}

	public double getRobotErrorAngle() {
		return robotErrorAngle;
	}

	public void setRobotErrorAngle(double robotErrorAngle) {
		this.robotErrorAngle = robotErrorAngle;
	}

	public int getRobotStopDistance() {
		return robotStopDistance;
	}

	public void setRobotStopDistance(int robotStopDistance) {
		this.robotStopDistance = robotStopDistance;
	}

	public int getRobotSonarAngle() {
		return robotSonarAngle;
	}

	public void setRobotSonarAngle(int robotSonarAngle) {
		this.robotSonarAngle = robotSonarAngle;
	}

}
