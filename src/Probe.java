public interface Probe {
    boolean isSatisfied();
    void sample();
    void describeFailureTo(Description d);
}
