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

package app.aria.connection;

import com.mobilerobots.Aria.ArSimpleConnector;
import com.mobilerobots.Aria.Aria;

import app.Log;
import app.Translate;
import app.aria.exception.ArException;
import app.aria.exception.ArExceptionParseArgs;
import app.aria.robot.ArRobotMobile;

public class ArConnector {
	private ArSimpleConnector conn;
	private String host;
	private int tcpPort;
	private ArRobotMobile robot;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getTcpPort() {
		return tcpPort;
	}

	public void setTcpPort(int tcpPort) {
		this.tcpPort = tcpPort;
	}

	public ArRobotMobile getRobot() {
		return robot;
	}

	public void setRobot(ArRobotMobile robot) {
		this.robot = robot;
	}

	public ArConnector(String host, int tcpPort, ArRobotMobile robot) {
		this.host = host;
		this.tcpPort = tcpPort;
		this.robot = robot;
	}

	public void connect() throws ArException {
		conn = new ArSimpleConnector(new String[] {
				"-rrtp", String.format("%d", tcpPort), "-rh", host
		});

		if (!Aria.parseArgs()) {
			throw new ArExceptionParseArgs(Translate.get("ERROR_PARSEARGS"));
		}

		if (!conn.connectRobot(robot)) {
			throw new ArException(Translate.get("INFO_UNSUCCESSFULCONN"));
		}

	}

	public void close() {
		if (robot.disconnect()) {
			Log.info(getClass(), Translate.get("INFO_CLOSECONN"));
		} else {
			Log.warning(getClass(), Translate.get("ERROR_CLOSECONN"));
		}
	}
}
