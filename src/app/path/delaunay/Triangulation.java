package app.path.delaunay;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import app.map.Map;
import app.path.geometry.Circle;
import app.path.geometry.Line;
import app.path.geometry.Point;
import app.path.geometry.Triangle;

public class Triangulation {

	private ArrayList<Triangle> triangles;

	public Triangulation(ArrayList<Point> inicialPoints, Map map,
			Double maxDistance) {
		super();
		bruteForceAlgorithm(inicialPoints, map, maxDistance);
		incrementalAlgorithm(inicialPoints, map, maxDistance);
	}

	public ArrayList<Triangle> getTriangles() {
		return triangles;
	}

	private void bruteForceAlgorithm(ArrayList<Point> inicialPoints, Map map,
			Double maxDistance) {
		ArrayList<Point> unorderedPoints = new ArrayList<Point>();
		unorderedPoints.addAll(inicialPoints);
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
			System.out.println(orderedPoints.get(i) + "=====================");
			for (int j = i + 1; j < orderedPoints.size(); j++) {
				System.out.println(orderedPoints.get(j));
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
								orderedPoints.get(j), orderedPoints.get(k),
								map, line1, line2);
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

	private void incrementalAlgorithm(ArrayList<Point> inicialPoints, Map map,
			Double maxDistance) {
		
	}

}
