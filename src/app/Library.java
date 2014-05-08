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

package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Vector;

public class Library {

	public static void load() throws Exception {

		if (!Library.is32Bit())
			throw new FileNotFoundException("Api library not found for arch: " + Config.get("OS_ARCH"));

		if (!Library.isLinux())
			throw new FileNotFoundException("Api library not found for os: " + Config.get("OS"));

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

	public static boolean isLinux() {
		return Config.get("OS").toLowerCase().contains("linux");
	}

	public static boolean is32Bit() {
		Vector<String> strings = new Vector<String>();
		strings.add("x86");
		strings.add("i386");

		for (String string : strings) {
			if (Config.get("OS_ARCH").toLowerCase().contains(string))
				return true;
		}

		return false;
	}

}
