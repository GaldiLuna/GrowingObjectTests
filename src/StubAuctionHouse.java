import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StubAuctionHouse implements AuctionHouse {
    public StubAuctionHouse(String name) {}
    public void willReturnSearchResults(Set<String> keywords, List<AuctionDescription> results) {}

    public static List<AuctionDescription> auction(AuctionHouse house, String id, String description) {
        return Arrays.asList(new AuctionDescription(house, id, description));
    }
    public static List<StubAuctionHouse> houses(StubAuctionHouse... houses) {
        return Arrays.asList(houses);
    }
    public static Set<String> set(String... elements) {
        return new HashSet<>(Arrays.asList(elements));
    }
    @Override
    public List<AuctionDescription> findAuctions(Set<String> keywords) {
        // stub
        return null;
    }
}
