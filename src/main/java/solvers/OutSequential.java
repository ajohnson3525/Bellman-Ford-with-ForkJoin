package solvers;

import java.util.HashMap;
import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;

import java.util.List;

public class OutSequential implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        List<HashMap<Integer, Integer>> g = Parser.parse(adjMatrix);
        int n = adjMatrix.length;
        int[] D1 = new int[n];
        int[] D2 = new int[n];
        int[] P = new int[n];
        int cost;

        // initialize D1 & P
        for (int i = 0; i < n; i++) {
            P[i] = -1;
            if (i == source) {
                D1[i] = 0;
            } else {
                D1[i] = GraphUtil.INF;
            }
        }

        // Bellman-Ford algorithm
        for (int k = 0; k < n; k++) {
            // Array copying
            for (int j = 0; j < n; j++){
                D2[j] = D1[j];
            }
            // Relaxing the edges
            for (int v = 0; v < n; v++) {
                for (int w : g.get(v).keySet()) {
                    cost = g.get(v).get(w);
                    if (D2[v] != GraphUtil.INF && D1[w] > D2[v] + cost) {
                        D1[w] = D2[v] + cost;
                        P[w] = v;
                    }
                }
            }
        }
        return GraphUtil.getCycle(P);
    }

}