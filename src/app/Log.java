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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.swing.JTextArea;

import app.util.UtilDate;

public class Log {

	private static LogLevel level = LogLevel.DEVEL;
	private static JTextArea textArea;

	public static LogLevel getLevel() {
		return level;
	}

	public static void setLevel(LogLevel level) {
		Log.level = level;
	}

	public static void setLogTextArea(JTextArea textArea) {
		Log.textArea = textArea;
		Log.textArea.setEditable(false);
	}

	private static void print(LogLevel printLevel, Class<?> clazz, String msg,
			Exception e) {
		if (level == LogLevel.NONE)
			return;

		if (printLevel.value > level.value)
			return;

		String type = "";

		switch (printLevel) {
		case INFO:
			type = "INFO";
			break;
		case WARN:
			type = "WARN";
			break;
		case ERROR:
			type = "ERROR";
			break;
		case DEVEL:
			type = "DEVEL";
			break;
		default:
			break;
		}

		String string = String.format(
				"TYPE: %s\nNAME: %s\nTIME: %s\n----> %s\n", type,
				clazz.getName(), UtilDate.nowFormat("yyyy-MM-dd HH:mm"), msg);

		printToConsole(string, e);

		if (printLevel.equals(LogLevel.DEVEL))
			return;

		printToFile(string, e);
		printToTextArea(string);
	}

	private static void printToConsole(String string, Exception e) {
		PrintStream ps = System.out;
		ps.print(string);
		if (e != null) {
			e.printStackTrace(ps);
		}
		ps.println();
	}

	private static void printToFile(String string, Exception e) {
		File folder = new File("log");
		folder.mkdir();
		FileOutputStream fs;
		OutputStreamWriter os;
		BufferedWriter bw;
		try {
			fs = new FileOutputStream(folder.getPath() + "/"
					+ UtilDate.nowFormat("yyyy-MM-dd") + ".log", true);
			os = new OutputStreamWriter(fs, "UTF-8");
			bw = new BufferedWriter(os);
			bw.write(string);
			bw.flush();
			if (e != null)
				e.printStackTrace(new PrintWriter(os));
			bw.write("\n");
			bw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void printToTextArea(String string) {
		if (textArea != null) {
			textArea.append(string + "\n");
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
	}
	
	private static void printText(String string) {
		if (textArea != null) {
			textArea.append(string);
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
	}

	public static synchronized void info(Class<?> clazz, String msg) {
		info(clazz, msg, null);
	}

	public static synchronized void info(Class<?> clazz, String msg, Exception e) {
		print(LogLevel.INFO, clazz, msg, e);
	}

	public static synchronized void error(Class<?> clazz, String msg) {
		error(clazz, msg, null);
	}

	public static synchronized void error(Class<?> clazz, String msg,
			Exception e) {
		print(LogLevel.ERROR, clazz, msg, e);
	}

	public static synchronized void warning(Class<?> clazz, String msg) {
		warning(clazz, msg, null);
	}

	public static synchronized void warning(Class<?> clazz, String msg,
			Exception e) {
		print(LogLevel.WARN, clazz, msg, e);
	}

	public static synchronized void devel(Class<?> clazz, String msg) {
		devel(clazz, msg, null);
	}

	public static synchronized void devel(Class<?> clazz, String msg,
			Exception e) {
		print(LogLevel.DEVEL, clazz, msg, e);
	}

	public static synchronized void print(String msg) {
		printText(msg);
	}

	public static synchronized void println(String msg) {
		print(msg + "\n");
	}

}
