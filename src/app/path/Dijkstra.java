package app.path;

import java.util.*;

import app.path.geometry.Point;
import app.path.graphs.Graph;
import app.path.graphs.components.Link;

public class Dijkstra {
/*
	static final int CANT_MAX_VERTICES = 10005; // maximo numero de vértices
	static final double INF = Integer.MAX_VALUE; // distancia infinita inicial

	// En el caso de java usamos una clase que representara el pair de C++
	static class Node implements Comparable<Node> {
		Point first;
		Double second;

		Node(Point d, Double p) { // constructor
			this.first = d;
			this.second = p;
		}

		public int compareTo(Node other) { // comparador para el PriorityQueue
			if (second > other.second)
				return 1;
			if (second == other.second)
				return 0;
			return -1;
		}
	};

	static Scanner sc = new Scanner(System.in); // para lectura de datos
	static List<List<Node>> ady = new ArrayList<List<Node>>(); // lista de
																// adyacencia
	static Double distancia[] = new Double[CANT_MAX_VERTICES]; // distancia[ u ]
																// distancia de
	// vértice inicial a vértice con ID
	// = u
	static boolean visitado[] = new boolean[CANT_MAX_VERTICES]; // para vértices
																// visitados
	static PriorityQueue<Node> Q = new PriorityQueue<Node>(); // priority queue
	static int V; // numero de vertices
	static int previo[] = new int[CANT_MAX_VERTICES]; // para la impresion de
														// caminos

	// función de inicialización
	static void init() {
		for (int i = 0; i <= V; ++i) {
			distancia[i] = INF; // inicializamos todas las distancias con valor
								// infinito
			visitado[i] = false; // inicializamos todos los vértices como no
									// visitados
			previo[i] = -1; // inicializamos el previo del vertice i con -1
		}
	}

	// Paso de relajacion
	static void relajacion(int actual, Point adyacente, Double peso) {
		// Si la distancia del origen al vertice actual + peso de su arista es
		// menor a la distancia del origen al vertice adyacente
		if (distancia[actual] + peso < distancia[adyacente]) {
			distancia[adyacente] = distancia[actual] + peso; // relajamos el
																// vertice
																// actualizando
																// la distancia
			previo[adyacente] = actual; // a su vez actualizamos el vertice
										// previo
			Q.add(new Node(adyacente, distancia[adyacente])); // agregamos
																// adyacente a
																// la cola de
																// prioridad
		}
	}

	// Impresion del camino mas corto desde el vertice inicial y final
	// ingresados
	static void print(int destino) {
		if (previo[destino] != -1) // si aun poseo un vertice previo
			print(previo[destino]); // recursivamente sigo explorando
		System.out.printf("%d ", destino); // terminada la recursion imprimo los
											// vertices recorridos
	}

	static void dijkstra(int inicial) {
		init(); // inicializamos nuestros arreglos
		Q.add(new Node(inicial, 0.0)); // Insertamos el vértice inicial en la
										// Cola
										// de Prioridad
		distancia[inicial] = 0.0; // Este paso es importante, inicializamos la
									// distancia del inicial como 0
		int actual, adyacente;
		Double peso;
		while (!Q.isEmpty()) { // Mientras cola no este vacia
			actual = Q.element().first; // Obtengo de la cola el nodo con menor
										// peso, en un comienzo será el inicial
			Q.remove(); // Sacamos el elemento de la cola
			if (visitado[actual])
				continue; // Si el vértice actual ya fue visitado entonces sigo
							// sacando elementos de la cola
			visitado[actual] = true; // Marco como visitado el vértice actual

			for (int i = 0; i < ady.get(actual).size(); ++i) { // reviso sus
																// adyacentes
																// del vertice
																// actual
				adyacente = ady.get(actual).get(i).first; // id del vertice
															// adyacente
				peso = ady.get(actual).get(i).second; // peso de la arista que
														// une actual con
														// adyacente ( actual ,
														// adyacente )
				if (!visitado[adyacente]) { // si el vertice adyacente no fue
											// visitado
					relajacion(actual, adyacente, peso); // realizamos el paso
															// de relajacion
				}
			}
		}

		System.out.printf("Distancias mas cortas iniciando en vertice %d\n",
				inicial);
		for (int i = 1; i <= V; ++i) {
			System.out.printf("Vertice %d , distancia mas corta = %d\n", i,
					distancia[i]);
		}

		System.out
				.println("\n**************Impresion de camino mas corto**************");
		System.out.printf("Ingrese vertice destino: ");
		int destino;
		destino = sc.nextInt();
		print(destino);
		System.out.printf("\n");
	}

	public static void main(String[] args) {
		int cantLinks, origen, destino, inicial;
		Double peso;
		V = sc.nextInt();
		cantLinks = sc.nextInt();
		for (int i = 0; i <= V; ++i)
			ady.add(new ArrayList<Node>()); // inicializamos lista de adyacencia
		for (int i = 0; i < cantLinks; ++i) {
			origen = sc.nextInt();
			destino = sc.nextInt();
			peso = sc.nextDouble();
			ady.get(origen).add(new Node(destino, peso)); // grafo diridigo
			ady.get(destino).add(new Node(origen, peso)); // no dirigido
		}
		System.out.print("Ingrese el vertice inicial: ");
		inicial = sc.nextInt();
		dijkstra(inicial);
	}

	public Dijkstra(Graph graph) {
		int cantLinks, origen, destino, inicial;
		Double peso;
		V = sc.nextInt();
		cantLinks = sc.nextInt();
		for (int i = 0; i <= V; ++i)
			ady.add(new ArrayList<Node>()); // inicializamos lista de adyacencia
		for (int i = 0; i < cantLinks; ++i) {
			origen = sc.nextInt();
			destino = sc.nextInt();
			peso = sc.nextDouble();
			ady.get(origen).add(new Node(destino, peso)); // grafo diridigo
			ady.get(destino).add(new Node(origen, peso)); // no dirigido
		}
		for (int i = 0; i < graph.getLinks().size(); i++) {
			Link link = graph.getLinks().get(i);
			peso = link.getWeight();
			ady.get(link.getPointA()).add(new Node(link.getPointB(), peso)); // grafo
																				// diridigo
			ady.get(link.getPointB()).add(new Node(link.getPointA(), peso)); // no
																				// dirigido
		}
		System.out.print("Ingrese el vertice inicial: ");
		inicial = sc.nextInt();
		dijkstra(inicial);
	}
*/
}
