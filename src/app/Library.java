/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJava P3DX.
 * 
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
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
