package com.msreem.shortestpathwithdijkstra.io;

import com.msreem.shortestpathwithdijkstra.city.City;
import com.msreem.shortestpathwithdijkstra.graph.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Represent an input stream to read the cities' data
public class MapInputStream {

    private Scanner in;

    public MapInputStream(String pathName) throws FileNotFoundException {
        in = new Scanner(new File(pathName));
    }

    public MapInputStream(File file) throws FileNotFoundException {
        in = new Scanner(file);
    }

    public Graph readAndBuildGraph() {
        if (!in.hasNext()) return null;
        int numOfCities = in.nextInt();
        in.nextLine(); // ignore second line
        in.nextLine();
        City[] cities = new City[numOfCities];
        for (int i = 0; i < numOfCities; i++) {
            String[] tokens = in.nextLine().split(",");
            String capitalName = tokens[1];
            double lat = Double.parseDouble(tokens[2]);
            double lon = Double.parseDouble(tokens[3]);
            cities[i] = new City(capitalName, lon, lat);
        }

        int numOfEdges = in.nextInt();
        in.nextLine();
        in.nextLine(); // ignore second line
        in.nextLine(); // ignore third line
        String[] sources = new String[numOfEdges], destinations = new String[numOfEdges];
        double[] durations = new double[numOfEdges], prices = new double[numOfEdges];
        for (int i = 0; i < numOfEdges; i++) {
            String[] tokens = in.nextLine().split("\\s*,\\s*");
            sources[i] = tokens[0];
            destinations[i] = tokens[1];
            durations[i] = Double.parseDouble(tokens[2]);
            prices[i] = Double.parseDouble(tokens[3]);
        }
        // in.close();
        return new Graph(cities, sources, destinations, durations, prices);
    }

}
