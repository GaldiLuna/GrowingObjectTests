import org.hamcrest.Matcher;
import java.util.List;
//import java.util.regex.Matcher;

public class NotificationStream<N> {
    private final List<N> notifications;
    private final Matcher<? super N> criteria;
    private int next = 0;
    public NotificationStream(List<N> notifications, Matcher<? super N> criteria) {
        this.notifications = notifications;
        this.criteria = criteria;
    }
    public boolean hasMatched() {
        while (next < notifications.size()) {
            if (criteria.matches(notifications.get(next)))
                return true;
            next++;
        }
        return false;
    }
}
