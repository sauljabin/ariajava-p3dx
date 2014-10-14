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

package app.aria.architecture;

import app.aria.exception.ArException;
import app.aria.exception.ArExceptionParseArgs;
import app.aria.robot.ArRobotMobile;
import app.map.Map;

public abstract class ArArchitecture implements Runnable, Comparable<ArArchitecture> {

	private String name;
	private Thread thread;
	private boolean run;
	private Map map;
	private ArRobotMobile robot;

	public ArRobotMobile getRobot() {
		return robot;
	}

	public void setRobot(ArRobotMobile robot) {
		this.robot = robot;
	}

	public ArArchitecture(String name, ArRobotMobile robot, Map map) {
		this.name = name;
		this.map = map;
		this.robot = robot;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(ArArchitecture o) {
		return this.toString().compareTo(o.toString());
	}

	public boolean isAlive() {
		return thread == null ? false : thread.isAlive();
	}

	public void start() throws ArException, ArExceptionParseArgs {
		if (!isAlive()) {
			thread = new Thread(this);
			robot.enableMotors();
			robot.runAsync(true);
			run = true;
			thread.start();
		}
	}

	public void stop() {
		run = false;
		try {
			if (isAlive()) {
				robot.stopRunning(false);
				thread.join(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (run) {
			behavior();
		}
	}

	public abstract void behavior();

}