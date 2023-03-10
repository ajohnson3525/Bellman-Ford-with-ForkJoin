package paralleltasks;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class RelaxOutTaskBad extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;

    private static List<HashMap<Integer, Integer>> g;
    private static int[] D1, D2, P;
    private static int n;

    public RelaxOutTaskBad(List<HashMap<Integer, Integer>> g, int[] D1, int[] D2, int[] P, int n) {
        this.g = g;
        this.D1 = D1;
        this.D2 = D2;
        this.P = P;
        this.n = n;
    }

    protected void compute() {
        // if cutoff - sequential
        // else - parallel
    }

    public static void sequential() {
        int cost;
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

    public static void parallel() {
        throw new NotYetImplementedException();
    }

}
