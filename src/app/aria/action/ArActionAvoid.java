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

public class ArActionAvoid extends ArAction {

	private ArRangeDevice sonar;
	private ArActionDesired actionDesired;
	private double turnThreshold;
	private double turnAmount;
	private int turning;

	public ArActionAvoid() {
		super("Avoid");
		turnThreshold = 400;
		turnAmount = 10;
		actionDesired = new ArActionDesired();
	}

	public double getTurnThreshold() {
		return turnThreshold;
	}

	public double getTurnAmount() {
		return turnAmount;
	}

	@Override
	public ArActionDesired fire(ArActionDesired currentAction) {
		actionDesired.reset();

		double robotRadius = 400;
		double leftRange = (sonar.currentReadingPolar(0, 100) - robotRadius);
		double rightRange = (sonar.currentReadingPolar(-100, 0) - robotRadius);

		if (leftRange > turnThreshold && rightRange > turnThreshold) {
			turning = 0;
			actionDesired.setDeltaHeading(0);
		} else if (turning != 0) {
			actionDesired.setDeltaHeading(turnAmount * turning);
		} else if (leftRange < rightRange) {
			turning = -1;
			actionDesired.setDeltaHeading(turnAmount * turning);
		} else {
			turning = 1;
			actionDesired.setDeltaHeading(turnAmount * turning);
		}

		return actionDesired;
	}

	@Override
	public void setRobot(ArRobot robot) {
		setActionRobot(robot);
		sonar = robot.findRangeDevice("sonar");
	}

}
