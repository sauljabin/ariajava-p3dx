/**
 * 
 * IntegerW.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.util;

public class IntegerW implements Comparable<IntegerW> {
	private int value;
	private String name;

	public IntegerW() {
	}

	public IntegerW(int value) {
		this.value = value;
	}

	public IntegerW(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getInteger() {
		return value;
	}

	public void setInteger(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name != null ? name : String.format("%d", value);
	}

	@Override
	public int compareTo(IntegerW o) {
		return Integer.compare(value, o.getInteger());
	}
}
