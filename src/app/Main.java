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

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.mobilerobots.Aria.Aria;

import app.gui.ControllerViewApp;

public class Main {

	public static void main(String[] args) {
		initGUI();
	}

	private static void loadFeaturesGUI() {
		try {
			Config.load();
			Config.save();
			Log.setLevel(LogLevel.valueOf(Config.get("LOG_LEVEL")));
			Translate.load();
			Theme.load();
			Library.load();
			Aria.init();
		} catch (Exception e) {
			Log.error(Main.class, "loadFeaturesGUI()", e);
			JOptionPane.showMessageDialog(null, e.getClass().getName() + "\n" + e.getMessage(), "Error loadFeaturesGUI()", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	private static void initGUI() {
		loadFeaturesGUI();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ControllerViewApp();
			}
		});
	}

}
