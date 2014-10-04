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

import app.map.Map;
import app.path.PathPlanner;
import app.path.geometry.Point;
import app.path.graphs.Graph;

public class ArMisionPlanner {

	private Point start;
	private Point target;
	private boolean ready;
	private ArPlanSequencer arPlanSequencer;
	private PathPlanner pathPlanner;

	public ArMisionPlanner(Map map) {
		//this.arPlanSequencer = new ArPlanSequencer(map);
		//this.pathPlanner = new PathPlanner(map);
		//this.ready = false;
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public void setTarget(Point target) {
		this.target = target;
	}

	public void execute() {
//		if (!ready)
//			calculePath();
//		else
//			followPath();
	}

	private void calculePath() {
		if (start != null && target != null) {
			pathPlanner.searchOptimalRoute(start, target);
			//Graph graph = pathPlanner.getGraph();
			ready = true;
		}
	}

	private void followPath() {
		
	}

}
