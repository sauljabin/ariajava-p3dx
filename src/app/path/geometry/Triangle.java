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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;

import app.animation.Animated;
import app.animation.AnimatedMouseListener;
import app.map.Map;
import app.path.graphs.components.Link;

public class Triangle implements Animated {

	private ArrayList<Point> vertexes;
	private Map map;

	public Triangle(Point pointA, Point pointB, Point pointC, Map map) {
		super();
		vertexes = new ArrayList<Point>();
		vertexes.add(pointA);
		vertexes.add(pointB);
		vertexes.add(pointC);
		this.map = map;
		System.out.println(pointA.toString());
		System.out.println(pointB.toString());
		System.out.println(pointC.toString());
	}

	public ArrayList<Point> getVertexes() {
		return vertexes;
	}

	public boolean isAdjacent(Triangle triangle) {
		int count = 0;
		for (Point vertex1 : getVertexes())
			for (Point vertex2 : triangle.getVertexes())
				if (vertex1.equals(vertex2))
					count++;
		return (count == 2);
	}

	public Circle getCircle() {
		Line line1 = new Line(vertexes.get(0), vertexes.get(1));
		Line line2 = new Line(vertexes.get(1), vertexes.get(2));
		Point point1 = vertexes.get(0).midpoint(vertexes.get(1));
		Point point2 = vertexes.get(1).midpoint(vertexes.get(2));
		Line perpendicularLine1 = new Line(line1.slopePerpendicular(), point1);
		Line perpendicularLine2 = new Line(line2.slopePerpendicular(), point2);
		Point intersectionPoint = perpendicularLine1
				.calculateIntersectionPoint(perpendicularLine2);
		double radius = vertexes.get(0).distance(intersectionPoint);
		return new Circle(intersectionPoint, radius);
	}

	public ArrayList<Link> calculateSegmentDirection() {
		Point center = getCircle().getCenter();
		Point point1 = vertexes.get(0).midpoint(vertexes.get(1));
		Point point2 = vertexes.get(1).midpoint(vertexes.get(2));
		Point point3 = vertexes.get(2).midpoint(vertexes.get(0));
		ArrayList<Link> links = new ArrayList<Link>();
		links.add(new Link(point1, center));
		links.add(new Link(point2, center));
		links.add(new Link(point3, center));
		return links;
	}

	public ArrayList<Point> verifyMaxDistance(Double maxDistance) {
		ArrayList<Point> points = new ArrayList<Point>();
		if (vertexes.get(0).distance(vertexes.get(1)) > maxDistance
				|| vertexes.get(1).distance(vertexes.get(2)) > maxDistance
				|| vertexes.get(2).distance(vertexes.get(0)) > maxDistance) {
			points.add(vertexes.get(0).midpoint(vertexes.get(1)));
			points.add(vertexes.get(1).midpoint(vertexes.get(2)));
			points.add(vertexes.get(2).midpoint(vertexes.get(0)));
		}
		return points;
	}

	@Override
	public String toString() {
		return ("T: {" + vertexes.get(0).toString() + " / "
				+ vertexes.get(1).toString() + " / "
				+ vertexes.get(2).toString() + "}");
	}

	@Override
	public void initAnimated() {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(new Color(0, 255, 0, 100));
		g.drawLine(map.canvasX(vertexes.get(0).getX()),
				map.canvasY(vertexes.get(0).getY()),
				map.canvasX(vertexes.get(1).getX()),
				map.canvasY(vertexes.get(1).getY()));
		g.drawLine(map.canvasX(vertexes.get(2).getX()),
				map.canvasY(vertexes.get(2).getY()),
				map.canvasX(vertexes.get(1).getX()),
				map.canvasY(vertexes.get(1).getY()));
		g.drawLine(map.canvasX(vertexes.get(0).getX()),
				map.canvasY(vertexes.get(0).getY()),
				map.canvasX(vertexes.get(2).getX()),
				map.canvasY(vertexes.get(2).getY()));
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AnimatedMouseListener getAnimatedMouseListener() {
		// TODO Auto-generated method stub
		return null;
	}

}
