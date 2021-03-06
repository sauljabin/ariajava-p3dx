/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.path.voronoi;

import app.map.Line;
import app.map.Map;
import app.path.geometry.Triangle;
import app.path.graphs.Graph;

import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Diagram {

    private ArrayList<Triangle> triangles;
    private Graph graph;

    public Diagram(ArrayList<Triangle> triangleList, Map map) {
        triangles = triangleList;
        graph = new Graph();
        for (int i = 0; i < triangles.size(); i++) {
            for (int j = i + 1; j < triangles.size(); j++) {
                Triangle tA = triangles.get(i);
                Triangle tB = triangles.get(j);
                Line2D line1 = new Line2D.Double(tA.getCircle().getCenter().getX(), tA.getCircle().getCenter().getY(), tB.getCircle().getCenter().getX(), tB.getCircle().getCenter().getY());
                boolean intersect = false;
                for (Line line : map.getLines()) {
                    Line2D line2 = new Line2D.Double(line.getX1(), line.getY1(), line.getX2(), line.getY2());
                    intersect = line1.intersectsLine(line2);
                    if (intersect)
                        break;
                }
                if (tA.isAdjacent(tB) && !intersect) {
                    graph.addLink(tA.getCircle().getCenter(), tB.getCircle().getCenter());
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
