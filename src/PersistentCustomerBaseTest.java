import org.junit.Test;

import org.junit.Before;
import org.junit.Assert;
import java.util.Date;
import java.util.List;
import java.util.Arrays;
import org.hamcrest.Matcher;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
//import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistentCustomerBaseTest {
    final EntityManagerFactory factory = Persistence.createEntityManagerFactory("example");
    final EntityManager entityManager = factory.createEntityManager();
    private final JPATransactor transactor = new JPATransactor(entityManager);
    final PersistentCustomerBase customerBase = new PersistentCustomerBase(entityManager);
    @Test
    @SuppressWarnings("unchecked")
    public void findsCustomersWithCreditCardsThatAreAboutToExpire() throws Exception {
        final String deadline = "6 Jun 2009";
        addCustomers(
                aCustomer().withName("Alice (Expired)")
                        .withPaymentMethods(aCreditCard().withExpiryDate(date("1 Jan 2009"))),
                aCustomer().withName("Bob (Expired)")
                        .withPaymentMethods(aCreditCard().withExpiryDate(date("5 Jun 2009"))),
                aCustomer().withName("Carol (Valid)")
                        .withPaymentMethods(aCreditCard().withExpiryDate(date(deadline))),
                aCustomer().withName("Dave (Valid)")
                        .withPaymentMethods(CustomerTestHelpers.aCreditCard().withExpiryDate(date("7 Jun 2009")))
        );
        assertCustomersExpiringOn(date(deadline),
                containsInAnyOrder(customerNamed("Alice (Expired)"),
                        customerNamed("Bob (Expired)")));
    }
    private void addCustomers(final CustomerBuilder... customers) throws Exception {
        transactor.perform(new UnitOfWork() {
            public void work() throws Exception {
                for (CustomerBuilder customer : customerBuilders) {
                    customerBase.addCustomer(customer.build());
                }
            }
        });
    }
    private void assertCustomersExpiringOn(final Date date, final Matcher<Iterable<Customer>> matcher) throws Exception {
        transactor.perform(new UnitOfWork() {
            public void work() throws Exception {
                assertThat(customerBase.customersWithExpiredCreditCardsAt(date), matcher);
            }
        });
    }
}
