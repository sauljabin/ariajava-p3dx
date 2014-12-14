/**
 * 
 * ConvexHullException.java
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

public class ConvexHullException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConvexHullException() {
		super();
	}

	public ConvexHullException(String s) {
		super(s);
	}
}
