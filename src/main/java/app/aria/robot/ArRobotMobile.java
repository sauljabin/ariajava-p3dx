/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.aria.robot;

import app.map.Robot;
import com.mobilerobots.Aria.ArPose;
import com.mobilerobots.Aria.ArRangeDevice;
import com.mobilerobots.Aria.ArRobot;
import com.mobilerobots.Aria.ArSonarDevice;

public class ArRobotMobile extends ArRobot {

    public static final int LONG = 455;
    public static final int WIDTH = 381;
    protected ArRangeDevice rangeSonar;
    private double relativeX;
    private double relativeY;
    private double relativeAngle;
    private double initX;
    private double initY;
    private double initAngle;
    private ArSonarDevice sonar;
    private Robot animatedRobot;
    private boolean completePath;

    public ArRobotMobile(double initX, double initY, double initAngle) {
        this.initX = initX;
        this.initY = initY;
        this.initAngle = initAngle;
        setEncoderTransform(new ArPose(initX, initY, initAngle));
        sonar = new ArSonarDevice();
        addRangeDevice(sonar);
        rangeSonar = findRangeDevice("sonar");
        completePath = false;
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

    public Robot getAnimatedRobot() {
        return animatedRobot;
    }

    public void setAnimatedRobot(Robot animatedRobot) {
        this.animatedRobot = animatedRobot;
    }

    public ArRangeDevice getRangeSonar() {
        return rangeSonar;
    }

    public void setRangeSonar(ArRangeDevice rangeSonar) {
        this.rangeSonar = rangeSonar;
    }

    public boolean isCompletePath() {
        return completePath;
    }

    public void setCompletePath(boolean completePath) {
        this.completePath = completePath;
    }

}
