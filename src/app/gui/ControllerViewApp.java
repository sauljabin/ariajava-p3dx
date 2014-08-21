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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
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
import app.gui.animation.Animated;
import app.gui.animation.Animator;
import app.map.Line;
import app.map.Map;
import app.util.ClassW;

public class ControllerViewApp implements ActionListener, ChangeListener, MouseWheelListener, MouseMotionListener, MouseListener {

	private ViewApp viewApp;
	private DefaultComboBoxModel<ClassW> modelCmbArch;
	private ArArchitecture arch;
	private Animator animator;
	private int mouseX;
	private int mouseY;

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

		viewApp.getCanvasAnimation().addMouseWheelListener(this);
		viewApp.getCanvasAnimation().addMouseListener(this);
		viewApp.getCanvasAnimation().addMouseMotionListener(this);
		
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

		viewApp.getCanvasAnimation().setSize(1500, 2000);

		animator = new Animator(viewApp.getCanvasAnimation());
		viewApp.getSpnFPS().setValue(animator.getFPS());
		animator.addAnimated(new Animated() {
			int x;

			@Override
			public void setAnimator(Animator animator) {
				// TODO Auto-generated method stub

			}

			@Override
			public void paint(Graphics2D g) {

				g.setColor(Color.BLACK);
				g.fillOval(10, 10, 100, 100);

			}

			@Override
			public void init() {
				// TODO Auto-generated method stub

			}

			@Override
			public int getZIndex() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Animator getAnimator() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void animate() {
				

			}
		});
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
			for (Line line : map.getLines()) {
				animator.addAnimated(line);
			}
		} catch (IOException e) {
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

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		animator.setScale(animator.getScale() - e.getPreciseWheelRotation() / 10);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		animator.setTranslate(e.getX() - mouseX, e.getY() - mouseY);
		
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
