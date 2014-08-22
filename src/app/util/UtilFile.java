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

import java.io.File;
import java.io.FileFilter;

public class UtilFile {

	public static File[] files(String path) {
		return files(new File(path));
	}

	public static File[] files(File file) {
		return file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile();
			}
		});
	}

	public static String getOnlyName(File file) {
		return getOnlyName(file.getName());
	}

	public static String getOnlyName(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf('.'));
	}

	public static String getExtension(File file) {
		return getExtension(file.getName());
	}

	public static String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
	}

	public static boolean isFileType(File file, String... extensions) {
		return isFileType(file.getName(), extensions);
	}

	public static boolean isFileType(String fileName, String... extensions) {
		for (String extension : extensions) {
			if (fileName.toUpperCase().endsWith(extension.toUpperCase()))
				return true;
		}
		return false;
	}

}
