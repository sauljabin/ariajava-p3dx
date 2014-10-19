/**
 * 
 * Copyright (c) 2014 Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>
 * 
 * This file is part of AriaJavaP3DX.
 *
 * AriaJavaP3DX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AriaJavaP3DX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AriaJavaP3DX.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package app.path.geometry;

import javafx.geometry.Point3D;

public class Flat {

	private Point3D puntoA;
	private Point3D puntoB;
	private Point3D puntoC;
	private Point3D vectorU;
	private Point3D vectorV;
	private Point3D vectorNormal;
	private double valorD;

	public Flat(double ax, double ay, double az, double bx, double by,
			double bz, double cx, double cy, double cz) {
		puntoA = new Point3D(ax, ay, az);
		puntoB = new Point3D(bx, by, bz);
		puntoC = new Point3D(cx, cy, cz);
		vectorU = new Point3D(puntoB.getX() - puntoA.getX(), puntoB.getY()
				- puntoA.getY(), puntoB.getZ() - puntoA.getZ());
		vectorV = new Point3D(puntoC.getX() - puntoA.getX(), puntoC.getY()
				- puntoA.getY(), puntoC.getZ() - puntoA.getZ());
		vectorNormal = new Point3D((vectorU.getY() * vectorV.getZ())
				- (vectorU.getZ() * vectorV.getY()),
				-(vectorU.getX() * vectorV.getZ())
						- (vectorU.getZ() * vectorV.getX()),
				(vectorU.getX() * vectorV.getY())
						- (vectorU.getY() * vectorV.getX()));
		valorD = -((vectorNormal.getX() * puntoA.getX())
				+ (vectorNormal.getY() * puntoA.getY()) + (vectorNormal.getZ() * puntoA
				.getZ()));
		System.out.println("vector AB");
		System.out.println(vectorU.getX());
		System.out.println(vectorU.getY());
		System.out.println(vectorU.getZ());

		System.out.println("vector AC");
		System.out.println(vectorV.getX());
		System.out.println(vectorV.getY());
		System.out.println(vectorV.getZ());

		System.out.println("NORMAL");
		System.out.println(vectorNormal.getX());
		System.out.println(vectorNormal.getY());
		System.out.println(vectorNormal.getZ());
		System.out.println("d");
		System.out.println(valorD);
	}

	public Point3D getPuntoA() {
		return puntoA;
	}

	public Point3D getPuntoB() {
		return puntoB;
	}

	public Point3D getPuntoC() {
		return puntoC;
	}

	public static void main(String[] arg) {
		Point3D pA = new Point3D(1, 1, 0);
		Point3D pB = new Point3D(1, 0, 1);
		Point3D pC = new Point3D(0, 1, 1);
		new Flat(pA.getX(), pA.getY(), pA.getZ(), pB.getX(), pB.getY(),
				pB.getZ(), pC.getX(), pC.getY(), pC.getZ());
	}

}
