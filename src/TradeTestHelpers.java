import org.hamcrest.Matcher;
import java.util.Date;
import java.util.Set;

public class TradeTestHelpers {
    public static final Object BUY = new Object();
    public static final Object SELL = new Object();
    public static final Object OTHER_REGION = new Object();
    public static final Object SAME_REGION = new Object();

    public static TradeEventBuilder aTradeEvent() { return new TradeEventBuilder(); }
    public static class TradeEventBuilder {
        public TradeEventBuilder ofType(Object type) { return this; }
        public TradeEventBuilder onDate(Date date) { return this; }
        public TradeEventBuilder forStock(String stock) { return this; }
        public TradeEventBuilder withQuantity(int qty) { return this; }
        public TradeEventBuilder inTradingRegion(Object region) { return this; }
        public Object build() { return new Object(); }
    }

    public static void send(Object tradeEvent) { /* stub */ }
    public static void assertEventually(Object probe) { /* stub */ }
    public static void assertEventually(Matcher<?> matcher) { /* stub */ }

    public static Matcher<Integer> holdingOfStock(String stock, Date date, Matcher<Integer> matcher) {
        return matcher;
    }
    public static Matcher<Integer> holdingOfStock(String stock, Date date) {
        return org.hamcrest.Matchers.equalTo(0); // Stub para o método sem matcher
    }
}
