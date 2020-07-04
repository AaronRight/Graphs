package com.company.model;

import com.company.help.GraphException;
import com.company.help.SearchInfo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public interface Graph<T> {
    Set<Vertex> vertices = new HashSet<>();

    default Vertex addVertex(T value) {
        Vertex nv = new Vertex<T>(value);
        this.vertices.add(nv);
        return nv;
    }
    void addEdge(Vertex a, Vertex b, double weight) throws GraphException;

    Collection<Edge> getPath(Vertex a, Vertex b) throws GraphException;
}
