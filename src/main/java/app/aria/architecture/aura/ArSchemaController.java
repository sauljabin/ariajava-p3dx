/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.aria.architecture.aura;

import app.aria.robot.ArRobotMobile;
import app.path.geometry.Point;
import com.mobilerobots.Aria.ArUtil;

public class ArSchemaController {

    private ArRobotMobile robot;

    public ArSchemaController(ArRobotMobile robot) {
        this.robot = robot;
    }

    private void lock() {
        robot.lock();
    }

    private void unlock() {
        robot.unlock();
    }

    public void turn(double angle) {
        lock();
        robot.setDeltaHeading(angle);
        unlock();
    }

    public void move(double speed) {
        lock();
        robot.setVel(speed);
        unlock();
    }

    public void stop() {
        lock();
        robot.setVel(0);
        unlock();
    }

    public Point getPosition() {
        lock();
        Point point = new Point(robot.getX(), robot.getY(), "");
        unlock();
        return point;
    }

    public double getAngle() {
        lock();
        double angle = robot.getTh();
        unlock();
        return angle;
    }

    public void sleep(int sleepTime) {
        ArUtil.sleep(sleepTime);
    }

    public double rangeObstacle(double startAngle, double endAngle) {
        lock();
        double range = robot.getRangeSonar().currentReadingPolar(startAngle, endAngle);
        unlock();
        return range;
    }

    public int getMaxRangeSonar() {
        return (int) robot.getSonar().getMaxRange();
    }

    public boolean detectObstacle(int distance, double angle) {
        lock();
        double range = robot.getRangeSonar().currentReadingPolar(-angle, angle);
        unlock();
        return range < distance;
    }

    public ArRobotMobile getRobot() {
        return robot;
    }
}
