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

package app.path.geometry;

public class Point {

	private double x;
	private double y;
	private String name;

	public Point(double x, double y, String name) {
		super();
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public Point midpoint(Point otro) {
		return new Point((otro.getX() + getX()) / 2.0,
				(otro.getY() + getY()) / 2.0, "");
	}

	public double distance(Point otro) {
		double difX = otro.getX() - getX();
		double difY = otro.getY() - getY();
		double sum = Math.pow(difX, 2) + Math.pow(difY, 2);
		return Math.sqrt(sum);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		Point point = (Point) obj;
		return (point.getX() == getX() && point.getY() == getY());
	}

	@Override
	public String toString() {
		return "( " + x + " , " + y + " )";
	}

}
