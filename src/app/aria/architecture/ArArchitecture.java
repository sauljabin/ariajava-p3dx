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

import app.Translate;
import app.aria.exception.ArException;
import app.aria.exception.ArExceptionParseArgs;

import com.mobilerobots.Aria.ArRobot;
import com.mobilerobots.Aria.ArSimpleConnector;
import com.mobilerobots.Aria.ArSonarDevice;
import com.mobilerobots.Aria.Aria;

public abstract class ArArchitecture implements Comparable<ArArchitecture>, Runnable {

	private String name;
	private String host;
	private int tcpPort;
	private Thread thread;
	private ArRobot robot;
	private ArSonarDevice sonar;
	private ArSimpleConnector conn;

	public String getName() {
		return name;
	}

	public String getHost() {
		return host;
	}

	public int getTcpPort() {
		return tcpPort;
	}

	public ArRobot getRobot() {
		return robot;
	}

	public ArSonarDevice getSonar() {
		return sonar;
	}

	public ArArchitecture(String name, String host, int tcpPort) {
		this.name = name;
		this.host = host;
		this.tcpPort = tcpPort;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(ArArchitecture o) {
		return this.toString().compareTo(o.toString());
	}

	public boolean isAlive() {
		return thread == null ? false : thread.isAlive();
	}

	public void start() throws ArException, ArExceptionParseArgs {
		if (!isAlive()) {

			thread = new Thread(this);

			Aria.init();
			conn = new ArSimpleConnector(new String[] { "-rrtp", String.format("%d", tcpPort), "-rh", host });
			robot = new ArRobot();
			sonar = new ArSonarDevice();

			if (!Aria.parseArgs()) {
				throw new ArExceptionParseArgs(Translate.get("ERROR_PARSEARGS"));
			}

			if (!conn.connectRobot(robot)) {
				throw new ArException(Translate.get("INFO_UNSUCCESSFULCONN"));
			}

			robot.addRangeDevice(sonar);

			robot.addAction(getBehavior(), 100);

			robot.enableMotors();

			thread.start();
		}
	}

	public void stop() {
		try {
			if (isAlive()) {
				robot.stopRunning(true);
				thread.join(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		robot.run(true);
	}

	public abstract ArBehavior getBehavior();

}
