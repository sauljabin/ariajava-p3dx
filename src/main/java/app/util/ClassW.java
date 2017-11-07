/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.util;

public class ClassW implements Comparable<ClassW> {

    private Class<?> value;
    private String name;

    public ClassW(Class<?> value) {
        this.value = value;
    }

    public ClassW(Class<?> value, String name) {
        this.value = value;
        this.name = name;
    }

    public ClassW() {
    }

    public Class<?> getValue() {
        return value;
    }

    public void setValue(Class<?> value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return toString().equals(o.toString());
    }

    @Override
    public String toString() {
        return name != null ? name : value.getSimpleName();
    }

    @Override
    public int compareTo(ClassW o) {
        return this.toString().compareTo(o.toString());
    }

}
