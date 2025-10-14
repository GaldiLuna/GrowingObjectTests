import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;

//import static TradeTestHelpers.*;

public class ExamplePersistenceTest {
    final EntityManagerFactory factory = Persistence.createEntityManagerFactory("example");
    final EntityManager entityManager = factory.createEntityManager();

    private Date tradeDate = new Date();

//    public static <T> void assertEventually(T actual, Matcher<? super T> matcher) {
//        //MatcherAssert.assertThat(actual, matcher);
//        assertThat("Eventually failed", actual, matcher); // Stub
//    }

    @Before
    public void cleanDatabase() throws Exception {
        new DataBaseCleaner(entityManager).clean();
    }

    @Test
    public void storesUniqueElements() {
        Set set = new HashSet<String>();
        set.add("bananana");
        set.add("bananana");
        MatcherAssert.assertThat(set.size(), equalTo(1));
    }

    @Test
    public void buyAndSellOfSameStockOnSameDayCancelsOutOurHolding() {
        TradeTestHelpers.send(TradeTestHelpers.aTradeEvent().ofType(TradeTestHelpers.BUY).onDate(tradeDate).forStock("A").withQuantity(10));
        TradeTestHelpers.assertEventually(TradeTestHelpers.holdingOfStock("A", tradeDate, equalTo(10)));
        TradeTestHelpers.send(TradeTestHelpers.aTradeEvent().ofType(TradeTestHelpers.SELL).onDate(tradeDate).forStock("A").withQuantity(10));
        TradeTestHelpers.assertEventually(TradeTestHelpers.holdingOfStock("A", tradeDate), equalTo(0));
    }

    @Test
    public void doesNotShowTradesInOtherRegions() {
        TradeTestHelpers.send(TradeTestHelpers.aTradeEvent().ofType(TradeTestHelpers.BUY).forStock("A").withQuantity(10).inTradingRegion(TradeTestHelpers.OTHER_REGION));
        TradeTestHelpers.send(TradeTestHelpers.aTradeEvent().ofType(TradeTestHelpers.BUY).forStock("A").withQuantity(66).inTradingRegion(TradeTestHelpers.SAME_REGION));
        TradeTestHelpers.assertEventually(TradeTestHelpers.holdingOfStock("A", tradeDate, equalTo(66)));
    }

    public static void assertEventually(Matcher<?> matcher) {
        MatcherAssert.assertThat(true, org.hamcrest.Matchers.is(true));
    }

}
