import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;

import org.jmock.Mockery;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.States;

import static java.util.Arrays.asList;
import org.hamcrest.MatcherAssert;

//import static StubAuctionHouse.auction;
//import static StubAuctionHouse.houses;
//import static AuctionSearchTests.StubAuctionHouse.auction;
//import static AuctionSearchTests.StubAuctionHouse.houses;
//import static AuctionSearchTests.StubAuctionHouse.set;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import static org.jmock.Expectations.*;
import static org.hamcrest.Matchers.anything;

@RunWith(JMock.class)
public class AuctionSearchTests {
    Mockery context = new JUnit4Mockery();
    final DeterministicExecutor executor = new DeterministicExecutor();
    final StubAuctionHouse houseA = new StubAuctionHouse("houseA");
    final StubAuctionHouse houseB = new StubAuctionHouse("houseB");
    List<AuctionDescription> resultsFromA = (StubAuctionHouse.auction(houseA, "1", "descriptionA"));
    List<AuctionDescription> resultsFromB = (StubAuctionHouse.auction(houseB, "2", "descriptionB"));;
    final AuctionSearchConsumer consumer = context.mock(AuctionSearchConsumer.class);
    final AuctionSearch search = new AuctionSearch(executor, auctionHouses(houseA, houseB), consumer);

    @Test
    public void searchesAllAuctionHouses() throws Exception {
        final Set<String> keywords = StubAuctionHouse.set("sheep", "cheese");
        houseA.willReturnSearchResults(keywords, resultsFromA);
        houseB.willReturnSearchResults(keywords, resultsFromB);
        context.checking(new Expectations() {{
            final States searching = context.states("searching");
            oneOf(consumer).auctionSearchFound(resultsFromA); when(searching.isNot("done"));
            oneOf(consumer).auctionSearchFound(resultsFromB); when(searching.isNot("done"));
            oneOf(consumer).auctionSearchFinished();
            then(searching.is("done"));
        }});
        search.search(keywords);
        executor.runUntilIdle();
    }
}
