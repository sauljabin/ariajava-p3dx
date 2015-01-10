/**
 * 
 * ArArchitectureAuRA.java
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
import app.Translate;
import app.aria.architecture.ArArchitecture;
import app.aria.robot.ArRobotMobile;
import app.map.Start;
import app.path.geometry.Point;

public class ArArchitectureAuRA extends ArArchitecture {

	private ArMisionPlanner arMisionPlanner;
	private ArPlanSequencer arPlanSequencer;
	private ArSpatialReasoner arSpatialReasoner;
	private ArSchemaController arSchemaController;
	private boolean pathExist;
	private boolean avoid;
	private double angleForAvoid;

	public ArArchitectureAuRA(ArMisionPlanner arMisionPlanner, ArRobotMobile robot) {
		super("AuRA", robot);
		this.arMisionPlanner = arMisionPlanner;
		arSchemaController = new ArSchemaController(robot);
		arMisionPlanner.getMap().setGraph(null);
		arMisionPlanner.getMap().setPathPoints(null);
		pathExist = false;
	}

	@Override
	public void behavior() {
		if (!pathExist)
			return;

		Point position = arSchemaController.getPosition();
		double angle = arSchemaController.getAngle();

		if (arSchemaController.detectObstacle(arMisionPlanner.getRobotStopDistance()) && !avoid) {
			arSchemaController.stop();
			arSchemaController.sleep(400);

			double point1X = position.getX() + arMisionPlanner.getRobotStopDistance() * Math.cos(Math.toRadians(angle + arSchemaController.getSonarRadius()));
			double point1Y = position.getY() + arMisionPlanner.getRobotStopDistance() * Math.sin(Math.toRadians(angle + arSchemaController.getSonarRadius()));

			double point2X = position.getX() + arMisionPlanner.getRobotStopDistance() * Math.cos(Math.toRadians(angle - arSchemaController.getSonarRadius()));
			double point2Y = position.getY() + arMisionPlanner.getRobotStopDistance() * Math.sin(Math.toRadians(angle - arSchemaController.getSonarRadius()));

			arMisionPlanner.addLine((int) point1X, (int) point1Y, (int) point2X, (int) point2Y);
			avoid = true;
			angleForAvoid = angle;
			return;
		}

		if (avoid) {

			double backPoitX = position.getX() + arMisionPlanner.getRobotStopDistance() * Math.cos(Math.toRadians(angleForAvoid + 180));
			double backPoitY = position.getY() + arMisionPlanner.getRobotStopDistance() * Math.sin(Math.toRadians(angleForAvoid + 180));

			Point nextGoal = new Point(backPoitX, backPoitY, "");
			double desiredAngle = arPlanSequencer.calculateDesiredAngle(position, nextGoal);
			double angleTurn = desiredAngle - angle;
			if (Math.abs(angleTurn) > arSpatialReasoner.getArMisionPlanner().getRobotErrorAngle()) {
				arSchemaController.turn(angleTurn);
			} else {
				avoid = false;
				arMisionPlanner.setStart(new Start(arMisionPlanner.getMap(), (int) position.getX(), (int) position.getY(), angle));
				calculatePath();
			}
		} else {
			arPlanSequencer.executePlan(arSchemaController);
		}
	}

	private void calculatePath() {
		Log.info(getClass(), String.format("%s: %s, %s: %s", Translate.get("GUI_STARTPOINT"), arMisionPlanner.getStart(), Translate.get("GUI_ENDPOINT"), arMisionPlanner.getGoal()));

		arSpatialReasoner = new ArSpatialReasoner(arMisionPlanner);
		if (pathExist = (arSpatialReasoner.calculatePath(arMisionPlanner.getStart().toPoint(), arMisionPlanner.getGoal().toPoint()))) {
			arPlanSequencer = new ArPlanSequencer(arSpatialReasoner);
		} else {
			Log.warning(getClass(), Translate.get("ERROR_NOPATHTOGOAL"));
		}
	}

	@Override
	public void init() {
		calculatePath();
	}
}
