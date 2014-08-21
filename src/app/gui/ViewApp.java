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

package app.gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.util.Vector;

import javax.swing.BorderFactory;
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
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

import app.Config;
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
	private JButton btnStartSimulation;
	private JButton btnStopSimulation;
	private JMenuItem menuItemLoadMap;
	private Canvas canvasAnimation;
	private JLabel lblFPS;
	private JSpinner spnFPS;

	private JPanel pnlAnimation;

	public ViewApp() {
		menuItems = new Vector<JMenuItem>();
		buttons = new Vector<JButton>();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		init();
		setLocationRelativeTo(this);
		setVisible(true);
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

		btnStartSimulation = new JButton(Translate.get("GUI_STARTSIMULATION"));
		btnStopSimulation = new JButton(Translate.get("GUI_STOPSIMULATION"));

		lblArch = new JLabel(Translate.get("GUI_ARCH"));
		cmbArch = new JComboBox<ClassW>();

		pnlConnection.add(lblHost, "width 100, height 25");
		pnlConnection.add(txtHost, "width 100, height 25, wrap");
		pnlConnection.add(lblPort, "grow, height 25");
		pnlConnection.add(txtPort, "grow, height 25, wrap 10");
		pnlConnection.add(lblArch, "width 100, height 25");
		pnlConnection.add(cmbArch, "width 100, height 25, wrap 10");
		pnlConnection.add(btnStartSimulation, "grow, height 25");
		pnlConnection.add(btnStopSimulation, "grow, height 25, wrap");

		lblFPS = new JLabel(Translate.get("GUI_FPS"));
		spnFPS = new JSpinner(new SpinnerNumberModel(24, 1, 100, 1));

		pnlAnimation = new JPanel();
		pnlAnimation.setLayout(new MigLayout());
		pnlAnimation.setBorder(BorderFactory.createTitledBorder(Translate.get("GUI_ANIMATION")));

		pnlAnimation.add(lblFPS, "width 100, height 25");
		pnlAnimation.add(spnFPS, "width 100, height 25, wrap");

		pnlWest = new JPanel();
		pnlWest.setLayout(new MigLayout());
		add(pnlWest, BorderLayout.WEST);
		pnlWest.add(pnlConnection, "wrap 10");
		pnlWest.add(pnlAnimation, "wrap 10");

		pnlCenter = new JPanel();
		pnlCenter.setLayout(new BorderLayout());
		pnlCenter.setBorder(BorderFactory.createEmptyBorder(8, 10, 0, 7));
		add(pnlCenter, BorderLayout.CENTER);

		canvasAnimation = new Canvas();
		canvasAnimation.setBackground(Color.WHITE);
		pnlCenter.add(canvasAnimation, BorderLayout.CENTER);

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

		buttons.add(btnStartSimulation);
		buttons.add(btnStopSimulation);
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

	public JButton getBtnStartSimulation() {
		return btnStartSimulation;
	}

	public JButton getBtnStopSimulation() {
		return btnStopSimulation;
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

}
