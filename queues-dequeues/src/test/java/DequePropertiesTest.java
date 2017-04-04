import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitQuickcheck.class)
public class DequePropertiesTest {

    @Property
    public void addFirstAddLast(ArrayList<String> strings) {

        final Deque<String> deque1 = new Deque<>();
        final Deque<String> deque2 = new Deque<>();

        for (final String string : strings) {
            deque1.addFirst(string);
            deque2.addLast(string);
        }

        assert (deque1.size() == strings.size());
        assert (deque2.size() == strings.size());
    }

    @Property
    public void iteratorOrdering(ArrayList<Integer> ints) {

        final Deque<Integer> deque1 = new Deque<>();
        final Deque<Integer> deque2 = new Deque<>();

        for (final Integer i : ints) {
            deque1.addFirst(i);
            deque2.addLast(i);
        }

        final List<Integer> list1 = new ArrayList<>();
        final List<Integer> list2 = new ArrayList<>();

        deque1.iterator().forEachRemaining(list1::add);
        deque2.iterator().forEachRemaining(list2::add);

        assertThat(list2, is(ints));
        assertThat(reverse(list1), is(ints));
    }

    @Property
    public void addRemove(ArrayList<Integer> ints) {

        final Deque<Integer> deque = new Deque<>();

        ints.forEach(i -> {
            if (Math.random() > 0.5) {
                deque.addFirst(i);
            } else {
                deque.addLast(i);
            }
        });

        assert (deque.size() == ints.size());

        ints.forEach(i -> {
            if (Math.random() > 0.5) {
                deque.removeFirst();
            } else {
                deque.removeLast();
            }
        });

        assert (deque.isEmpty());

    }

    private static <T> List<T> reverse(List<T> list) {
        for (int i = 0, j = list.size() - 1; i < j; i++) {
            list.add(i, list.remove(j));
        }
        return list;
    }
}
