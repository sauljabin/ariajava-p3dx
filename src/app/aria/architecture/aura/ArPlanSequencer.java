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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import app.Log;
import app.Translate;
import app.path.geometry.Point;

public class ArPlanSequencer {

	private final static double ERROR_DISTANCE_TARGET = 30.;
	private final static double ERROR_ANGLE = 1.;

	private ArrayList<Point> path;
	private Queue<Point> goalControl;

	private Point nextGoal;
	private boolean planFinish;
	private ArSpatialReasoner arSpatialReasoner;
	private Point prevGoal;

	public ArrayList<Point> getPath() {
		return path;
	}

	public Queue<Point> getGoalControl() {
		return goalControl;
	}

	public ArPlanSequencer(ArSpatialReasoner arSpatialReasoner) {
		this.path = arSpatialReasoner.getPath();
		this.arSpatialReasoner = arSpatialReasoner;
		goalControl = new LinkedList<Point>(path);
		newSection();
		planFinish = false;
	}

	public ArSpatialReasoner getArSpatialReasoner() {
		return arSpatialReasoner;
	}

	public void executePlan(ArSchemaController schema) {

		if (planFinish)
			return;

		double angle = schema.getAngle();

		if (nextGoal == null) {
			double lastAngleTurn = arSpatialReasoner.getArMisionPlanner().getGoal().getAngle() - angle;
			if (Math.abs(lastAngleTurn) > ERROR_ANGLE) {
				schema.turn(lastAngleTurn);
			} else {
				Log.info(getClass(), Translate.get("INFO_GOALFOUND"));
				planFinish = true;
			}
			return;
		}

		Point position = schema.getPosition();
		double distance = position.distance(nextGoal);
		double desiredAngle = calculateDesiredAngle(position, nextGoal);
		double angleTurn = desiredAngle - angle;

		if (distance < ERROR_DISTANCE_TARGET) {
			schema.stop();
			schema.sleep();
			newSection();
		} else if (Math.abs(angleTurn) > ERROR_ANGLE) {
			schema.turn(angleTurn);
		} else {
			if (distance > schema.getRobotMaxSpeed())
				distance = schema.getRobotMaxSpeed();
			schema.move(distance);
		}

	}

	public boolean isPlanFinish() {
		return planFinish;
	}

	private void newSection() {
		prevGoal = nextGoal;
		nextGoal = goalControl.poll();

		String nextGoalString = "null";
		String prevGoalString = "null";

		if (nextGoal != null)
			nextGoalString = String.format("x=%.2f,y=%.2f", nextGoal.getX(), nextGoal.getY());

		if (prevGoal != null)
			prevGoalString = String.format("x=%.2f,y=%.2f", prevGoal.getX(), prevGoal.getY());

		Log.info(getClass(), String.format("%s [%s;%s]", Translate.get("INFO_NEWSECTION"), prevGoalString, nextGoalString));
	}

	private double calculateDesiredAngle(Point point1, Point point2) {
		return Math.toDegrees(Math.atan2(point2.getY() - point1.getY(), point2.getX() - point1.getX()));
	}

}
