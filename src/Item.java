public class Item {
    public final String identifier;
    public final int stopPrice;
    public Item(String identifier, int stopPrice) {
        this.identifier = identifier;
        this.stopPrice = stopPrice;
    }
    // also equals(), hashCode(), toString()

    public boolean allowsBid(int bid) {
        return bid <= stopPrice;
    }
}
