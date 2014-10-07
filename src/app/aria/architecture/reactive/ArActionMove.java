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

package app.aria.architecture.reactive;

import app.aria.architecture.ArBehavior;
import app.aria.robot.ArRobotMobile;

import com.mobilerobots.Aria.ArActionDesired;

public class ArActionMove extends ArBehavior {

	private double maxSpeed;
	private ArActionDesired actionDesired;
	private double stopDistance;
	private double speed;
	private double angle;

	public ArActionMove(ArRobotMobile robot) {
		super("Move", robot);
		maxSpeed = 500;
		stopDistance =  ArRobotMobile.STOP_DISTANCE;
		angle = 90;
		actionDesired = new ArActionDesired();
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public double getStopDistance() {
		return stopDistance;
	}

	public void setStopDistance(double stopDistance) {
		this.stopDistance = stopDistance;
	}

	@Override
	public ArActionDesired action() {
		actionDesired.reset();
		double range = getRobot().getRangeSonar().currentReadingPolar(-angle, angle);
		if (range > stopDistance) {
			speed = range * .3;
			if (speed > maxSpeed)
				speed = maxSpeed;
			actionDesired.setVel(speed);
		} else {
			actionDesired.setVel(0);
		}
		return actionDesired;
	}

}
