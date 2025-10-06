public interface Clock {
    java.util.Date now();
    boolean dayHasChangedFrom(java.util.Date date);
}
