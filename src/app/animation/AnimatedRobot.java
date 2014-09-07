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

package app.animation;

import java.awt.Color;
import java.awt.Graphics2D;

import app.map.Map;

public class AnimatedRobot implements Animated {

	public static final int LONG = 455;
	public static final int WIDTH = 381;
	private boolean visible;
	private Map map;
	private double animatedX;
	private double animatedY;
	private double animatedAngle;
	private int[] xRobot;
	private int[] yRobot;

	public AnimatedRobot(Map map) {
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
		int widthRobot = AnimatedRobot.WIDTH;
		int longRobot = AnimatedRobot.LONG;
		int robotHomeX = map.canvasX(animatedX - longRobot / 2);
		int robotHomeY = map.canvasY(animatedY + widthRobot / 2);
		int corner = map.proportionalValue(50);
		double angle = animatedAngle;
		g.rotate(-Math.toRadians(angle), map.canvasX(animatedX), map.canvasY(animatedY));

		xRobot = new int[] {
				robotHomeX + corner, robotHomeX + map.proportionalValue(longRobot * 2 / 3), robotHomeX + map.proportionalValue(longRobot), robotHomeX + map.proportionalValue(longRobot * 2 / 3), robotHomeX + corner, robotHomeX, robotHomeX, robotHomeX + corner
		};

		yRobot = new int[] {
				robotHomeY, robotHomeY, robotHomeY + map.proportionalValue(widthRobot / 2), robotHomeY + map.proportionalValue(widthRobot), robotHomeY + map.proportionalValue(widthRobot), robotHomeY + map.proportionalValue(widthRobot) - corner, robotHomeY + corner, robotHomeY
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
	public int getZIndex() {
		return 100;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public void initAnimated() {
		visible = true;
	}

}
