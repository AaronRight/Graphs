package com.company;

import com.company.help.GraphException;
import com.company.help.SearchInfo;
import com.company.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class MainTest {
    @Test(expected = GraphException.class)
    public void testUndirectedCicleEdges() throws GraphException {
        UndirectedGraph<String> graph = new UndirectedGraph<>();
        Map<Character, Vertex> vertices = new HashMap<>();

        vertices.put('A', graph.addVertex("A"));
        vertices.put('B', graph.addVertex("B"));

        graph.addEdge(vertices.get('A'), vertices.get('B'), 1);
        graph.addEdge(vertices.get('B'), vertices.get('A'), 1);
    }

    @Test(expected = GraphException.class)
    public void testDirectedCicleEdges() throws GraphException {
        DirectedGraph<String> graph = new DirectedGraph<>();
        Map<Character, Vertex> vertices = new HashMap<>();

        vertices.put('A', graph.addVertex("A"));
        vertices.put('B', graph.addVertex("B"));

        graph.addEdge(vertices.get('A'), vertices.get('B'), 1);
        graph.addEdge(vertices.get('B'), vertices.get('A'), 1);
        graph.addEdge(vertices.get('A'), vertices.get('A'), 1);
        graph.addEdge(vertices.get('A'), vertices.get('A'), 1);
    }

    void fillGraph(Graph<String> graph, Map<Character, Vertex> vertices) throws GraphException{
        /*   A-----10----G--3--H
             |           |     |
             2           3     |
             |           |     |
             B--1--D--2--F     7
                   |     |     |
                   6     3     |
                   |     |     |
             C--2--E--1--I--5--J  */

        for (char v = 'A'; v <= 'J'; v++)
            vertices.put(v, graph.addVertex(String.valueOf(v)));

        graph.addEdge(vertices.get('A'), vertices.get('B'), 2);
        graph.addEdge(vertices.get('A'), vertices.get('G'), 10);
        graph.addEdge(vertices.get('G'), vertices.get('F'), 3);
        graph.addEdge(vertices.get('G'), vertices.get('H'), 3);
        graph.addEdge(vertices.get('H'), vertices.get('J'), 7);
        graph.addEdge(vertices.get('J'), vertices.get('I'), 5);
        graph.addEdge(vertices.get('I'), vertices.get('F'), 3);
        graph.addEdge(vertices.get('I'), vertices.get('E'), 1);
        graph.addEdge(vertices.get('E'), vertices.get('C'), 2);
        graph.addEdge(vertices.get('E'), vertices.get('D'), 6);
        graph.addEdge(vertices.get('D'), vertices.get('F'), 2);
        graph.addEdge(vertices.get('D'), vertices.get('B'), 1);
    }

    @Test
    public void testUndirectedPathFinding() throws GraphException {
        UndirectedGraph<String> graph = new UndirectedGraph<>();
        Map<Character, Vertex> vertices = new HashMap<>();
        fillGraph(graph, vertices);

        SearchInfo<Vertex> path = graph.calcPath(vertices.get('A'), vertices.get('F'));
        assertEquals(5.0, path.getSumWeight(), 0);

        List<String>  expected = Arrays.asList("A", "B", "D", "F");
        List<String> actual = path.getPreviousPath().stream().map(vertex -> vertex.toString())
                .collect(Collectors.toList());;
        assertEquals(expected, actual);
    }

    @Test
    public void testDirectedPathFinding() throws GraphException {
        DirectedGraph<String> graph = new DirectedGraph<>();
        Map<Character, Vertex> vertices = new HashMap<>();
        fillGraph(graph, vertices);

        SearchInfo<Vertex> path = graph.calcPath(vertices.get('A'), vertices.get('D'));
        assertEquals(32.0, path.getSumWeight(), 0);

        List<String>  expected = Arrays.asList("A", "G", "H", "J", "I", "E", "D");
        List<String> actual = path.getPreviousPath().stream().map(vertex -> vertex.toString())
                .collect(Collectors.toList());;
        assertEquals(expected, actual);
    }

    @Test(expected = GraphException.class)
    public void testNotFound() throws GraphException {
        UndirectedGraph<String> graph = new UndirectedGraph<>();
        Map<Character, Vertex> vertices = new HashMap<>();

        /*   A---B
             |
             C   D  */

        for (char v = 'A'; v <= 'D'; v++)
            vertices.put(v, graph.addVertex(String.valueOf(v)));

        graph.addEdge(vertices.get('A'), vertices.get('B'), 1);
        graph.addEdge(vertices.get('A'), vertices.get('C'), 1);

        graph.getPath(vertices.get('A'), vertices.get('D'));
    }

}