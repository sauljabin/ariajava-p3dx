/**
 * 
 * ArSpatialReasoner.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.aria.architecture.aura;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

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

	public ArrayList<Point> getPath() {
		return path;
	}

	public ArSpatialReasoner(Map map) {
		this.map = map;
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
		return path != null;
	}

	public void addPathToMap() {
		map.setGraph(graph);
		if (path != null) {
			map.setPathPoints(new ArrayList<Point>(path));
		}
	}

	public void addStartGoal(Point start, Point goal, Graph graph) {
		TreeSet<Link> orderedLinksHome = new TreeSet<Link>(new Comparator<Link>() {
			@Override
			public int compare(Link o1, Link o2) {
				if (o1.getWeight() > o2.getWeight())
					return 1;
				if (o2.getWeight() > o1.getWeight())
					return -1;
				return 0;
			}
		});
		TreeSet<Link> orderedLinksTarget = new TreeSet<Link>(new Comparator<Link>() {
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
			orderedLinksHome.add(new Link(point, start, "Start"));
			orderedLinksTarget.add(new Link(point, goal, "Goal"));
		}
		for (int i = 0; i < 5; i++) {
			Link homeLink = orderedLinksHome.pollFirst();
			Link targetLink = orderedLinksTarget.pollFirst();
			graph.getLinks().add(homeLink);
			graph.getLinks().add(targetLink);
		}
	}

}
