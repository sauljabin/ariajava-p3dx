/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJava P3DX.
 * 
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
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
