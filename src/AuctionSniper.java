public class AuctionSniper implements AuctionEventListener {
    private SniperSnapshot snapshot;
    private boolean isWinning = false;
    private final String itemId;
    private final Item item;
    private final SniperListener sniperListener;
    private final Auction auction;
    private final Announcer<AuctionEventListener> listeners = Announcer.to(AuctionEventListener.class);
    public AuctionSniper(String itemId, Auction auction, SniperListener sniperListener) {
        this.itemId = itemId;
        this.auction = auction;
        this.sniperListener = sniperListener;
        this.snapshot = SniperSnapshot.joining(itemId);
        this.item = new Item(itemId, Integer.MAX_VALUE);
    }

    public AuctionSniper(Item item, Auction auction, SniperListener sniperListener) {
        this.item = item;
        this.itemId = item.identifier;
        this.auction = auction;
        this.sniperListener = sniperListener;
        this.snapshot = SniperSnapshot.joining(item.identifier);
    }

    public void addSniperListener(SniperListener listener) {
        listeners.addListener((AuctionEventListener) listener);
    }

    public SniperSnapshot getSnapshot() {
        return snapshot;
    }

    public void auctionClosed() {
        snapshot = snapshot.closed();
        notifyChange();
    }
    public void auctionFailed() {
        snapshot = snapshot.failed();
        listeners.announce().auctionFailed();
        notifyChange();
    }
    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        switch(priceSource) {
            case PriceSource.FromSniper:
                snapshot = snapshot.winning(price);
                break;
            case PriceSource.FromOtherBidder:
                int bid = price + increment;
                if (item.allowsBid(bid)) {
                    auction.bid(bid);
                    snapshot = snapshot.bidding(price, bid);
                } else {
                    snapshot = snapshot.losing(price);
                }
                break;
        }
        notifyChange();
    }
    private void notifyChange() {
        sniperListener.sniperStateChanged(snapshot);
    }

}
