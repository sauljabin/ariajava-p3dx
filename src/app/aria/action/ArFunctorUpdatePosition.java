package app.aria.action;

import com.mobilerobots.Aria.ArFunctor;
import com.mobilerobots.Aria.ArRobot;

public class ArFunctorUpdatePosition extends ArFunctor {
	private ArRobot myRobot;

	public ArFunctorUpdatePosition(ArRobot robot) {
		myRobot = robot;
		myRobot.addSensorInterpTask("PrintingTask", 50, this);
	}

	public void invoke() {
		System.out.println("Java PrintingTask: x " + (int) myRobot.getX() + " y " + (int) myRobot.getY() + " th " + (int) myRobot.getTh() + " vel " + (int) myRobot.getVel() + " mpacs " + (int) myRobot.getMotorPacCount());
	}
}
