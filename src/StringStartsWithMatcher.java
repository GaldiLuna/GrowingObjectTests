public class StringStartsWithMatcher extends TypeSafeMatcher<String> {
    private final String expectedPrefix;

    public static Matcher<String> aStringStartingWith(String prefix ) {
        return new StringStartsWithMatcher(prefix);
    }

    public StringStartsWithMatcher(String expectedPrefix) {
        this.expectedPrefix = expectedPrefix;
    }

    @Override
    protected boolean matchesSafely(String actual) {
        return actual.startsWith(expectedPrefix);
    }

    @Override
    public void describeTo(Description matchDescription) {
        matchDescription.appendText("a string starting with ").appendValue(expectedPrefix);
    }

    @Override
    protected void describeMismatchSafely(String actual, Description mismatchDescription) {
        String actualPrefix = actual.substring(0, Math.min(actual.length(), expectedPrefix.length()));
        mismatchDescription.appendText("started with ").appendValue(actualPrefix);
    }
}
