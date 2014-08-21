/**
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 *		SAUL PIÑA - SAULJP07@GMAIL.COM
 *		JORGE PARRA - THEJORGEMYLIO@GMAIL.COM
 *		2014
 */

package app.util;

public class ClassW implements Comparable<ClassW> {

	private Class<?> value;
	private String name;

	public Class<?> getValue() {
		return value;
	}

	public void setValue(Class<?> value) {
		this.value = value;
	}

	public ClassW(Class<?> value) {
		this.value = value;
	}

	public ClassW(Class<?> value, String name) {
		this.value = value;
		this.name = name;
	}

	public ClassW() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name != null ? name : value.getSimpleName();
	}

	@Override
	public int compareTo(ClassW o) {
		return this.toString().compareTo(o.toString());
	}

}
