import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public abstract class Maybe<T> implements Iterable<T> {
    abstract boolean hasResult();
    public static <T> Maybe<T> just(T oneValue) {
        return new Maybe<T>() {
            @Override public boolean hasResult() { return true; }
            @Override public Iterator<T> iterator() {
                return Collections.singleton(oneValue).iterator();
            }
        };
    }
    public static <T> Maybe<T> nothing() {
        return new Maybe<T>() {
            @Override public boolean hasResult() { return false; }
            @Override public Iterator<T> iterator() {
                return Collections.<T>emptySet().iterator();
            }
        };
    }
    public abstract Iterator<T> iterator();
}