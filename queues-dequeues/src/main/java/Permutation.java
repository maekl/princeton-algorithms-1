import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Permutation {

    private final RandomizedQueue<String> q = new RandomizedQueue<>();

    private Permutation(int k) {
        for (int i = 0; !StdIn.isEmpty(); i++) {
            final String item = StdIn.readLine();

            if (i < k) {
                q.enqueue(item);
            } else if (StdRandom.uniform(i + 1) < k) {
                q.dequeue();
                q.enqueue(item);
            }
        }
    }

    private Iterator<String> result() {
        return q.iterator();
    }

    public static void main(String[] args) {

        final int k = Integer.parseInt(args[0]);

        final Permutation p = new Permutation(k);

        p.result().forEachRemaining(StdOut::println);
    }
}
