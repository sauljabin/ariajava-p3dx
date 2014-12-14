/**
 * 
 * TestConnect.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
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
