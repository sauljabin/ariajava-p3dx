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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UtilIn {

	public static String readString(String msg) {
		System.out.printf("%s", msg);
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(input);
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String readString() {
		return readString("");
	}

	public static Integer readInteger(String msg) {
		System.out.printf("%s", msg);
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(input);
		try {
			String s = br.readLine();
			if (s.matches("[0-9]+"))
				return new Integer(s);
			else
				return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Integer readInteger() {
		return readInteger("");
	}
}
