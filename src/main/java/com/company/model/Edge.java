package com.company.model;

public class Edge {
    private Vertex first;
    private Vertex second;
    private double weight;

    public Edge(Vertex first, Vertex second, double weight) {
        this.first = first;
        this.second = second;
        this.weight = weight;

        first.addEdge(this);
        second.addEdge(this);
    }

    public Vertex getFirst() {
        return first;
    }

    public Vertex getSecond() {
        return second;
    }

    public double getWeight() {
        return weight;
    }

    public Vertex getNext(Vertex next) {
        if (this.first.equals(next))
            return this.second;
        else
            return this.first;
    }
}
