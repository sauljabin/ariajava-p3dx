/**
 * 
 * Line.java
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

public class Line {

	private Double slope;
	private Double referencePoint;
	private boolean isVertical;

	public Line(Double slope, Point point) {
		super();
		this.slope = slope;
		if (slope == null) {
			isVertical = true;
			this.referencePoint = point.getX();
		} else {
			isVertical = false;
			this.referencePoint = (slope * point.getX()) - point.getY();
		}
	}

	public Line(Point pointA, Point pointB) {
		super();
		if (pointA.getX() == pointB.getX()) {
			this.slope = null;
			isVertical = true;
			this.referencePoint = pointA.getX();
		} else {
			this.slope = (pointB.getY() - pointA.getY())
					/ (pointB.getX() - pointA.getX());
			this.referencePoint = (slope * pointA.getX()) - pointA.getY();
			isVertical = false;
		}
	}

	public Double getSlope() {
		return slope;
	}

	public Double getReferencePoint() {
		return referencePoint;
	}

	public Point calculateIntersectionPoint(Line line) {
		if (getSlope() == line.getSlope())
			return null;
		double x;
		double y;
		if (isVertical) {
			y = (line.getSlope() * getReferencePoint())
					- line.getReferencePoint();
			return new Point(getReferencePoint(), y, "");
		}
		if (line.isVertical) {
			y = (getSlope() * line.getReferencePoint()) - getReferencePoint();
			return new Point(line.getReferencePoint(), y, "");
		}
		x = (line.getReferencePoint() - getReferencePoint())
				/ (line.getSlope() - getSlope());
		y = (getSlope() * x) - getReferencePoint();
		return new Point(x, y, "");
	}

	public Double slopePerpendicular() {
		if (isVertical)
			return 0.0;
		if (getSlope() == 0)
			return null;
		return ((-1.0) / getSlope());
	}
}
