/**
 * 
 * Point.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.path.geometry;

public class Point {

	private double x;
	private double y;
	private String name;
	private Point ancestor;
	private boolean marked;
	private double cumulativeWeight;

	public Point getAncestor() {
		return ancestor;
	}

	public void setAncestor(Point ancestor) {
		this.ancestor = ancestor;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	public double getCumulativeWeight() {
		return cumulativeWeight;
	}

	public void setCumulativeWeight(double cumulativeWeight) {
		this.cumulativeWeight = cumulativeWeight;
	}

	public Point(double x, double y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public Point midPoint(Point point) {
		return new Point((point.getX() + getX()) / 2.0, (point.getY() + getY()) / 2.0, "");
	}

	public double distance(Point point) {
		double difX = point.getX() - getX();
		double difY = point.getY() - getY();
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
		return String.format("Point [x=%s, y=%s, name=%s, cumulativeWeight=%s, ancestor=(%s)]", x, y, name, cumulativeWeight, ancestor);
	}

}
