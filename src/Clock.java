public interface Clock {
    Object now();
    boolean dayHasChangedFrom(Object date);
    void setNextDate(Object date);

    void hasExpired();
}
