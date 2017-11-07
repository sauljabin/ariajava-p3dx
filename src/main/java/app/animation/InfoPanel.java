/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.animation;

import java.awt.*;

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
