/**
 * 
 * Copyright (c) 2014 Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>
 * 
 * This file is part of AriaJavaP3DX.
 *
 * AriaJavaP3DX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AriaJavaP3DX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AriaJavaP3DX.  If not, see <http://www.gnu.org/licenses/>.
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

public class Goal implements Animated, AnimatedMouseListener {

	private int x;
	private int y;
	private double angle;
	private boolean visible;
	private Map map;
	private boolean mouseOver;
	private int goalX;
	private int goalY;
	private int widthRobot;
	private int longRobot;

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

	public Goal() {
		this(0, 0, 0);
	}

	public Goal(int x, int y, double angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		visible = true;
	}

	@Override
	public String toString() {
		return String.format("Goal [x=%s, y=%s, angle=%s]", x, y, angle);
	}

	@Override
	public void initAnimated() {
		widthRobot = ArRobotMobile.WIDTH;
		longRobot = ArRobotMobile.LONG;
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.RED);		
		goalX = map.canvasX(getX() - longRobot / 2);
		goalY = map.canvasY(getY() + widthRobot / 2);
		g.rotate(-Math.toRadians(getAngle()), map.canvasX(getX()), map.canvasY(getY()));
		if (mouseOver)
			g.fillRect(goalX, goalY, map.proportionalValue(longRobot), map.proportionalValue(widthRobot));
		g.drawRect(goalX, goalY, map.proportionalValue(longRobot), map.proportionalValue(widthRobot));
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
		return new Rectangle2D.Double(goalX, goalY, map.proportionalValue(longRobot), map.proportionalValue(widthRobot));
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

}
