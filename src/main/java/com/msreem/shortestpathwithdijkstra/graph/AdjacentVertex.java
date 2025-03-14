package com.msreem.shortestpathwithdijkstra.graph;

import static com.msreem.shortestpathwithdijkstra.filter.Filter.*;

// Represent the adjacent vertex for adjacency-list representation
public class AdjacentVertex {

    private Vertex target;
    private double[] weight;

    public AdjacentVertex(Vertex target, double distance, double time, double cost) {
        this.target = target;
        weight = new double[3];
        weight[DISTANCE] = distance;
        weight[TIME] = time;
        weight[COST] = cost;
    }

    public Vertex getTargetVertex() {
        return target;
    }

    public void setTarget(Vertex target) {
        this.target = target;
    }

    public double getWeight(int index) {
        if (index < 0 || index >= weight.length)
            return 0;
        return weight[index];
    }

    @Override
    public String toString() {
        return target.toString();
    }
}
