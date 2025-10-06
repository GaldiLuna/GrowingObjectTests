import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.anything;

public class AuctionSearchTestHelpers {
    public static <T> Set<T> setOf(T... elements) {
        return new HashSet<>(Arrays.asList(elements));
    }

    public static Matcher<List<AuctionDescription>> anyResults() {
        return (Matcher<List<AuctionDescription>>) (Matcher<?>) anything();
    }
}
