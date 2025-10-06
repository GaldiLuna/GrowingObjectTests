import java.util.List;

public interface AuctionSearchConsumer {
    void auctionSearchFound(List<AuctionDescription> results);
    void auctionSearchFinished();
}
