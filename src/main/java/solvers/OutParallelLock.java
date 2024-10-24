package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;
import paralleltasks.ArrayCopyTask;
import paralleltasks.RelaxOutTaskLock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.HashMap;
import java.util.List;

public class OutParallelLock implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        List<HashMap<Integer, Integer>> g = Parser.parse(adjMatrix);
        int n = adjMatrix.length;
        int[] D1 = new int[n];
        int[] D2;
        int[] P = new int[n];

        // initialize D1 & P
        for (int i = 0; i < n; i++) {
            P[i] = -1;
            if (i == source) {
                D1[i] = 0;
            } else {
                D1[i] = GraphUtil.INF;
            }
        }

        for (int k = 0; k < n; k++) {
            // Array copying
            D2 = ArrayCopyTask.copy(D1);
            // Relaxing the edges
            RelaxOutTaskLock.parallel(g, D1, D2, P, n);
        }
        return GraphUtil.getCycle(P);
    }

}