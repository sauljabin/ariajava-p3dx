/**
 * 
 * Copyright (c) 2014 Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>
 * 
 * This file is part of AriaJavaP3DX.
 *
 * AriaJavaP3DX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AriaJavaP3DX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AriaJavaP3DX.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package app.util;

public class DoubleW implements Comparable<DoubleW> {
	private double value;
	private String name;

	public DoubleW() {
	}

	public DoubleW(double value) {
		this.value = value;
	}

	public DoubleW(double value, String name) {
		this.value = value;
		this.name = name;
	}

	public double getDouble() {
		return value;
	}

	public void setDouble(int value) {
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
	public int compareTo(DoubleW o) {
		return Double.compare(value, o.getDouble());
	}
}
