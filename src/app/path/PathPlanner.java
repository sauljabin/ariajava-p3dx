/**
 * 
 * PathPlanner.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.path;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import app.Log;
import app.map.Map;
import app.path.delaunay.Triangulation;
import app.path.geometry.Point;
import app.path.graphs.Graph;
import app.path.graphs.components.Link;
import app.path.voronoi.Diagram;

public class PathPlanner {
	private Diagram diagram;
	private Map map;
	private Graph graph;

	public PathPlanner(Map map) {
		super();
		this.map = map;
		Triangulation triangulation = new Triangulation(map);
		diagram = new Diagram(triangulation.getTriangles(), map);
	}

	public ArrayList<Point> searchOptimalRoute(Point home, Point target) {
		graph = diagram.getGraph();
		addHomeAndTarget(home, target, graph);
		map.setGraph(graph);
		
		Dijkstra algorithm = new Dijkstra(graph);
		ArrayList<Point> path = algorithm.searchOptimalRoute(home, target);

		map.setPathPoints(new ArrayList<Point>());
		map.getPathPoints().addAll(path);

		Log.info(getClass(), String.format("Inicio: %s, Fin: %s", home, target));

		return path;
	}

	public ArrayList<Point> testPath(Point home) {
		ArrayList<Point> lista = new ArrayList<Point>();
		lista.add(home);
		ArrayList<Link> linkes = new ArrayList<Link>();
		Point punto = home;
		for (int i = 0; i < 25; i++) {
			Link link = graph.linkWith(punto).get(0);
			for (int j = 0; j < graph.linkWith(punto).size(); j++) {
				if (linkes.contains(link))
					link = graph.linkWith(punto).get(j);
			}
			linkes.add(link);
			if (link.getPointA().distance(punto) < 0.1) {
				lista.add(link.getPointB());
				punto = link.getPointB();
			} else {
				lista.add(link.getPointA());
				punto = link.getPointA();
			}
		}

		return lista;
	}

	public void addHomeAndTarget(Point home, Point target, Graph graph) {
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
			orderedLinksHome.add(new Link(point, home, "home"));
			orderedLinksTarget.add(new Link(point, target, "target"));
		}
		for (int i = 0; i < 5; i++) {
			Link homeLink = orderedLinksHome.pollFirst();
			Link targetLink = orderedLinksTarget.pollFirst();
			graph.getLinks().add(homeLink);
			graph.getLinks().add(targetLink);
		}
	}
}
