public class AuctionDescription {
    private final AuctionHouse house;
    private final String id;
    private final String description;
    private final StubAuctionHouse h;
    private final String desc;
    public AuctionDescription(AuctionHouse house, String id, String description) {
        this.house = house;
        this.id = id;
        this.description = description;
    }
    public AuctionDescription(StubAuctionHouse h, String id, String desc) {
        this.h = h;
        this.id = id;
        this.desc = desc;
    }
}
