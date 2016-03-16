/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJava P3DX.
 * 
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
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
