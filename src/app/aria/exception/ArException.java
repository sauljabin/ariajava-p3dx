/**
 * 
 * ArException.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.aria.exception;

public class ArException extends Exception {

	private static final long serialVersionUID = -2595110526724338943L;

	public ArException() {
		super();
	}

	public ArException(String message) {
		super(message);
	}

}
