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

package app.aria;

import app.Translate;
import app.aria.animation.AnRobot;
import app.aria.exception.ArException;
import app.aria.exception.ArExceptionParseArgs;
import app.gui.animation.Animator;
import app.map.Map;

import com.mobilerobots.Aria.ArPose;
import com.mobilerobots.Aria.ArRangeDevice;
import com.mobilerobots.Aria.ArRobot;
import com.mobilerobots.Aria.ArSimpleConnector;
import com.mobilerobots.Aria.ArSonarDevice;
import com.mobilerobots.Aria.Aria;

public abstract class ArArchitecture implements Runnable, Comparable<ArArchitecture> {

	private String name;
	private String host;
	private int tcpPort;
	private Thread thread;
	private ArRobot arRobot;
	private ArSonarDevice sonar;
	protected ArRangeDevice rangeSonar;
	private ArSimpleConnector conn;
	private boolean run;
	private Map map;
	private Animator animator;
	private AnRobot anRobot;

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

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
		return arRobot;
	}

	public ArSonarDevice getSonar() {
		return sonar;
	}

	public ArRangeDevice getRangeSonar() {
		return rangeSonar;
	}

	public ArArchitecture(String name, String host, int tcpPort, Map map, Animator animator) {
		this.name = name;
		this.host = host;
		this.tcpPort = tcpPort;
		this.map = map;
		this.animator = animator;
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
			conn = new ArSimpleConnector(new String[] {
					"-rrtp", String.format("%d", tcpPort), "-rh", host
			});
			arRobot = new ArRobot();
			arRobot.setEncoderTransform(new ArPose(map.getRobotHome().getX(), map.getRobotHome().getY(), map.getRobotHome().getAngle()));
			sonar = new ArSonarDevice();

			if (!Aria.parseArgs()) {
				throw new ArExceptionParseArgs(Translate.get("ERROR_PARSEARGS"));
			}

			if (!conn.connectRobot(arRobot)) {
				throw new ArException(Translate.get("INFO_UNSUCCESSFULCONN"));
			}

			arRobot.addRangeDevice(sonar);
			rangeSonar = arRobot.findRangeDevice("sonar");
			arRobot.enableMotors();
			arRobot.runAsync(true);
			run = true;
			thread.start();
		}
	}

	public void stop() {
		run = false;
		try {
			if (isAlive()) {
				long antes = System.currentTimeMillis();
				System.out.println("Antes: " + antes);
				thread.join(1000);
				Thread.sleep(1000);
				long despues = System.currentTimeMillis();
				System.out.println("Despues: " + despues);
				System.out.println("Total: " + ((despues - antes) / 1000));
				arRobot.stopRunning(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		if (anRobot == null) {
			anRobot = new AnRobot(map, arRobot.getPose().getX(), arRobot.getPose().getY(), arRobot.getPose().getTh());
			anRobot = new AnRobot(map, 0, 0, 0);
			animator.addAnimated(anRobot);
		}

		while (run) {

			arRobot.lock();
			anRobot.updateAnimatedPosition(arRobot.getPose().getX(), arRobot.getPose().getY(), arRobot.getPose().getTh());
			arRobot.unlock();

			behavior();
		}
	}

	public abstract void behavior();

}
