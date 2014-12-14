/**
 * 
 * ArSpatialReasoner.java
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

import app.Log;
import app.aria.robot.ArRobotMobile;
import app.map.Map;
import app.path.PathPlanner;
import app.path.geometry.Point;

public class ArSpatialReasoner {

	public final static int ASR_NEW_SECTION = 0;
	public final static int ASR_IN_PROGRESS = 1;
	public final static int ASR_END_SECTION = 2;
	public final static int ASR_RECALCULATE = 3;

	private Point target;
	private int state;

	private Point lastTarget;
	private Point nextTarget;

	private ArrayList<Point> path;
	private ArrayList<Point> pathTraveled;

	private PathPlanner pathPlanner;
	private ArPlanSequencer arPlanSequencer;

	public ArSpatialReasoner(Map map, ArRobotMobile robot) {
		this.pathPlanner = new PathPlanner(map);
		this.arPlanSequencer = new ArPlanSequencer(map, robot);
		this.state = ArSpatialReasoner.ASR_NEW_SECTION;
		this.pathTraveled = new ArrayList<Point>();
	}

	public int calculatePath(Point start, Point target) {
		if (start != null && target != null) {
			this.target = target;
			path = pathPlanner.searchOptimalRoute(start, target);
			if (path == null)
				return ArMisionPlanner.AMP_UNATTAINABLE_GOAL;
			return ArMisionPlanner.AMP_SEARCH_IN_PROGRESS;
		}
		return ArMisionPlanner.AMP_INIT;
	}

	public int continuePath() {
		switch (state) {
		case ArSpatialReasoner.ASR_NEW_SECTION:
			Log.println("SPATIAL_REASONER: Definiendo nueva seccion");
			nextTarget = path.remove(0);
			state = arPlanSequencer.newSection(nextTarget);
			break;
		case ArSpatialReasoner.ASR_IN_PROGRESS:
			state = arPlanSequencer.continuePath();
			break;
		case ArSpatialReasoner.ASR_END_SECTION:
			Log.println("SPATIAL_REASONER: Finalizando seccion");
			lastTarget = nextTarget;
			if(lastTarget !=null)
				pathTraveled.add(lastTarget);
			if (path.isEmpty())
				return ArMisionPlanner.AMP_TARGET_ACHIEVED;
			state = ArSpatialReasoner.ASR_NEW_SECTION;
			break;
		case ArSpatialReasoner.ASR_RECALCULATE:
			Log.println("SPATIAL_REASONER: Recalculando ruta");
			state = ArSpatialReasoner.ASR_NEW_SECTION;
			return calculatePath(lastTarget, target);
		}
		return ArMisionPlanner.AMP_SEARCH_IN_PROGRESS;
	}
}
