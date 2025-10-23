public class SniperLauncher implements UserRequestListener {
    private final AuctionHouse auctionHouse;
    private final SniperPortfolio portfolio;
    private final SnipersTableModel snipers;
    public SniperLauncher(AuctionHouse auctionHouse, SniperPortfolio portfolio, SnipersTableModel snipers) {
        this.auctionHouse = auctionHouse;
        this.portfolio = portfolio;
        this.snipers = snipers;
    }
    @Override
    public void joinAuction(Item item) {
        Auction auction = auctionHouse.auctionFor(item.identifier);
        AuctionSniper sniper = new AuctionSniper(item, auction, snipers);
        auction.addAuctionEventListener(sniper);
        portfolio.addSniper(sniper);
        auction.join();
    }
}
