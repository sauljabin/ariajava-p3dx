package app.animation;

import java.awt.event.MouseEvent;

public interface AnimatedMouseListener {
	
	public void mousePressed(Animated source, MouseEvent e);

	public void mouseReleased(Animated source, MouseEvent e);

	public void mouseDragged(Animated source, MouseEvent e);
	
	public void mouseEntered(Animated source, MouseEvent e);
	
	public void mouseExited(Animated source, MouseEvent e);
}
