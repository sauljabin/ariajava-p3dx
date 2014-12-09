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

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Vector;

public class Library {

	public static void load() throws Exception {

		String path = Config.get("PATH_LIBARIA");

		File file = new File(path);

		Field field = ClassLoader.class.getDeclaredField("usr_paths");
		field.setAccessible(true);
		String[] paths = (String[]) field.get(null);

		Vector<String> pathsList = new Vector<String>();
		Collections.addAll(pathsList, paths);
		pathsList.add(file.getAbsolutePath());
		field.set(null, pathsList.toArray(new String[pathsList.size()]));
		System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + file.getAbsolutePath());
		System.loadLibrary("AriaJava");
	}
	

}
