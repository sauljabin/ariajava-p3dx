/**
 * 
 * RobotInfoPanel.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.animation;

import app.Translate;
import app.aria.robot.ArRobotMobile;

public class RobotInfoPanel extends InfoPanel {

	private ArRobotMobile robot;

	public RobotInfoPanel(ArRobotMobile robot) {
		this.robot = robot;
	}

	@Override
	public void initInfoPanel() {

	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public void printInfo() {
		if (robot == null)
			return;
		printLine(String.format("%s: %.2f",Translate.get("GUI_X"), robot.getRelativeX()));
		printLine(String.format("%s: %.2f",Translate.get("GUI_Y"), robot.getRelativeY()));
		printLine(String.format("%s: %.2f",Translate.get("GUI_ANGLE"), robot.getRelativeAngle()));
	}

}
