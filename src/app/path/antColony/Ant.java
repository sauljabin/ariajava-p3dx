/**
 * 
 * Ant.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.path.antColony;

import java.util.ArrayList;

import app.path.geometry.Point;
import app.path.graphs.Graph;
import app.path.graphs.components.Link;

public class Ant {

	private int limit;
	private int name;
	private Point origin;
	private Point destination;
	private Point target;
	private double path;
	private boolean finding;
	private boolean initiating;
	private ArrayList<Point> route;
	private ArrayList<ArrayList<Point>> lastRoutes;

	public Ant(int i) {
		route = new ArrayList<Point>();
		lastRoutes = new ArrayList<ArrayList<Point>>();
		finding = true;
		name = i;
		limit = 5;
		initiating = true;
	}

	public void defineTarget(Point home, Point target, Graph graph) {
		this.origin = home;
		this.target = target;
		this.destination = selectDestination(graph, null);
		this.path = origin.distance(destination);
	}

	public void step(Graph graph) {
		if (finding)
			stepForward(graph);
		else
			stepBack(graph);
	}

	public void stepForward(Graph graph) {
		path -= 25;
		if (path <= 0) {
			Point previousPoint = null;
			if (!initiating)
				previousPoint = origin;
			initiating = false;
			route.add(origin);
			origin = destination;
			destination = selectDestination(graph, previousPoint);
			path += origin.distance(destination);
		}
		if (origin.equals(target)) {
			finding = false;
			clearRoute();
			ArrayList<Point> lastRoute = new ArrayList<Point>();
			lastRoute.addAll(route);
			lastRoute.add(origin);
			lastRoutes.add(lastRoute);
			if (lastRoutes.size() > limit)
				lastRoutes.remove(0);
			destination = route.remove(route.size() - 1);
			path = origin.distance(destination);
		}
	}

	private void clearRoute() {
		boolean repeat = true;
		while (repeat) {
			repeat = false;
			for (int i = route.size() - 1; i >= 0; i--) {
				Point commonEnd = route.get(i);
				for (int j = 0; j < i; j++) {
					Point commonStart = route.get(j);
					if (commonEnd.equals(commonStart)) {
						ArrayList<Point> newRoute = new ArrayList<Point>();
						newRoute.addAll(route.subList(0, j));
						newRoute.addAll(route.subList(i, route.size()));
						route = newRoute;
						repeat = true;
						break;
					}
				}
				if (repeat)
					break;
			}
		}
		System.out.println(n() + "RECORRIDO N");
		for (Point p : route)
			System.out.println(p);
	}

	private Point selectDestination(Graph grafo, Point previousPoint) {
		ArrayList<Link> links = grafo.linkWith(origin);
		double sum = 0.0;
		for (Link enlace : links)
			if (previousPoint == null
					|| (!enlace.getPointA().equals(previousPoint) && !enlace
							.getPointB().equals(previousPoint)))
				sum += enlace.getPheromone();
		double selection = Math.random() * sum;
		if (sum == 0)
			return previousPoint;
		sum = 0.0;
		for (Link link : links) {
			if (previousPoint == null
					|| (!link.getPointA().equals(previousPoint) && !link
							.getPointB().equals(previousPoint))) {
				sum += link.getPheromone();
				if (selection <= sum) {
					if (link.getPointA().equals(origin))
						return link.getPointB();
					else
						return link.getPointA();
				}
			}
		}
		return origin;
	}

	public void stepBack(Graph graph) {
		path -= 1;
		if (path <= 0) {
			graph.findLink(origin, destination).updatePheromone(1.2);
			if (!route.isEmpty()) {
				origin = destination;
				destination = route.remove(route.size() - 1);
				path += origin.distance(destination);
			} else {
				origin = destination;
				initiating = true;
				finding = true;
			}
		}
	}

	public ArrayList<ArrayList<Point>> getLastRoutes() {
		return lastRoutes;
	}

	public String n() {
		return name + " - ";
	}

	public boolean validRoutes() {
		if (getLastRoutes().isEmpty())
			return false;
		if (lastRoutes.size() != limit)
			return false;
		ArrayList<Point> rec = getLastRoutes().get(0);
		for (int i = 1; i < lastRoutes.size(); i++)
			if (rec.size() != lastRoutes.get(i).size())
				return false;
		for (int i = 1; i < lastRoutes.size(); i++) {
			ArrayList<Point> otra = lastRoutes.get(i);
			for (int n = 0; n < rec.size(); n++)
				if (!rec.get(n).equals(otra.get(n)))
					return false;
		}
		return true;
	}

}
