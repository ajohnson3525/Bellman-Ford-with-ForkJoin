package paralleltasks;

import cse332.graph.GraphUtil;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class RelaxOutTaskBad extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;

    private static List<HashMap<Integer, Integer>> g;

    public static void parallel(List<HashMap<Integer, Integer>> g, int[] D1, int[] D2,
                                int[] P, int n) {
        RelaxOutTaskBad task = new RelaxOutTaskBad(g, D1, D2, P, 0, n);
        pool.invoke(task);
    }

    private final int[] D1, D2, P;
    private final int lo, hi;

    public RelaxOutTaskBad(List<HashMap<Integer, Integer>> g, int[] D1, int[] D2,
                           int[] P, int lo, int hi) {
        this.g = g;
        this.D1 = D1;
        this.D2 = D2;
        this.P = P;
        this.lo = lo;
        this.hi = hi;
    }



    protected void compute() {
        // if cutoff - sequential
        if (hi - lo <= CUTOFF) {
            sequential();

        // else - parallel
        } else {
            RelaxOutTaskBad left = new RelaxOutTaskBad(g, D1, D2, P, lo, ((hi + lo) / 2));
            RelaxOutTaskBad right = new RelaxOutTaskBad(g, D1, D2, P, ((hi + lo) / 2), hi);

            left.fork();
            right.compute();
            left.join();
        }
    }

    public void sequential() {
        int cost;
        for (int v = lo; v < hi; v++){
            for (int w : g.get(v).keySet()) {
                cost = g.get(v).get(w);
                if (D2[v] != GraphUtil.INF && D1[w] > D2[v] + cost) {
                    D1[w] = D2[v] + cost;
                    P[w] = v;
                }
            }
        }
    }


}
