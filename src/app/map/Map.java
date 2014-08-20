package app.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Map {
	private String path;
	private List<Line> lines;
	private RobotHome robotHome;
	private int mmWidth;
	private int mmHeight;

	public List<Line> getLines() {
		return lines;
	}

	public RobotHome getRobotHome() {
		return robotHome;
	}

	public void setRobotHome(RobotHome robotHome) {
		this.robotHome = robotHome;
	}

	public int getMmWidth() {
		return mmWidth;
	}

	public void setMmWidth(int mmWidth) {
		this.mmWidth = mmWidth;
	}

	public int getMmHeight() {
		return mmHeight;
	}

	public void setMmHeight(int mmHeight) {
		this.mmHeight = mmHeight;
	}

	public int pxX(int mmX) {
		return 0;
	}

	public int pxY(int mmY) {
		return 0;
	}

	public int mmX(int pxX) {
		return 0;
	}

	public int mmY(int pxY) {
		return 0;
	}

	public Map() {
		lines = new LinkedList<>();
	}

	public String getPath() {
		return path;
	}

	public void load(String path) throws IOException {
		this.path = path;

		String numberLinesLabel = "NumLines: ";
		String robotLabel = "Cairn: RobotHome ";
		String linesLabel = "LINES";
		boolean linesSector = false;
		int countLine = 0;
		int totalLines = 0;

		String stringRead;
		BufferedReader br = new BufferedReader(new FileReader(path));

		while ((stringRead = br.readLine()) != null) {
			if (stringRead.startsWith(numberLinesLabel)) {
				totalLines = Integer.parseInt(stringRead.substring(numberLinesLabel.length()));
			} else if (stringRead.startsWith(robotLabel)) {
				String[] tokens = stringRead.substring(robotLabel.length()).split(" ");
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

}
