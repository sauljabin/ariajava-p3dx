package app.path.antColony;

import java.util.ArrayList;

import app.path.geometry.Point;
import app.path.graphs.Graph;

public class Algorithm {

	private Graph graph;
	private ArrayList<Ant> ants;

	public Algorithm(Graph graph, int antAmount) {
		super();
		this.graph = graph;
		ants = new ArrayList<Ant>();
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
			graph.updatePheromone();
		}
		return ants.get(0).getLastRoutes().get(0);
	}

	private boolean checkRoutes() {
		for (Ant ant : ants)
			if (!ant.validRoutes())
				return false;
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
