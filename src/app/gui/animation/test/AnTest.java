package app.gui.animation.test;

import java.awt.Color;
import java.awt.Graphics2D;

import app.gui.animation.Animated;
import app.gui.animation.Animator;

public class AnTest implements Animated {

	@Override
	public void init() {

	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(100, 100, 50, 50);
	}

	@Override
	public void animate() {

	}

	@Override
	public int getZIndex() {
		return 0;
	}

	@Override
	public void setAnimator(Animator animator) {

	}

}
