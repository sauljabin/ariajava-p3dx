/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.map;

import app.animation.Animated;
import app.animation.AnimatedMouseListener;
import app.aria.robot.ArRobotMobile;

import java.awt.*;

public class Robot implements Animated {

    private boolean visible;
    private Map map;
    private double animatedX;
    private double animatedY;
    private double animatedAngle;
    private int[] xRobot;
    private int[] yRobot;

    public Robot(Map map) {
        this.map = map;
    }

    public void updateAnimatedPosition(double x, double y, double angle) {
        animatedX = x;
        animatedY = y;
        animatedAngle = angle;
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

    public Map getMap() {
        return map;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.RED);
        int widthRobot = ArRobotMobile.WIDTH;
        int longRobot = ArRobotMobile.LONG;
        int startX = map.canvasX(animatedX - longRobot / 2.);
        int startY = map.canvasY(animatedY + widthRobot / 2.);
        int corner = map.proportionalValue(50);
        double angle = animatedAngle;
        g.rotate(-Math.toRadians(angle), map.canvasX(animatedX), map.canvasY(animatedY));

        xRobot = new int[]{
                startX + corner, startX + map.proportionalValue(longRobot * 2. / 3.), startX + map.proportionalValue(longRobot), startX + map.proportionalValue(longRobot * 2. / 3.), startX + corner, startX, startX, startX + corner
        };

        yRobot = new int[]{
                startY, startY, startY + map.proportionalValue(widthRobot / 2.), startY + map.proportionalValue(widthRobot), startY + map.proportionalValue(widthRobot), startY + map.proportionalValue(widthRobot) - corner, startY + corner, startY
        };

        g.fillPolygon(xRobot, yRobot, 8);

        g.setColor(Color.BLACK);
        g.drawPolyline(xRobot, yRobot, 8);

        g.rotate(Math.toRadians(angle), map.canvasX(animatedX), map.canvasY(animatedY));
    }

    @Override
    public void animate() {

    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void initAnimated() {
        visible = true;
    }

    @Override
    public Shape getShape() {
        return null;
    }

    @Override
    public int getZ() {
        return 150;
    }

    @Override
    public AnimatedMouseListener getAnimatedMouseListener() {
        return null;
    }

}
