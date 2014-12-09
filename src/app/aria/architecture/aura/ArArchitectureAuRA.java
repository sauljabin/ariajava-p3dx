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

import com.mobilerobots.Aria.ArUtil;

import app.aria.architecture.ArArchitecture;
import app.aria.robot.ArRobotMobile;
import app.map.Map;
import app.path.geometry.Point;

public class ArArchitectureAuRA extends ArArchitecture {

	private ArMisionPlanner arMisionPlanner;
	private boolean listo;

	private final static long SLEEP = 100;

	public ArArchitectureAuRA(ArRobotMobile robot, Map map) {
		super("AuRA", robot, map);
		arMisionPlanner = new ArMisionPlanner(map, robot);
		listo = false;
	}

	@Override
	public void behavior() {
		if (!listo) {
			Point start = new Point(getMap().getRobotHome().getX(), getMap()
					.getRobotHome().getY(), "INICIO");
			Point finish = new Point(getMap().getGoal().getX(), getMap()
					.getGoal().getY(), "FIN");
			arMisionPlanner.setStart(start);
			arMisionPlanner.setTarget(finish);
			listo = true;
		}
		arMisionPlanner.execute();
		ArUtil.sleep(ArArchitectureAuRA.SLEEP);
	}
}
