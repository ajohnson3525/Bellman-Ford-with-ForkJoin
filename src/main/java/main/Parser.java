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
            adjReturn.add(i, new HashMap<>());
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
    public static List<HashMap<Integer, Integer>> parseInverse(int[][] adjMatrix) {
        int numV = adjMatrix.length;
        List<HashMap<Integer, Integer>> adjReturn = new ArrayList<HashMap<Integer, Integer>>();
        for (int i = 0; i < numV; i++) {
            adjReturn.add(i, new HashMap<>());
        }
        for(int j = 0; j < numV; j++) {
            for (int k = 0; k < numV; k++) {
                if (adjMatrix[j][k] != GraphUtil.INF) {
                    adjReturn.get(k).put(j, adjMatrix[j][k]);
                }
            }
        }
        return adjReturn;
    }

}
