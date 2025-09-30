import java.util.List;
import java.util.Set;

public class AuctionSearch {
    private final Executor executor;
    private final List<AuctionHouse> auctionHouses;
    private final AuctionSearchConsumer consumer;
    private int runningSearchCount = 0;
    private final AtomicInteger runningSearchCount = new AtomicInteger();
    public AuctionSearch(Executor executor,
                         List<AuctionHouse> auctionHouses,
                         AuctionSearchConsumer consumer)
    {
        this.executor = executor;
        this.auctionHouses = auctionHouses;
        this.consumer = consumer;
    }
    public void search(Set<String> keywords) {
        runningSearchCount.set(auctionHouses.size());
        for (AuctionHouse auctionHouse : auctionHouses) {
            startSearching(auctionHouse, keywords);
        }
    }
    private void startSearching(final AuctionHouse auctionHouse,
                                final Set<String> keywords)
    {
        // no longer increments the count here
        executor.execute(new Runnable() {
            public void run() {
                search(auctionHouse, keywords);
            }
        });
    }
    private void search(AuctionHouse auctionHouse, Set<String> keywords) {
        consumer.auctionSearchFound(auctionHouse.findAuctions(keywords));
        if (runningSearchCount.decrementAndGet() == 0) {
            consumer.auctionSearchFinished();
        }
    }
}
