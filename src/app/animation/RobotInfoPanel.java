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
		printLine(String.format("%s: %.0f",Translate.get("GUI_X"), robot.getRelativeX()));
		printLine(String.format("%s: %.0f",Translate.get("GUI_Y"), robot.getRelativeY()));
		printLine(String.format("%s: %.0f",Translate.get("GUI_ANGLE"), robot.getRelativeAngle()));
	}

}
