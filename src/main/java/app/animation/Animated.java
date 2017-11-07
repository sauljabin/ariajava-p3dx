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

public interface Animated {
    public void initAnimated();

    public void paint(Graphics2D g);

    public void animate();

    public boolean isVisible();

    public Shape getShape();

    public int getZ();

    public AnimatedMouseListener getAnimatedMouseListener();
}
