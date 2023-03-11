package paralleltasks;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class RelaxInTask extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;
    //final int lo, hi;
    private static int lo, hi;
    private static int[] D1, D2, P;
    private static List<HashMap<Integer, Integer>> g;

    public RelaxInTask(List<HashMap<Integer, Integer>> g, int[] D1, int[] D2, int [] P,
                       int lo, int hi) {

        this.g = g;
        this.D1 = D1;
        this.D2 = D2;
        this.P = P;
        this.lo = lo;
        this.hi = hi;

    }

    protected void compute() {
        if (hi - lo <= CUTOFF) {
            //sequential
            sequential();
        } else {
            // parallel
            RelaxInTask left = new RelaxInTask(g, D1, D2, P, lo, (hi + lo) / 2);
            RelaxInTask right = new RelaxInTask(g, D1, D2, P, (hi + lo) / 2, hi);

            left.fork();
            right.compute();
            left.join();
        }
    }

    public static void sequential() {
        int cost;
        for (int w = lo; w < hi; w++) {
            for (int v : g.get(w).keySet()) {
                cost = g.get(w).get(v); // cost from v to w
                if (D2[v] != GraphUtil.INF && D1[w] > D2[v] + cost) {
                    D1[w] = D2[v] + cost;
                    P[w] = cost;
                }
            }
        }
    }

    public static void parallel(List<HashMap<Integer, Integer>> g, int[] D1, int[] D2,
                                int[] P, int n) {
        pool.invoke(new RelaxInTask(g, D1, D2, P, 0, n));
    }

}
