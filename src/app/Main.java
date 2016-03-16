/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJava P3DX.
 * 
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
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
