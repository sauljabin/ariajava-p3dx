/**
 * 
 * Copyright (c) 2014 Saul Piña <sauljp07@gmail.com>.
 * 
 * This file is part of Jelpers.
 *
 * Jelpers is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Jelpers is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Jelpers.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package app.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serializable;

import javax.swing.JLabel;

/**
 * 
 * 
 * @author Saul Piña <sauljp07@gmail.com>
 * @version 1.0
 */
public class JOpenFileLabel extends JLabel implements Serializable {

	public static final String VERSION = "1.0";
	private static final long serialVersionUID = 6806479127091499505L;
	private static final Color color = Color.BLACK;
	private static final Color colorEntered = Color.DARK_GRAY;

	private File file;

	/**
	 * Get file
	 * 
	 * @return file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Set File
	 * 
	 * @param file
	 *            File
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Set text and file
	 * 
	 * @param text
	 *            Text label
	 * @param file
	 *            File
	 */
	public void setText(String text, File file) {
		setFile(file);
		setText(text);
	}

	/**
	 * Set text and file
	 * 
	 * @param text
	 *            Text label
	 * @param file
	 *            File
	 */
	public JOpenFileLabel(String text, File file) {
		setText(text);
		setFile(file);
		setForeground(color);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				openFile();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				setForeground(colorEntered);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				setForeground(color);
			}
		});

	}

	/**
	 * Set the file
	 * 
	 * @param file
	 *            File
	 */
	public JOpenFileLabel(File file) {
		this(file.getName(), file);
	}

	public JOpenFileLabel() {
		this("", null);
	}

	/**
	 * Open file
	 */
	public void openFile() {
		try {
			if (file != null)
				Desktop.getDesktop().open(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
