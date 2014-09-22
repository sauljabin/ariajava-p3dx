package app.path.geometry;

public class Point {

	private double x;
	private double y;
	private String name;

	public Point(int x, int y, String name) {
		super();
		this.x = (x * 1.0);
		this.y = (y * 1.0);
		this.name = name;
	}
	
	public Point(long x, long y, String name) {
		super();
		this.x = (x * 1.0);
		this.y = (y * 1.0);
		this.name = name;
	}

	public Point(double x, double y, String name) {
		super();
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public Point midpoint(Point otro) {
		return new Point((otro.getX() + getX()) / 2.0,
				(otro.getY() + getY()) / 2.0, "");
	}

	public double distance(Point otro) {
		double difX = otro.getX() - getX();
		double difY = otro.getY() - getY();
		double sum = Math.pow(difX, 2) + Math.pow(difY, 2);
		return Math.sqrt(sum);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		Point point = (Point) obj;
		return (point.getX() == getX() && point.getY() == getY());
	}

	@Override
	public String toString() {
		return "( " + x + " , " + y + " )";
	}

}
