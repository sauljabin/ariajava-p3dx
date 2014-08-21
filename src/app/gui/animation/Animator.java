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
 */

package app.gui.animation;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * 
 * @author Saul Pina - sauljp07@gmail.com
 */
public class Animator implements Runnable, MouseWheelListener, MouseMotionListener, MouseListener {

	private Vector<Animated> animateds;
	private int FPS;
	private boolean pause;
	private Thread thread;
	private boolean stop;
	private Image image;
	private Graphics2D graphics;
	private boolean antialiasing;
	private Canvas canvas;
	private double scale;
	private int height;
	private int width;
	private int translateX;
	private int translateY;
	private int scaleW;
	private int scaleH;
	private int mouseX;
	private int mouseY;

	public void setSize(int height, int width) {
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

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		//if ((width >= Math.abs(width * (scaleW + scale) / 10) + Math.abs(width * scale / 10)) && (height >= Math.abs(height * (scaleH + scale) / 10) + Math.abs(width * scale / 10))) {
			this.scale = scale;
			scaleW += scale;
			scaleH += scale;
		//}
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Animator(Canvas canvas) {
		this.canvas = canvas;
		scale = 1;
		FPS = 24;
		width = canvas.getWidth();
		height = canvas.getHeight();
		translateX = (width + width * scaleW / 100) / 2;
		translateY = (height + height * scaleH / 100) / 2;

		canvas.addMouseWheelListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		animateds = new Vector<Animated>();
		thread = new Thread(this);
		canvas.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				makeImage();
			}
		});
	}

	public boolean isAntialiasing() {
		return antialiasing;
	}

	public void setAntialiasing(boolean antialiasing) {
		this.antialiasing = antialiasing;
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
		animated.setAnimator(this);
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

	public synchronized void makeImage() {
		image = new BufferedImage(width+100, height, 1);
		graphics = (Graphics2D) image.getGraphics();
		graphics.setBackground(Color.WHITE);		
		antialiasing();
	}

	public synchronized void start() {
		if (!thread.isAlive()) {
			makeImage();
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
		canvas.getGraphics().setColor(canvas.getBackground());
		canvas.getGraphics().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphics.clearRect(0, 0, width, height);
		for (int i = 0; i < animateds.size(); i++) {
			Animated animate = animateds.get(i);
			animate.paint(graphics);
		}
		canvas.getGraphics().drawImage(image, translateX - (width + width * scaleW / 100) / 2, translateY - (height + height * scaleH / 100) / 2, width + width * scaleW / 100, height + height * scaleH / 100, null);
	}

	public synchronized void restart() {
		makeImage();
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
		setScale(-e.getPreciseWheelRotation());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setTranslate(e.getX() - mouseX, e.getY() - mouseY);
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

	public void removeAnimateds() {
		animateds.removeAllElements();
	}
}
