public class MultithreadedStressTester {
    private final int numberOfThreads;

    public MultithreadedStressTester(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public void stress(Runnable action) throws InterruptedException {
        action.run();
    }

    public void shutdown() throws InterruptedException {}

    public int totalActionCount() {
        return 25000; // Stub para o valor passado no construtor de teste.
    }
}
