public class SniperLauncher implements UserRequestListener {
    private final AuctionHouse auctionHouse;
    private final SniperCollector collector;
    private final SnipersTableModel snipers;
    public SniperLauncher(AuctionHouse auctionHouse, SniperCollector sniperCollector, SnipersTableModel snipers) {
        this.auctionHouse = auctionHouse;
        this.collector = sniperCollector;
        this.snipers = snipers;
    }
    @Override
    public void joinAuction(Item item) {
        Auction auction = auctionHouse.auctionFor(item.identifier);
        AuctionSniper sniper = new AuctionSniper(item, auction, snipers);
        auction.addAuctionEventListener(sniper);
        collector.addSniper(sniper);
        auction.join();
    }
}
