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

package app;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		if (args.length > 0) {
			for (String arg : args) {
				switch (arg) {
				case "-console":
					commandConsole();
					break;
				case "-help":
				default:
					commandHelp();
					break;
				}
			}
		} else {
			initGUI();
		}
	}

	private static void commandConsole() {
		loadFeatures();
		System.out.println(Config.get("APP_NAME"));
		System.out.println(Translate.get("CONSOLE_MSGSELECTARCH"));

	}

	private static void commandHelp() {
		loadFeatures();
		System.out.println(Config.get("APP_NAME"));
		System.out.println(String.format("%10s\t%s", "-help", Translate.get("CONSOLE_MSGSHOWHELP")));
		System.out.println(String.format("%10s\t%s", "-console", Translate.get("CONSOLE_MSGEXECTCONSOLE")));
		System.out.println(String.format("%10s\t%s", "-gui", Translate.get("CONSOLE_MSGEXECTGUI")));
		System.out.println(String.format("%10s\t%s", "", Translate.get("CONSOLE_MSGPARAMEMPTY")));
		System.out.println();
	}

	private static void loadFeatures() {
		try {
			Config.load();
			Translate.load();
			Library.load();
		} catch (Exception e) {
			Log.error(Main.class, "loadFeatures()", e);
			System.exit(0);
		}
	}

	private static void loadFeaturesGUI() {
		try {
			Config.load();
			Translate.load();
			Library.load();
		} catch (Exception e) {
			Log.error(Main.class, "loadFeaturesGUI()", e);
			JOptionPane.showMessageDialog(null, "loadFeaturesGUI()", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	private static void initGUI() {
		loadFeaturesGUI();
	}

}
