/**
 * 
 * Copyright (c) 2014 Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>
 * 
 * This file is part of AriaJavaP3DX.
 *
 * AriaJavaP3DX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AriaJavaP3DX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AriaJavaP3DX.  If not, see <http://www.gnu.org/licenses/>.
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
