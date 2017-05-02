import com.google.common.collect.Iterables;
import org.junit.Test;

public class SolverTest {

    @Test
    public void solutionInitialBoard() {
        final Board board = TestUtils.boardFromFile("puzzle15.txt");


        final Solver solver = new Solver(board);


        final Board initial = Iterables.getFirst(solver.solution(), null);

        for (final Board b : solver.solution()) {
            System.out.println(b);
        }

//        Assert.assertEquals(board, initial);

    }

}
