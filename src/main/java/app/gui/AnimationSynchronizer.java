/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.gui;

import app.aria.robot.ArRobotMobile;

public class AnimationSynchronizer implements Runnable {

    private int updateAnimatedPositionRate;
    private Thread thread;
    private boolean run;
    private double animatedX;
    private double animatedY;
    private double animatedAngle;
    private ArRobotMobile robot;
    private ControllerViewApp controllerViewApp;

    public AnimationSynchronizer(ControllerViewApp controllerViewApp, ArRobotMobile robot, int updateAnimatedPositionRate) {
        this.robot = robot;
        this.updateAnimatedPositionRate = updateAnimatedPositionRate;
        this.controllerViewApp = controllerViewApp;
        updateAnimatedPosition();
    }

    private synchronized void updateAnimatedPosition() {
        robot.lock();
        updateAnimatedPosition(robot.getX(), robot.getY(), robot.getTh());
        robot.unlock();
    }

    private synchronized boolean isConnect() {
        boolean connect;
        robot.lock();
        connect = robot.isConnected();
        robot.unlock();
        return connect;
    }

    public void updateAnimatedPosition(double x, double y, double angle) {
        animatedX = x;
        animatedY = y;
        animatedAngle = angle;
        robot.setRelativeAngle(angle);
        robot.setRelativeX(x);
        robot.setRelativeY(y);
        if (robot.getAnimatedRobot() != null)
            robot.getAnimatedRobot().updateAnimatedPosition(x, y, angle);
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

    public int getUpdateAnimatedPositionRate() {
        return updateAnimatedPositionRate;
    }

    public synchronized void setUpdateAnimatedPositionRate(int updateAnimatedPositionRate) {
        this.updateAnimatedPositionRate = updateAnimatedPositionRate;
    }

    public boolean isAlive() {
        return thread == null ? false : thread.isAlive();
    }

    public void stop() {
        run = false;
        try {
            if (isAlive()) {
                thread.join(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (run) {
            if (!isConnect()) {
                controllerViewApp.disconnect();
                continue;
            }
            updateAnimatedPosition();
            try {
                Thread.sleep(1000 / updateAnimatedPositionRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        if (!isAlive()) {
            thread = new Thread(this);
            run = true;
            thread.start();
        }
    }

}
