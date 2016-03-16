/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJava P3DX.
 * 
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.animation;

import app.Translate;
import app.aria.robot.ArRobotMobile;

public class RobotInfoPanel extends InfoPanel {

	private ArRobotMobile robot;
	private long initTime;
	private long currentTime;
	private boolean stop;

	public RobotInfoPanel(ArRobotMobile robot) {
		this.robot = robot;
		initTime = System.currentTimeMillis();
		stopInfoPanel();
	}

	@Override
	public void initInfoPanel() {
		stop = false;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public void printInfo() {

		if (robot == null)
			return;
		printLine(String.format("%s: %.2f", Translate.get("GUI_X"), robot.getRelativeX()));
		printLine(String.format("%s: %.2f", Translate.get("GUI_Y"), robot.getRelativeY()));
		printLine(String.format("%s: %.2f", Translate.get("GUI_ANGLE"), robot.getRelativeAngle()));

		if (!stop && robot.isCompletePath())
			stopInfoPanel();

		if (!stop)
			currentTime = System.currentTimeMillis();

		printLine(String.format("%s: %.2f seg", Translate.get("GUI_TIME"), (currentTime - initTime) / 1000.));
	}

	@Override
	public void stopInfoPanel() {
		stop = true;
		currentTime = System.currentTimeMillis();
	}

}
