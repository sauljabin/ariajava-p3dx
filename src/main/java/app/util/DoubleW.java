/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.util;

public class DoubleW implements Comparable<DoubleW> {
    private double value;
    private String name;

    public DoubleW() {
    }

    public DoubleW(double value) {
        this.value = value;
    }

    public DoubleW(double value, String name) {
        this.value = value;
        this.name = name;
    }

    public double getDouble() {
        return value;
    }

    public void setDouble(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name != null ? name : String.format("%d", value);
    }

    @Override
    public int compareTo(DoubleW o) {
        return Double.compare(value, o.getDouble());
    }
}
