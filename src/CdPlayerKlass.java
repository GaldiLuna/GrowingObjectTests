public class CdPlayerKlass {
    CdPlayer player = new CdPlayer() {
        public void scheduleToStartAt(Object startTime) {
            scheduledTime.set(startTime);
        }
    };
}
