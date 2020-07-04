package com.company.model;

import java.util.Collection;
import java.util.HashSet;

public class Vertex<T> {
    private T value;
    private Collection<Edge> edges;

    protected Vertex(T value) {
        this.value = value;
        this.edges = new HashSet<>();
    }

    protected T getValue() {
        return value;
    }

    protected void setValue(T value) {
        this.value = value;
    }

    protected Collection<Edge> getEdges() {
        return edges;
    }

    protected void addEdge(Edge ne) {
        this.edges.add(ne);
    }

    protected void setEdges(Collection<Edge> edges) {
        this.edges = edges;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
