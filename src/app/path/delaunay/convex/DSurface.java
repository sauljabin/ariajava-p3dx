package app.path.delaunay.convex;

import java.util.*;

public class DSurface extends Surface {

	public void addFace(Polygon p) {
		getFaces().add(p);
	}

	public void addFaces(ArrayList<Polygon> v) {
		getFaces().addAll(v);
	}

	public boolean deleteFace(Polygon p) {
		return getFaces().remove(p);
	}
}
