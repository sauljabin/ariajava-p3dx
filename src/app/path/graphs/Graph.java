package app.path.graphs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import app.gui.animation.Animated;
import app.map.Map;
import app.path.geometry.Point;
import app.path.graphs.components.Link;

public class Graph implements Animated {

	private ArrayList<Point> points;
	private ArrayList<Link> links;
	private Double greaterWeight;
	private Double lowerWeight;
	private Map map;

	public Graph(Map map) {
		super();
		this.map = map;
		this.points = new ArrayList<Point>();
		this.links = new ArrayList<Link>();
	}

	public void addPoint(Point point) {
		points.add(point);
	}

	public void addLink(Point pointA, Point pointB) {
		links.add(new Link(pointA, pointB));
	}

	public void removePoint(Point point) {
		for (Point p : points)
			if (p.equals(point)) {
				points.remove(p);
				break;
			}

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
		for (Link link : links)
			if (link.getPointA().equals(point))
				points.add(link.getPointB());
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

	public ArrayList<Point> getPoints() {
		return points;
	}

	public ArrayList<Link> getLinks() {
		return links;
	}

	public Point findPointByName(String name) {
		for (Point point : points)
			if (point.getName().equals(name))
				return point;
		return null;
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

	@Override
	public void initAnimated() {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(new Color(255, 0,0, 100));
		for (int i = 0; i < links.size(); i++) {
			g.drawLine(map.canvasX(links.get(i).getPointA().getX()),
					map.canvasY(links.get(i).getPointA().getY()),
					map.canvasX(links.get(i).getPointB().getX()),
					map.canvasY(links.get(i).getPointB().getY()));
		}
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getZIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

}
