import org.junit.Test;

public class AtomicBigCounterTests {
    final AtomicBigCounter counter = new AtomicBigCounter();
    @Test
    public void canIncrementCounterFromMultipleThreadsSimultaneously() throws InterruptedException {
        MultithreadedStressTester stressTester = new MultithreadedStressTester(25000);
        stressTester.stress(new Runnable() {
            public void run() {
                counter.inc();
            }
        });
        stressTester.shutdown();
        assertThat("final count", counter.count(), equalTo(BigInteger.valueOf(stressTester.totalActionCount())));
    }
}
