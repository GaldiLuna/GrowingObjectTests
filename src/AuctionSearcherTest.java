import org.junit.Test;

import org.junit.runner.RunWith;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.jmock.States;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import static org.jmock.Expectations.*;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.jmock.States;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import static org.jmock.Expectations.*;

@RunWith(JMock.class)
public class AuctionSearcherTest {
    AuctionSearchListener searchListener;
    private final Mockery context = new JUnit4Mockery();
    private final Auction STUB_AUCTION1 = context.mock(Auction.class, "STUB_AUCTION1");
    private final Auction STUB_AUCTION2 = context.mock(Auction.class, "STUB_AUCTION2");
    private final Set<String> KEYWORDS = new HashSet<>(Arrays.asList("keyword"));

    @Test
    public void announcesMatchForOneAuction() {
        final AuctionSearcher auctionSearch = new AuctionSearcher(searchListener, asList(STUB_AUCTION1));
        context.checking(new Expectations() {{
            Sequence events = context.sequence("events");
            oneOf(searchListener).searchMatched(STUB_AUCTION1); inSequence(events);
            oneOf(searchListener).searchMatched(STUB_AUCTION2); inSequence(events);
            oneOf(searchListener).searchFinished(); inSequence(events);
        }});
        auctionSearch.searchFor(KEYWORDS);
    }
    @Test
    public void announcesMatchForTwoAuctions() {
        final AuctionSearcher auctionSearch = new AuctionSearcher(searchListener, asList(STUB_AUCTION1, STUB_AUCTION2));
        context.checking(new Expectations() {{
            States searching = context.states("searching");
            oneOf(searchListener).searchMatched(STUB_AUCTION1);when(searching.isNot("finished"));
            oneOf(searchListener).searchMatched(STUB_AUCTION2);when(searching.isNot("finished"));
            oneOf(searchListener).searchFinished(); then(searching.is("finished"));
        }});
        auctionSearch.searchFor(KEYWORDS);
    }
}
