import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matcher;

public class NotificationTrace<T> {
    private final Object traceLock = new Object();
    private final List<T> trace = new ArrayList<>();
    private long timeoutMs;
    // constructors and accessors to configure the timeout
    public void append(T message) {
        synchronized (traceLock) {
            trace.add(message);
            traceLock.notifyAll();
        }
    }
    public void containsNotification(Matcher<? super T> criteria) throws InterruptedException {
        Timeout timeout = new Timeout(timeoutMs);
        synchronized (traceLock) {
            NotificationStream<T> stream = new NotificationStream<T>(trace, criteria);
            while (! stream.hasMatched()) {
                if (timeout.hasTimedOut()) {
                    throw new AssertionError(failureDescriptionFrom(criteria));
                }
                timeout.waitOn(traceLock);
            }
        }
    }
    private String failureDescriptionFrom(Matcher<? super T> matcher) {
        // Construct a description of why there was no match,
        // including the matcher and all the received messages.
    }
}
