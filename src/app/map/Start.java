/**
 * 
 * Start.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import app.animation.Animated;
import app.animation.AnimatedMouseListener;
import app.aria.robot.ArRobotMobile;
import app.path.geometry.Point;

public class Start implements Animated, AnimatedMouseListener {

	private int x;
	private int y;
	private double angle;
	private boolean visible;
	private Map map;
	private int widthRobot;
	private int longRobot;
	private int startX;
	private int startY;
	private Color color;
	private boolean mouseOver;

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public Start() {
		this(0, 0, 0);
	}

	public Start(int x, int y, double angle) {
		this(null, x, y, angle);
	}

	public Start(Map map, int x, int y, double angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		visible = true;
		this.map = map;
	}

	@Override
	public String toString() {
		return String.format("Start [x=%s, y=%s, angle=%s]", x, y, angle);
	}

	@Override
	public void initAnimated() {
		widthRobot = ArRobotMobile.WIDTH;
		longRobot = ArRobotMobile.LONG;
		color = Color.BLUE;
	}

	@Override
	public void paint(Graphics2D g) {
		startX = map.canvasX(getX() - longRobot / 2);
		startY = map.canvasY(getY() + widthRobot / 2);
		g.setColor(color);
		g.rotate(-Math.toRadians(getAngle()), map.canvasX(getX()), map.canvasY(getY()));
		if (mouseOver)
			g.fillRect(startX, startY, map.proportionalValue(longRobot), map.proportionalValue(widthRobot));
		g.drawRect(startX, startY, map.proportionalValue(longRobot), map.proportionalValue(widthRobot));
		g.rotate(Math.toRadians(getAngle()), map.canvasX(getX()), map.canvasY(getY()));
	}

	@Override
	public void animate() {

	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public Shape getShape() {
		return new Rectangle2D.Double(startX, startY, map.proportionalValue(longRobot), map.proportionalValue(widthRobot));
	}

	@Override
	public int getZ() {
		return 100;
	}

	@Override
	public AnimatedMouseListener getAnimatedMouseListener() {
		return this;
	}

	@Override
	public void mousePressed() {

	}

	@Override
	public void mouseDragged(int x, int y) {
		setTranslate(x, y);
	}

	@Override
	public void mouseEntered() {
		mouseOver = true;
	}

	@Override
	public void mouseExited() {
		mouseOver = false;
	}

	public void setTranslate(double x, double y) {
		this.x += x * map.getProportion();
		this.y -= y * map.getProportion();
	}

	public Point toPoint() {
		return new Point(x, y, "Start");
	}
}