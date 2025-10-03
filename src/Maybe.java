public abstract class Maybe<T> implements Iterable<T> {
    abstract boolean hasResult();
    public static Maybe<T> just(T oneValue) { }
    public static Maybe<T> nothing() { }
}