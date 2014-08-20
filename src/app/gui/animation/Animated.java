package app.gui.animation;

import java.awt.Graphics2D;

public interface Animated {
	public void init();

	public void paint(Graphics2D g);

	public void animate();

	public int getZIndex();
	
	public void setAnimator(Animator animator);
	
	public Animator getAnimator();
}
