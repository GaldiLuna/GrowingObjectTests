import org.junit.Test;

public class AuctionSearcherTest {
    @Test
    public void announcesMatchForOneAuction() {
        final AuctionSearcher auctionSearch = new AuctionSearcher(searchListener,
                new AuctionSearcher(searchListener, asList(STUB_AUCTION1, STUB_AUCTION2));
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
        final AuctionSearcher auctionSearch = new AuctionSearcher(searchListener,
                new AuctionSearcher(searchListener, asList(STUB_AUCTION1, STUB_AUCTION2));
        context.checking(new Expectations() {{
            States searching = context.states("searching");
            oneOf(searchListener).searchMatched(STUB_AUCTION1);
            when(searching.isNot("finished"));
            oneOf(searchListener).searchMatched(STUB_AUCTION2);
            when(searching.isNot("finished"));
            oneOf(searchListener).searchFinished(); then(searching.is("finished"));
        }});
        auctionSearch.searchFor(KEYWORDS);
    }
}
