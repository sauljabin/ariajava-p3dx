/**
 * 
 * Dijkstra.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.path;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import app.Log;
import app.Translate;
import app.path.geometry.Point;
import app.path.graphs.Graph;

public class Dijkstra {

	private Graph graph;
	private Queue<Point> visitedNode;
	private ArrayList<Point> path;

	public Dijkstra(Graph graph) {
		this.graph = graph;
	}

	private void nextNode(Point current, Point target) {
		if (current.equals(target))
			return;
		current.setMarked(true);
		ArrayList<Point> adjacent = graph.linkedWith(current);
		for (Point point : adjacent) {
			if (!point.isMarked()) {
				if (point.getAncestor() == null) {
					point.setCumulativeWeight(Double.POSITIVE_INFINITY);
				}
				double weight = current.getCumulativeWeight() + current.distance(point);
				if (weight < point.getCumulativeWeight()) {
					point.setAncestor(current);
					point.setCumulativeWeight(weight);
				}
				if (!visitedNode.contains(point))
					visitedNode.add(point);
			}
		}
		nextNode(visitedNode.poll(), target);
	}

	private void findPath(Point point) {
		path.add(0, point);
		if (point.getAncestor() != null)
			findPath(point.getAncestor());
	}

	public ArrayList<Point> searchOptimalPath(Point start, Point target) {
		Log.info(getClass(), Translate.get("INFO_INITSEARCHPATH"));
		this.path = new ArrayList<Point>();
		this.visitedNode = new LinkedList<Point>();
		try {
			nextNode(start, target);
			findPath(target);
		} catch (Exception e) {
			return null;
		}
		return path;
	}
}
