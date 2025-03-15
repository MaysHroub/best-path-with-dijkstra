package com.msreem.shortestpathwithdijkstra.dijkstra;

import com.msreem.shortestpathwithdijkstra.graph.AdjacentVertex;
import com.msreem.shortestpathwithdijkstra.graph.Graph;
import com.msreem.shortestpathwithdijkstra.graph.Vertex;
import com.msreem.shortestpathwithdijkstra.linkedlist.LinkedList;
import com.msreem.shortestpathwithdijkstra.linkedlist.Node;
import com.msreem.shortestpathwithdijkstra.table.TableEntry;

import static com.msreem.shortestpathwithdijkstra.filter.Filter.*;

// Handle the logic of dijkstra's algorithm
public class DijkstraLogic {

    private TableEntry[] table;
    private Graph graph;

    private Vertex target;

    public DijkstraLogic(Graph graph) {
        this.graph = graph;
        int n = graph.getVertices().length;
        table = new TableEntry[n];
    }

    // Initialize the table given source and target vertices, and filter
    public void run(Vertex source, Vertex target, int index) {
        this.target = target;
        initializeTable(source, index);
        while (true) {
            Vertex v = getMinimumUnvisitedVertex(index);
            if (v == null) break;
            if (v.equals(target)) break;
            int currIdx = v.getIndex();
            table[currIdx].setVisited(true);

            Node<AdjacentVertex> curr = v.getAdjacentVertices().getHead();
            while (curr != null) {
                AdjacentVertex av = curr.getData();
                int nextIdx = av.getTargetVertex().getIndex();

                if (!table[nextIdx].isVisited()) {
                    double oldWeight = table[nextIdx].getWeight(index),
                            newWeight = table[currIdx].getWeight(index) + av.getWeight(index);

                    if (newWeight < oldWeight) {
                        table[nextIdx].setWeight(DISTANCE, table[currIdx].getWeight(DISTANCE) + av.getWeight(DISTANCE));
                        table[nextIdx].setWeight(TIME, table[currIdx].getWeight(TIME) + av.getWeight(TIME));
                        table[nextIdx].setWeight(COST, table[currIdx].getWeight(COST) + av.getWeight(COST));
                        table[nextIdx].setPath(v);
                    }
                }
                curr = curr.getNext();
            }
        }
    }

    // Return minimum unknown/unvisited vertex
    private Vertex getMinimumUnvisitedVertex(int index) {
        Vertex minVertex = null;
        double minWeight = Double.MAX_VALUE;
        for (TableEntry tableEntry : table)
            if (!tableEntry.isVisited() && tableEntry.getWeight(index) < minWeight) {
                minWeight = tableEntry.getWeight(index);
                minVertex = tableEntry.getVertex();
            }
        return minVertex;
    }

    // Reset the table
    private void initializeTable(Vertex source, int index) {
        for (int i = 0; i < table.length; i++) {
            table[i] = new TableEntry(graph.getVertices()[i]);
            table[i].setWeight(index, Double.MAX_VALUE);
        }
        table[source.getIndex()].setWeight(index, 0);
    }

    // Return the path between source and target
    public LinkedList<Vertex> getPath() {
        LinkedList<Vertex> path = new LinkedList<>();

        int itr = target.getIndex();
        if (table[itr].getPath() != null)
            path.insertLast(target);

        while (table[itr].getPath() != null) {
            path.insertFirst(table[itr].getPath());
            itr = table[itr].getPath().getIndex();
        }

        return path;
    }

    // Return a TableEntry object at given index
    public TableEntry getTableEntry(int index) {
        return table[index];
    }

}
