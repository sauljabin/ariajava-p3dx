/**
 * 
 * ArConnector.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
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
