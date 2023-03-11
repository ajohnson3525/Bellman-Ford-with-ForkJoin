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
    private static int n, lo, hi;


    public RelaxOutTaskBad(List<HashMap<Integer, Integer>> g, int[] D1, int[] D2,
                           int[] P, int n, int lo, int hi) {
        this.g = g;
        this.D1 = D1;
        this.D2 = D2;
        this.P = P;
        this.n = n;
        this.lo = lo;
        this.hi = hi;
    }

    protected void compute() {
        // if cutoff - sequential
        if (hi - lo <= CUTOFF) {
            for (int v = lo; v < hi; v++) {
                sequential(v);
            }

        // else - parallel
        } else {
            parallel(g, D1, D2, P, n, lo, hi);
        }
    }

    public static void sequential(int v) {
        int cost;
        for (int w : g.get(v).keySet()) {
            cost = g.get(v).get(w);
            if (D2[v] != GraphUtil.INF && D1[w] > D2[v] + cost) {
                D1[w] = D2[v] + cost;
                P[w] = v;
            }
        }
    }

    public static void parallel(List<HashMap<Integer, Integer>> g, int[] D1, int[] D2, int[] P,
                                int n, int lo, int hi) {
        RelaxOutTaskBad left = new RelaxOutTaskBad(g, D1, D2, P, n, lo, (hi + lo) / 2);
        RelaxOutTaskBad right = new RelaxOutTaskBad(g, D1, D2, P, n, (hi + lo) / 2, hi);

        pool.invoke(left);
        right.compute();
        left.join();
    }

}
