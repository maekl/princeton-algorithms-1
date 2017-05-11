import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import edu.princeton.cs.algs4.RectHV;

@SuppressWarnings("unused")
public class Rectangles extends Generator<RectHV> {

    public Rectangles(final Class<RectHV> type) {
        super(type);
    }

    @Override
    public RectHV generate(final SourceOfRandomness rand, final GenerationStatus generationStatus) {

        final double xmin = rand.nextDouble(0, 1);
        final double xmax = rand.nextDouble(xmin, 1);

        final double ymin = rand.nextDouble(0, 1);
        final double ymax = rand.nextDouble(ymin, 1);

        return new RectHV(xmin, ymin, xmax, ymax);

    }
}
