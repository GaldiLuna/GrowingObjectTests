import org.junit.Test;
import org.junit.Before;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import org.hamcrest.Matcher;

public class PersistabilityTest {
    final List<? extends Builder<?>> persistentObjectBuilders = Arrays.asList(
            new AddressBuilder(),
            new PayMateDetailsBuilder(),
            new CreditCardDetailsBuilder(),
            new AuctionSiteBuilder(),
            new AuctionSiteCredentialsBuilder().forSite(persisted(new AuctionSiteBuilder())),
            new CustomerBuilder().usingAuctionSites(
            new AuctionSiteCredentialsBuilder().forSite(persisted(
            new AuctionSiteBuilder()))).withPaymentMethods(
            new CreditCardDetailsBuilder(),
            new PayMateDetailsBuilder()));
    final EntityManagerFactory factory = Persistence.createEntityManagerFactory("example");
    final EntityManager entityManager = factory.createEntityManager();
    private final JPATransactor transactor = new JPATransactor(entityManager);

    @Before
    public void setup() throws Exception {
        new DataBaseCleaner(entityManager).clean();
    }
    private <T> Builder<T> persisted(final Builder<T> builder) {
        return new Builder<T>() {
            public T build() {
                T entity = builder.build();
                entityManager.persist(entity);
                return entity;
            }
        };
    }

    @Test
    public void roundTripsPersistentObjects() throws Exception {
        for (Builder<?> builder : persistentObjectBuilders) {
            assertCanBePersisted(builder);
        }
    }
    private void assertCanBePersisted(Builder<?> builder) throws Exception {
        try {
            assertReloadsWithSameStateAs(persistedObjectFrom(builder));
        } catch (PersistenceException e) {
            throw new PersistenceException("could not round-trip " + typeNameFor(builder), e);
        }
    }
    private Object persistedObjectFrom(final Builder<?> builder) throws Exception {
        return transactor.performQuery(new QueryUnitOfWork() {
            public Object query() throws Exception {
                Object original = builder.build();
                entityManager.persist(original);
                return original;
            }
        });
    }
    private void assertReloadsWithSameStateAs(final Object original) throws Exception {
        transactor.perform(new UnitOfWork() {
            public void work() throws Exception {
                Object reloaded = entityManager.find(original.getClass(), PersistenceHelpers.idOf(original));
                assertThat(reloaded, PersistenceHelpers.hasSamePersistenFieldsAs(original));
            }
        });
    }
    private String typeNameFor(Builder<?> builder) {
        return builder.getClass().getSimpleName().replace("Builder", "");
    }
}
