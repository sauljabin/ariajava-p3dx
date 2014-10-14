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

import com.mobilerobots.Aria.ArUtil;

import app.map.Map;
import app.aria.architecture.ArArchitecture;
import app.aria.robot.ArRobotMobile;

public class ArArchitectureReactive extends ArArchitecture {

	private double angle;
	private double stopDistance;
	private double turnAngle;
	private double speed;
	private boolean turnLeft;
	private boolean turnRight;
	private int sleep;

	public ArArchitectureReactive(ArRobotMobile robot, Map map) {
		super("Reactive", robot, map);
		angle = 45;
		stopDistance = 500;
		turnAngle = 20;
		speed = 200;
		turnLeft = false;
		turnRight = false;
		sleep=200;
	}

	@Override
	public void behavior() {
		getRobot().lock();
		double range = getRobot().getRangeSonar().currentReadingPolar(-angle, angle);
		getRobot().unlock();
		if (range <= stopDistance) {
			getRobot().lock();
			getRobot().setVel(0);
			getRobot().unlock();

			if (getRobot().getVel() > 0)
				ArUtil.sleep(sleep);

			getRobot().lock();
			double leftRange = getRobot().getRangeSonar().currentReadingPolar(0, angle);
			double rightRange = getRobot().getRangeSonar().currentReadingPolar(-angle, 0);
			getRobot().unlock();
			
			if (!turnRight && !turnLeft) {
				if (leftRange <= rightRange) {
					turnLeft = true;
				} else {
					turnRight = true;
				}
			}

			getRobot().lock();
			if (turnLeft) {
				getRobot().setDeltaHeading(-turnAngle);
			} else if (turnRight) {
				getRobot().setDeltaHeading(turnAngle);
			}
			getRobot().unlock();

		} else {
			if (getRobot().getVel() == 0)
				ArUtil.sleep(sleep);
			getRobot().lock();
			getRobot().setVel(speed);
			getRobot().unlock();
			turnLeft = false;
			turnRight = false;
		}
	}

}
