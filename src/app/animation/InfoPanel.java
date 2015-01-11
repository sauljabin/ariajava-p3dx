/**
 * 
 * InfoPanel.java
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

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class InfoPanel {

	private Graphics2D temGraphics;
	private int line;
	private int lineSize = 20;
	private int lineMargin = 20;
	private Color color = Color.BLACK;

	public abstract void initInfoPanel();
	
	public abstract void stopInfoPanel();

	public abstract void printInfo();

	public abstract boolean isVisible();

	public void paint(Graphics2D g) {
		g.setColor(color);
		temGraphics = g;
		line = 1;
		printInfo();
	}

	public void printLine(String info) {
		if (temGraphics != null)
			temGraphics.drawString(info, lineMargin, lineSize * line);
		line++;
	}
}
