/**
 * 
 * ConvexHull.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.path.delaunay.convex;

import java.util.*;

public class ConvexHull extends DSurface {

	public ConvexHull() {
		super();
	}

	public ConvexHull(ArrayList<Vertex> vertices) throws ConvexHullException {
		super();
		if (vertices.size() < 4)
			throw new ConvexHullException("Too few (" + vertices.size()
					+ ") vertices to form hull");
		Iterator<Vertex> e = vertices.iterator();
		if (e.hasNext()) {
			Vertex v1 = e.next();
			Vertex v2 = null;
			for (; e.hasNext();) {
				v2 = e.next();
				if (!Vertex.sameVertex(v1, v2))
					break;
			}
			Vertex v3 = null;
			Triangle t = null;
			ArrayList<Vertex> coVerts = new ArrayList<Vertex>();
			for (; e.hasNext();) {
				v3 = e.next();
				if (Vertex.collinear(v1, v2, v3))
					coVerts.add(v3);
				else {
					t = new Triangle(v1, v2, v3);
					break;
				}
			}
			Vertex v4 = null;
			for (; e.hasNext();) {
				v4 = (Vertex) e.next();
				int volSign = t.volumeSign(v4);
				if (volSign == 0)
					coVerts.add(v4);
				else {
					addFace(t);
					triToTet(t, v4, volSign);
					break;
				}
			}
			checkVertex(v1);
			checkVertex(v2);
			checkVertex(v3);
			checkVertex(v4);
			for (; e.hasNext();)
				addVertex(e.next());
			if (getFaces().size() > 0)
				for (Vertex we : coVerts)
					addVertex(we);
			else
				throw new ConvexHullException("Vertices coplanar");
		}
	}

	public ArrayList<Triangle> interSetFaces(ArrayList<Vertex> s1,
			ArrayList<Vertex> s2) {
		ArrayList<Triangle> xFaces = new ArrayList<Triangle>();
		for (Polygon polygon : getFaces()) {
			Triangle t = (Triangle) polygon;
			ArrayList<Vertex> v = t.getVertexes();
			Vertex v1 = (Vertex) v.get(0);
			Vertex v2 = (Vertex) v.get(1);
			Vertex v3 = (Vertex) v.get(v.size() - 1);
			if ((s1.contains(v1) || s1.contains(v2) || s1.contains(v3))
					&& (s2.contains(v1) || s2.contains(v2) || s2.contains(v3))) {
				xFaces.add(t);
			}
		}
		return xFaces;
	}

	private static boolean checkVertex(Vertex vertex) {
		return true;
	}

	private void triToTet(Polygon face, Vertex vertex, int vol) {
		ArrayList<Vertex> v = face.getVertexes();
		Vertex v1 = (Vertex) v.get(0);
		Vertex v2 = (Vertex) v.get(1);
		Vertex v3 = (Vertex) v.get(2);

		if (vol < 0) {
			v.set(0, v3);
			v.set(2, v1);
			Vertex tv = v1;
			v1 = v3;
			v3 = tv;
		}
		addFace(new Triangle(v3, v2, vertex));
		addFace(new Triangle(v2, v1, vertex));
		addFace(new Triangle(v1, v3, vertex));
	}

	private void addVertex(Vertex vertex) {
		ArrayList<Edge> visEdges = new ArrayList<Edge>();
		ArrayList<Polygon> visFaces = new ArrayList<Polygon>();
		checkVertex(vertex);
		for (Polygon polygon : getFaces()) {
			Triangle face = (Triangle) polygon;
			if (face.volumeSign(vertex) < 0)
				visFaces.add(face);
		}

		for (Polygon face : visFaces)
			deleteVisibleFace(face, visEdges);

		for (Edge edge : visEdges) {
			Vertex ends[] = edge.getVertices();
			addFace(new Triangle(ends[0], ends[1], vertex));
		}
	}

	private void deleteVisibleFace(Polygon face, ArrayList<Edge> visibleEdges) {
		ArrayList<Vertex> v = face.getVertexes();
		Vertex v1 = (Vertex) v.get(0);
		Vertex v2 = (Vertex) v.get(1);
		Vertex v3 = (Vertex) v.get(2);
		Edge e1 = new Edge(v1, v2);
		Edge e2 = new Edge(v2, v3);
		Edge e3 = new Edge(v3, v1);
		updateVisibleEdges(e1, visibleEdges);
		updateVisibleEdges(e2, visibleEdges);
		updateVisibleEdges(e3, visibleEdges);
		deleteFace(face);
	}

	private void updateVisibleEdges(Edge e, ArrayList<Edge> visibleEdges) {
		boolean same = false;
		for (Edge edge : visibleEdges)
			if (Edge.sameEdge(e, edge)) {
				same = true;
				e = edge;
				break;
			}
		if (same)
			visibleEdges.remove(e);
		else
			visibleEdges.add(e);
	}
}
