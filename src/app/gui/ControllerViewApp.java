/**
 * 
 * ControllerViewApp.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import app.animation.Animator;
import app.animation.RobotInfoPanel;
import app.aria.architecture.ArArchitecture;
import app.aria.architecture.ArUpdaterPositionAnimation;
import app.aria.architecture.aura.ArArchitectureAuRA;
import app.aria.architecture.aura.ArMisionPlanner;
import app.aria.architecture.reactive.ArArchitectureReactive;
import app.aria.connection.ArConnector;
import app.aria.exception.ArException;
import app.aria.robot.ArRobotMobile;
import app.map.Goal;
import app.map.Robot;
import app.map.Map;
import app.map.Start;
import app.util.ClassW;
import app.util.UtilFile;
import app.util.UtilImage;

public class ControllerViewApp implements ActionListener, ChangeListener {

	private ViewApp viewApp;
	private DefaultComboBoxModel<ClassW> modelCmbArch;
	private ArArchitecture arch;
	private Animator animator;
	private Map map;
	private Robot anRobot;
	private ArRobotMobile robot;
	private ArConnector connector;
	public static final int TRANSLATE = 20;
	public static final int ZOOM = 1;
	public ArUpdaterPositionAnimation updaterPosition;
	private boolean showPath;
	private RobotInfoPanel robotInfoPanel;
	private ArMisionPlanner arMisionPlanner;

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
		archs.add(new ClassW(ArArchitectureAuRA.class, "AuRA"));
		archs.add(new ClassW(ArArchitectureReactive.class, "Reactive"));

		modelCmbArch = new DefaultComboBoxModel<ClassW>(archs);
		viewApp.getCmbArch().setModel(modelCmbArch);

		viewApp.getTxtHost().setText(Config.get("HOST_SERVER"));
		viewApp.getTxtPort().setText(Config.get("HOST_PORT"));
		viewApp.getBtnDisconnect().setEnabled(false);

		animator = new Animator(viewApp.getCanvasAnimation());
		animator.enableMouseListenerAnimated(true);
		setAntialiasing(Boolean.parseBoolean(Config.get("ANIMATION_ANTIALIASING")));
		animator.setStrokeSize(Integer.parseInt(Config.get("ANIMATION_STROKESIZE")));
		animator.setFPS(Integer.parseInt(Config.get("ANIMATION_FPS")));
		animator.start();

		viewApp.getSpnFPS().setModel(new SpinnerNumberModel(Integer.parseInt(Config.get("ANIMATION_FPS")), 1, 100, 1));
		int proportion = Integer.parseInt(Config.get("ANIMATION_PROPORTION"));
		int minProportion = Integer.parseInt(Config.get("ANIMATION_MINPROPORTION"));
		if (proportion < minProportion)
			proportion = minProportion;
		viewApp.getSpnProportion().setModel(new SpinnerNumberModel(proportion, minProportion, 100, 1));
		viewApp.getSpnStrokeSize().setModel(new SpinnerNumberModel(Integer.parseInt(Config.get("ANIMATION_STROKESIZE")), 1, 100, 1));

		int initMapSize = 6000;
		map = new Map(-initMapSize, initMapSize, -initMapSize, initMapSize);
		arMisionPlanner = new ArMisionPlanner();
		arMisionPlanner.setStart(new Start(map, 0, 0, 0));
		arMisionPlanner.setGoal(new Goal(map, ArRobotMobile.LONG * 2, 0, 0));
		arMisionPlanner.setMap(map);
		setShowPath(Boolean.parseBoolean(Config.get("ANIMATION_SHOWPATH")));
		animator.showMap(arMisionPlanner);
		updateStartEndPoint();
		viewApp.getSpnMaxSpeed().setModel(new SpinnerNumberModel(Integer.parseInt(Config.get("ROBOT_MAXSPEED")), 1, 500, 1));

		viewApp.getCanvasAnimation().addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				updateStartEndPoint();
			}
		});

		viewApp.getSpnPositionUpdate().setModel(new SpinnerNumberModel(Integer.parseInt(Config.get("ANIMATION_POSITIONUPDATERATE")), 1, 100, 1));
		viewApp.getSpnSleepTime().setModel(new SpinnerNumberModel(Integer.parseInt(Config.get("ROBOT_SLEEPTIME")), 1, 500, 1));
		viewApp.getSpnErrorDistance().setModel(new SpinnerNumberModel(Double.parseDouble(Config.get("ROBOT_ERRORDISTANCE")), 0., 100., .1));
		viewApp.getSpnErrorAngle().setModel(new SpinnerNumberModel(Double.parseDouble(Config.get("ROBOT_ERRORANGLE")), 0., 10., .1));
		viewApp.getSpnStopDistance().setModel(new SpinnerNumberModel(Integer.parseInt(Config.get("ROBOT_STOPDISTANCE")), 1, 1000, 1));
		viewApp.getSpnSonarAngle().setModel(new SpinnerNumberModel(Integer.parseInt(Config.get("ROBOT_SONARANGLE")), 1, 180, 1));
	}

	public void updateStartEndPoint() {
		viewApp.getSpnEndX().setValue(arMisionPlanner.getGoal().getX());
		viewApp.getSpnEndY().setValue(arMisionPlanner.getGoal().getY());
		viewApp.getSpnEndAngle().setValue(arMisionPlanner.getGoal().getAngle());
		viewApp.getSpnInitX().setValue(arMisionPlanner.getStart().getX());
		viewApp.getSpnInitY().setValue(arMisionPlanner.getStart().getY());
		viewApp.getSpnInitAngle().setValue(arMisionPlanner.getStart().getAngle());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source.equals(viewApp.getMenuItemClose()))
			close();
		else if (source.equals(viewApp.getMenuItemAbout()))
			about();
		else if (source.equals(viewApp.getMenuItemDoc()))
			documentation();
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
		else if (e.getSource().equals(viewApp.getBtnShowHidePath()))
			setShowPath(!map.isShowPath());
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

	public void documentation() {
		try {
			Desktop.getDesktop().open(new File(Config.get("APP_DOCUMENTACION")));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Translate.get("ERROR_OPENDOCUMENTATION") + "\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
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

	public void setShowPath(boolean showPath) {
		map.setShowPath(showPath);
		this.showPath = showPath;
		if (showPath) {
			viewApp.getBtnShowHidePath().setIcon(new ImageIcon(Theme.getIconPath("HIDE_PATH")));
			viewApp.getBtnShowHidePath().setToolTipText(Translate.get("HIDE_PATH"));
		} else {
			viewApp.getBtnShowHidePath().setIcon(new ImageIcon(Theme.getIconPath("SHOW_PATH")));
			viewApp.getBtnShowHidePath().setToolTipText(Translate.get("SHOW_PATH"));
		}

		Config.set("ANIMATION_SHOWPATH", String.valueOf(showPath));
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

		arMisionPlanner = new ArMisionPlanner();

		try {
			arMisionPlanner.load(path.getAbsolutePath());
			map = arMisionPlanner.getMap();
			map.setShowPath(showPath);
			map.setProportion((int) viewApp.getSpnProportion().getValue());
			animator.showMap(arMisionPlanner);
		} catch (Exception e) {
			Log.error(getClass(), Translate.get("ERROR_MAPLOADED"), e);
			e.printStackTrace();
		}

		Log.info(getClass(), Translate.get("INFO_MAPLOADED") + ": " + fileName);
		updateStartEndPoint();
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
			updaterPosition.stop();
			robotInfoPanel.stopInfoPanel();
			arch.stop();
			connector.close();
			animator.enableMouseListenerAnimated(true);
		}
		viewApp.getTxtPort().setEnabled(true);
		viewApp.getTxtHost().setEnabled(true);
		viewApp.getBtnDisconnect().setEnabled(false);
		viewApp.getBtnConnect().setEnabled(true);
		viewApp.getCmbArch().setEnabled(true);
		viewApp.getMenuItemLoadMap().setEnabled(true);

		viewApp.getSpnEndX().setEnabled(true);
		viewApp.getSpnEndY().setEnabled(true);
		viewApp.getSpnEndAngle().setEnabled(true);
		viewApp.getSpnInitX().setEnabled(true);
		viewApp.getSpnInitY().setEnabled(true);
		viewApp.getSpnInitAngle().setEnabled(true);
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

		robot = new ArRobotMobile(arMisionPlanner.getStart().getX(), arMisionPlanner.getStart().getY(), arMisionPlanner.getStart().getAngle());
		setRobotMaxSpeed();
		setRobotSleepTime();
		setRobotErrorAngle();
		setRobotErrorDistance();
		setRobotStopDistance();
		setRobotSonarAngle();
		updaterPosition = new ArUpdaterPositionAnimation(robot, Integer.parseInt(Config.get("ANIMATION_POSITIONUPDATERATE")));

		if (anRobot != null) {
			animator.removeAnimated(anRobot);
		}
		anRobot = new Robot(map);
		anRobot.updateAnimatedPosition(arMisionPlanner.getStart().getX(), arMisionPlanner.getStart().getY(), arMisionPlanner.getStart().getAngle());
		animator.addAnimated(anRobot);
		robot.setAnimatedRobot(anRobot);

		robotInfoPanel = new RobotInfoPanel(robot);

		animator.setInfoPanel(robotInfoPanel);

		connector = new ArConnector(host, port, robot);

		try {
			connector.connect();
		} catch (ArException e) {
			Log.error(getClass(), Translate.get("INFO_UNSUCCESSFULCONN"), e);
			return;
		}

		if (classArch.getValue().equals(ArArchitectureAuRA.class)) {
			arch = new ArArchitectureAuRA(arMisionPlanner, robot);
		} else if (classArch.getValue().equals(ArArchitectureReactive.class)) {
			arch = new ArArchitectureReactive(robot);
		} else {
			Log.error(getClass(), Translate.get("ERROR_NOARCHINSTANCE"));
			return;
		}

		try {
			arch.start();
			updaterPosition.start();
			animator.enableMouseListenerAnimated(false);
			robotInfoPanel.initInfoPanel();
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

		viewApp.getSpnEndX().setEnabled(false);
		viewApp.getSpnEndY().setEnabled(false);
		viewApp.getSpnEndAngle().setEnabled(false);
		viewApp.getSpnInitX().setEnabled(false);
		viewApp.getSpnInitY().setEnabled(false);
		viewApp.getSpnInitAngle().setEnabled(false);
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
			setAnimationFPS();
		else if (e.getSource().equals(viewApp.getSpnProportion()))
			setAnimationProportion();
		else if (e.getSource().equals(viewApp.getSpnStrokeSize()))
			setAnimationStrokeSize();
		else if (e.getSource().equals(viewApp.getSpnMaxSpeed()))
			setRobotMaxSpeed();
		else if (e.getSource().equals(viewApp.getSpnSleepTime()))
			setRobotSleepTime();
		else if (e.getSource().equals(viewApp.getSpnStopDistance()))
			setRobotStopDistance();
		else if (e.getSource().equals(viewApp.getSpnErrorAngle()))
			setRobotErrorAngle();
		else if (e.getSource().equals(viewApp.getSpnErrorDistance()))
			setRobotErrorDistance();
		else if (e.getSource().equals(viewApp.getSpnSonarAngle()))
			setRobotSonarAngle();
		else if (e.getSource().equals(viewApp.getSpnInitAngle()))
			setStartAngle();
		else if (e.getSource().equals(viewApp.getSpnInitX()))
			setStartX();
		else if (e.getSource().equals(viewApp.getSpnInitY()))
			setStartY();
		else if (e.getSource().equals(viewApp.getSpnEndAngle()))
			setEndAngle();
		else if (e.getSource().equals(viewApp.getSpnEndX()))
			setEndX();
		else if (e.getSource().equals(viewApp.getSpnEndY()))
			setEndY();
		else if (e.getSource().equals(viewApp.getSpnPositionUpdate()))
			setPositionUpdateRate();
	}

	public void setPositionUpdateRate() {

		Config.set("ANIMATION_POSITIONUPDATERATE", viewApp.getSpnPositionUpdate().getValue().toString());
		try {
			Config.save();
		} catch (Exception e) {
			Log.warning(ControllerViewApp.class, Translate.get("ERROR_NOSAVECONFIG"), e);
		}

		if (updaterPosition == null)
			return;
		updaterPosition.setUpdateAnimatedPositionRate(((int) viewApp.getSpnPositionUpdate().getValue()));

	}

	public void setEndY() {
		arMisionPlanner.getGoal().setY((int) viewApp.getSpnEndY().getValue());
	}

	public void setEndX() {
		arMisionPlanner.getGoal().setX((int) viewApp.getSpnEndX().getValue());
	}

	public void setEndAngle() {
		arMisionPlanner.getGoal().setAngle(Double.valueOf(viewApp.getSpnEndAngle().getValue().toString()));
	}

	public void setStartY() {
		arMisionPlanner.getStart().setY((int) viewApp.getSpnInitY().getValue());
	}

	public void setStartX() {
		arMisionPlanner.getStart().setX((int) viewApp.getSpnInitX().getValue());
	}

	public void setStartAngle() {
		arMisionPlanner.getStart().setAngle(Double.valueOf(viewApp.getSpnInitAngle().getValue().toString()));
	}

	public void setRobotMaxSpeed() {
		if (arMisionPlanner != null) {
			arMisionPlanner.setRobotMaxSpeed((int) viewApp.getSpnMaxSpeed().getValue());
			Config.set("ROBOT_MAXSPEED", viewApp.getSpnMaxSpeed().getValue().toString());
			try {
				Config.save();
			} catch (Exception e) {
				Log.warning(ControllerViewApp.class, Translate.get("ERROR_NOSAVECONFIG"), e);
			}

		}
	}

	public void setRobotSonarAngle() {
		if (arMisionPlanner != null) {
			arMisionPlanner.setRobotSonarAngle((int) viewApp.getSpnSonarAngle().getValue());
			Config.set("ROBOT_SONARANGLE", viewApp.getSpnSonarAngle().getValue().toString());
			try {
				Config.save();
			} catch (Exception e) {
				Log.warning(ControllerViewApp.class, Translate.get("ERROR_NOSAVECONFIG"), e);
			}

		}
	}

	public void setRobotSleepTime() {
		if (arMisionPlanner != null) {
			arMisionPlanner.setRobotSleepTime((int) viewApp.getSpnSleepTime().getValue());
			Config.set("ROBOT_SLEEPTIME", viewApp.getSpnSleepTime().getValue().toString());
			try {
				Config.save();
			} catch (Exception e) {
				Log.warning(ControllerViewApp.class, Translate.get("ERROR_NOSAVECONFIG"), e);
			}

		}
	}

	public void setRobotStopDistance() {
		if (arMisionPlanner != null) {
			arMisionPlanner.setRobotStopDistance((int) viewApp.getSpnStopDistance().getValue());
			Config.set("ROBOT_STOPDISTANCE", viewApp.getSpnStopDistance().getValue().toString());
			try {
				Config.save();
			} catch (Exception e) {
				Log.warning(ControllerViewApp.class, Translate.get("ERROR_NOSAVECONFIG"), e);
			}

		}
	}

	public void setRobotErrorDistance() {
		if (arMisionPlanner != null) {
			arMisionPlanner.setRobotErrorDistance((double) viewApp.getSpnErrorDistance().getValue());
			Config.set("ROBOT_ERRORDISTANCE", viewApp.getSpnErrorDistance().getValue().toString());
			try {
				Config.save();
			} catch (Exception e) {
				Log.warning(ControllerViewApp.class, Translate.get("ERROR_NOSAVECONFIG"), e);
			}

		}
	}

	public void setRobotErrorAngle() {
		if (arMisionPlanner != null) {
			arMisionPlanner.setRobotErrorAngle((double) viewApp.getSpnErrorAngle().getValue());
			Config.set("ROBOT_ERRORANGLE", viewApp.getSpnErrorAngle().getValue().toString());
			try {
				Config.save();
			} catch (Exception e) {
				Log.warning(ControllerViewApp.class, Translate.get("ERROR_NOSAVECONFIG"), e);
			}

		}
	}

	public void setAnimationFPS() {
		animator.setFPS((int) viewApp.getSpnFPS().getValue());
		Config.set("ANIMATION_FPS", viewApp.getSpnFPS().getValue().toString());
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
