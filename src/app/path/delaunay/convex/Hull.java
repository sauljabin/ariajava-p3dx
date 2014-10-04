package app.path.delaunay.convex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;

public class Hull {

	public static void main(String[] args) throws IOException,
			ConvexHullException {
		args = new String[2];
		args[0] = "t2";
		args[1] = "t2salida";
		if (args.length != 2) {
			System.out.println("usage: Hull <infile> <outfile>");
		} else {
			BufferedReader r = new BufferedReader(new FileReader(args[0]));
			StreamTokenizer st = new StreamTokenizer(r);
			ArrayList<Vertex> vertices = new ArrayList<Vertex>();

			/* Read vertices */
			while (st.nextToken() != StreamTokenizer.TT_EOF) {
				int x = 0, y = 0, z = 0;

				if (st.ttype == StreamTokenizer.TT_NUMBER) {
					x = (int) st.nval;
				} else {
					System.out.println("Error reading " + args[0]);
					System.exit(-1);
				}
				if (st.nextToken() == StreamTokenizer.TT_NUMBER) {
					y = (int) st.nval;
				} else {
					System.out.println("Error reading " + args[0]);
					System.exit(-1);
				}
				if (st.nextToken() == StreamTokenizer.TT_NUMBER) {
					z = (int) st.nval;
				} else {
					System.out.println("Error reading " + args[0]);
					System.exit(-1);
				}
				vertices.add(new Vertex(x, y, z));
			}
			r.close();

			ConvexHull hull = new ConvexHull(vertices);

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
					args[1])));
			hull.writeOFF(pw);
			pw.close();
		}
	}
}
