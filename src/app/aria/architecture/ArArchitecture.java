/**
 * 
 * ArArchitecture.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
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
