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

import java.awt.Color;
import java.awt.Graphics2D;

import app.Translate;
import app.aria.exception.ArException;
import app.aria.exception.ArExceptionParseArgs;
import app.gui.animation.Animated;
import app.map.Map;
import app.map.RobotHome;

import com.mobilerobots.Aria.ArPose;
import com.mobilerobots.Aria.ArRangeDevice;
import com.mobilerobots.Aria.ArRobot;
import com.mobilerobots.Aria.ArSimpleConnector;
import com.mobilerobots.Aria.ArSonarDevice;
import com.mobilerobots.Aria.Aria;

public class ArRobotMobile extends ArRobot implements Animated, Runnable {

	public static final int LONG = 455;
	public static final int WIDTH = 381;
	private boolean visible;
	private ArArchitecture arch;
	private Thread thread;
	protected ArSonarDevice sonar;
	protected ArRangeDevice rangeSonar;
	private ArSimpleConnector conn;
	private boolean run;
	private Map map;
	private String host;
	private int tcpPort;
	private Thread threadUpdateAnimatedPosition;
	private int updateAnimatedPositionRate;
	private double animatedX;
	private double animatedY;
	private double animatedAngle;
	private int[] xRobot;
	private int[] yRobot;

	public int getUpdateAnimatedPositionRate() {
		return updateAnimatedPositionRate;
	}

	public void setUpdateAnimatedPositionRate(int updateAnimatedPositionRate) {
		this.updateAnimatedPositionRate = updateAnimatedPositionRate;
	}

	public void updateAnimatedPosition() {
		lock();
		animatedX = getX();
		animatedY = getY();
		animatedAngle = getTh();
		unlock();
	}

	public double getAnimatedX() {
		return animatedX;
	}

	public void setAnimatedX(double animatedX) {
		this.animatedX = animatedX;
	}

	public double getAnimatedY() {
		return animatedY;
	}

	public void setAnimatedY(double animatedY) {
		this.animatedY = animatedY;
	}

	public double getAnimatedAngle() {
		return animatedAngle;
	}

	public void setAnimatedAngle(double animatedAngle) {
		this.animatedAngle = animatedAngle;
	}

	public ArRangeDevice getRangeSonar() {
		return rangeSonar;
	}

	public void setRangeSonar(ArRangeDevice rangeSonar) {
		this.rangeSonar = rangeSonar;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
		lock();
		setEncoderTransform(new ArPose(map.getRobotHome().getX(), map.getRobotHome().getY(), map.getRobotHome().getAngle()));
		unlock();
		updateAnimatedPosition();
	}

	public ArRobotMobile(String host, int tcpPort) {
		this.host = host;
		this.tcpPort = tcpPort;
		updateAnimatedPositionRate = 2;
		visible = true;
		Map map = new Map();
		map.setRobotHome(new RobotHome(0, 0, 0));
		setMap(map);
	}

	public int getTcpPort() {
		return tcpPort;
	}

	public void setTcpPort(int tcpPort) {
		this.tcpPort = tcpPort;
	}

	public String getHost() {
		return host;
	}

	public ArArchitecture getArch() {
		return arch;
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.RED);
		int widthRobot = ArRobotMobile.WIDTH;
		int longRobot = ArRobotMobile.LONG;
		int robotHomeX = map.canvasX(animatedX - longRobot / 2);
		int robotHomeY = map.canvasY(animatedY + widthRobot / 2);
		int corner = map.proportionalValue(50);
		double angle = animatedAngle;
		g.rotate(-Math.toRadians(angle), map.canvasX(animatedX), map.canvasY(animatedY));

		xRobot = new int[] {
				robotHomeX + corner
				, robotHomeX + map.proportionalValue(longRobot * 2 / 3)
				, robotHomeX + map.proportionalValue(longRobot)
				, robotHomeX + map.proportionalValue(longRobot * 2 / 3)
				, robotHomeX + corner
				, robotHomeX
				, robotHomeX
				, robotHomeX + corner
		};

		yRobot = new int[] {
				robotHomeY
				, robotHomeY
				, robotHomeY + map.proportionalValue(widthRobot/2 )
				, robotHomeY + map.proportionalValue(widthRobot)
				, robotHomeY + map.proportionalValue(widthRobot)
				, robotHomeY + map.proportionalValue(widthRobot) - corner
				, robotHomeY + corner
				, robotHomeY
		};

		g.fillPolygon(xRobot, yRobot, 8);
		
		g.setColor(Color.BLACK);
		g.drawPolyline(xRobot, yRobot, 8);

		g.rotate(Math.toRadians(angle), map.canvasX(animatedX), map.canvasY(animatedY));
	}

	@Override
	public void animate() {

	}

	@Override
	public int getZIndex() {
		return 100;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	public boolean isAlive() {
		return thread == null ? false : thread.isAlive();
	}

	public void startBehavior(ArArchitecture arch) throws ArException, ArExceptionParseArgs {
		if (isAlive())
			return;

		this.arch = arch;
		arch.setRobot(this);

		Aria.init();
		conn = new ArSimpleConnector(new String[] {
				"-rrtp", String.format("%d", tcpPort), "-rh", host
		});

		thread = new Thread(this);

		sonar = new ArSonarDevice();

		if (!Aria.parseArgs()) {
			throw new ArExceptionParseArgs(Translate.get("ERROR_PARSEARGS"));
		}

		if (!conn.connectRobot(this)) {
			throw new ArException(Translate.get("INFO_UNSUCCESSFULCONN"));
		}

		run = true;
		lock();
		addRangeDevice(sonar);
		rangeSonar = findRangeDevice("sonar");
		runAsync(true);
		enableMotors();
		unlock();
		thread.start();

		threadUpdateAnimatedPosition = new Thread(new Runnable() {

			@Override
			public void run() {
				while (run) {
					updateAnimatedPosition();
					try {
						Thread.sleep(1000 / updateAnimatedPositionRate);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		threadUpdateAnimatedPosition.start();
	}

	public void stop() {
		run = false;
		try {
			if (isAlive()) {
				lock();
				stopRunning();
				disconnect();
				unlock();
				thread.join(1000);
				threadUpdateAnimatedPosition.join(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (run) {
			arch.behavior();
		}
	}

	@Override
	public void initAnimated() {

	}
}
