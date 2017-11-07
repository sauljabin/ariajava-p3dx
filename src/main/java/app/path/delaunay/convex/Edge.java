/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
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
