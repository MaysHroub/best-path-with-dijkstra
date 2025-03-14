package com.msreem.shortestpathwithdijkstra.graph;

import com.msreem.shortestpathwithdijkstra.city.City;
import com.msreem.shortestpathwithdijkstra.geo.GeoCalculator;
import com.msreem.shortestpathwithdijkstra.linkedlist.Node;

// Represent the graph of the airline system
public class Graph {

    private Vertex[] vertices;

    public Graph(City[] cities, String[] sources, String[] targets,
                 double[] durations, double[] costs) {
        init(cities, sources, targets, durations, costs);
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    // Initialize the graph given cities and edges
    private void init(City[] cities, String[] sources, String[] targets,
                      double[] durations, double[] costs) {
        int n = cities.length;
        vertices = new Vertex[n];
        for (int i = 0; i < n; i++)
            vertices[i] = new Vertex(cities[i], i);

        int m = sources.length;;
        for (int i = 0; i < m; i++) {
            System.out.println(sources[i] + ", " + targets[i]);
            int sourceIdx = getCityIndex(cities, sources[i]),
                    destIdx = getCityIndex(cities, targets[i]);
            double distance = calculateDistance(cities[sourceIdx], cities[destIdx]);
            vertices[sourceIdx].addAdjacent(vertices[destIdx], distance, durations[i], costs[i]);
        }
    }

    // Calculate the distance between two cities
    private double calculateDistance(City source, City destination) {
        return GeoCalculator.calculateDistance(source.getLatitude(), source.getLongitude(),
                destination.getLatitude(), destination.getLongitude());
    }

    // Return city index given its name
    private int getCityIndex(City[] cities, String cityName) {
        for (int i = 0; i < cities.length; i++)
            if (cities[i].getName().equalsIgnoreCase(cityName))
                return i;
        return -1;
    }

    // Print the graph
    public void traverse() {
        for (Vertex v : vertices) {
            System.out.print(v + " --> ");
            Node<AdjacentVertex> curr = v.getAdjacentVertices().getHead();
            while (curr != null) {
                System.out.print(curr.getData() + " -> ");
                curr = curr.getNext();
            }
            System.out.println("end");
        }
    }

}
