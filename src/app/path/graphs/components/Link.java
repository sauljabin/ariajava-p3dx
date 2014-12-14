/**
 * 
 * Link.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.path.graphs.components;

import app.path.geometry.Point;

public class Link {

	private Point pointA;
	private Point pointB;
	private String name;

	public Link(Point pointA, Point pointB) {
		this(pointA, pointB, "");
	}

	public Link(Point pointA, Point pointB, String name) {
		super();
		this.pointA = pointA;
		this.pointB = pointB;
		this.name = name;
	}

	public Point getPointA() {
		return pointA;
	}

	public Point getPointB() {
		return pointB;
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
		return ((getPointA().equals(link.pointA) && (getPointB().equals(link.pointB))) || (getPointA().equals(link.pointB) && (getPointB().equals(link.pointA))));
	}

	@Override
	public String toString() {
		return String.format("Link [pointA=%s, pointB=%s, name=%s]", pointA, pointB, name);
	}

}
