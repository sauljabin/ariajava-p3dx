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
import app.aria.architecture.ArArchitecture;
import app.aria.robot.ArRobotMobile;
import app.map.Map;
import app.path.geometry.Point;

public class ArArchitectureAuRA extends ArArchitecture {

	private ArMisionPlanner arMisionPlanner;
	private boolean ready;

	private final static long SLEEP = 100;

	public ArArchitectureAuRA(ArRobotMobile robot, Map map) {
		super("AuRA", robot, map);
		arMisionPlanner = new ArMisionPlanner(map, robot);
		ready = false;
	}

	@Override
	public void behavior() {
		if (!ready) {

			Point start = new Point(getMap().getRobotHome().getX(), getMap().getRobotHome().getY(), "INICIO");
			Point finish = new Point(getMap().getGoal().getX(), getMap().getGoal().getY(), "FIN");

			Log.info(getClass(), String.format("Inicio: %s, Fin: %s", start, finish));
			arMisionPlanner.setStart(start);
			arMisionPlanner.setTarget(finish);
			ready = true;
		}
		arMisionPlanner.execute();
		ArUtil.sleep(ArArchitectureAuRA.SLEEP);
	}
}
