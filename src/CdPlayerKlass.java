public class CdPlayerKlass {
    private final MutableTime scheduledTime = new MutableTime() {
        @Override public void set(Object time) {}

        @Override
        public Object get() {
            return null;
        }
    };
    CdPlayer player = new CdPlayer() {
        public void scheduleToStartAt(Object time) {
            scheduledTime.set(time);
        }
        @Override public void stop() {}
        @Override public void gotoTrack(int trackNumber) {}
        @Override public void spinUpDisk() {}
        @Override public void eject() {}
    };
}
