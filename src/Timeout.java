public class Timeout {
    private long endTime;
    public Timeout(long duration) {
        this.endTime = System.currentTimeMillis() + duration;
    }
    public boolean hasTimedOut() { return false; }
    public void waitOn(Object lock) throws InterruptedException {}
}
