import java.util.EventListener;

public interface AuctionEventListener extends EventListener {

    void currentPrice(int price, int increment, PriceSource priceSource);
    void auctionFailed();
    void auctionClosed();
}
