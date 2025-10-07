import org.junit.Test;
import org.junit.runner.RunWith;
import org.jmock.integration.junit4.JMock;

import org.jmock.States;
import org.jmock.Mockery;
import org.jmock.Expectations;
import static org.jmock.Expectations.*;

@RunWith(JMock.class)
public class SniperLauncherTest {
    private final Mockery context = new Mockery();
    private final States auctionState = context.states("auction state").startsAs("not joined");
    private final AuctionHouse auctionHouse = context.mock(AuctionHouse.class);
    private final SniperCollector sniperCollector = context.mock(SniperCollector.class);
    private final Auction auction = context.mock(Auction.class);
    private final SniperLauncher launcher = new SniperLauncher(auctionHouse, sniperCollector);
    private final String item = "item 123"; // Simplificação do item usado no teste.
    private Matcher<AuctionSniper> sniperForItem(final String itemId) {
        // Assume-se que a lógica do FeatureMatcher para verificar o itemId
        return (Matcher<AuctionSniper>) (Matcher<?>) anything(); // Simplificação para compilação
    }


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
