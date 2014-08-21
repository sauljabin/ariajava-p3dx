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

import java.util.Vector;

import javax.swing.JOptionPane;

import app.aria.ArArchitecture;
import app.aria.architecture.aura.ArArchitectureAuRA;
import app.aria.architecture.reactive.ArArchitectureReactive;
import app.aria.exception.ArException;
import app.gui.ControllerViewApp;
import app.util.ClassW;
import app.util.UtilIn;

public class Main {

	public static void main(String[] args) {
		if (args.length > 0) {
			for (String arg : args) {
				switch (arg) {
				case "-console":
				case "-c":
					commandConsole();
					break;
				case "-gui":
				case "-g":
					initGUI();
					break;
				case "-help":
				case "-h":
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
		String host = UtilIn.readString(String.format("%s (%s): ", Translate.get("CONSOLE_MSGHOSTNAME"), Config.get("HOST_SERVER")));
		Integer port = UtilIn.readInteger(String.format("%s (%s): ", Translate.get("CONSOLE_MSGTCPPORT"), Config.get("HOST_PORT")));

		if (host != null && !host.isEmpty())
			Config.set("HOST_SERVER", host);
		if (port != null)
			Config.set("HOST_PORT", port.toString());

		try {
			Config.save();
		} catch (Exception e) {
			Log.warning(ControllerViewApp.class, Translate.get("ERROR_NOSAVECONFIG"), e);
		}

		host = Config.get("HOST_SERVER");
		port = new Integer(Config.get("HOST_PORT"));

		Vector<ClassW> archs = new Vector<>();
		archs.add(new ClassW(ArArchitectureReactive.class, "Reactive"));
		archs.add(new ClassW(ArArchitectureAuRA.class, "AuRA"));

		for (int i = 0; i < archs.size(); i++) {
			System.out.println(String.format("%d: %s", i + 1, archs.get(i).getName()));
		}

		Integer select = null;
		do {
			select = UtilIn.readInteger(String.format("%s: ", Translate.get("CONSOLE_MSGSETNUMBER")));
		} while (select == null || select > archs.size() || select <= 0);
		System.out.println();

		ClassW classArch = (ClassW) archs.get(select - 1);

		ArArchitecture arch = null;

		if (classArch.getValue().equals(ArArchitectureAuRA.class)) {
			arch = new ArArchitectureAuRA(host, port);
		} else if (classArch.getValue().equals(ArArchitectureReactive.class)) {
			arch = new ArArchitectureReactive(host, port);
		} else {
			Log.error(Main.class, Translate.get("ERROR_NOARCHINSTANCE"));
			return;
		}

		try {
			arch.start();
		} catch (ArException e) {
			Log.error(Main.class, Translate.get("INFO_UNSUCCESSFULCONN"), e);
			return;
		}

		Log.info(Main.class, Translate.get("INFO_SUCCESSFULCONN") + " " + arch.getName());

		String command = "";

		do {
			command = UtilIn.readString(Translate.get("CONSOLE_MSGQTOEXIT") + "\n");
		} while (!command.equals("q"));

		arch.stop();

		Log.info(Main.class, Translate.get("INFO_CLOSECONN") + " " + arch.getName());
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
			Config.save();
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
			Config.save();
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
		new ControllerViewApp();
	}

}
