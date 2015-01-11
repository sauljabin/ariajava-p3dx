/**
 * 
 * DSurface.java
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
