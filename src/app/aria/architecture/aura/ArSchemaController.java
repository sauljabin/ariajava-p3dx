/**
 * 
 * ArSchemaController.java
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

import app.aria.robot.ArRobotMobile;
import app.path.geometry.Point;

public class ArSchemaController {

	private ArRobotMobile robot;

	private final static long SLEEP_TIME = 200;

	public ArSchemaController(ArRobotMobile robot) {
		this.robot = robot;
	}

	private void lock() {
		robot.lock();
	}

	private void unlock() {
		robot.unlock();
	}

	public void turn(double angle) {
		lock();
		robot.setDeltaHeading(angle);
		unlock();
	}

	public void move(double speed) {
		lock();
		robot.setVel(speed);
		unlock();
	}

	public void stop() {
		lock();
		robot.setVel(0);
		unlock();
	}

	public Point getPosition() {
		robot.lock();
		Point point = new Point(robot.getX(), robot.getY(), "");
		robot.unlock();
		return point;
	}

	public double getAngle() {
		robot.lock();
		double angle = robot.getTh();
		robot.unlock();
		return angle;
	}

	public double getRobotMaxSpeed() {
		return robot.getMaxSpeed();
	}

	public void sleep() {
		ArUtil.sleep(SLEEP_TIME);
	}
}
