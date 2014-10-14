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
