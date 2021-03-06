/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.aria.architecture.reactive;

import app.aria.architecture.ArArchitecture;
import app.aria.robot.ArRobotMobile;
import com.mobilerobots.Aria.ArUtil;

public class ArArchitectureReactive extends ArArchitecture {

    private double angle;
    private double stopDistance;
    private double turnAngle;
    private double speed;
    private boolean turnLeft;
    private boolean turnRight;
    private int sleep;

    public ArArchitectureReactive(ArRobotMobile robot) {
        super("Reactive", robot);
        angle = 45;
        stopDistance = 500;
        turnAngle = 20;
        speed = 200;
        turnLeft = false;
        turnRight = false;
        sleep = 200;
    }

    @Override
    public void behavior() {
        getRobot().lock();
        double range = getRobot().getRangeSonar().currentReadingPolar(-angle, angle);
        getRobot().unlock();
        if (range <= stopDistance) {
            getRobot().lock();
            getRobot().setVel(0);
            getRobot().unlock();

            if (getRobot().getVel() > 0)
                ArUtil.sleep(sleep);

            getRobot().lock();
            double leftRange = getRobot().getRangeSonar().currentReadingPolar(0, angle);
            double rightRange = getRobot().getRangeSonar().currentReadingPolar(-angle, 0);
            getRobot().unlock();

            if (!turnRight && !turnLeft) {
                if (leftRange <= rightRange) {
                    turnLeft = true;
                } else {
                    turnRight = true;
                }
            }

            getRobot().lock();
            if (turnLeft) {
                getRobot().setDeltaHeading(-turnAngle);
            } else if (turnRight) {
                getRobot().setDeltaHeading(turnAngle);
            }
            getRobot().unlock();

        } else {
            if (getRobot().getVel() == 0)
                ArUtil.sleep(sleep);
            getRobot().lock();
            getRobot().setVel(speed);
            getRobot().unlock();
            turnLeft = false;
            turnRight = false;
        }
    }

    @Override
    public void init() {

    }

}
