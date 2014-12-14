/**
 * 
 * ArUpdaterPositionAnimation.java
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

import app.aria.robot.ArRobotMobile;

public class ArUpdaterPositionAnimation implements Runnable {
	
	private int updateAnimatedPositionRate;
	private Thread thread;
	private boolean run;
	private double animatedX;
	private double animatedY;
	private double animatedAngle;

	private ArRobotMobile robot;
	
	public ArUpdaterPositionAnimation(ArRobotMobile robot, int updateAnimatedPositionRate) {
		this.robot = robot;
		this.updateAnimatedPositionRate = updateAnimatedPositionRate;
		updateAnimatedPosition();
	}

	private synchronized void updateAnimatedPosition() {
		robot.lock();
		updateAnimatedPosition(robot.getX(), robot.getY(), robot.getTh());
		robot.unlock();
	}

	public void updateAnimatedPosition(double x, double y, double angle) {
		animatedX = x;
		animatedY = y;
		animatedAngle = angle;		
		robot.setRelativeAngle(angle);
		robot.setRelativeX(x);
		robot.setRelativeY(y);
		if (robot.getAnimatedRobot() != null)
			robot.getAnimatedRobot().updateAnimatedPosition(x, y, angle);
	}

	public double getAnimatedX() {
		return animatedX;
	}

	public void setAnimatedX(double animatedX) {
		this.animatedX = animatedX;
	}

	public double getAnimatedY() {
		return animatedY;
	}

	public void setAnimatedY(double animatedY) {
		this.animatedY = animatedY;
	}

	public double getAnimatedAngle() {
		return animatedAngle;
	}

	public void setAnimatedAngle(double animatedAngle) {
		this.animatedAngle = animatedAngle;
	}

	public synchronized void setUpdateAnimatedPositionRate(int updateAnimatedPositionRate) {
		this.updateAnimatedPositionRate = updateAnimatedPositionRate;
	}

	public int getUpdateAnimatedPositionRate() {
		return updateAnimatedPositionRate;
	}

	public boolean isAlive() {
		return thread == null ? false : thread.isAlive();
	}

	public void stop() {
		run = false;
		try {
			if (isAlive()) {
				thread.join(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (run) {
			updateAnimatedPosition();
			try {
				Thread.sleep(1000 / updateAnimatedPositionRate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		if (!isAlive()) {
			thread = new Thread(this);
			run = true;
			thread.start();
		}
	}

}
