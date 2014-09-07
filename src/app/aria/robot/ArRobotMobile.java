package app.aria.robot;

import app.animation.AnimatedRobot;

import com.mobilerobots.Aria.ArPose;
import com.mobilerobots.Aria.ArRobot;
import com.mobilerobots.Aria.ArSonarDevice;

public class ArRobotMobile extends ArRobot {

	private double relativeX;
	private double relativeY;
	private double relativeAngle;
	private double initX;
	private double initY;
	private double initAngle;
	private ArSonarDevice sonar;
	private AnimatedRobot animatedRobot;

	public ArRobotMobile(double initX, double initY, double initAngle) {
		super();
		this.initX = initX;
		this.initY = initY;
		this.initAngle = initAngle;
		setEncoderTransform(new ArPose(initX, initY, initAngle));
		new ArFunctorUpdatePosition(this);
		sonar = new ArSonarDevice();
		addRangeDevice(sonar);
	}

	public ArSonarDevice getSonar() {
		return sonar;
	}

	public void setSonar(ArSonarDevice sonar) {
		this.sonar = sonar;
	}

	public double getInitX() {
		return initX;
	}

	public void setInitX(double initX) {
		this.initX = initX;
	}

	public double getInitY() {
		return initY;
	}

	public void setInitY(double initY) {
		this.initY = initY;
	}

	public double getInitAngle() {
		return initAngle;
	}

	public void setInitAngle(double initAngle) {
		this.initAngle = initAngle;
	}

	public double getRelativeX() {
		return relativeX;
	}

	public void setRelativeX(double relativeX) {
		this.relativeX = relativeX;
	}

	public double getRelativeY() {
		return relativeY;
	}

	public void setRelativeY(double relativeY) {
		this.relativeY = relativeY;
	}

	public double getRelativeAngle() {
		return relativeAngle;
	}

	public void setRelativeAngle(double relativeAngle) {
		this.relativeAngle = relativeAngle;
	}

	public AnimatedRobot getAnimatedRobot() {
		return animatedRobot;
	}

	public void setAnimatedRobot(AnimatedRobot animatedRobot) {
		this.animatedRobot = animatedRobot;
	}

}
