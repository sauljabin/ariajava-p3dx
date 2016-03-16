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
