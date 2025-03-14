package com.msreem.shortestpathwithdijkstra.table;

import com.msreem.shortestpathwithdijkstra.graph.Vertex;

import static com.msreem.shortestpathwithdijkstra.filter.Filter.*;

// Represent a row for the table used in dijkstra's algorithm
public class TableEntry {

    private Vertex currVertex;
    private Vertex path;
    private boolean visited;
    private double[] weight;

    public TableEntry(Vertex currVertex) {
        this.currVertex = currVertex;
        weight = new double[3];
    }

    public Vertex getPath() {
        return path;
    }

    public void setPath(Vertex path) {
        this.path = path;
    }

    public double getWeight(int index) {
        if (index < 0 || index >= weight.length)
            return 0;
        return weight[index];
    }

    public void setWeight(int index, double weightValue) {
        weight[index] = weightValue;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Vertex getVertex() {
        return currVertex;
    }

    public void setCurrVertex(Vertex currVertex) {
        this.currVertex = currVertex;
    }

}
