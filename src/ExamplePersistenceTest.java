import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

//import org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;

//import static TradeTestHelpers.*;

public class ExamplePersistenceTest {
    final EntityManagerFactory factory = Persistence.createEntityManagerFactory("example");
    final EntityManager entityManager = factory.createEntityManager();

    private Date tradeDate = new Date();

    @Before
    public void cleanDatabase() throws Exception {
        new DataBaseCleaner(entityManager).clean();
    }

    @Test
    public void storesUniqueElements() {
        Set set = new HashSet<String>();
        set.add("bananana");
        set.add("bananana");
        assertThat(set.size(), equalTo(1));
    }

    @Test
    public void buyAndSellOfSameStockOnSameDayCancelsOutOurHolding() {
        send(aTradeEvent().ofType(BUY).onDate(tradeDate).forStock("A").withQuantity(10));
        assertEventually(TradeTestHelpers.holdingOfStock("A", tradeDate, equalTo(10)));
        send(aTradeEvent().ofType(SELL).onDate(tradeDate).forStock("A").withQuantity(10));
        assertEventually(TradeTestHelpers.holdingOfStock("A", tradeDate), equalTo(0));
    }

    @Test
    public void doesNotShowTradesInOtherRegions() {
        send(aTradeEvent().ofType(BUY).forStock("A").withQuantity(10).inTradingRegion(OTHER_REGION));
        send(aTradeEvent().ofType(BUY).forStock("A").withQuantity(66).inTradingRegion(SAME_REGION));
        assertEventually(TradeTestHelpers.holdingOfStock("A", tradeDate, equalTo(66)));
    }

    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        MatcherAssert.assertThat(actual, matcher);
    }

}
