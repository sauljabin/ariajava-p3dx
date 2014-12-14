/**
 * 
 * UtilIn.java
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
