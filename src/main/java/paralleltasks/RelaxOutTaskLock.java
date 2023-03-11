package paralleltasks;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.ReentrantLock;

public class RelaxOutTaskLock extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;

    private static List<HashMap<Integer, Integer>> g;
    private static int[] D1, D2, P;
    private static int n, lo, hi;
    private static ReentrantLock[] locks;


    public RelaxOutTaskLock(ReentrantLock[] locks, List<HashMap<Integer, Integer>> g, int[] D1,
                            int[] D2, int[] P, int n, int lo, int hi) {
        this.locks = locks;
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
            sequential(locks,g, D1, D2, P, lo, hi);
            // else - parallel
        } else {
            RelaxOutTaskLock left = new RelaxOutTaskLock(locks, g, D1, D2, P, n, lo, (hi + lo) / 2);
            RelaxOutTaskLock right = new RelaxOutTaskLock(locks, g, D1, D2, P, n, (hi + lo) / 2, hi);

            left.fork();
            right.compute();
            left.join();
        }
    }

    public static void sequential(ReentrantLock[] locks, List<HashMap<Integer,Integer>> g, int[] D1, int[] D2,
                                  int[] P, int lo, int hi) {
        int cost;
        for (int v = lo; v < hi; v++) {
            for (int w : g.get(v).keySet()) {
                locks[w].lock();
                cost = g.get(v).get(w);
                if (D2[v] != GraphUtil.INF && D1[w] > D2[v] + cost) {
                    D1[w] = D2[v] + cost;
                    P[w] = v;
                }
                locks[w].unlock();
            }
        }
    }

    public static void parallel(List<HashMap<Integer, Integer>> g, int[] D1, int[] D2, int[] P,
                                int n) {
        ReentrantLock[] locks = new ReentrantLock[n];
        for (int i = 0; i < n; i++) {
            locks[i] = new ReentrantLock();
        }
        RelaxOutTaskLock task = new RelaxOutTaskLock(locks, g, D1, D2, P, n, 0, n);
        pool.invoke(task);
    }
}

