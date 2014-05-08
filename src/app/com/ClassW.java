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

package app.com;

public class ClassW implements Comparable<ClassW> {

	private Class<?> clazz;

	public Class<?> getValue() {
		return clazz;
	}

	public void setValue(Class<?> clazz) {
		this.clazz = clazz;
	}

	public ClassW(Class<?> clazz) {
		this.clazz = clazz;
	}

	public ClassW() {

	}

	@Override
	public String toString() {
		return clazz.getSimpleName();
	}

	@Override
	public int compareTo(ClassW o) {
		return this.toString().compareTo(o.toString());
	}

}
