/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJava P3DX.
 * 
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app;

public enum LogLevel {
	NONE(0), INFO(1), WARN(2), ERROR(3), DEVEL(4);
	public final int value;

	LogLevel(int value) {
		this.value = value;
	}
}
