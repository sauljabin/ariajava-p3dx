/**
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 *		SAUL PIÑA - SAULJP07@GMAIL.COM
 *		JORGE PARRA - THEJORGEMYLIO@GMAIL.COM
 *		2014
 */
 
package app.aria.action;

import com.mobilerobots.Aria.ArAction;
import com.mobilerobots.Aria.ArActionDesired;
import com.mobilerobots.Aria.ArRangeDevice;
import com.mobilerobots.Aria.ArRobot;

public class ArActionMove extends ArAction {

	private double maxSpeed;
	private ArActionDesired actionDesired;
	private double stopDistance;
	private ArRangeDevice sonar;

	public ArActionMove() {
		super("Move");
		maxSpeed = 500;
		stopDistance = 350;
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
	public ArActionDesired fire(ArActionDesired currentAction) {
		actionDesired.reset();
		
		double speed;		
		double robotRadius = 400;
		double range = sonar.currentReadingPolar(-70, 70) - robotRadius;
		
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

	@Override
	public void setRobot(ArRobot robot) {
		setActionRobot(robot);
		sonar = robot.findRangeDevice("sonar");
	}

}
