package main;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import cse332.graph.GraphUtil;
import cse332.exceptions.NotYetImplementedException;

public class Parser {

    /**
     * Parse an adjacency matrix into an adjacency list.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list
     */
    public static List<HashMap<Integer, Integer>> parse(int[][] adjMatrix) {
        int numV = adjMatrix.length;
        List<HashMap<Integer, Integer>> adjReturn = new ArrayList<HashMap<Integer, Integer>>();
        for (int i = 0; i < numV; i++) {
            adjReturn.add(new HashMap<>());
            for (int j = 0; j < numV; j++) {
                if (adjMatrix[i][j] != GraphUtil.INF) {
                    adjReturn.get(i).put(j, adjMatrix[i][j]);
                }
            }
        }

        return adjReturn;
    }

    /**
     * Parse an adjacency matrix into an adjacency list with incoming edges instead of outgoing edges.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list with incoming edges
     */
    public static Object parseInverse(int[][] adjMatrix) {
        throw new NotYetImplementedException();
    }

}
