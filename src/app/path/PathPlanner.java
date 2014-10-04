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

package app.path;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import app.map.Map;
import app.path.antColony.Algorithm;
import app.path.delaunay.Triangulation;
import app.path.geometry.Point;
import app.path.graphs.Graph;
import app.path.graphs.components.Link;
import app.path.voronoi.Diagram;

public class PathPlanner {

	private Algorithm antAlgorithm;
	private Diagram diagram;
	private Map map;

	public PathPlanner(Map map) {
		super();
		this.map = map;
		Triangulation triangulation = new Triangulation(map);
		diagram = new Diagram(triangulation.getTriangles(), map);
	}

	public ArrayList<Point> searchOptimalRoute(Point home, Point target) {
		Graph graph = diagram.getGraph();
		addHomeAndTarget(home, target, graph);
		map.setGraph(graph);

		antAlgorithm = new Algorithm(graph, 20);
		return antAlgorithm.searchOptimalRoute(home, target);
	}

	public void addHomeAndTarget(Point home, Point target, Graph graph) {
		TreeSet<Link> orderedLinksHome = new TreeSet<Link>(
				new Comparator<Link>() {
					@Override
					public int compare(Link o1, Link o2) {
						if (o1.getWeight() > o2.getWeight())
							return -1;
						if (o2.getWeight() > o1.getWeight())
							return 1;
						return 0;
					}
				});
		TreeSet<Link> orderedLinksTarget = new TreeSet<Link>(
				new Comparator<Link>() {
					@Override
					public int compare(Link o1, Link o2) {
						if (o1.getWeight() > o2.getWeight())
							return -1;
						if (o2.getWeight() > o1.getWeight())
							return 1;
						return 0;
					}
				});
		for (Point point : graph.getPoints()) {
			orderedLinksHome.add(new Link(point, home, "home"));
			orderedLinksTarget.add(new Link(point, target, "target"));
		}
		for (int i = 0; i < 5; i++) {
			Link homeLink = orderedLinksHome.pollFirst();
			System.out.println(homeLink);
			Link targetLink = orderedLinksTarget.pollFirst();
			System.out.println(targetLink);
		}
	}
}
