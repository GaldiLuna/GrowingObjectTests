import org.hamcrest.Matcher;
import org.hamcrest.Description;
import java.io.File;
import java.io.IOException;

public class ProbeTestHelpers {
    private static final long NOT_SET = -1L;

    public static void assertEventually(Probe probe) throws InterruptedException {
        new Poller(1000L, 100L).check(probe); // Poller agora tem o construtor
    }

    public static Probe fileLength(String path, final Matcher<Integer> matcher) {
        final File file = new File(path);
        return new Probe() {
            private long lastFileLength = NOT_SET;

            public void sample() {
                // try-catch necessário para lidar com a ausência de métodos
                try {
                    lastFileLength = file.length();
                } catch (SecurityException e) {
                    lastFileLength = NOT_SET;
                }
            }

            public boolean isSatisfied() {
                return lastFileLength != NOT_SET && matcher.matches((int)lastFileLength);
            }

            public void describeFailureTo(Description d) {
                d.appendText("length was ").appendValue(lastFileLength);
            }
        };
    }
}
