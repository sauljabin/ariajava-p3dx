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

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import app.Config;
import app.Theme;
import app.Translate;
import app.util.ClassW;
import app.util.JIntegerField;
import net.miginfocom.swing.MigLayout;

public class ViewApp extends JFrame {

	private static final long serialVersionUID = 4806248606059318670L;

	private ControllerViewApp controller;
	private Vector<JMenuItem> menuItems;
	private Vector<JButton> buttons;
	private JMenuBar menuBar;
	private JMenu menuOptions;
	private JMenuItem menuItemShowConfig;
	private JMenuItem menuItemClose;
	private JMenu menuHelp;
	private JMenuItem menuItemAbout;
	private JPanel pnlConnection;
	private JLabel lblHost;
	private JLabel lblPort;
	private JTextField txtHost;
	private JIntegerField txtPort;
	private JPanel pnlWest;
	private JPanel pnlSouth;
	private JTextArea tarConsole;
	private JPanel pnlCenter;
	private JLabel lblArch;
	private JComboBox<ClassW> cmbArch;
	private JButton btnConnect;
	private JButton btnDisconnect;
	private JMenuItem menuItemLoadMap;
	private Canvas canvasAnimation;
	private JLabel lblFPS;
	private JSpinner spnFPS;
	private JPanel pnlAnimation;
	private JPanel pnlAnimationControl;
	private JButton btnZoomIn;
	private JButton btnZoomOut;
	private JButton btnSaveImage;
	private JButton btnArrowUp;
	private JButton btnArrowDown;
	private JButton btnArrowLeft;
	private JButton btnArrowRight;
	private JButton btnShowHidePath;
	private JButton btnCenterMap;
	private JButton btnAntialiasingOnOff;
	private JPanel pnlRobotControl;

	public ViewApp() {
		menuItems = new Vector<JMenuItem>();
		buttons = new Vector<JButton>();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		init();
		setLocationRelativeTo(this);
	}

	public ControllerViewApp getController() {
		return controller;
	}

	public void setController(ControllerViewApp controller) {
		this.controller = controller;
		for (int i = 0; i < menuItems.size(); i++) {
			menuItems.get(i).addActionListener(controller);
		}
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).addActionListener(controller);
		}
	}

	private void init() {
		setLayout(new BorderLayout());
		setSize(800, 600);
		setTitle(Config.get("APP_NAME"));

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menuOptions = new JMenu(Translate.get("GUI_OPTIONS"));
		menuBar.add(menuOptions);

		menuItemLoadMap = new JMenuItem(Translate.get("GUI_LOADMAP"));
		menuOptions.add(menuItemLoadMap);

		menuItemShowConfig = new JMenuItem(Translate.get("GUI_SHOWCONFIG"));
		menuOptions.add(menuItemShowConfig);

		menuOptions.add(new JSeparator());

		menuItemClose = new JMenuItem(Translate.get("GUI_CLOSE"));
		menuOptions.add(menuItemClose);

		menuHelp = new JMenu(Translate.get("GUI_HELP"));
		menuBar.add(menuHelp);

		menuItemAbout = new JMenuItem(Translate.get("GUI_ABOUT"));
		menuHelp.add(menuItemAbout);

		pnlConnection = new JPanel();
		pnlConnection.setLayout(new MigLayout());
		pnlConnection.setBorder(BorderFactory.createTitledBorder(Translate.get("GUI_CONNECTION")));

		lblHost = new JLabel(Translate.get("GUI_HOST"));
		lblPort = new JLabel(Translate.get("GUI_PORT"));

		txtHost = new JTextField();
		txtPort = new JIntegerField();

		btnConnect = new JButton(Translate.get("GUI_CONNECT"));
		btnDisconnect = new JButton(Translate.get("GUI_DISCONNECT"));

		lblArch = new JLabel(Translate.get("GUI_ARCH"));
		cmbArch = new JComboBox<ClassW>();

		pnlConnection.add(lblHost, "width 100, height 25");
		pnlConnection.add(txtHost, "width 100, height 25, wrap");
		pnlConnection.add(lblPort, "grow, height 25");
		pnlConnection.add(txtPort, "grow, height 25, wrap 10");
		pnlConnection.add(btnConnect, "grow, height 25");
		pnlConnection.add(btnDisconnect, "grow, height 25, wrap");

		lblFPS = new JLabel(Translate.get("GUI_FPS"));
		spnFPS = new JSpinner();

		pnlAnimation = new JPanel();
		pnlAnimation.setLayout(new MigLayout());
		pnlAnimation.setBorder(BorderFactory.createTitledBorder(Translate.get("GUI_ANIMATION")));

		pnlAnimation.add(lblFPS, "width 100, height 25");
		pnlAnimation.add(spnFPS, "width 100, height 25, wrap");

		pnlRobotControl = new JPanel();
		pnlRobotControl.setLayout(new MigLayout());
		pnlRobotControl.setBorder(BorderFactory.createTitledBorder(Translate.get("GUI_CONTROL")));

		pnlRobotControl.add(lblArch, "width 100, height 25");
		pnlRobotControl.add(cmbArch, "width 100, height 25, wrap 10");

		pnlWest = new JPanel();
		pnlWest.setLayout(new MigLayout());
		add(pnlWest, BorderLayout.WEST);
		pnlWest.add(pnlAnimation, "wrap 10");
		pnlWest.add(pnlRobotControl, "wrap 10");
		pnlWest.add(pnlConnection, "wrap 10");

		pnlCenter = new JPanel();
		pnlCenter.setLayout(new BorderLayout());
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 7));
		add(pnlCenter, BorderLayout.CENTER);

		canvasAnimation = new Canvas();
		canvasAnimation.setBackground(Color.WHITE);
		pnlCenter.add(canvasAnimation, BorderLayout.CENTER);

		pnlAnimationControl = new JPanel(new MigLayout("insets 0 7 0 0"));

		btnZoomIn = new JButton(new ImageIcon(Theme.getIconPath("ZOOM_IN")));
		btnZoomIn.setToolTipText(Translate.get("GUI_ZOOMIN"));
		btnZoomOut = new JButton(new ImageIcon(Theme.getIconPath("ZOOM_OUT")));
		btnZoomOut.setToolTipText(Translate.get("GUI_ZOOMOUT"));

		btnArrowUp = new JButton(new ImageIcon(Theme.getIconPath("ARROW_UP")));
		btnArrowUp.setToolTipText(Translate.get("GUI_ARROWUP"));
		btnArrowDown = new JButton(new ImageIcon(Theme.getIconPath("ARROW_DOWN")));
		btnArrowDown.setToolTipText(Translate.get("GUI_ARROWDOWN"));
		btnArrowLeft = new JButton(new ImageIcon(Theme.getIconPath("ARROW_LEFT")));
		btnArrowLeft.setToolTipText(Translate.get("GUI_ARROWLEFT"));
		btnArrowRight = new JButton(new ImageIcon(Theme.getIconPath("ARROW_RIGHT")));
		btnArrowRight.setToolTipText(Translate.get("GUI_ARROWRIGHT"));

		btnSaveImage = new JButton(new ImageIcon(Theme.getIconPath("SAVE_IMAGE")));
		btnSaveImage.setToolTipText(Translate.get("GUI_SAVEIMAGE"));

		btnShowHidePath = new JButton(new ImageIcon(Theme.getIconPath("HIDE_PATH")));
		btnShowHidePath.setToolTipText(Translate.get("GUI_HIDEPATH"));

		btnCenterMap = new JButton(new ImageIcon(Theme.getIconPath("CENTER_MAP")));
		btnCenterMap.setToolTipText(Translate.get("GUI_CENTERMAP"));

		btnAntialiasingOnOff = new JButton(new ImageIcon(Theme.getIconPath("ANTIALIASING_ON")));
		btnAntialiasingOnOff.setToolTipText(Translate.get("GUI_ANTIALIASINGON"));

		pnlAnimationControl.add(btnZoomIn, "wrap");
		pnlAnimationControl.add(btnZoomOut, "wrap 15");
		pnlAnimationControl.add(btnArrowDown, "wrap");
		pnlAnimationControl.add(btnArrowUp, "wrap");
		pnlAnimationControl.add(btnArrowLeft, "wrap");
		pnlAnimationControl.add(btnArrowRight, "wrap 15");
		pnlAnimationControl.add(btnCenterMap, "wrap");
		pnlAnimationControl.add(btnSaveImage, "wrap 15");
		pnlAnimationControl.add(btnShowHidePath, "wrap");
		pnlAnimationControl.add(btnAntialiasingOnOff, "wrap");

		pnlCenter.add(pnlAnimationControl, BorderLayout.EAST);

		pnlSouth = new JPanel();
		pnlSouth.setLayout(new MigLayout());
		add(pnlSouth, BorderLayout.SOUTH);

		JScrollPane scrollPanelConsole = new JScrollPane();
		pnlSouth.add(scrollPanelConsole, "width 100%, height 100");
		tarConsole = new JTextArea();
		scrollPanelConsole.setViewportView(tarConsole);

		menuItems.add(menuItemShowConfig);
		menuItems.add(menuItemClose);
		menuItems.add(menuItemAbout);
		menuItems.add(menuItemLoadMap);

		buttons.add(btnConnect);
		buttons.add(btnDisconnect);
		buttons.add(btnAntialiasingOnOff);
		buttons.add(btnArrowDown);
		buttons.add(btnArrowLeft);
		buttons.add(btnArrowRight);
		buttons.add(btnArrowUp);
		buttons.add(btnCenterMap);
		buttons.add(btnSaveImage);
		buttons.add(btnShowHidePath);
		buttons.add(btnZoomIn);
		buttons.add(btnZoomOut);
	}

	public JMenuItem getMenuItemClose() {
		return menuItemClose;
	}

	public JMenuItem getMenuItemAbout() {
		return menuItemAbout;
	}

	public JMenuItem getMenuItemShowConfig() {
		return menuItemShowConfig;
	}

	public JTextField getTxtHost() {
		return txtHost;
	}

	public JIntegerField getTxtPort() {
		return txtPort;
	}

	public JTextArea getTarConsole() {
		return tarConsole;
	}

	public JComboBox<ClassW> getCmbArch() {
		return cmbArch;
	}

	public JButton getBtnConnect() {
		return btnConnect;
	}

	public JButton getBtnDisconnect() {
		return btnDisconnect;
	}

	public JMenuItem getMenuItemLoadMap() {
		return menuItemLoadMap;
	}

	public Canvas getCanvasAnimation() {
		return canvasAnimation;
	}

	public JSpinner getSpnFPS() {
		return spnFPS;
	}

	public JButton getBtnZoomIn() {
		return btnZoomIn;
	}

	public JButton getBtnZoomOut() {
		return btnZoomOut;
	}

	public JButton getBtnSaveImage() {
		return btnSaveImage;
	}

	public JButton getBtnArrowUp() {
		return btnArrowUp;
	}

	public JButton getBtnArrowDown() {
		return btnArrowDown;
	}

	public JButton getBtnArrowLeft() {
		return btnArrowLeft;
	}

	public JButton getBtnArrowRight() {
		return btnArrowRight;
	}

	public JButton getBtnShowHidePath() {
		return btnShowHidePath;
	}

	public JButton getBtnCenterMap() {
		return btnCenterMap;
	}

	public JButton getBtnAntialiasingOnOff() {
		return btnAntialiasingOnOff;
	}

}
