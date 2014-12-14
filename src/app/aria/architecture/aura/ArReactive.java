/**
 * 
 * ArReactive.java
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

import app.aria.robot.ArRobotMobile;
import app.path.geometry.Point;

public class ArReactive {

	private ArRobotMobile robot;

	public ArReactive(ArRobotMobile robot) {
		this.robot = robot;
	}

	/**
	 * Move in a particular compass direction
	 */
	public void moveAhead() {

	}

	/**
	 * Move towards a detected goal object
	 */
	public void moveToGoal() {

	}

	/**
	 * Move away from barriers
	 */
	public void avoidStaticObstacle() {

	}

	/**
	 * Move toward the center of a path, road or hallway
	 */
	public void stayOnPath() {

	}

	private void bloqueo() {
		robot.lock();
	}

	private void desbloqueo() {
		robot.unlock();
	}

	public void look(double angle) {
		bloqueo();
		robot.setDeltaHeading(angle);
		desbloqueo();
	}
	
	public void move(double speed) {
		bloqueo();
		robot.setVel(speed);
		desbloqueo();
	}
	
	public void stop() {
		bloqueo();
		robot.setVel(0);
		desbloqueo();
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

}
