/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.animation;

public interface AnimatedMouseListener {

    public void mousePressed();

    public void mouseDragged(int translateX, int translateY);

    public void mouseEntered();

    public void mouseExited();
}
