public interface Matcher<T> extends SelfDescribing {
    boolean matches(Object item);
    void describeMismatch(Object item, Description mismatchDescription);
}
