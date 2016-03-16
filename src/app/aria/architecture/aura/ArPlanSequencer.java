/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJava P3DX.
 * 
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.aria.architecture.aura;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import app.Log;
import app.Translate;
import app.path.geometry.Point;

public class ArPlanSequencer {

	public static final int APS_MOVING = 1;
	public static final int APS_FINISH = 2;
	public static final int APS_TURN = 3;

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

	public int executePlan(ArSchemaController schema) {

		if (planFinish)
			return APS_FINISH;

		double angle = schema.getAngle();

		if (nextGoal == null) {
			double lastAngleTurn = arSpatialReasoner.getArMisionPlanner().getGoal().getAngle() - angle;
			if (Math.abs(lastAngleTurn) > arSpatialReasoner.getArMisionPlanner().getRobotErrorAngle()) {
				schema.turn(lastAngleTurn);
			} else {
				Log.info(getClass(), Translate.get("INFO_GOALFOUND"));
				schema.getRobot().setCompletePath(true);
				planFinish = true;
			}
			return APS_FINISH;
		}

		Point position = schema.getPosition();
		double distance = position.distance(nextGoal);
		double desiredAngle = calculateDesiredAngle(position, nextGoal);
		double angleTurn = desiredAngle - angle;

		if (distance < arSpatialReasoner.getArMisionPlanner().getRobotErrorDistance()) {
			schema.stop();
			schema.sleep(arSpatialReasoner.getArMisionPlanner().getRobotSleepTime());
			newSection();
			return APS_TURN;
		} else if (Math.abs(angleTurn) > arSpatialReasoner.getArMisionPlanner().getRobotErrorAngle()) {
			schema.turn(angleTurn);
			return APS_TURN;
		} else {
			if (distance > arSpatialReasoner.getArMisionPlanner().getRobotMaxSpeed())
				distance = arSpatialReasoner.getArMisionPlanner().getRobotMaxSpeed();
			schema.move(distance);
			return APS_MOVING;
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

	public double calculateDesiredAngle(Point point1, Point point2) {
		return Math.toDegrees(Math.atan2(point2.getY() - point1.getY(), point2.getX() - point1.getX()));
	}

}
