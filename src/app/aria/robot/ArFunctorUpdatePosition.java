package app.aria.robot;

import app.Log;

import com.mobilerobots.Aria.ArFunctor;

public class ArFunctorUpdatePosition extends ArFunctor {
	private ArRobotMobile robot;
	private double x;
	private double y;
	private double angle;

	public ArFunctorUpdatePosition(ArRobotMobile robot) {
		this.robot = robot;
		robot.addSensorInterpTask("ArFunctorUpdatePosition", 50, this);
	}

	public void invoke() {
		x = robot.getX();
		y = robot.getY();
		angle = robot.getTh();
		robot.setRelativeAngle(angle);
		robot.setRelativeX(x);
		robot.setRelativeY(y);
		if (robot.getAnimatedRobot() != null)
			robot.getAnimatedRobot().updateAnimatedPosition(x, y, angle);
		Log.devel(getClass(), String.format("ArFunctorUpdatePosition: x=%f; y=%f; angle=%f", x, y, angle));
	}
}
