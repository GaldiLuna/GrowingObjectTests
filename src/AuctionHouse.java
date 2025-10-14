import java.util.List;
import java.util.Set;

public interface AuctionHouse {
    List<AuctionDescription> findAuctions(Set<String> keywords);
    Auction auctionFor(String itemId);
    void disconnect();
}
