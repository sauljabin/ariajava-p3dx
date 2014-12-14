/**
 * 
 * ArPlanSequencer.java
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

import app.Log;
import app.aria.robot.ArRobotMobile;
import app.map.Map;
import app.path.geometry.Point;

public class ArPlanSequencer {

	public final static int APS_LOOK = 1;
	public final static int APS_LOOKING = 2;
	public final static int APS_MOVE = 3;
	public final static int APS_MOVING = 4;
	public final static int APS_STOP = 5;
	public final static int APS_STOPING = 6;

	private final static double ERROR_DISTANCE_TARGET = 30.0;
	private final static double ERROR_DISTANCE_DEVIATED = 1000.0;
	private final static double ERROR_ANGLE = 1.0;
	private final static double SPEED_MIN = 50.0;
	private final static double SPEED_MAX = 2000.0;
	private final static double DISTANCE_MIN = 400.0;
	private final static double DISTANCE_MAX = 4000.0;

	private double speed;

	private ArReactive arReactive;

	private double desiredAngle;
	private Point start;
	private Point finish;

	private int state;
	private int nextState;

	private double currentAngle;
	private Point positionA;
	private Point positionB;
	private double positionAngleA;
	private double positionAngleB;
	private int waiting;

	public ArPlanSequencer(Map map, ArRobotMobile robot) {
		arReactive = new ArReactive(robot);
		currentAngle = 0;
		state = ArPlanSequencer.APS_STOP;
		nextState = ArPlanSequencer.APS_STOP;
		waiting = 0;
	}

	public int continuePath() {
		Log.print(".");
		switch (state) {
		case ArPlanSequencer.APS_LOOK:
			desiredAngle = calculateDesiredAngle();
			positionAngleA = arReactive.getAngle();
			state = ArPlanSequencer.APS_LOOKING;
			Log.print("\nLooking");
			arReactive.look(desiredAngle);
			break;
		case ArPlanSequencer.APS_LOOKING:
			if (isDesiredAngleAchieved()) {
				currentAngle = arReactive.getAngle();
				state = ArPlanSequencer.APS_MOVE;
				Log.print("\nMove");
			} else {
				// positionAngleB = positionAngleA;
				// positionAngleA = arReactive.getAngle();
				// if (Math.abs(positionAngleA - positionAngleB) < 0.01) {
				// if (waiting < 20)
				// waiting++;
				// else {
				// waiting = 0;
				// state = ArPlanSequencer.APS_MOVE;
				// }
				// }
			}
			break;
		case ArPlanSequencer.APS_MOVE:
			speed = calculateSpeed();
			arReactive.move(speed);
			state = ArPlanSequencer.APS_MOVING;
			Log.print("\nMoving");
			break;
		case ArPlanSequencer.APS_MOVING:
			if (diminutionSpeed())
				state = ArPlanSequencer.APS_MOVE;
			if (targetAchieved()) {
				positionA = arReactive.getPosition();
				nextState = ArPlanSequencer.APS_STOP;
				state = ArPlanSequencer.APS_STOPING;
				Log.print("\nStoping");
			}
			if (isDeviated()) {
				positionA = arReactive.getPosition();
				nextState = ArPlanSequencer.APS_LOOK;
				state = ArPlanSequencer.APS_STOPING;
				Log.print("\nStoping");
			}
			break;
		case ArPlanSequencer.APS_STOP:
			switch (nextState) {
			case ArPlanSequencer.APS_STOP:
				if (targetAchieved()) {
					return ArSpatialReasoner.ASR_END_SECTION;
				}else{
					state=ArPlanSequencer.APS_MOVE;
				}
				break;
			case ArPlanSequencer.APS_LOOK:
				state = ArPlanSequencer.APS_LOOK;
				Log.print("\nLook");
			}
			break;
		case ArPlanSequencer.APS_STOPING:
			arReactive.stop();
			positionB = positionA;
			positionA = arReactive.getPosition();
			if (positionA.distance(positionB) < 0.01) {
				state = ArPlanSequencer.APS_STOP;
				Log.print("\nStop");
			}
			break;
		}
		return ArSpatialReasoner.ASR_IN_PROGRESS;
	}

	private double calculateSpeed() {
		Point robotPosition = arReactive.getPosition();
		double dFinish = robotPosition.distance(finish);
		if (dFinish > ArPlanSequencer.DISTANCE_MAX)
			return ArPlanSequencer.SPEED_MAX;
		if (dFinish < ArPlanSequencer.DISTANCE_MIN)
			return ArPlanSequencer.SPEED_MIN;
		return (dFinish * (ArPlanSequencer.SPEED_MAX - SPEED_MIN)) / (ArPlanSequencer.DISTANCE_MAX - DISTANCE_MIN);
	}

	private boolean diminutionSpeed() {
		Point robotPosition = arReactive.getPosition();
		double dFinish = robotPosition.distance(finish);
		if (dFinish > ArPlanSequencer.DISTANCE_MIN && dFinish < ArPlanSequencer.DISTANCE_MAX)
			return true;
		return false;
	}

	public int newSection(Point nextTarget) {
		start = finish;
		finish = nextTarget;
		if (start == null)
			return ArSpatialReasoner.ASR_END_SECTION;
		state = ArPlanSequencer.APS_LOOK;
		return ArSpatialReasoner.ASR_IN_PROGRESS;
	}

	private double calculateDesiredAngle() {
		Point robotPosition = arReactive.getPosition();
		double dh = robotPosition.distance(finish);
		double angleX = Math.acos((finish.getX() - robotPosition.getX()) / dh);
		double dy = finish.getY() - robotPosition.getY();
		double angle = (angleX * 360) / (2 * Math.PI);
		if (dy < 0)
			angle = ((-angleX) * 360) / (2 * Math.PI);
		angle = angle - currentAngle;
		while (angle > 180 || angle < -180)
			if (angle > 180)
				angle -= 360;
			else
				angle += 360;
		return angle;
	}

	private boolean targetAchieved() {
		Point robotPosition = arReactive.getPosition();
		return robotPosition.distance(finish) < ERROR_DISTANCE_TARGET;
	}

	private boolean isDeviated() {
		Point robotPosition = arReactive.getPosition();
		double dStart = robotPosition.distance(start);
		double dFinish = robotPosition.distance(finish);
		double dPath = start.distance(finish);
		if (dStart > dPath || dFinish > dPath + ArPlanSequencer.ERROR_DISTANCE_DEVIATED)
			return true;
		double dLine = calculateDistancePointToLine(start, finish, robotPosition);
		if (dLine > ArPlanSequencer.ERROR_DISTANCE_DEVIATED)
			return true;
		return false;
	}

	private double calculateDistancePointToLine(Point start, Point finish, Point robotPosition) {
		double dy = finish.getY() - start.getY();
		double dx = finish.getX() - start.getX();
		double vA;
		double vB;
		double vC;
		if (dy < 0.0001) {
			// HORIZONTAL
			vA = 0.0;
			vB = -1.0;
			vC = finish.getY();
		} else if (dx < 0.0001) {
			// VERTICAL
			vA = -1.0;
			vB = 0.0;
			vC = finish.getX();
		} else {
			// OBLICUA
			double m = dy / dx;
			vA = m;
			vB = -1.0;
			vC = (robotPosition.getY() + (m * robotPosition.getX()));
		}
		double d = Math.abs((vA * robotPosition.getX()) + (vB * robotPosition.getY()) + vC) / Math.sqrt(Math.pow(vA, 2.0) + Math.pow(vB, 2.0));
		return d;
	}

	private boolean isDesiredAngleAchieved() {		
		double angle = arReactive.getAngle();
		double value = Math.abs(angle - desiredAngle - currentAngle);
		while (value > 180 || value < -180)
			if (value > 180)
				value -= 360;
			else
				value += 360;
		return value < ArPlanSequencer.ERROR_ANGLE;
	}

}
