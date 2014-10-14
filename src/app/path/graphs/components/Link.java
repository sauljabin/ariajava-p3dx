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

package app.path.graphs.components;

import app.path.geometry.Point;

public class Link {

	private Point pointA;
	private Point pointB;
	private Double pheromone;
	private String name;

	public Link(Point pointA, Point pointB) {
		this(pointA, pointB, "");
	}

	public Link(Point pointA, Point pointB, String name) {
		super();
		this.pointA = pointA;
		this.pointB = pointB;
		this.pheromone = 100.0;
		this.name = name;
	}

	public Point getPointA() {
		return pointA;
	}

	public Point getPointB() {
		return pointB;
	}

	public Double getPheromone() {
		return pheromone;
	}

	public String getName() {
		return name;
	}

	public double getWeight() {
		return this.pointA.distance(this.pointB);
	}

	@Override
	public boolean equals(Object obj) {
		Link link = (Link) obj;
		return ((getPointA().equals(link.pointA) && (getPointB()
				.equals(link.pointB))) || (getPointA().equals(link.pointB) && (getPointB()
				.equals(link.pointA))));
	}

	public void updatePheromone(Double value) {
		pheromone *= value;
	}

	@Override
	public String toString() {
		return "[ " + pointA.getName() + " / " + pointB.getName() + " ]";
	}

}
