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

public class Triangle extends Polygon {

	public Triangle(Vertex v1, Vertex v2, Vertex v3) {
		super();
		getVertexes().add(v1);
		getVertexes().add(v2);
		getVertexes().add(v3);
	}

	public static boolean sameTriangle(Triangle t1, Triangle t2) {
		if (t1 == t2)
			return true;
		ArrayList<Vertex> v1 = t1.getVertexes();
		ArrayList<Vertex> v2 = t2.getVertexes();
		if (v1 == v2)
			return true;
		Vertex t1A = (Vertex) v1.get(0);
		Vertex t1B = (Vertex) v1.get(1);
		Vertex t1C = (Vertex) v1.get(v1.size() - 1);
		Vertex t2A = (Vertex) v2.get(0);
		Vertex t2B = (Vertex) v2.get(1);
		Vertex t2C = (Vertex) v2.get(v2.size() - 1);
		return (Vertex.sameVertex(t1A, t2A) || Vertex.sameVertex(t1A, t2B) || Vertex
				.sameVertex(t1A, t2C))
				&& (Vertex.sameVertex(t1B, t2A) || Vertex.sameVertex(t1B, t2B) || Vertex
						.sameVertex(t1B, t2C))
				&& (Vertex.sameVertex(t1C, t2A) || Vertex.sameVertex(t1C, t2B) || Vertex
						.sameVertex(t1C, t2C));
	}

	public boolean matches(ArrayList<Triangle> vec) {
		for (Triangle t : vec)
			if (sameTriangle(this, t))
				return true;
		return false;
	}

	public int matchIndex(ArrayList<Triangle> vec) {
		int i = 0;
		for (Triangle t : vec) {
			if (sameTriangle(this, t))
				return i;
			i++;
		}
		return -1;
	}

	public Vertex thirdVertex(Vertex v1, Vertex v2) {
		ArrayList<Vertex> vertices = getVertexes();
		Vertex vert1 = (Vertex) vertices.get(0);
		Vertex vert2 = (Vertex) vertices.get(1);
		Vertex vert3 = (Vertex) vertices.get(2);
		if (v1 == vert1 && v2 == vert2 || v2 == vert1 && v1 == vert2) {
			return vert3;
		}
		if (v1 == vert1 && v2 == vert3 || v2 == vert1 && v1 == vert3) {
			return vert2;
		}
		if (v1 == vert2 && v2 == vert3 || v2 == vert2 && v1 == vert3) {
			return vert1;
		}
		return null;
	}

	public Triangle edgeNeighbour(ArrayList<Triangle> vec, Vertex v1, Vertex v2) {
		for (Triangle t : vec)
			if (!sameTriangle(t, this)) {
				ArrayList<Vertex> verts = t.getVertexes();
				if (verts.contains(v1) && verts.contains(v2))
					return t;
			}
		return null;
	}

	public int volumeSign(Vertex v) {
		ArrayList<Vertex> vertices = getVertexes();
		double[] v1 = ((Vertex) vertices.get(0)).getCoords();
		double[] v2 = ((Vertex) vertices.get(1)).getCoords();
		double[] v3 = ((Vertex) vertices.get(vertices.size() - 1)).getCoords();
		double[] v4 = v.getCoords();
		double ax = v1[0] - v4[0];
		double ay = v1[1] - v4[1];
		double az = v1[2] - v4[2];
		double bx = v2[0] - v4[0];
		double by = v2[1] - v4[1];
		double bz = v2[2] - v4[2];
		double cx = v3[0] - v4[0];
		double cy = v3[1] - v4[1];
		double cz = v3[2] - v4[2];

		double vol = ax * (by * cz - bz * cy) + ay * (bz * cx - bx * cz) + az
				* (bx * cy - by * cx);

		if (vol > 0)
			return 1;
		else if (vol < 0)
			return -1;
		else
			return 0;
	}

	public double volume6(Vertex v) {
		ArrayList<Vertex> vertices = getVertexes();
		double[] v1 = ((Vertex) vertices.get(0)).getCoords();
		double[] v2 = ((Vertex) vertices.get(1)).getCoords();
		double[] v3 = ((Vertex) vertices.get(vertices.size() - 1)).getCoords();
		double[] v4 = v.getCoords();
		double ax = v1[0];
		double ay = v1[1];
		double az = v1[2];
		double bx = v2[0];
		double by = v2[1];
		double bz = v2[2];
		double cx = v3[0];
		double cy = v3[1];
		double cz = v3[2];
		double dx = v4[0];
		double dy = v4[1];
		double dz = v4[2];
		double bxdx = bx - dx;
		double bydy = by - dy;
		double bzdz = bz - dz;
		double cxdx = cx - dx;
		double cydy = cy - dy;
		double czdz = cz - dz;
		return (az - dz) * (bxdx * cydy - bydy * cxdx) + (ay - dy)
				* (bzdz * cxdx - bxdx * czdz) + (ax - dx)
				* (bydy * czdz - bzdz * cydy);
	}
}
