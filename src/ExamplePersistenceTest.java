import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

public class ExamplePersistenceTest {
    final EntityManagerFactory factory = Persistence.createEntityManagerFactory("example");
    final EntityManager entityManager = factory.createEntityManager();

    @Before
    public void cleanDatabase() throws Exception {
        new DatabaseCleaner(entityManager).clean();
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
        Date tradeDate = new Date();
        send(aTradeEvent().ofType(BUY).onDate(tradeDate).forStock("A").withQuantity(10));
        assertEventually(holdingOfStock("A", tradeDate, equalTo(10)));
        send(aTradeEvent().ofType(SELL).onDate(tradeDate).forStock("A").withQuantity(10));
        assertEventually(holdingOfStock("A", tradeDate), equalTo(0));
    }

    @Test
    public void doesNotShowTradesInOtherRegions() {
        send(aTradeEvent().ofType(BUY).forStock("A").withQuantity(10).inTradingRegion(OTHER_REGION));
        send(aTradeEvent().ofType(BUY).forStock("A").withQuantity(66).inTradingRegion(SAME_REGION));
        assertEventually(holdingOfStock("A", tradeDate, equalTo(66)));
    }

}
