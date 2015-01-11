/**
 * 
 * JIntegerField.java
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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import javax.swing.JTextField;

public class JIntegerField extends JTextField implements Serializable {

	private static final long serialVersionUID = -3669579723172144525L;

	public JIntegerField() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Character.isDigit(e.getKeyChar())) {
					e.consume();
				}
			}
		});
	}

	public JIntegerField(Integer value) {
		setValue(value);
	}

	@Override
	public void setText(String text) {
		if (text.matches("[0-9]+"))
			super.setText(text);
		else
			throw new NumberFormatException("Not integer");
	}

	/**
	 * Return integer value in the field
	 * 
	 * @return Integer, null if is empty
	 */
	public Integer getValue() {
		if (getText() == null || getText().trim().isEmpty() || !getText().matches("[0-9]+"))
			return null;
		return new Integer(getText().trim());
	}

	public void setValue(Integer i) {
		setText(i.toString());
	}

}
