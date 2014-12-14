/**
 * 
 * AnimatedMouseListener.java
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

public interface AnimatedMouseListener {

	public void mousePressed();

	public void mouseDragged(int translateX, int translateY);

	public void mouseEntered();

	public void mouseExited();
}
