import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueueTest {

    @Test(expected = NullPointerException.class)
    public void testEnqueueThrowsNPE() {
        new RandomizedQueue<>().enqueue(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeNotSupportedOnIterator() {
        new RandomizedQueue<>().iterator().remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void throwNoSuchElementExceptionNextOnEmptyIterator() {
        Iterator<Integer> iterator = new RandomizedQueue<Integer>().iterator();
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void throwNoSuchElementExceptionNextOnEmptyIterator2() {
        final RandomizedQueue<Integer> q = new RandomizedQueue<>();

        q.enqueue(1);

        Iterator<Integer> iterator = q.iterator();

        iterator.next();
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void throwNoSuchElementExceptionNextOnEmptyIterator3() {
        final RandomizedQueue<Integer> q = new RandomizedQueue<>();

        q.enqueue(1);
        q.enqueue(1);

        Iterator<Integer> iterator = q.iterator();

        iterator.next();
        iterator.next();
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void throwNoSuchElementExceptionDequeOnEmptyQueue() {
        new RandomizedQueue<Integer>().dequeue();
    }

    @Test(expected = NoSuchElementException.class)
    public void throwNoSuchElementExceptionSampleOnEmptyQueue() {
        new RandomizedQueue<Integer>().sample();
    }

    @Test
    public void newlyCreatedQueueIsEmpty() {
        final RandomizedQueue<Integer> q = new RandomizedQueue<>();

        assert (q.isEmpty());
    }

    @Test
    public void newlyCreatedQueueIsHasSizeZero() {
        final RandomizedQueue<Integer> q = new RandomizedQueue<>();

        assert (q.size() == 0);
    }

    @Test
    public void enqueueOnceResultsInNonEmptyQueue() {
        final RandomizedQueue<Integer> q = new RandomizedQueue<>();

        q.enqueue(1);

        assert (!q.isEmpty());
    }

    @Test
    public void enqueueOnceResultsInQueueSizeOne() {
        final RandomizedQueue<Integer> q = new RandomizedQueue<>();

        q.enqueue(1);

        assert (q.size() == 1);
    }

    @Test
    public void enqueueOnceDequeOnceResultsInEmptyQueue() {
        final RandomizedQueue<Integer> q = new RandomizedQueue<>();

        q.enqueue(1);
        q.dequeue();

        assert (q.isEmpty());
    }

    @Test
    public void enqueueOnceDequeOnceResultsInQueueSizeZero() {
        final RandomizedQueue<Integer> q = new RandomizedQueue<>();

        q.enqueue(1);
        q.dequeue();

        assert (q.size() == 0);
    }
}
