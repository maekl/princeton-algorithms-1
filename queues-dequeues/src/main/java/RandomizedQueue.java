import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;

    private int capacity;
    private int size;

    /**
     * Construct an empty randomized queue
     */
    public RandomizedQueue() {
        this.capacity = 1;
        this.size = 0;
        this.items = itemArray(this.capacity);
    }


    /**
     * Is the queue empty?
     *
     * @return true if the queue is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * The number of items on the queue
     *
     * @return number of items on the queue
     */
    public int size() {
        return size;
    }

    /**
     * Add the item
     *
     * @param item item to add
     */
    public void enqueue(final Item item) {
        if (item == null) {
            throw new NullPointerException("Does not support null items");
        }

        if (size + 1 > capacity) {
            grow();
        }

        items[size++] = item;
    }

    /**
     * Remove and return a random item
     *
     * @return item removed
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        final int randomIndex = StdRandom.uniform(size);

        final Item ret = items[randomIndex];

        items[randomIndex] = items[--size];

        items[size] = null;

        if (capacity / 4 > size) {
            shrink();
        }

        return ret;
    }

    /**
     * Return (but do not remove) a random item
     *
     * @return random item on queue
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return items[StdRandom.uniform(size)];
    }

    /**
     * Return iterator over items in random order
     *
     * @return iterator of items
     */
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private final int[] randomizedIndices = randomizeIndices();
        private int current = 0;

        private int[] randomizeIndices() {
            final int[] indices = new int[size];

            for (int i = 0; i < size; i++) {
                indices[i] = i;
            }

            StdRandom.shuffle(indices);

            return indices;
        }


        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return items[randomizedIndices[current++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove not supported");

        }
    }

    private void shrink() {
        this.capacity = this.capacity / 2;

        final Item[] shrunk = itemArray(capacity);

        int index = 0;

        for (int i = 0; i < capacity; i++) {
            shrunk[index++] = items[i];
        }

        this.items = shrunk;
    }

    private void grow() {
        this.capacity = this.capacity * 2;

        final Item[] grown = itemArray(capacity);

        int index = 0;

        for (final Item i : items) {
            grown[index++] = i;
        }

        this.items = grown;
    }

    private Item[] itemArray(int maxCapacity) {
        return (Item[]) new Object[maxCapacity];
    }
}
