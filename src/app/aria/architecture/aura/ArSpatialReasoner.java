/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJava P3DX.
 * 
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.aria.architecture.aura;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import app.map.Line;
import app.map.Map;
import app.path.Dijkstra;
import app.path.delaunay.Triangulation;
import app.path.geometry.Point;
import app.path.graphs.Graph;
import app.path.graphs.components.Link;
import app.path.voronoi.Diagram;

public class ArSpatialReasoner {
	private Triangulation triangulation;
	private Diagram diagram;
	private Dijkstra dijkstra;
	private Map map;
	private Graph graph;
	private ArrayList<Point> path;
	private ArMisionPlanner arMisionPlanner;
	private int numLinksToStartGoal;

	public ArrayList<Point> getPath() {
		return path;
	}

	public ArSpatialReasoner(ArMisionPlanner arMisionPlanner) {
		this.map = arMisionPlanner.getMap();
		this.arMisionPlanner = arMisionPlanner;
		numLinksToStartGoal = 4;
	}

	public ArMisionPlanner getArMisionPlanner() {
		return arMisionPlanner;
	}

	public boolean calculatePath(Point start, Point target) {
		if (start == null || target == null)
			return false;

		triangulation = new Triangulation(map);
		diagram = new Diagram(triangulation.getTriangles(), map);
		graph = diagram.getGraph();
		addStartGoal(start, target, graph);

		dijkstra = new Dijkstra(graph);
		path = dijkstra.searchOptimalPath(start, target);

		map.setGraph(graph);
		boolean pathFound;
		if (pathFound = (path != null)) {
			map.setPathPoints(new ArrayList<Point>(path));
		}

		return pathFound;
	}

	public void addStartGoal(Point start, Point goal, Graph graph) {
		TreeSet<Link> orderedLinksStart = new TreeSet<Link>(new Comparator<Link>() {
			@Override
			public int compare(Link o1, Link o2) {
				if (o1.getWeight() > o2.getWeight())
					return 1;
				if (o2.getWeight() > o1.getWeight())
					return -1;
				return 0;
			}
		});
		TreeSet<Link> orderedLinksGoal = new TreeSet<Link>(new Comparator<Link>() {
			@Override
			public int compare(Link o1, Link o2) {
				if (o1.getWeight() > o2.getWeight())
					return 1;
				if (o2.getWeight() > o1.getWeight())
					return -1;
				return 0;
			}
		});
		for (Point point : graph.getPoints()) {
			orderedLinksStart.add(new Link(point, start, "Start"));
			orderedLinksGoal.add(new Link(point, goal, "Goal"));
		}
		for (int i = 0; i < numLinksToStartGoal; i++) {
			Link startLink = orderedLinksStart.pollFirst();
			Link goalLink = orderedLinksGoal.pollFirst();

			Line2D lineStart = new Line2D.Double(startLink.getPointA().getX(), startLink.getPointA().getY(), startLink.getPointB().getX(), startLink.getPointB().getY());
			Line2D lineGoal = new Line2D.Double(goalLink.getPointA().getX(), goalLink.getPointA().getY(), goalLink.getPointB().getX(), goalLink.getPointB().getY());

			boolean intersectStart = false;
			boolean intersectGoal = false;
			for (Line line : map.getLines()) {
				Line2D line2 = new Line2D.Double(line.getX1(), line.getY1(), line.getX2(), line.getY2());
				if (!intersectStart)
					intersectStart = lineStart.intersectsLine(line2);
				if (!intersectGoal)
					intersectGoal = lineGoal.intersectsLine(line2);
			}

			if (!intersectStart)
				graph.getLinks().add(startLink);
			if (!intersectGoal)
				graph.getLinks().add(goalLink);
		}
	}

}
