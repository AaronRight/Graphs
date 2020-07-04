package com.company.model;

import com.company.help.GraphException;
import com.company.help.SearchInfo;

import java.util.*;

public class DirectedGraph<T> implements Graph<T> {
    @Override
    public void addEdge(Vertex a, Vertex b, double weight) throws GraphException {
        // check if there is no such edge in this direction
        for (Object edge : a.getEdges()) {
            if (((Edge) edge).getFirst().equals(a) && ((Edge) edge).getSecond().equals(b)) {
                throw new GraphException(a.equals(b) ?
                        "Can be only one cyclic edge for this vertex" :
                        "There can not be more edges for these vertices");
            }
        }
        // For set direction is used notation:
        // first: from
        // second: to
        new Edge(a, b, weight);
    }

    @Override
    public Collection<Edge> getPath(Vertex a, Vertex b) throws GraphException {
        List<Edge> result = new ArrayList<>();

        SearchInfo<Vertex> searchResult = calcPath(a, b);
        if(searchResult == null) throw new GraphException("There is no such path");
        Vertex[] path = (Vertex[])searchResult.getPreviousPath().toArray();

        for (int i = 0; i < path.length-1; i++)
            for(Object e: path[i].getEdges())
                if(((Edge)e).getSecond().equals(path[i+1]))
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
                Vertex candidate = edge.getSecond();
                if(searchedNow.equals(edge.getSecond()))
                    continue;

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
