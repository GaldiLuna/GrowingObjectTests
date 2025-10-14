import java.util.ArrayList;

public class SniperLauncher implements UserRequestListener {
    private final ArrayList<Auction> notToBeGCd = new ArrayList<Auction>();
    private final AuctionHouse auctionHouse;
    private final SniperCollector collector;
    private final SnipersTableModel snipers;
    public SniperLauncher(AuctionHouse auctionHouse, SniperCollector collector, SnipersTableModel snipers) {
        this.auctionHouse = auctionHouse;
        this.collector = collector;
        this.snipers = snipers;
    }
    public void joinAuction(Item item) {
        Auction auction = auctionHouse.auctionFor(item.identifier);
        AuctionSniper sniper = new AuctionSniper(item.identifier, auction, snipers);
        auction.addAuctionEventListener(sniper);
        collector.addSniper(sniper);
        auction.join();
    }
}
