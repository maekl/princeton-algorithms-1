import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import edu.princeton.cs.algs4.Point2D;

@SuppressWarnings("unused")
public class Points extends Generator<Point2D> {

    public Points(final Class<Point2D> type) {
        super(type);
    }

    @Override
    public Point2D generate(final SourceOfRandomness rand, final GenerationStatus generationStatus) {
        return new Point2D(rand.nextDouble(), rand.nextDouble());
    }
}
