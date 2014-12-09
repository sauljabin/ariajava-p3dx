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

package app.path.antColony;

import java.util.ArrayList;

import app.path.geometry.Point;
import app.path.graphs.Graph;

public class Algorithm {

	private Graph graph;
	private ArrayList<Ant> ants;
	private boolean found;

	public Algorithm(Graph graph, int antAmount) {
		super();
		this.graph = graph;
		ants = new ArrayList<Ant>();
		found = false;
		for (int i = 0; i < antAmount; i++)
			ants.add(new Ant(i + 1));
	}

	public ArrayList<Point> searchOptimalRoute(Point home, Point target) {
		for (Ant h : ants)
			h.defineTarget(home, target, graph);
		boolean isDefinedPath = false;
		while (!isDefinedPath) {
			isDefinedPath = true;
			for (Ant ant : ants)
				ant.step(graph);
			isDefinedPath = checkRoutes();
			if (found)
				graph.updatePheromone();
		}
		return ants.get(0).getLastRoutes().get(0);
	}

	private boolean checkRoutes() {
		for (Ant ant : ants)
			if (!ant.validRoutes())
				return false;
		found = true;
		System.out.println("ENCONTRADA");
		ArrayList<Point> route = ants.get(0).getLastRoutes().get(0);
		for (int i = 1; i < ants.size(); i++)
			if (route.size() != ants.get(i).getLastRoutes().get(0).size())
				return false;
		for (int i = 1; i < ants.size(); i++) {
			ArrayList<Point> otherRoute = ants.get(i).getLastRoutes().get(0);
			for (int n = 0; n < route.size(); n++)
				if (!route.get(n).equals(otherRoute.get(n)))
					return false;
		}
		return true;
	}
}
