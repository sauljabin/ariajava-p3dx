/**
 * 
 * Graph.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.path.graphs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import app.path.geometry.Point;
import app.path.graphs.components.Link;

public class Graph {

	private TreeSet<Point> points;
	private ArrayList<Link> links;
	private Double greaterWeight;
	private Double lowerWeight;

	public Graph() {
		super();
		this.points = new TreeSet<Point>(new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				if (o1.getX() == o2.getX() && o1.getY() == o2.getY())
					return 0;
				return 1;
			}
		});
		this.links = new ArrayList<Link>();
	}

	public void addLink(Point pointA, Point pointB) {
		points.add(pointA);
		points.add(pointB);
		links.add(new Link(pointA, pointB));
	}

	public void removeLink(Point pointA, Point pointB) {
		for (Link link : links)
			if ((link.getPointA().equals(pointA) && link.getPointB().equals(
					pointB))
					|| (link.getPointA().equals(pointB) && link.getPointB()
							.equals(pointA))) {
				links.remove(link);
				break;
			}
	}

	public ArrayList<Link> linkWith(Point point) {
		ArrayList<Link> selectedLinks = new ArrayList<Link>();
		for (Link link : links)
			if (link.getPointA().equals(point)
					|| link.getPointB().equals(point))
				selectedLinks.add(link);
		return selectedLinks;
	}

	public ArrayList<Point> linkedWith(Point point) {
		ArrayList<Point> points = new ArrayList<Point>();
		for (Link link : links){
			if (link.getPointA().equals(point)) {
				if (!points.contains(link.getPointB()))
					points.add(link.getPointB());
			} else if (link.getPointB().equals(point)) {
				if (!points.contains(link.getPointA()))
					points.add(link.getPointA());
			}
		}
		return points;
	}

	public Link findLink(Point pointA, Point pointB) {
		for (Link link : links)
			if ((link.getPointA().equals(pointA) && link.getPointB().equals(
					pointB))
					|| (link.getPointA().equals(pointB) && link.getPointB()
							.equals(pointA))) {
				return link;
			}
		return null;
	}

	public ArrayList<Link> getLinks() {
		return links;
	}

	public TreeSet<Point> getPoints() {
		return points;
	}

	public Double calculateGreaterWeight() {
		if (greaterWeight == null) {
			greaterWeight = links.get(0).getWeight();
			for (Link link : links)
				if (link.getWeight() > greaterWeight)
					greaterWeight = link.getWeight();
		}
		return greaterWeight;
	}

	public Double calculateLowerWeight() {
		if (lowerWeight == null) {
			lowerWeight = links.get(0).getWeight();
			for (Link link : links)
				if (link.getWeight() < lowerWeight)
					lowerWeight = link.getWeight();
		}
		return lowerWeight;
	}

	public void updatePheromone() {
		int i = 0;
		for (Link link : links) {
			link.updatePheromone(0.99 + decrease(link.getWeight()));
			System.out.printf("%d %s F: %.2f\n", i + 1, link.toString(),
					link.getPheromone());
		}
	}

	public double decrease(double weight) {
		double value = (1.0 / weight) - (1.0 / calculateGreaterWeight());
		double max = (1.0 / calculateLowerWeight())
				- (1.0 / calculateGreaterWeight());
		return (value * 0.00999 / max);
	}

}
