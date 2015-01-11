/**
 * 
 * JUrlLinkLabel.java
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
	private static final Color colorEntered = new Color(60, 100, 255);
	private static final Color color = new Color(60, 80, 255);

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
		setForeground(color);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				goUrl();
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
	 * Set the url link
	 * 
	 * @param url
	 *            Url
	 */
	public JUrlLinkLabel(String url) {
		this(url, url);
	}

	public JUrlLinkLabel() {
		this("", "");
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
