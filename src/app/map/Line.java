package app.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import app.gui.animation.Animated;
import app.gui.animation.Animator;

public class Line implements Animated {

	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private Animator animator;

	public Line() {

	}

	public Line(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	@Override
	public String toString() {
		return String.format("Line [x1=%s, y1=%s, x2=%s, y2=%s]", x1, y1, x2, y2);
	}

	@Override
	public void init() {

	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawLine(x1, y1, x2, y2);
		
		AffineTransform transformer = new AffineTransform();
		transformer.scale(.5,.5);
		g.setTransform(transformer);
	}

	@Override
	public void animate() {

	}

	@Override
	public int getZIndex() {
		return 1;
	}

	@Override
	public void setAnimator(Animator animator) {
		this.animator = animator;
	}

	@Override
	public Animator getAnimator() {
		return animator;
	}

}
