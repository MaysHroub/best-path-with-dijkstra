package com.msreem.shortestpathwithdijkstra.graph;


import com.msreem.shortestpathwithdijkstra.city.City;
import com.msreem.shortestpathwithdijkstra.linkedlist.LinkedList;

// Represent vertex of the graph and encapsulate city's data
public class Vertex {

    private City city;
    private int index;
    private LinkedList<AdjacentVertex> adjacentVertices;

    private double x, y;

    public Vertex(City city, int index) {
        adjacentVertices = new LinkedList<>();
        this.city = city;
        this.index = index;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public LinkedList<AdjacentVertex> getAdjacentVertices() {
        return adjacentVertices;
    }

    public void addAdjacent(Vertex destination, double distance, double time, double cost) {
        adjacentVertices.insertLast(new AdjacentVertex(destination, distance, time, cost));
    }

    @Override
    public String toString() {
        return city.toString();
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vertex)) return false;
        return city.equals( ((Vertex)obj).city );
    }

}
