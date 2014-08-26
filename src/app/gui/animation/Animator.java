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

package app.gui.animation;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.SwingUtilities;

import app.map.Map;

/**
 * Animator
 * 
 * @author Saul Pina - sauljp07@gmail.com
 */
public class Animator implements Runnable, MouseWheelListener, MouseMotionListener, MouseListener {

	public static final int ZOOM_PROPORTION = 20;

	private Vector<Animated> animateds;
	private int FPS;
	private boolean pause;
	private Thread thread;
	private boolean stop;
	private BufferedImage image;
	private Graphics2D graphics;
	private boolean antialiasing;
	private Canvas canvas;
	private int height;
	private int width;
	private int translateX;
	private int translateY;
	private int zoomW;
	private int zoomH;
	private int mouseX;
	private int mouseY;
	private BufferedImage backImage;
	private Graphics2D backGraphics;
	private Map map;
	private int strokeSize;

	public int getStrokeSize() {
		return strokeSize;
	}

	public void setStrokeSize(int strokeSize) {
		this.strokeSize = strokeSize;
		refresh();
	}

	public void antialiasing() {
		if (graphics != null) {
			if (antialiasing) {
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			} else {
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			}
		}
	}

	public void setSize(int width, int height) {
		setHeight(height);
		setWidth(width);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setZoom(double zoom) {
		zoomW += zoom;
		zoomH += zoom;
		if (zoomWidth() <= 0 || zoomHeight() <= 0) {
			zoomW -= zoom;
			zoomH -= zoom;
		}
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Animator(Canvas canvas) {
		this.canvas = canvas;
		FPS = 24;
		strokeSize = 1;
		canvas.addMouseWheelListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		animateds = new Vector<Animated>();
		initState();
		thread = new Thread(this);
		canvas.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				refresh();
			}
		});
	}

	public void initState() {
		width = 0;
		height = 0;
		translateX = offsetWidth();
		translateY = offsetHeight();
	}

	public int offsetWidth() {
		return zoomWidth() / 2;
	}

	public int offsetHeight() {
		return zoomHeight() / 2;
	}

	public int zoomWidth() {
		return (width + width * zoomW / ZOOM_PROPORTION);
	}

	public int zoomHeight() {
		return (height + height * zoomH / ZOOM_PROPORTION);
	}

	public boolean isAntialiasing() {
		return antialiasing;
	}

	public void setAntialiasing(boolean antialiasing) {
		this.antialiasing = antialiasing;
		refresh();
	}

	public Vector<Animated> getAnimateds() {
		return animateds;
	}

	public boolean isPause() {
		return pause;
	}

	public boolean isStop() {
		return stop;
	}

	public synchronized void addAnimated(Animated animated) {
		animateds.add(animated);
		animated.init();
	}

	public synchronized void removeAnimated(Animated animated) {
		animateds.remove(animated);
	}

	public int getFPS() {
		return FPS;
	}

	public void setFPS(int FPS) {
		this.FPS = FPS;
	}

	public synchronized void refresh() {
		if (width > 0 && height > 0) {
			image = new BufferedImage(width, height, 1);
			graphics = (Graphics2D) image.getGraphics();
			graphics.setBackground(Color.WHITE);
			BasicStroke stroke = new BasicStroke(strokeSize);
			graphics.setStroke(stroke);
		}
		backImage = new BufferedImage(canvas.getWidth(), canvas.getHeight(), 1);
		backGraphics = (Graphics2D) backImage.getGraphics();
		backGraphics.setBackground(Color.GRAY);

		antialiasing();
	}

	public synchronized void start() {
		if (!thread.isAlive()) {
			refresh();
			thread.start();
		}
	}

	public synchronized void stop() {
		stop = true;
		pause = true;
		try {
			thread.join(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void pause() {
		pause = true;
	}

	public synchronized void resume() {
		pause = false;
	}

	@Override
	public void run() {
		stop = false;
		pause = false;
		while (!stop) {
			sortAnimateds();

			rendering();

			if (!pause) {
				animate();
			}

			try {
				Thread.sleep(1000 / FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void sortAnimateds() {
		for (int i = 1; i < animateds.size(); i++) {
			Animated aux = animateds.get(i);
			int j;
			for (j = i - 1; j >= 0 && animateds.get(j).getZIndex() > aux.getZIndex(); j--) {
				animateds.set(j + 1, animateds.get(j));
			}
			animateds.set(j + 1, aux);
		}
	}

	public synchronized void animate() {
		for (int i = 0; i < animateds.size(); i++) {
			animateds.get(i).animate();
		}
	}

	public synchronized void rendering() {
		backGraphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		if (width > 0 && height > 0) {
			graphics.clearRect(0, 0, width, height);
			for (int i = 0; i < animateds.size(); i++) {
				Animated animate = animateds.get(i);
				animate.paint(graphics);
			}
			backGraphics.drawImage(image, translateX - offsetWidth(), translateY - offsetHeight(), zoomWidth(), zoomHeight(), null);
		}
		canvas.getGraphics().drawImage(backImage, 0, 0, null);
	}

	public synchronized void restart() {
		refresh();
		initAnimateds();
	}

	public synchronized void initAnimateds() {
		for (int i = 0; i < animateds.size(); i++) {
			animateds.get(i).init();
		}
	}

	public void setTranslate(int x, int y) {
		translateX = translateX + x;
		translateY = translateY + y;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		setZoom(-e.getPreciseWheelRotation());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			setTranslate(e.getX() - mouseX, e.getY() - mouseY);
			mouseX = e.getX();
			mouseY = e.getY();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			mouseX = e.getX();
			mouseY = e.getY();
		}
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

	public void removeAnimateds() {
		animateds.removeAllElements();
	}

	public void setSizeAndRefresh(int width, int height) {
		setSize(width, height);
		refresh();
	}

	public void centerMap() {
		zoomH = 0;
		zoomW = 0;
		translateX = canvas.getWidth() / 2;
		translateY = canvas.getHeight() / 2;
	}

	public void showMap(Map map) {
		this.map = map;
		removeAnimateds();
		addAnimated(map);
		setSize(map.getCanvasWidth(), map.getCanvasHeight());
		centerMap();
		refresh();
	}

	public void updateMapProportion(int proportion) {
		if (map == null)
			return;
		map.setProportion(proportion);
		setSizeAndRefresh(map.getCanvasWidth(), map.getCanvasHeight());
		centerMap();
	}

	public synchronized BufferedImage getImage() {
		if (image == null)
			return null;
		BufferedImage bi = new BufferedImage(width, height, 1);
		bi.getGraphics().drawImage(image, 0, 0, null);
		return bi;
	}

	public Map getMap() {
		return map;
	}
}
