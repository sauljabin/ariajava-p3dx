package app.map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import javax.swing.border.StrokeBorder;

import app.gui.animation.Animated;
import app.gui.animation.Animator;

public class Map implements Animated {

	public static final String numberLinesLabel = "NumLines: ";
	public static final String robotLabel = "Cairn: RobotHome ";
	public static final String linesLabel = "LINES";
	public static final String lineMinPosLabel = "LineMinPos: ";
	public static final String lineMaxPosLabel = "LineMaxPos: ";
	final static BasicStroke dashed = new BasicStroke(5);
	private String path;
	private List<Line> lines;
	private RobotHome robotHome;
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;

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

	public int getScaledWidth() {
		return scale(maxX - minX);
	}

	public int getScaledHeight() {
		return scale(maxY - minY);
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
				// CAMBIAR A DOUBLE
				robotHome = new RobotHome(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
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

	public int scale(int value) {
		return value / 10;
	}

	public int canvasX(int x) {
		int value = 0;
		if (minX < 0) {
			value = Math.abs(minX) + x;
		} else {
			value = x;
		}
		return scale(value);
	}

	public int canvasY(int y) {
		int value = 0;
		if (minY < 0) {
			value = Math.abs(minY) + y;
		} else {
			value = y;
		}
		return scale(value);
	}

	@Override
	public void init() {

	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.RED);
		g.setStroke(dashed);
		for (int i = 0; i < lines.size(); i++) {
			g.drawLine(canvasX(lines.get(i).getX1()), canvasY(lines.get(i).getY1()), canvasX(lines.get(i).getX2()), canvasY(lines.get(i).getY2()));
		}
	}

	@Override
	public void animate() {

	}

	@Override
	public int getZIndex() {
		return 0;
	}

	@Override
	public void setAnimator(Animator animator) {

	}

	@Override
	public Animator getAnimator() {
		return null;
	}

}
