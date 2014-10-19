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

package app.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import app.animation.Animated;
import app.animation.AnimatedMouseListener;
import app.aria.robot.ArRobotMobile;
import app.path.geometry.Point;
import app.path.graphs.Graph;

public class Map implements Animated {

	public static final String numberLinesLabel = "NumLines: ";
	public static final String robotLabel = "Cairn: RobotHome ";
	public static final String linesLabel = "LINES";
	public static final String lineMinPosLabel = "LineMinPos: ";
	public static final String lineMaxPosLabel = "LineMaxPos: ";
	public static final String goalLabel = "Cairn: Goal ";

	private String path;
	private List<Line> lines;
	private RobotHome robotHome;
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	private Goal goal;
	private int proportion;
	private int offsetPosition;
	private boolean visible;
	private Graph graph;

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

	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
		goal.setMap(this);
	}

	public void setPath(String path) {
		this.path = path;
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

	public RobotHome getRobotHome() {
		return robotHome;
	}

	public void setRobotHome(RobotHome robotHome) {
		this.robotHome = robotHome;
		robotHome.setMap(this);
	}

	public Map(RobotHome robotHome, Goal goal, int minX, int maxX, int minY, int maxY) {
		this.robotHome = robotHome;
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.goal = goal;
		lines = new LinkedList<>();
		proportion = 10;
		offsetPosition = 1500;
		if (robotHome != null)
			robotHome.setMap(this);
		if (goal != null)
			goal.setMap(this);
		visible = true;
	}

	public Map(RobotHome robotHome, int minX, int maxX, int minY, int maxY) {
		this(robotHome, null, minX, maxX, minY, maxY);
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}

	public Map(int minX, int maxX, int minY, int maxY) {
		this(new RobotHome(), null, minX, maxX, minY, maxY);
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}

	public Map() {
		this(new RobotHome(), null, 0, 0, 0, 0);
	}

	public String getPath() {
		return path;
	}

	public void load(String path) throws Exception {
		this.path = path;

		boolean linesSector = false;
		int countLine = 0;
		int totalLines = 0;

		String stringRead;
		BufferedReader br = new BufferedReader(new FileReader(path));

		while ((stringRead = br.readLine()) != null) {
			if (stringRead.startsWith(numberLinesLabel)) {
				totalLines = Integer.parseInt(stringRead.substring(numberLinesLabel.length()));
			} else if (stringRead.startsWith(lineMinPosLabel)) {
				String[] tokens = stringRead.substring(lineMinPosLabel.length()).split(" ");
				minX = Integer.parseInt(tokens[0]);
				minY = Integer.parseInt(tokens[1]);
			} else if (stringRead.startsWith(lineMaxPosLabel)) {
				String[] tokens = stringRead.substring(lineMaxPosLabel.length()).split(" ");
				maxX = Integer.parseInt(tokens[0]);
				maxY = Integer.parseInt(tokens[1]);
			} else if (stringRead.startsWith(robotLabel)) {
				String[] tokens = stringRead.substring(robotLabel.length()).split(" ");
				setRobotHome(new RobotHome(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2])));
			} else if (stringRead.startsWith(goalLabel)) {
				String[] tokens = stringRead.substring(goalLabel.length()).split(" ");
				setGoal(new Goal(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2])));
			} else if (stringRead.startsWith(linesLabel)) {
				linesSector = true;
			} else if (linesSector) {
				String[] tokens = stringRead.split(" ");
				addLine(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
				if (++countLine == totalLines) {
					linesSector = false;
				}
			}
		}
		br.close();
		if (getGoal() == null && getRobotHome() != null)
			setGoal(new Goal(robotHome.getX() + ArRobotMobile.LONG * 2, robotHome.getY(), robotHome.getAngle()));
	}

	private void addLine(int x1, int y1, int x2, int y2) {
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
		if (graph != null) {
			g.setColor(new Color(255, 0, 0, 100));
			for (int i = 0; i < graph.getLinks().size(); i++) {
				g.drawLine(canvasX(graph.getLinks().get(i).getPointA().getX()), canvasY(graph.getLinks().get(i).getPointA().getY()), canvasX(graph.getLinks().get(i).getPointB().getX()), canvasY(graph.getLinks().get(i).getPointB().getY()));
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
			Point p3 = p1.midpoint(p5);
			Point p2 = p1.midpoint(p3);
			Point p4 = p3.midpoint(p5);
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

}
