import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Permutation {

    private static class StdInIterator implements Iterator<String> {

        @Override
        public boolean hasNext() {
            return !StdIn.isEmpty();
        }

        @Override
        public String next() {
            return StdIn.readString();
        }
    }

    private final RandomizedQueue<String> q = new RandomizedQueue<>();

    public Permutation(int k, Iterator<String> input) {

        int i = 0;

        while (input.hasNext()) {
            final String next = input.next();

            if (i < k) {
                q.enqueue(next);
            } else if (StdRandom.uniform(i + 1) < k) {
                q.dequeue();
                q.enqueue(next);
            }

            i++;
        }
    }

    public Iterator<String> result() {
        return q.iterator();
    }

    public static void main(String[] args) {

        final int k = Integer.parseInt(args[0]);

        final Permutation p = new Permutation(k, new StdInIterator());

        p.result().forEachRemaining(StdOut::println);
    }
}
