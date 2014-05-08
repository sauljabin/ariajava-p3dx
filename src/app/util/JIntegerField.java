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
