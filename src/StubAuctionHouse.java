import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StubAuctionHouse {
    public StubAuctionHouse(String name) {}
    public void willReturnSearchResults(Set<String> keywords, List<AuctionDescription> results) {}

    // Métodos estáticos auxiliares usados nas linhas de declaração de campo:
    public static List<AuctionDescription> auction(StubAuctionHouse house, String id) {
        return Arrays.asList(new AuctionDescription(house, id));
    }
    public static List<StubAuctionHouse> houses(StubAuctionHouse... houses) {
        return Arrays.asList(houses);
    }
    public static Set<String> set(String... elements) {
        return new HashSet<>(Arrays.asList(elements));
    }
}
