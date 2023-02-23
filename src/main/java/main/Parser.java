package main;

import java.util.HashMap;
import cse332.graph.GraphUtil;
import cse332.exceptions.NotYetImplementedException;

public class Parser {

    /**
     * Parse an adjacency matrix into an adjacency list.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list
     */
    public static HashMap<Integer, Integer>[] parse(int[][] adjMatrix) {
        HashMap<Integer, Integer>[] adjReturn = new HashMap[adjMatrix.length];

        for(int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix.length; i++) {
                if (adjMatrix[i][j] != GraphUtil.INF) {
                    adjReturn[i].put(j, adjMatrix[i][j]);
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
