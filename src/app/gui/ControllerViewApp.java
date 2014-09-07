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
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import app.Config;
import app.Log;
import app.Theme;
import app.Translate;
import app.animation.AnimatedRobot;
import app.animation.Animator;
import app.aria.architecture.ArArchitecture;
import app.aria.architecture.ArArchitectureAuRA;
import app.aria.architecture.ArArchitectureReactive;
import app.aria.connection.ArConnector;
import app.aria.exception.ArException;
import app.aria.robot.ArRobotMobile;
import app.map.Map;
import app.map.RobotHome;
import app.util.ClassW;
import app.util.UtilFile;
import app.util.UtilImage;

public class ControllerViewApp implements ActionListener, ChangeListener {

	private ViewApp viewApp;
	private DefaultComboBoxModel<ClassW> modelCmbArch;
	private ArArchitecture arch;
	private Animator animator;
	private Map map;
	private AnimatedRobot anRobot;
	private ArRobotMobile robot;
	private ArConnector connector;
	public static final int TRANSLATE = 20;
	public static final int ZOOM = 1;

	public ControllerViewApp() {
		init();
	}

	public void init() {
		viewApp = new ViewApp();
		viewApp.setController(this);

		if (Boolean.parseBoolean(Config.get("GUI_MAXIMIZED")))
			viewApp.setExtendedState(JFrame.MAXIMIZED_BOTH);

		viewApp.setVisible(true);

		Log.setLogTextArea(viewApp.getTarConsole());

		viewApp.getCanvasAnimation().setBackground(Color.GRAY);

		Vector<ClassW> archs = new Vector<ClassW>();
		archs.add(new ClassW(ArArchitectureReactive.class, "Reactive"));
		archs.add(new ClassW(ArArchitectureAuRA.class, "AuRA"));

		modelCmbArch = new DefaultComboBoxModel<ClassW>(archs);
		viewApp.getCmbArch().setModel(modelCmbArch);

		viewApp.getTxtHost().setText(Config.get("HOST_SERVER"));
		viewApp.getTxtPort().setText(Config.get("HOST_PORT"));
		viewApp.getBtnDisconnect().setEnabled(false);

		animator = new Animator(viewApp.getCanvasAnimation());
		setAntialiasing(Boolean.parseBoolean(Config.get("ANIMATION_ANTIALIASING")));
		animator.setStrokeSize(Integer.parseInt(Config.get("ANIMATION_STROKESIZE")));
		animator.start();

		viewApp.getSpnFPS().setModel(new SpinnerNumberModel(animator.getFPS(), 1, 100, 1));
		viewApp.getSpnProportion().setModel(new SpinnerNumberModel(Integer.parseInt(Config.get("ANIMATION_PROPORTION")), 1, 100, 1));
		viewApp.getSpnStrokeSize().setModel(new SpinnerNumberModel(Integer.parseInt(Config.get("ANIMATION_STROKESIZE")), 1, 100, 1));
		viewApp.getSpnPositionUpdate().setModel(new SpinnerNumberModel(Integer.parseInt(Config.get("ANIMATION_POSITIONUPDATERATE")), 1, 100, 1));

		int initMapSize = 6000;

		map = new Map(new RobotHome(0, 0, 0), -initMapSize, initMapSize, -initMapSize, initMapSize);
		animator.showMap(map);
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
		else if (source.equals(viewApp.getBtnConnect()))
			connect();
		else if (source.equals(viewApp.getBtnDisconnect()))
			disconnect();
		else if (source.equals(viewApp.getMenuItemLoadMap()))
			loadMap();
		else if (e.getSource().equals(viewApp.getBtnAntialiasingOnOff()))
			setAntialiasing(!animator.isAntialiasing());
		else if (source.equals(viewApp.getBtnSaveImage()))
			saveImage();
		else if (source.equals(viewApp.getBtnCenterMap()))
			centerMap();
		else if (source.equals(viewApp.getBtnArrowDown()))
			translateMap(0, TRANSLATE);
		else if (source.equals(viewApp.getBtnArrowUp()))
			translateMap(0, -TRANSLATE);
		else if (source.equals(viewApp.getBtnArrowLeft()))
			translateMap(-TRANSLATE, 0);
		else if (source.equals(viewApp.getBtnArrowRight()))
			translateMap(TRANSLATE, 0);
		else if (source.equals(viewApp.getBtnZoomIn()))
			zoomMap(ZOOM);
		else if (source.equals(viewApp.getBtnZoomOut()))
			zoomMap(-ZOOM);
	}

	public void zoomMap(int zoom) {
		animator.setZoom(zoom);
	}

	public void translateMap(int x, int y) {
		animator.setTranslate(x, y);
	}

	public void centerMap() {
		animator.centerMap();
	}

	public void saveImage() {
		BufferedImage bImage = animator.getImage();

		if (bImage == null) {
			Log.warning(getClass(), Translate.get("INFO_NOMAPLOADED"));
			return;
		}

		JFileChooser file = new JFileChooser();
		file.setCurrentDirectory(new File("."));
		file.setAcceptAllFileFilterUsed(false);
		file.setMultiSelectionEnabled(false);
		file.setFileFilter(new FileNameExtensionFilter("JPG", "jpg"));
		file.setFileFilter(new FileNameExtensionFilter("PNG", "png"));
		file.showSaveDialog(viewApp);
		File path = file.getSelectedFile();
		String ext = file.getFileFilter().getDescription().toLowerCase();

		if (path == null)
			return;

		String fileName = path.getAbsolutePath();

		if (!UtilFile.isFileType(fileName, ext))
			fileName += "." + ext;

		try {
			UtilImage ui = new UtilImage();
			ui.writeImage(bImage, fileName);
			Log.info(getClass(), Translate.get("INFO_SAVEIMAGE") + " " + fileName);
		} catch (Exception e) {
			Log.error(getClass(), Translate.get("ERROR_SAVEIMAGE"), e);
			return;
		}

	}

	public void setAntialiasing(boolean antialiasing) {
		animator.setAntialiasing(antialiasing);

		if (antialiasing) {
			viewApp.getBtnAntialiasingOnOff().setIcon(new ImageIcon(Theme.getIconPath("ANTIALIASING_OFF")));
			viewApp.getBtnAntialiasingOnOff().setToolTipText(Translate.get("GUI_ANTIALIASINGOFF"));
		} else {
			viewApp.getBtnAntialiasingOnOff().setIcon(new ImageIcon(Theme.getIconPath("ANTIALIASING_ON")));
			viewApp.getBtnAntialiasingOnOff().setToolTipText(Translate.get("GUI_ANTIALIASINGON"));

		}

		Config.set("ANIMATION_ANTIALIASING", String.valueOf(antialiasing));
		try {
			Config.save();
		} catch (Exception e) {
			Log.warning(ControllerViewApp.class, Translate.get("ERROR_NOSAVECONFIG"), e);
		}
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

		map = new Map();
		try {
			map.load(path.getAbsolutePath());
			map.setProportion((int) viewApp.getSpnProportion().getValue());
			animator.showMap(map);
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
			connector.close();
		}
		viewApp.getTxtPort().setEnabled(true);
		viewApp.getTxtHost().setEnabled(true);
		viewApp.getBtnDisconnect().setEnabled(false);
		viewApp.getBtnConnect().setEnabled(true);
		viewApp.getCmbArch().setEnabled(true);
		viewApp.getMenuItemLoadMap().setEnabled(true);
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

		if (map == null) {
			JOptionPane.showMessageDialog(viewApp, Translate.get("ERROR_MAPNOTLOADED"), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		robot = new ArRobotMobile(map.getRobotHome().getX(), map.getRobotHome().getY(), map.getRobotHome().getAngle());

		if (anRobot != null) {
			animator.removeAnimated(anRobot);
		}
		anRobot = new AnimatedRobot(map);
		anRobot.updateAnimatedPosition(map.getRobotHome().getX(), map.getRobotHome().getY(), map.getRobotHome().getAngle());
		animator.addAnimated(anRobot);

		robot.setAnimatedRobot(anRobot);

		connector = new ArConnector(host, port, robot);

		try {
			connector.connect();
		} catch (ArException e) {
			Log.error(getClass(), Translate.get("INFO_UNSUCCESSFULCONN"), e);
			return;
		}

		if (classArch.getValue().equals(ArArchitectureAuRA.class)) {
			arch = new ArArchitectureAuRA(robot, map);
		} else if (classArch.getValue().equals(ArArchitectureReactive.class)) {
			arch = new ArArchitectureReactive(robot, map);
		} else {
			Log.error(getClass(), Translate.get("ERROR_NOARCHINSTANCE"));
			return;
		}

		try {
			arch.start();
		} catch (ArException e) {
			Log.error(getClass(), Translate.get("INFO_UNSUCCESSFULARCHSTART"), e);
			return;
		}

		Log.info(getClass(), Translate.get("INFO_SUCCESSFULCONN") + " " + arch.getName());

		viewApp.getTxtPort().setEnabled(false);
		viewApp.getTxtHost().setEnabled(false);
		viewApp.getBtnDisconnect().setEnabled(true);
		viewApp.getBtnConnect().setEnabled(false);
		viewApp.getCmbArch().setEnabled(false);
		viewApp.getMenuItemLoadMap().setEnabled(false);
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
		if (e.getSource().equals(viewApp.getSpnFPS()))
			animator.setFPS((int) viewApp.getSpnFPS().getValue());
		else if (e.getSource().equals(viewApp.getSpnProportion()))
			setAnimationProportion();
		else if (e.getSource().equals(viewApp.getSpnStrokeSize()))
			setAnimationStrokeSize();
		else if (e.getSource().equals(viewApp.getSpnPositionUpdate()))
			setPositionUpdateRate();
	}

	public void setPositionUpdateRate() {
		if (arch == null)
			return;

		// anRobot.setUpdateAnimatedPositionRate(((int)
		// viewApp.getSpnPositionUpdate().getValue()));

		Config.set("ANIMATION_POSITIONUPDATERATE", viewApp.getSpnPositionUpdate().getValue().toString());

		try {
			Config.save();
		} catch (Exception e) {
			Log.warning(ControllerViewApp.class, Translate.get("ERROR_NOSAVECONFIG"), e);
		}

	}

	public void setAnimationProportion() {
		animator.updateMapProportion((int) viewApp.getSpnProportion().getValue());
		Config.set("ANIMATION_PROPORTION", viewApp.getSpnProportion().getValue().toString());
		try {
			Config.save();
		} catch (Exception e) {
			Log.warning(ControllerViewApp.class, Translate.get("ERROR_NOSAVECONFIG"), e);
		}
	}

	public void setAnimationStrokeSize() {
		animator.setStrokeSize((int) viewApp.getSpnStrokeSize().getValue());
		Config.set("ANIMATION_STROKESIZE", viewApp.getSpnStrokeSize().getValue().toString());
		try {
			Config.save();
		} catch (Exception e) {
			Log.warning(ControllerViewApp.class, Translate.get("ERROR_NOSAVECONFIG"), e);
		}
	}

}
