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
import java.io.Serializable;
import java.net.URI;

import javax.swing.JLabel;

/**
 * Url link
 * 
 * @author Saul Piña <sauljp07@gmail.com>
 * @version 1.0
 */
public class JUrlLinkLabel extends JLabel implements Serializable {

	public static final String VERSION = "1.0";
	private static final long serialVersionUID = 7594812715384885615L;

	private String url;

	/**
	 * Get url
	 * 
	 * @return String url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set the url link
	 * 
	 * @param url
	 *            Url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Set text and url
	 * 
	 * @param text
	 *            Text to show
	 * @param url
	 *            Url link
	 */
	public void setText(String text, String url) {
		setUrl(url);
		setText(text);
	}

	/**
	 * Set text and url
	 * 
	 * @param text
	 *            Text to show
	 * @param url
	 *            Url link
	 */
	public JUrlLinkLabel(String text, String url) {
		setText(text);
		setUrl(url);
		setForeground(Color.BLUE);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				goUrl();
			}
		});
	}

	/**
	 * Set the url link
	 * 
	 * @param url
	 *            Url
	 */
	public JUrlLinkLabel(String url) {
		this(url, url);
	}

	public JUrlLinkLabel() {
		this("Link", "");
	}

	/**
	 * Opens the url in the browser
	 */
	public void goUrl() {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
