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

import java.util.*;
import java.io.*;

public class Surface {

	private ArrayList<Polygon> faces;

	public Surface() {
		faces = new ArrayList<Polygon>();
	}

	public Surface(ArrayList<Polygon> f) {
		faces = f;
	}

	public ArrayList<Polygon> getFaces() {
		return faces;
	}

	public void setFaces(ArrayList<Polygon> f) {
		faces = f;
	}

	public ArrayList<Vertex> getVertices() {
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		for (Polygon polygon : faces)
			for (Vertex vertex : polygon.getVertexes())
				if (vertices.indexOf(vertex) == -1)
					vertices.add(vertex);
		return vertices;
	}

	public void writeOFF(PrintWriter pw) throws IOException {
		/* Write header */
		pw.println("OFF");
		ArrayList<Vertex> vertices = getVertices();
		pw.println(vertices.size() + " " + " " + faces.size() + " "
				+ (3 * faces.size()));

		for (Vertex vertex : vertices)
			pw.println(vertex.getCoords()[0] + " " + vertex.getCoords()[1]
					+ " " + vertex.getCoords()[2]);

		for (Polygon polygon : faces) {
			pw.print("CANT: " + polygon.getVertexes().size());
			for (Vertex vertex : polygon.getVertexes())
				pw.print(" / V: " + vertex.getCoords()[0] + " "
						+ vertex.getCoords()[1] + " " + vertex.getCoords()[2]);
			pw.print("\n");
		}
	}

	public String toString() {
		String s = new String();
		for (Polygon polygon : faces)
			s += polygon + "\n";
		return s;
	}
}
