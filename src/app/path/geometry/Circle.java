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

public class Circle {

	private Point center;
	private Double radius;

	public Circle(Point center, Double radius) {
		super();
		this.center = center;
		this.radius = radius;
	}

	public Point getCenter() {
		return center;
	}

	public Double getRadius() {
		return radius;
	}

	public boolean isInternalPoint(Point point) {
		Double vX = Math.pow(point.getX() - center.getX(), 2.0);
		Double vY = Math.pow(point.getY() - center.getY(), 2.0);
		Double vR = Math.pow(radius, 2.0);
		return (vX + vY < vR);
	}

}
