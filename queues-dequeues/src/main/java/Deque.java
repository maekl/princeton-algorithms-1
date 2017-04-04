import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private final DequeOps<Item> empty = new Empty();

    private DequeOps<Item> ops = empty;

    /**
     * Construct an empty deque
     */
    public Deque() {
    }

    /**
     * is the deque empty?
     *
     * @return true if deque is empty
     */
    public boolean isEmpty() {
        return ops.size() == 0;
    }

    /**
     * Return the number of items on the deque
     *
     * @return the number of items on the deque
     */
    public int size() {
        return ops.size();
    }

    /**
     * Add the item to the front
     *
     * @param item the item to add
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Null items not supported");
        }

        ops.addFirst(item);
    }

    /**
     * Add the item to the end
     *
     * @param item the item to add
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("Null items not supported");
        }

        ops.addLast(item);

    }

    /**
     * Remove and return the item from the front
     *
     * @return removed item
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("removeFirst called on empty Deque");
        }

        return ops.removeFirst();
    }

    /**
     * Remove and return the item from the end
     *
     * @return removed item
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("removeLast called on empty Deque");
        }

        return ops.removeLast();
    }

    /**
     * Return an iterator over items in order from front to end
     *
     * @return iterator over items
     */
    public Iterator<Item> iterator() {
        return ops.iterator();
    }

    /**
     * Abstracts over the operations on the deque. Simplifies add/remove logic.
     *
     * @param <T> item type
     */
    private interface DequeOps<T> extends Iterable<T> {
        void addFirst(T item);
        void addLast(T item);
        T removeFirst();
        T removeLast();
        int size();
    }

    /**
     * The empty deque
     */
    private final class Empty implements DequeOps<Item> {

        @Override
        public void addFirst(final Item item) {
            ops = new Singleton(item);
        }

        @Override
        public void addLast(final Item item) {
            addFirst(item);
        }

        @Override
        public Item removeFirst() {
            throw new NoSuchElementException("removeFirst() called on empty Deque");
        }

        @Override
        public Item removeLast() {
            throw new NoSuchElementException("removeLast() called on empty Deque");
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public Iterator<Item> iterator() {
            return new Iterator<Item>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public Item next() {
                    throw new NoSuchElementException("empty iterator");
                }
            };
        }
    }

    /**
     * A deque with a single item
     */
    private final class Singleton implements DequeOps<Item> {

        private final Item item;

        Singleton(final Item item) {
            this.item = item;
        }

        @Override
        public void addFirst(final Item first) {
            ops = new Multiple(first, this.item);
        }

        @Override
        public void addLast(final Item last) {
            ops = new Multiple(this.item, last);
        }

        @Override
        public Item removeFirst() {
            ops = empty;
            return item;
        }

        @Override
        public Item removeLast() {
            return removeFirst();
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public Iterator<Item> iterator() {

            return new Iterator<Item>() {
                private boolean called = false;

                @Override
                public boolean hasNext() {
                    return !called;
                }

                @Override
                public Item next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }

                    called = true;
                    return item;
                }
            };
        }
    }

    /**
     * A deque with at least two items.
     */
    private class Multiple implements DequeOps<Item> {

        private Node first;
        private Node last;
        private int size;

        Multiple(final Item first, final Item last) {
            this.first = new Node(first, last);

            this.last = this.first.next;
            this.size = 2;
        }


        @Override
        public void addFirst(final Item item) {
            this.first = new Node(item, first);
            this.size++;
        }

        @Override
        public void addLast(final Item item) {
            this.last = new Node(last, item);
            this.size++;
        }

        @Override
        public Item removeFirst() {

            final Item item = this.first.removeFirst();

            this.first = this.first.next;

            if (this.first == this.last) {
                ops = new Singleton(this.first.item);
            }

            this.size--;

            return item;
        }

        @Override
        public Item removeLast() {

            final Item item = this.last.removeLast();

            this.last = last.prev;

            if (this.first == this.last) {
                ops = new Singleton(this.first.item);
            }

            this.size--;

            return item;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public Iterator<Item> iterator() {
            return new DequeIterator(first);
        }

        private final class DequeIterator implements Iterator<Item> {

            private Node current;

            public DequeIterator(final Node first) {
                this.current = first;
            }

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (hasNext()) {
                    try {
                        return current.item;
                    } finally {
                        current = current.next;
                    }
                } else {
                    throw new NoSuchElementException("next() called on empty iterator");
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove not supported by deque iterator");
            }
        }
    }

    /**
     * Deque node
     */
    private final class Node {

        private final Item item;

        private Node prev;
        private Node next;

        /**
         * Create a new first node for the Deque.
         *
         * @param item item
         * @param next existing first node.
         */
        private Node(final Item item, final Node next) {
            this.item = item;
            this.next = next;
            this.next.prev = this;
        }

        /**
         * Create a new last node for the Deque.
         *
         * @param prev existing last node.
         * @param item item
         */
        private Node(final Node prev, final Item item) {
            this.item = item;
            this.prev = prev;
            this.prev.next = this;
        }

        /**
         * Create new first (this) & last node (this.next) for the deque. Use only
         * when initializing a Deque with exactly two items.
         *
         * @param first the first item.
         * @param last  the last item.
         */
        private Node(final Item first, final Item last) {
            this.item = first;
            this.next = new Node(this, last);
        }

        private Item removeFirst() {
            if (this.prev != null) {
                throw new IllegalStateException("removeFirst() called on node which is not the first.");
            }

            next.prev = null;

            return item;
        }

        private Item removeLast() {
            if (this.next != null) {
                throw new IllegalStateException("removeLast() called on node which is not the last");
            }

            prev.next = null;

            return item;
        }
    }
}