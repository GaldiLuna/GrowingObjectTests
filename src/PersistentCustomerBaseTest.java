import org.junit.Test;

public class PersistentCustomerBaseTest {
    final PersistentCustomerBase customerBase =
            new PersistentCustomerBase(entityManager);
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
                        .withPaymentMethods(aCreditCard().withExpiryDate(date("7 Jun 2009")))
        );
        assertCustomersExpiringOn(date(deadline),
                containsInAnyOrder(customerNamed("Alice (Expired)"),
                        customerNamed("Bob (Expired)")));
    }
    private void addCustomers(final CustomerBuilder... customers) throws Exception {
        transactor.perform(new UnitOfWork() {
            public void work() throws Exception {
                for (CustomerBuilder customer : customers) {
                    customerBase.addCustomer(customer.build());
                }
            }
        });
    }
    private void assertCustomersExpiringOn(final Date date,
                                           final Matcher<Iterable<Customer>> matcher)
            throws Exception
    {
        transactor.perform(new UnitOfWork() {
            public void work() throws Exception {
                customers.addCustomer(aNewCustomer());
                assertThat(customerBase.customersWithExpiredCreditCardsAsOf(date), matcher);
            }
        });
    }
}
