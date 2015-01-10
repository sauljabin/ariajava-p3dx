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

import com.mobilerobots.Aria.ArUtil;

import app.Log;
import app.Translate;
import app.aria.architecture.ArArchitecture;
import app.aria.robot.ArRobotMobile;

public class ArArchitectureAuRA extends ArArchitecture {

	private ArMisionPlanner arMisionPlanner;
	//private ArPlanSequencer arPlanSequencer;
	private ArSpatialReasoner arSpatialReasoner;
	//private ArSchemaController arSchemaController;

	private final static long SLEEP = 100;

	public ArArchitectureAuRA(ArMisionPlanner arMisionPlanner, ArRobotMobile robot) {
		super("AuRA", robot);
		this.arMisionPlanner = arMisionPlanner;
	}

	@Override
	public void behavior() {

		ArUtil.sleep(ArArchitectureAuRA.SLEEP);
	}

	@Override
	public void init() {
		Log.info(getClass(), String.format("%s, %s", arMisionPlanner.getStart(), arMisionPlanner.getGoal()));

		arSpatialReasoner = new ArSpatialReasoner(arMisionPlanner.getMap());
		if (arSpatialReasoner.calculatePath(arMisionPlanner.getStart().toPoint(), arMisionPlanner.getGoal().toPoint())) {
			arSpatialReasoner.addPathToMap();
		} else {
			Log.warning(getClass(), Translate.get("ERROR_NOPATHTOGOAL"));
		}

	}
}
