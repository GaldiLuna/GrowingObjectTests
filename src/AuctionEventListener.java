import java.util.EventListener;

public interface AuctionEventListener extends EventListener {
    void currentPrice(int price, int increment);
    void auctionFailed();
    void auctionClosed();
}
