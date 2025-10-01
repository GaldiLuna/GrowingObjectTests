public class Poller {
    private long timeoutMillis;
    private long pollDelayMillis;
    // constructors and accessors to configure the timeout [...]
    public void check(Probe probe) throws InterruptedException {
        Timeout timeout = new Timeout(timeoutMillis);
        while (! probe.isSatisfied()) {
            if (timeout.hasTimedOut()) {
                throw new AssertionError(describeFailureOf(probe));
            }
            Thread.sleep(pollDelayMillis);
            probe.sample();
        }
    }
    private String describeFailureOf(Probe probe) {  }

    public static void assertEventually(Probe probe) throws InterruptedException {
        new Poller(1000L, 100L).check(probe);
    }
    public static Probe fileLength(String path, final Matcher<Integer> matcher) {
        final File file = new File(path);
        return new Probe() {
            private long lastFileLength = NOT_SET;
            public void sample() { lastFileLength = file.length(); }
            public boolean isSatisfied() {
                return lastFileLength != NOT_SET && matcher.matches(lastFileLength);
            }
            public void describeFailureTo(Description d) {
                d.appendText("length was ").appendValue(lastFileLength);
            }
        };
    }
}
