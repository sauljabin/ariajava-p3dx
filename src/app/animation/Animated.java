/**
 * 
 * Animated.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.animation;

import java.awt.Graphics2D;
import java.awt.Shape;

public interface Animated {
	public void initAnimated();

	public void paint(Graphics2D g);

	public void animate();

	public boolean isVisible();
	
	public Shape getShape();
	
	public int getZ();
	
	public AnimatedMouseListener getAnimatedMouseListener();
}
