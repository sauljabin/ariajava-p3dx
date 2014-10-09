package app.animation;

public interface AnimatedMouseListener {
	
	public void mousePressed(double x, double y);

	public void mouseDragged(double x, double y);
	
	public void mouseEntered();
	
	public void mouseExited();
}
