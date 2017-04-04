import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DequeTest {
    @Test(expected = NullPointerException.class)
    public void testAddThrowsNPE() {
        new Deque<>().addFirst(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFirstThrowsNoSuchElementExceptionIfEmpty() {
        new Deque<>().removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void removeLastThrowsNoSuchElementExceptionIfEmpty() {
        new Deque<>().removeLast();
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorNextThrowsNoSuchElementExceptionIfEmpty() {
        new Deque<>().iterator().next();
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorNextThrowsNoSuchElementExceptionIfEmpty2() {
        final Deque<Integer> deque = new Deque<>();

        deque.addFirst(1);
        deque.removeLast();

        new Deque<>().iterator().next();
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorNextThrowsNoSuchElementExceptionIfEmpty3() {
        final Deque<Integer> deque = new Deque<>();

        deque.addFirst(1);

        final Iterator<Integer> iterator = deque.iterator();

        iterator.next();
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorNextThrowsNoSuchElementExceptionIfEmpty4() {
        final Deque<Integer> deque = new Deque<>();

        deque.addFirst(1);
        deque.addFirst(1);

        final Iterator<Integer> iterator = deque.iterator();

        iterator.next();
        iterator.next();
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorNextThrowsNoSuchElementExceptionIfEmpty5() {
        final Deque<Integer> deque = new Deque<>();

        deque.addFirst(1);
        deque.addFirst(1);
        deque.addFirst(1);

        final Iterator<Integer> iterator = deque.iterator();

        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeNotSupportedOnIterator() {
        new Deque<>().iterator().remove();
    }

    @Test
    public void emptyDequeHasSizeZero() {
        assert (new Deque<>().size() == 0);
    }

    @Test
    public void emptyDequeHasEmptyIterator() {
        assert (!new Deque<>().iterator().hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void emptyIteratorThrowsNoSuchElementException() {
        new Deque<>().iterator().next();
    }

    @Test
    public void addFirstMakesDequeSizeOne() {
        final Deque<String> stringDeque = new Deque<>();

        stringDeque.addFirst("test");

        assert (stringDeque.size() == 1);
        assert (!stringDeque.isEmpty());
    }

    @Test
    public void addLastMakesDequeSizeOne() {
        final Deque<String> stringDeque = new Deque<>();

        stringDeque.addFirst("test");

        assert (stringDeque.size() == 1);
        assert (!stringDeque.isEmpty());
    }

    @Test
    public void addFirstRemoveFirstMakesDequeSizeZero() {
        final Deque<String> stringDeque = new Deque<>();

        stringDeque.addFirst("test");
        stringDeque.removeFirst();

        assert (stringDeque.size() == 0);
        assert (stringDeque.isEmpty());
    }

    @Test
    public void removeLastSingletonDequeYieldsEmptyDeque() {
        final Deque<String> d1 = new Deque<>();

        final String s = "test";

        d1.addFirst(s);

        final String last = d1.removeLast();

        assert (last.equals(s));
        assert (d1.isEmpty());
        assert (d1.size() == 0);
    }

    @Test
    public void removeFirstSingletonDequeYieldsEmptyDeque() {
        final Deque<String> d1 = new Deque<>();

        final String s = "test";

        d1.addFirst(s);

        final String first = d1.removeFirst();

        assert (first.equals(s));
        assert (d1.isEmpty());
        assert (d1.size() == 0);
    }

    @Test
    public void addLastOnEmptyDequeYieldsSingletonDeque() {
        final Deque<String> d1 = new Deque<>();

        final String s = "test";

        d1.addLast(s);

        final String first = d1.removeFirst();

        assert (first.equals(s));
        assert (d1.isEmpty());
        assert (d1.size() == 0);
    }

    @Test
    public void dequeIteratorInOrder() {
        final Deque<String> d1 = new Deque<>();

        final String first = "first";
        final String next = "next";

        d1.addFirst(first);
        d1.addFirst(next);

        final Iterator<String> iterator = d1.iterator();

        final String first1 = iterator.next();
        final String next1 = iterator.next();

        assert (next.equals(first1));
        assert (first.equals(next1));
    }

    @Test
    public void removeFirstRemoveLast() {

        final Deque<String> d1 = new Deque<>();

        final String first = "first";
        final String last = "last";

        d1.addFirst(first);
        d1.addLast(last);

        assert (d1.size() == 2);

        final String f = d1.removeFirst();
        final String l = d1.removeLast();

        assert (f.equals(first));
        assert (l.equals(last));
    }

}
