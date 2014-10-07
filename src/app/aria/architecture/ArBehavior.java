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

import app.aria.robot.ArRobotMobile;

import com.mobilerobots.Aria.ArAction;
import com.mobilerobots.Aria.ArActionDesired;

public abstract class ArBehavior extends ArAction {

	private ArRobotMobile robot;

	@Override
	public ArRobotMobile getRobot() {
		return robot;
	}

	public void setRobot(ArRobotMobile robot) {
		this.robot = robot;
	}

	public ArBehavior(String name, ArRobotMobile robot) {
		super(name);
		this.robot = robot;
		setActionRobot(robot);
	}

	@Override
	public ArActionDesired fire(ArActionDesired action) {
		return action();
	}

	public abstract ArActionDesired action();

}
