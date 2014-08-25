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
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import app.aria.ArRobotP3DX;
import app.gui.animation.Animated;

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
	public int proportion;

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
		return proportionalValue(maxX - minX);
	}

	public int getProportionalHeight() {
		return proportionalValue(maxY - minY);
	}

	public List<Line> getLines() {
		return lines;
	}

	public RobotHome getRobotHome() {
		return robotHome;
	}

	public void setRobotHome(RobotHome robotHome) {
		this.robotHome = robotHome;
	}

	public Map() {
		lines = new LinkedList<>();
		proportion = 10;
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
				robotHome = new RobotHome(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2]));
			} else if (stringRead.startsWith(goalLabel)) {
				String[] tokens = stringRead.substring(goalLabel.length()).split(" ");
				goal = new Goal(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2]));
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
	}

	public void addLine(int x1, int y1, int x2, int y2) {
		Line line = new Line(x1, y1, x2, y2);
		lines.add(line);
	}

	public int proportionalValue(int value) {
		return value / proportion;
	}

	public int canvasX(int x) {
		int value = 0;
		if (minX < 0) {
			value = Math.abs(minX) + x;
		} else {
			value = x;
		}
		return proportionalValue(value);
	}

	public int canvasY(int y) {
		int value = 0;
		if (minY < 0) {
			value = Math.abs(minY) + y;
		} else {
			value = y;
		}
		return proportionalValue(getHeight() - value);
	}

	@Override
	public void init() {

	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.BLACK);
		for (int i = 0; i < lines.size(); i++) {
			g.drawLine(canvasX(lines.get(i).getX1()), canvasY(lines.get(i).getY1()), canvasX(lines.get(i).getX2()), canvasY(lines.get(i).getY2()));
		}
		if (robotHome != null) {
			g.setColor(Color.BLUE);
			int widthRobot = ArRobotP3DX.WIDTH;
			int longRobot = ArRobotP3DX.LONG;
			int robotHomeX = canvasX(robotHome.getX() - widthRobot / 2);
			int robotHomeY = canvasY(robotHome.getY() + longRobot / 2);
			g.rotate(-Math.toRadians(robotHome.getAngle() - 90), canvasX(robotHome.getX()), canvasY(robotHome.getY()));
			g.fillRect(robotHomeX, robotHomeY, proportionalValue(widthRobot), proportionalValue(longRobot));
			g.rotate(Math.toRadians(robotHome.getAngle() - 90), canvasX(robotHome.getX()), canvasY(robotHome.getY()));
		}
	}

	@Override
	public void animate() {

	}

	@Override
	public int getZIndex() {
		return 0;
	}

}
