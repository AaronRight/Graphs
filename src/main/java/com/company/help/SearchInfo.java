package com.company.help;

import com.company.model.Vertex;

import java.util.Collection;

public class SearchInfo<T>{
    private Vertex vertexToSearch;
    private Collection<Vertex> previousPath;
    private double sumWeight = 0;

    public SearchInfo(Vertex vertexToSearch, Collection<Vertex> previousPath, double sumWeight) {
        this.vertexToSearch = vertexToSearch;
        this.previousPath = previousPath;
        this.sumWeight = sumWeight;
    }

    public Vertex getVertexToSearch() {
        return vertexToSearch;
    }

    public Collection<Vertex> getPreviousPath() {
        return previousPath;
    }

    public double getSumWeight() {
        return sumWeight;
    }

    @Override
    public String toString() {
        return "SearchInfo{" + vertexToSearch +" "+ previousPath +": " + sumWeight + '}';
    }
}
