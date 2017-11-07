/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.path.geometry;

public class Circle {

    private Point center;
    private Double radius;

    public Circle(Point center, Double radius) {
        super();
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public Double getRadius() {
        return radius;
    }

    public boolean isInternalPoint(Point point) {
        Double vX = Math.pow(point.getX() - center.getX(), 2.0);
        Double vY = Math.pow(point.getY() - center.getY(), 2.0);
        Double vR = Math.pow(radius, 2.0);
        return (vX + vY < vR);
    }

}
