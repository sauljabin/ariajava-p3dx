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

package app.aria.test;

import app.Config;
import app.Library;

import com.mobilerobots.Aria.ArLog;
import com.mobilerobots.Aria.ArRobot;
import com.mobilerobots.Aria.ArSimpleConnector;
import com.mobilerobots.Aria.ArUtil;
import com.mobilerobots.Aria.Aria;

public class TestConnect {

	static {
		try {
			Config.load();
			Library.load();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library libAriaJava failed to load. Make sure that its directory is in your library path. See javaExamples/README.txt and the chapter on Dynamic Linking Problems in the SWIG Java Documentation (http://www.swig.org) for help.\n" + e);
			System.exit(1);
		}
	}

	public static void main(String argv[]) {
		Aria.init();

		ArSimpleConnector conn = new ArSimpleConnector(new String[] { "-rrtp", String.format("%d", 8101), "-rh", "10.0.126.11" });
		ArRobot robot = new ArRobot();

		if (!Aria.parseArgs()) {
			Aria.logOptions();
			System.exit(1);
		}

		if (!conn.connectRobot(robot)) {
			ArLog.log(ArLog.LogLevel.Terse, "actionExample: Could not connect to robot! Exiting.");
			System.exit(2);
		}

		ArUtil.sleep(2000);

		Aria.exit(0);
	}
}
