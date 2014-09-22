package app.path.graphs.components;

import app.path.geometry.Point;

public class Link {

	private Point pointA;
	private Point pointB;
	private Double pheromone;

	public Link(Point pointA, Point pointB) {
		super();
		this.pointA = pointA;
		this.pointB = pointB;
		this.pheromone = 100.0;
	}

	public Point getPointA() {
		return pointA;
	}

	public Point getPointB() {
		return pointB;
	}

	public Double getPheromone() {
		return pheromone;
	}

	public double getWeight() {
		return this.pointA.distance(this.pointB);
	}

	@Override
	public boolean equals(Object obj) {
		Link link = (Link) obj;
		return ((getPointA().equals(link.pointA) && (getPointB()
				.equals(link.pointB))) || (getPointA().equals(link.pointB) && (getPointB()
				.equals(link.pointA))));
	}

	public void updatePheromone(Double value) {
		pheromone *= value;
	}

	@Override
	public String toString() {
		return "[ " + pointA.getName() + " / " + pointB.getName() + " ]";
	}

}
