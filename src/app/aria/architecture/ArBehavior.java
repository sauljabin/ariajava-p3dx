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

package app.aria.architecture;

import com.mobilerobots.Aria.ArAction;
import com.mobilerobots.Aria.ArActionDesired;
import com.mobilerobots.Aria.ArRangeDevice;
import com.mobilerobots.Aria.ArRobot;

public abstract class ArBehavior extends ArAction {

	private ArArchitecture arArchitecture;
	private ArRangeDevice rangeSonar;

	public ArBehavior(ArArchitecture arArchitecture) {
		super(arArchitecture.getName());
		this.arArchitecture = arArchitecture;
	}

	public ArArchitecture getArArchitecture() {
		return arArchitecture;
	}

	public void setArArchitecture(ArArchitecture arArchitecture) {
		this.arArchitecture = arArchitecture;
	}

	public ArRangeDevice getRangeSonar() {
		return rangeSonar;
	}

	public void setRangeSonar(ArRangeDevice rangeSonar) {
		this.rangeSonar = rangeSonar;
	}

	@Override
	public void setRobot(ArRobot robot) {
		super.setRobot(robot);
		rangeSonar = robot.findRangeDevice("sonar");
	}

	@Override
	public abstract ArActionDesired fire(ArActionDesired currentAction);

}
