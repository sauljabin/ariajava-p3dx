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

public class Edge {

	private Vertex end1, end2;

	public Edge(Vertex v1, Vertex v2) {
		end1 = v1;
		end2 = v2;
	}

	public static boolean sameEdge(Edge e1, Edge e2) {
		return (e1 == e2
				|| (Vertex.sameVertex(e1.end1, e2.end1) && Vertex.sameVertex(
						e1.end2, e2.end2)) || (Vertex.sameVertex(e1.end2,
				e2.end1) && Vertex.sameVertex(e1.end1, e2.end2)));
	}

	public Vertex[] getVertices() {
		Vertex v[] = new Vertex[2];
		v[0] = end1;
		v[1] = end2;
		return v;
	}

	public String toString() {
		return "[" + end1 + ", " + end2 + "]";
	}
}
