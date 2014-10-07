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

public class ArActionTurn extends ArBehavior {

	private ArActionDesired actionDesired;
	private double turnThreshold;
	private double turnAmount;
	private double angle;
	private boolean turnLeft;
	private boolean turnRight;
	private double turnAngle;
	private double stopDistance;

	public ArActionTurn(ArRobotMobile robot) {
		super("Turn", robot);
		turnAmount = 10;
		angle = 90;
		actionDesired = new ArActionDesired();
		turnLeft = false;
		turnRight = false;
		turnAngle = 20;
		stopDistance = ArRobotMobile.STOP_DISTANCE;
	}

	public double getTurnThreshold() {
		return turnThreshold;
	}

	public double getTurnAmount() {
		return turnAmount;
	}

	@Override
	public ArActionDesired action() {
		actionDesired.reset();

		double leftRange = getRobot().getRangeSonar().currentReadingPolar(0, angle);
		double rightRange = getRobot().getRangeSonar().currentReadingPolar(-angle, 0);

		if (leftRange > stopDistance && rightRange > stopDistance) {
			turnLeft = false;
			turnRight = false;
		} else if (rightRange <= leftRange) {
			turnLeft = true;
		} else {
			turnRight = true;
		}

		if (!turnRight && !turnLeft) {
			actionDesired.setDeltaHeading(0);
		}else if (turnLeft) {
			actionDesired.setDeltaHeading(turnAngle);
		} else if (turnRight) {
			actionDesired.setDeltaHeading(-turnAngle);
		}

		return actionDesired;
	}

}
