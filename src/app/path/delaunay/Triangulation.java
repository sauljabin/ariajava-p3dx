package app.path.delaunay;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import app.map.Map;
import app.path.delaunay.convex.ConvexHull;
import app.path.delaunay.convex.Polygon;
import app.path.delaunay.convex.Vertex;
import app.path.geometry.Circle;
import app.path.geometry.Line;
import app.path.geometry.Point;
import app.path.geometry.Triangle;

public class Triangulation {

	private ArrayList<Triangle> triangles;

	public Triangulation(Map map) {
		super();
		// bruteForceAlgorithm(inicialPoints, map);
		convexHullAlgorithm(map);
	}

	public ArrayList<Triangle> getTriangles() {
		return triangles;
	}

	@SuppressWarnings("unused")
	private void bruteForceAlgorithm(Map map) {
		ArrayList<Point> unorderedPoints = new ArrayList<Point>();
		unorderedPoints.addAll(map.getPoints());
		TreeSet<Point> setOrderedPoints = new TreeSet<Point>(
				new Comparator<Point>() {
					@Override
					public int compare(Point point1, Point point2) {
						if (point1.getX() < point2.getX())
							return -1;
						if (point2.getX() < point1.getX())
							return 1;
						if (point1.getY() < point2.getY())
							return -1;
						if (point2.getY() < point1.getY())
							return 1;
						return 0;
					}
				});
		setOrderedPoints.addAll(unorderedPoints);
		ArrayList<Point> newPoints = new ArrayList<Point>();
		setOrderedPoints.addAll(newPoints);
		newPoints = new ArrayList<Point>();
		ArrayList<Point> orderedPoints = new ArrayList<Point>();
		orderedPoints.addAll(setOrderedPoints);
		this.triangles = new ArrayList<Triangle>();
		for (int i = 0; i < orderedPoints.size(); i++) {
			for (int j = i + 1; j < orderedPoints.size(); j++) {
				for (int k = j + 1; k < orderedPoints.size(); k++) {
					Line line1 = new Line(orderedPoints.get(i),
							orderedPoints.get(j));
					Line line2 = new Line(orderedPoints.get(i),
							orderedPoints.get(k));
					String slope1 = line1.getSlope() == null ? "null" : String
							.format("%.9f", line1.getSlope());
					String slope2 = line2.getSlope() == null ? "null" : String
							.format("%.9f", line2.getSlope());
					if ((orderedPoints.get(i).getX() != orderedPoints.get(j)
							.getX() || orderedPoints.get(i).getX() != orderedPoints
							.get(k).getX())
							&& (orderedPoints.get(i).getY() != orderedPoints
									.get(j).getY() || orderedPoints.get(i)
									.getY() != orderedPoints.get(k).getY())
							&& (!slope1.equals(slope2))) {
						Triangle t = new Triangle(orderedPoints.get(i),
								orderedPoints.get(j), orderedPoints.get(k), map);
						Circle c = t.getCircle();
						boolean accept = true;
						for (Point point : orderedPoints)
							if (!point.equals(orderedPoints.get(i))
									&& !point.equals(orderedPoints.get(j))
									&& !point.equals(orderedPoints.get(k))
									&& c.isInternalPoint(point)) {
								accept = false;
								break;
							}
						if (accept) {
							triangles.add(t);
						}
					}
				} // FOR k
			} // FOR j
		} // FOR i
	}

	private void convexHullAlgorithm(Map map) {
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		for (Point point : map.getPoints()) {
			vertices.add(new Vertex(point.getX(), point.getY(), (Math.pow(
					point.getX(), 2) + Math.pow(point.getY(), 2))));
		}
		this.triangles = new ArrayList<Triangle>();
		try {
			ConvexHull hull = new ConvexHull(vertices);
			for (Polygon p : hull.getFaces()) {
				Point p1 = new Point(p.getVertexes().get(0).getCoords()[0], p
						.getVertexes().get(0).getCoords()[1], "");
				Point p2 = new Point(p.getVertexes().get(1).getCoords()[0], p
						.getVertexes().get(1).getCoords()[1], "");
				Point p3 = new Point(p.getVertexes().get(2).getCoords()[0], p
						.getVertexes().get(2).getCoords()[1], "");
				if (!(p1.equals(p2) || p1.equals(p3) || p2.equals(p3))
						&& !(p1.getX() == p2.getX() && p1.getX() == p3.getX())
						&& !(p1.getY() == p2.getY() && p1.getY() == p3.getY())) {
					Line line1 = new Line(p1, p2);
					Line line2 = new Line(p1, p3);
					String slope1 = line1.getSlope() == null ? "null" : String
							.format("%.9f", line1.getSlope());
					String slope2 = line2.getSlope() == null ? "null" : String
							.format("%.9f", line2.getSlope());
					if (!slope1.equals(slope2))
						triangles.add(new Triangle(p1, p2, p3, map));
				}
			}
			for (Triangle t : triangles)
				System.out.println(t.toString());
		} catch (Exception e) {
			System.out.println("CATCH");
			e.printStackTrace();
		}
	}
}
