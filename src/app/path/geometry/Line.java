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
