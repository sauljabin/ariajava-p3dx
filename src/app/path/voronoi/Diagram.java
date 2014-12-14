/**
 * 
 * Diagram.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.path.voronoi;

import java.util.ArrayList;

import app.map.Line;
import app.map.Map;
import app.path.geometry.Triangle;
import app.path.graphs.Graph;

import com.sun.javafx.geom.Line2D;

public class Diagram {

	private ArrayList<Triangle> triangles;
	private Graph graph;

	public Diagram(ArrayList<Triangle> triangleList, Map map) {
		triangles = triangleList;
		graph = new Graph();
		// for (Triangle t : triangles)
		// graph.addPoint(t.getCircle().getCenter());
		for (int i = 0; i < triangles.size(); i++) {
			for (int j = i + 1; j < triangles.size(); j++) {
				Triangle tA = triangles.get(i);
				Triangle tB = triangles.get(j);
				Line2D line1 = new Line2D(new Double(tA.getCircle().getCenter()
						.getX()).floatValue(), new Double(tA.getCircle()
						.getCenter().getY()).floatValue(), new Double(tB
						.getCircle().getCenter().getX()).floatValue(),
						new Double(tB.getCircle().getCenter().getY())
								.floatValue());
				boolean intersect = false;
				for (Line line : map.getLines()) {
					Line2D line2 = new Line2D(line.getX1(), line.getY1(),
							line.getX2(), line.getY2());
					intersect = line1.intersectsLine(line2);
					if (intersect)
						break;
				}
				if (tA.isAdjacent(tB) && !intersect) {
					graph.addLink(tA.getCircle().getCenter(), tB.getCircle()
							.getCenter());
				}
			}
		}
	}

	public ArrayList<Triangle> getTriangles() {
		return triangles;
	}

	public Graph getGraph() {
		return graph;
	}

}
