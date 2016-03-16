/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJava P3DX.
 * 
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
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
	private int arPlanSequencerState;

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

		if (detectObstacle()) {
			arSchemaController.stop();
			arSchemaController.sleep(400);
			obstacleScanner(position, angle);
			arMisionPlanner.setStart(new Start(arMisionPlanner.getMap(), (int) position.getX(), (int) position.getY(), angle));
			calculatePath();
			arPlanSequencerState = ArPlanSequencer.APS_TURN;
		} else {
			arPlanSequencerState = arPlanSequencer.executePlan(arSchemaController);
		}
	}

	private boolean detectObstacle() {
		if (arPlanSequencerState != ArPlanSequencer.APS_TURN)
			return arSchemaController.detectObstacle(arMisionPlanner.getRobotStopDistance(), arMisionPlanner.getRobotSonarAngle());
		else
			return false;
	}

	private void obstacleScanner(Point position, double angle) {
		int startAngle = (int) -arMisionPlanner.getRobotSonarAngle() + 15;
		int endAngle = (int) arMisionPlanner.getRobotSonarAngle() + 15;

		boolean startReady = false;

		double scannerRangeStart = 0.;
		double scannerAngleStart = startAngle;
		double scannerRangeEnd = 0.;
		double scannerAngleEnd = endAngle;

		double maxDistance = arMisionPlanner.getRobotStopDistance() + arMisionPlanner.getRobotStopDistance() / 2;

		for (int i = startAngle; i < endAngle; i += 2) {
			double range = arSchemaController.rangeObstacle(i - 1, i + 1);
			if (range < maxDistance && !startReady) {
				startReady = true;
				scannerRangeStart = range;
				scannerAngleStart = i;
			}
			if (startReady) {
				if (range < maxDistance) {
					scannerRangeEnd = range;
					scannerAngleEnd = i;
				}
			}
		}

		double point1X = position.getX() + scannerRangeStart * Math.cos(Math.toRadians(angle + scannerAngleStart));
		double point1Y = position.getY() + scannerRangeStart * Math.sin(Math.toRadians(angle + scannerAngleStart));

		double point2X = position.getX() + scannerRangeEnd * Math.cos(Math.toRadians(angle + scannerAngleEnd));
		double point2Y = position.getY() + scannerRangeEnd * Math.sin(Math.toRadians(angle + scannerAngleEnd));

		arMisionPlanner.addLine((int) point1X, (int) point1Y, (int) point2X, (int) point2Y);

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
