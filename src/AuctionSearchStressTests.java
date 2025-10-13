import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;

import org.jmock.Mockery;
import org.jmock.Expectations;
import org.jmock.States;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;

import static java.util.Arrays.asList;

import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(JMock.class)
public class AuctionSearchStressTests {
    private static final int NUMBER_OF_AUCTION_HOUSES = 4;
    private static final int NUMBER_OF_SEARCHES = 8;
    private static final Set<String> KEYWORDS = AuctionSearchTestHelpers.setOf("sheep", "cheese");
    final Synchroniser synchroniser = new Synchroniser();
    final Mockery context = new JUnit4Mockery() {{
        setThreadingPolicy(synchroniser);
    }};
    final AuctionSearchConsumer consumer = context.mock(AuctionSearchConsumer.class);
    final States searching = context.states("searching");
    final ExecutorService executor = Executors.newCachedThreadPool();
    final AuctionSearch search = new AuctionSearch(executor, auctionHouses(), consumer);
    private void waitForSearchToFinish() throws InterruptedException {
        Thread.sleep(250);
    }
    private List<AuctionHouse> auctionHouses() {
        ArrayList<AuctionHouse> auctionHouses = new ArrayList<AuctionHouse>();
        for (int i = 0; i < NUMBER_OF_AUCTION_HOUSES; i++) {
            auctionHouses.add(stubbedAuctionHouse(i));
        }
        return auctionHouses;
    }
    private AuctionHouse stubbedAuctionHouse(final int id) {
        StubAuctionHouse house = new StubAuctionHouse("house" + id);
        house.willReturnSearchResults(
                KEYWORDS, asList(new AuctionDescription(house, "id" + id, "description")));
        return house;
    }
    @Test(timeout=500)
    public void onlyOneAuctionSearchFinishedNotificationPerSearch() throws Exception {
        context.checking(new Expectations() {{
            ignoring (consumer).auctionSearchFound(with(AuctionSearchTestHelpers.anyResults()));
        }});
        for (int i = 0; i < NUMBER_OF_SEARCHES; i++) {
            completeASearch();
        }
    }
    private void completeASearch() throws InterruptedException {
        searching.startsAs("in progress");
        context.checking(new Expectations() {{
            exactly(1).of(consumer).auctionSearchFinished(); then(searching.is("done"));
        }});
        search.search(KEYWORDS);
        synchroniser.waitUntil(searching.is("done"));
    }
    @After
    public void cleanUp() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(1, SECONDS);
    }
}
