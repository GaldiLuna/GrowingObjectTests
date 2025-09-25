import org.junit.Test;

public class SniperLauncherTest {
    private final States auctionState = context.states("auction state").startsAs("not joined");
    [...]
    @Test
    public void addsNewSniperToCollectorAndThenJoinsAuction() {
        final String itemId = "item 123";
        context.checking(new Expectations() {{
            allowing(auctionHouse).auctionFor(itemId); will(returnValue(auction));
            oneOf(auction).addAuctionEventListener(with(sniperForItem(itemId)));
            when(auctionState.is("not joined"));
            oneOf(sniperCollector).addSniper(with(sniperForItem(item)));
            when(auctionState.is("not joined"));
            one(auction).join(); then(auctionState.is("joined"));
        }});
        launcher.joinAuction(itemId);
    }
}
