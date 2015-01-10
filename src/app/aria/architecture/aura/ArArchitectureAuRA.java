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

public class ArArchitectureAuRA extends ArArchitecture {

	private ArMisionPlanner arMisionPlanner;
	private ArPlanSequencer arPlanSequencer;
	private ArSpatialReasoner arSpatialReasoner;
	private ArSchemaController arSchemaController;
	private boolean pathExist;

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
		arPlanSequencer.executePlan(arSchemaController);
	}

	@Override
	public void init() {
		Log.info(getClass(), String.format("%s: %s, %s: %s", Translate.get("GUI_STARTPOINT"), arMisionPlanner.getStart(), Translate.get("GUI_ENDPOINT"), arMisionPlanner.getGoal()));

		arSpatialReasoner = new ArSpatialReasoner(arMisionPlanner);
		if (pathExist = (arSpatialReasoner.calculatePath(arMisionPlanner.getStart().toPoint(), arMisionPlanner.getGoal().toPoint()))) {
			arPlanSequencer = new ArPlanSequencer(arSpatialReasoner);
		} else {
			Log.warning(getClass(), Translate.get("ERROR_NOPATHTOGOAL"));
		}

	}
}
