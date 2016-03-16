/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJava P3DX.
 * 
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.path.delaunay.convex;

import java.util.ArrayList;

public class Polygon {

	private ArrayList<Vertex> vertexes; // Polygon vertices

	public Polygon() {
		vertexes = new ArrayList<Vertex>();
	}

	public Polygon(ArrayList<Vertex> v) {
		vertexes = v;
	}

	public void reverse() {
		ArrayList<Vertex> vertices = getVertexes();
		int size = vertices.size();
		for (int i = 1; i < size; i++) {
			vertices.add(0, vertices.get(i));
			vertices.remove(i + 1);
		}
	}

	public ArrayList<Vertex> getVertexes() {
		return vertexes;
	}

	public Vertex nextVertex(Vertex vertex) {
		int index = vertexes.indexOf(vertex);
		return (Vertex) (index == -1 ? null : vertexes.get((index + 1)
				% vertexes.size()));
	}

	public String toString() {
		String s = new String();
		for (Vertex vertex : getVertexes())
			s += vertex;
		return s;
	}
}
