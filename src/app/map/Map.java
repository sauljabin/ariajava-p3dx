/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJava P3DX.
 * 
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import app.animation.Animated;
import app.animation.AnimatedMouseListener;
import app.path.geometry.Point;
import app.path.graphs.Graph;

public class Map implements Animated {

	private List<Line> lines;
	private List<Point> pathPoints;
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	private int proportion;
	private int offsetPosition;
	private boolean visible;
	private Graph graph;
	private Color pathColor1;
	private Color pathColor2;
	private boolean showPath;

	public boolean isShowPath() {
		return showPath;
	}

	public void setShowPath(boolean showPath) {
		this.showPath = showPath;
	}

	public int getOffsetPosition() {
		return offsetPosition;
	}

	public void setOffsetPosition(int offsetPosition) {
		this.offsetPosition = offsetPosition;
	}

	public int getProportion() {
		return proportion;
	}

	public void setProportion(int proportion) {
		this.proportion = proportion;
	}

	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	public void setMinX(int minX) {
		this.minX = minX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public void setMinY(int minY) {
		this.minY = minY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public int getMinX() {
		return minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getWidth() {
		return maxX - minX;
	}

	public int getHeight() {
		return maxY - minY;
	}

	public int getProportionalWidth() {
		return proportionalValue(getWidth());
	}

	public int getProportionalHeight() {
		return proportionalValue(getHeight());
	}

	public int getCanvasWidth() {
		return getProportionalWidth() + proportionalValue(offsetPosition * 2);
	}

	public int getCanvasHeight() {
		return getProportionalHeight() + proportionalValue(offsetPosition * 2);
	}

	public List<Line> getLines() {
		return lines;
	}

	public Map(int minX, int maxX, int minY, int maxY) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		lines = new LinkedList<>();
		proportion = 10;
		offsetPosition = 1500;
		visible = true;
		pathColor1 = new Color(194, 11, 210);
		pathColor2 = new Color(218, 70, 231);
	}

	public Map() {
		this(0, 0, 0, 0);
	}

	public void addLine(int x1, int y1, int x2, int y2) {
		Line line = new Line(x1, y1, x2, y2);
		lines.add(line);
	}

	public double proportionalDoubleValue(double value) {
		return value / proportion;
	}

	public int proportionalValue(double value) {
		return (int) Math.round(value / proportion);
	}

	public int canvasX(double x) {
		double value = 0;
		if (minX < 0) {
			value = Math.abs(minX) + x;
		} else {
			value = x;
		}
		return proportionalValue(value + offsetPosition);
	}

	public int canvasY(double y) {
		double value = 0;
		if (minY < 0) {
			value = Math.abs(minY) + y;
		} else {
			value = y;
		}
		return proportionalValue(getHeight() - value + offsetPosition);
	}

	public double canvasDoubleX(double x) {
		double value = 0;
		if (minX < 0) {
			value = Math.abs(minX) + x;
		} else {
			value = x;
		}
		return proportionalDoubleValue(value + offsetPosition);
	}

	public double canvasDoubleY(int y) {
		double value = 0;
		if (minY < 0) {
			value = Math.abs(minY) + y;
		} else {
			value = y;
		}
		return proportionalDoubleValue(getHeight() - value + offsetPosition);
	}

	@Override
	public void initAnimated() {

	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.BLACK);
		for (int i = 0; i < lines.size(); i++) {
			g.drawLine(canvasX(lines.get(i).getX1()), canvasY(lines.get(i).getY1()), canvasX(lines.get(i).getX2()), canvasY(lines.get(i).getY2()));
		}
		if (!showPath)
			return;
		if (graph != null) {
			g.setColor(new Color(255, 0, 0, 100));
			for (int i = 0; i < graph.getLinks().size(); i++) {
				g.drawLine(canvasX(graph.getLinks().get(i).getPointA().getX()), canvasY(graph.getLinks().get(i).getPointA().getY()), canvasX(graph.getLinks().get(i).getPointB().getX()), canvasY(graph.getLinks().get(i).getPointB().getY()));
			}
		}
		if (pathPoints != null) {
			boolean changeColor = true;
			g.setColor(pathColor1);
			for (int i = 0; i < pathPoints.size() - 1; i++) {
				if (changeColor = !changeColor)
					g.setColor(pathColor2);
				else
					g.setColor(pathColor1);
				g.drawLine(canvasX(pathPoints.get(i).getX()), canvasY(pathPoints.get(i).getY()), canvasX(pathPoints.get(i + 1).getX()), canvasY(pathPoints.get(i + 1).getY()));
			}
		}
	}

	@Override
	public void animate() {

	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public Shape getShape() {
		return null;
	}

	@Override
	public int getZ() {
		return 0;
	}

	@Override
	public AnimatedMouseListener getAnimatedMouseListener() {
		return null;
	}

	public ArrayList<Point> getPoints() {
		ArrayList<Point> list = new ArrayList<Point>();
		for (Line line : getLines()) {
			Point p1 = new Point(line.getX1(), line.getY1(), "");
			Point p5 = new Point(line.getX2(), line.getY2(), "");
			Point p3 = p1.midPoint(p5);
			Point p2 = p1.midPoint(p3);
			Point p4 = p3.midPoint(p5);
			list.add(p1);
			list.add(p2);
			list.add(p3);
			list.add(p4);
			list.add(p5);
		}
		return list;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public List<Point> getPathPoints() {
		return pathPoints;
	}

	public void setPathPoints(List<Point> pathPoints) {
		this.pathPoints = pathPoints;
	}

}
