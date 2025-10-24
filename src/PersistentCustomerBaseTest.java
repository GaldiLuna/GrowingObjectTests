import org.junit.Test;

//import static CustomerTestHelpers.*;
//import static CustomerTestHelpers.date;

import org.hamcrest.Matcher;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
//import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

public class PersistentCustomerBaseTest {
    final EntityManagerFactory factory = Persistence.createEntityManagerFactory("example");
    final EntityManager entityManager = factory.createEntityManager();
    private final JPATransactor transactor = new JPATransactor(entityManager);
    final PersistentCustomerBase customerBase = new PersistentCustomerBase(entityManager);
    private void assertCustomersExpiringOn(final String date, final Matcher<Iterable<? extends Customer>> matcher) throws Exception {
        transactor.perform(new UnitOfWork() {
            public void work() throws Exception {
                Date deadline = null; //linha sugerida pelo intelliji para resolver o deadline abaixo
                assertThat(customerBase.customersWithExpiredCreditCardsAt(deadline), (Matcher)matcher);
                // É necessário um cast no matcher se o assertThat não conseguir inferir
            }
        });
    }

    @Test
    @SuppressWarnings("unchecked")
    public void findsCustomersWithCreditCardsThatAreAboutToExpire() throws Exception {
        final String deadline = "6 Jun 2009";
        addCustomers(
                CustomerTestHelpers.aCustomer().withName("Alice (Expired)")
                        .withPaymentMethods(CustomerTestHelpers.aCreditCard().withExpiryDate(date("1 Jan 2009"))),
                CustomerTestHelpers.aCustomer().withName("Bob (Expired)")
                        .withPaymentMethods(CustomerTestHelpers.aCreditCard().withExpiryDate(date("5 Jun 2009"))),
                CustomerTestHelpers.aCustomer().withName("Carol (Valid)")
                        .withPaymentMethods(CustomerTestHelpers.aCreditCard().withExpiryDate(date(deadline))),
                CustomerTestHelpers.aCustomer().withName("Dave (Valid)")
                        .withPaymentMethods(CustomerTestHelpers.aCreditCard().withExpiryDate(date("7 Jun 2009")))
        );
        assertCustomersExpiringOn(date(deadline),
                containsInAnyOrder(CustomerTestHelpers.customerNamed("Alice (Expired)"),
                        CustomerTestHelpers.customerNamed("Bob (Expired)")));
    }
    private void addCustomers(final CustomerBuilder... customerBuilders) throws Exception {
        transactor.perform(new UnitOfWork() {
            public void work() throws Exception {
                for (CustomerBuilder customer : customerBuilders) {
                    customerBase.addCustomer(customer.build());
                }
            }
        });
    }
}
