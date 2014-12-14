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

import app.Log;
import app.aria.robot.ArRobotMobile;
import app.map.Map;
import app.path.geometry.Point;

public class ArMisionPlanner {

	public final static int AMP_INIT = 0;
	public final static int AMP_SEARCH_IN_PROGRESS = 1;
	public final static int AMP_TARGET_ACHIEVED = 2;
	public final static int AMP_UNATTAINABLE_GOAL = 3;
	public final static int FIN = 4;

	private Point start;
	private Point target;
	private int state;
	private ArSpatialReasoner arSpatialReasoner;

	public ArMisionPlanner(Map map, ArRobotMobile robot) {
		this.state = ArMisionPlanner.AMP_INIT;
		this.arSpatialReasoner = new ArSpatialReasoner(map, robot);
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public void setTarget(Point target) {
		this.target = target;
	}

	public void execute() {
		switch (state) {
		case ArMisionPlanner.AMP_INIT:
			Log.info(getClass(), "MISION_PLANNER: Inicializando");
			state = arSpatialReasoner.calculatePath(start, target);
			break;
		case ArMisionPlanner.AMP_SEARCH_IN_PROGRESS:
			state = arSpatialReasoner.continuePath();
			break;
		case ArMisionPlanner.AMP_TARGET_ACHIEVED:
			Log.info(getClass(), "MISION_PLANNER: Objetivo Alcanzado");
			state = ArMisionPlanner.FIN;
			break;
		case ArMisionPlanner.AMP_UNATTAINABLE_GOAL:
			Log.info(getClass(), "MISION_PLANNER: Imposible llegar al destino");
			break;
		case ArMisionPlanner.FIN:
			break;
		}
	}

}
