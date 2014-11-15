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

package app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class Theme {

	private static Properties iconsNames = new Properties();
	public static String iconsNamesPath = "ICONS.props";
	private static Charset charset = StandardCharsets.UTF_8;

	public static void load() throws FileNotFoundException, IOException {
		iconsNames.load(new InputStreamReader(new FileInputStream(iconsNamesPath), charset));
	}

	public static String getIconPath(String key) {
		return Config.get("PATH_IMAGES") + iconsNames.getProperty(key);
	}

	public static Vector<String> getKeys() {
		Vector<String> vectorKeys = new Vector<String>();
		Enumeration<Object> keys = iconsNames.keys();
		while (keys.hasMoreElements()) {
			vectorKeys.add((String) keys.nextElement());
		}
		return vectorKeys;
	}
}
