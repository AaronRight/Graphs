package com.company.model;

import com.company.help.GraphException;
import com.company.help.SearchInfo;

import java.util.*;
import java.util.function.Consumer;

public class UndirectedGraph<T> implements Graph<T> {
    @Override
    public void addEdge(Vertex a, Vertex b, double weight) throws GraphException {
        // check if there is no such edge
        for (Object edge : a.getEdges()) {
            if (((Edge) edge).getNext(a).equals(b)) {
                throw new GraphException("Can not be more than one edge for pair of vertices");
            }
        }

        for (Object edge : b.getEdges()) {
            if (((Edge) edge).getNext(b).equals(a)) {
                throw new GraphException("Can not be more than one edge for pair of vertices");
            }
        }

        new Edge(a, b, weight);
    }

    @Override
    public Collection<Edge> getPath(Vertex a, Vertex b)throws GraphException{
        List<Edge> result = new ArrayList<>();

        SearchInfo<Vertex> searchResult = calcPath(a, b);
        if(searchResult == null) throw new GraphException("There is no such path");
        Vertex[] path = (Vertex[])searchResult.getPreviousPath().toArray();

        for (int i = 0; i < path.length-1; i++)
            for(Object e: path[i].getEdges())
                if(((Edge)e).getNext(path[i]).equals(path[i+1]))
                    result.add((Edge)e);
        return result;
    }

    public SearchInfo<Vertex> calcPath(Vertex a, Vertex b) {
        Map<Vertex, Double> alreadySearched = new HashMap<>();
        Deque<SearchInfo<Vertex>> toBeSearched = new ArrayDeque<>();
        Vertex<T> searchedNow;

        SearchInfo<Vertex> success = null;

        toBeSearched.add(new SearchInfo<>(a, Arrays.asList(a), 0));

        searching:
        while(!toBeSearched.isEmpty()){
            SearchInfo<Vertex> c = toBeSearched.pop();
            searchedNow = c.getVertexToSearch();

            if(alreadySearched.containsKey(searchedNow) && alreadySearched.get(searchedNow) < c.getSumWeight())
                continue searching; // not worth looking

            alreadySearched.put(searchedNow, c.getSumWeight()); // add or update with better weight

            for (Edge edge : searchedNow.getEdges()) {
                Vertex candidate = edge.getNext(searchedNow);
                ArrayList prevPath = new ArrayList<>(c.getPreviousPath());
                prevPath.add(candidate);

                if(candidate.equals(b)){
                    if(null == success || (success != null && success.getSumWeight() > c.getSumWeight() + edge.getWeight())){
                        success = new SearchInfo<>(candidate, prevPath, c.getSumWeight() + edge.getWeight());
                    }
                    continue searching;
                } else {
                    toBeSearched.add(new SearchInfo<>(candidate, prevPath, c.getSumWeight() + edge.getWeight()));
                }
            }
        }
        return success;
    }
}
