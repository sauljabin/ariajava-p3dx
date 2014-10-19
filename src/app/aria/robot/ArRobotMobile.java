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

package app.aria.robot;

import app.map.Robot;

import com.mobilerobots.Aria.ArPose;
import com.mobilerobots.Aria.ArRangeDevice;
import com.mobilerobots.Aria.ArRobot;
import com.mobilerobots.Aria.ArSonarDevice;

public class ArRobotMobile extends ArRobot {

	public static final int LONG = 455;
	public static final int WIDTH = 381;
	public static final int STOP_DISTANCE = 400;

	private double relativeX;
	private double relativeY;
	private double relativeAngle;
	private double initX;
	private double initY;
	private double initAngle;
	private ArSonarDevice sonar;
	private Robot animatedRobot;
	protected ArRangeDevice rangeSonar;
	private int maxSpeed;

	public ArRobotMobile(double initX, double initY, double initAngle) {
		this.initX = initX;
		this.initY = initY;
		this.initAngle = initAngle;
		setEncoderTransform(new ArPose(initX, initY, initAngle));
		sonar = new ArSonarDevice();
		addRangeDevice(sonar);
		rangeSonar = findRangeDevice("sonar");
	}

	public ArSonarDevice getSonar() {
		return sonar;
	}

	public void setSonar(ArSonarDevice sonar) {
		this.sonar = sonar;
	}

	public double getInitX() {
		return initX;
	}

	public void setInitX(double initX) {
		this.initX = initX;
	}

	public double getInitY() {
		return initY;
	}

	public void setInitY(double initY) {
		this.initY = initY;
	}

	public double getInitAngle() {
		return initAngle;
	}

	public void setInitAngle(double initAngle) {
		this.initAngle = initAngle;
	}

	public double getRelativeX() {
		return relativeX;
	}

	public void setRelativeX(double relativeX) {
		this.relativeX = relativeX;
	}

	public double getRelativeY() {
		return relativeY;
	}

	public void setRelativeY(double relativeY) {
		this.relativeY = relativeY;
	}

	public double getRelativeAngle() {
		return relativeAngle;
	}

	public void setRelativeAngle(double relativeAngle) {
		this.relativeAngle = relativeAngle;
	}

	public Robot getAnimatedRobot() {
		return animatedRobot;
	}

	public void setAnimatedRobot(Robot animatedRobot) {
		this.animatedRobot = animatedRobot;
	}

	public ArRangeDevice getRangeSonar() {
		return rangeSonar;
	}

	public void setRangeSonar(ArRangeDevice rangeSonar) {
		this.rangeSonar = rangeSonar;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

}
