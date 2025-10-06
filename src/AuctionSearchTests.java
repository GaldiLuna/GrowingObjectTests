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

@RunWith(JMock.class)
public class AuctionSearchTests {
    Mockery context = new JUnit4Mockery();
    final DeterministicExecutor executor = new DeterministicExecutor();
    final StubAuctionHouse houseA = new StubAuctionHouse("houseA");
    final StubAuctionHouse houseB = new StubAuctionHouse("houseB");
    List<AuctionDescription> resultsFromA = asList(auction(houseA, "1"));
    List<AuctionDescription> resultsFromB = asList(auction(houseB, "2"));;
    final AuctionSearchConsumer consumer = context.mock(AuctionSearchConsumer.class);
    final AuctionSearch search = new AuctionSearch(executor, houses(houseA, houseB), consumer);

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
