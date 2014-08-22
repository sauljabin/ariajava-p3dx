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
