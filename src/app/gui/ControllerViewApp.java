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

package app.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import app.Config;
import app.Log;
import app.Translate;
import app.aria.ArArchitecture;
import app.aria.architecture.aura.ArArchitectureAuRA;
import app.aria.architecture.reactive.ArArchitectureReactive;
import app.aria.exception.ArException;
import app.gui.animation.Animator;
import app.map.Map;
import app.util.ClassW;

public class ControllerViewApp implements ActionListener, ChangeListener {

	private ViewApp viewApp;
	private DefaultComboBoxModel<ClassW> modelCmbArch;
	private ArArchitecture arch;
	private Animator animator;

	public ControllerViewApp() {
		viewApp = new ViewApp();
		viewApp.setController(this);

		viewApp.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		Log.setLogTextArea(viewApp.getTarConsole());

		viewApp.getCanvasAnimation().setBackground(Color.GRAY);

		Vector<ClassW> archs = new Vector<ClassW>();
		archs.add(new ClassW(ArArchitectureReactive.class, "Reactive"));
		archs.add(new ClassW(ArArchitectureAuRA.class, "AuRA"));

		modelCmbArch = new DefaultComboBoxModel<ClassW>(archs);
		viewApp.getCmbArch().setModel(modelCmbArch);

		viewApp.getSpnFPS().addChangeListener(this);

		viewApp.getTxtHost().setText(Config.get("HOST_SERVER"));
		viewApp.getTxtPort().setText(Config.get("HOST_PORT"));
		viewApp.getBtnStopSimulation().setEnabled(false);

		animator = new Animator(viewApp.getCanvasAnimation());
		viewApp.getSpnFPS().setValue(animator.getFPS());
		animator.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source.equals(viewApp.getMenuItemClose()))
			close();
		else if (source.equals(viewApp.getMenuItemAbout()))
			about();
		else if (source.equals(viewApp.getMenuItemShowConfig()))
			showConfig();
		else if (source.equals(viewApp.getBtnStartSimulation()))
			connect();
		else if (source.equals(viewApp.getBtnStopSimulation()))
			disconnect();
		else if (source.equals(viewApp.getMenuItemLoadMap()))
			loadMap();
	}

	public void loadMap() {

		JFileChooser file = new JFileChooser();
		file.setCurrentDirectory(new File("."));
		file.setAcceptAllFileFilterUsed(false);
		file.setMultiSelectionEnabled(false);
		file.setFileFilter(new FileNameExtensionFilter("MAP", "map"));
		file.showOpenDialog(viewApp);

		File path = file.getSelectedFile();

		if (path == null)
			return;

		String fileName = path.getAbsolutePath();

		Map map = new Map();
		try {
			map.load(path.getAbsolutePath());
			animator.removeAnimateds();
			animator.addAnimated(map);
			animator.setSize(map.getScaledWidth(),map.getScaledHeight());
			animator.setSizeAndRefresh(map.getScaledWidth(),map.getScaledHeight());
		} catch (Exception e) {
			Log.error(getClass(), Translate.get("ERROR_MAPLOADED"), e);
			e.printStackTrace();
		}

		Log.info(getClass(), Translate.get("INFO_MAPLOADED") + ": " + fileName);

	}

	public void about() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ViewAbout();
			}
		});
	}

	public void disconnect() {
		if (arch != null) {
			arch.stop();
			Log.info(getClass(), Translate.get("INFO_CLOSECONN") + " " + arch.getName());
		}
		viewApp.getTxtPort().setEnabled(true);
		viewApp.getTxtHost().setEnabled(true);
		viewApp.getBtnStopSimulation().setEnabled(false);
		viewApp.getBtnStartSimulation().setEnabled(true);
		viewApp.getCmbArch().setEnabled(true);
	}

	public void connect() {
		Integer port = viewApp.getTxtPort().getValue();
		String host = viewApp.getTxtHost().getText();

		Config.set("HOST_SERVER", host);
		Config.set("HOST_PORT", port.toString());
		try {
			Config.save();
		} catch (Exception e) {
			Log.warning(ControllerViewApp.class, Translate.get("ERROR_NOSAVECONFIG"), e);
		}

		ClassW classArch = (ClassW) modelCmbArch.getSelectedItem();

		if (classArch.getValue().equals(ArArchitectureAuRA.class)) {
			arch = new ArArchitectureAuRA(host, port);
		} else if (classArch.getValue().equals(ArArchitectureReactive.class)) {
			arch = new ArArchitectureReactive(host, port);
		} else {
			Log.error(getClass(), Translate.get("ERROR_NOARCHINSTANCE"));
			return;
		}

		try {
			arch.start();
		} catch (ArException e) {
			Log.error(getClass(), Translate.get("INFO_UNSUCCESSFULCONN"), e);
			return;
		}

		Log.info(getClass(), Translate.get("INFO_SUCCESSFULCONN") + " " + arch.getName());

		viewApp.getTxtPort().setEnabled(false);
		viewApp.getTxtHost().setEnabled(false);
		viewApp.getBtnStopSimulation().setEnabled(true);
		viewApp.getBtnStartSimulation().setEnabled(false);
		viewApp.getCmbArch().setEnabled(false);
	}

	public void showConfig() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ViewConfig();
			}
		});
	}

	public void close() {
		animator.stop();
		disconnect();
		viewApp.dispose();
		System.exit(0);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		animator.setFPS((int) viewApp.getSpnFPS().getValue());
	}

}
