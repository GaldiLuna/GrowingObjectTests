public class PersistentCustomerBase implements CustomerBase {
    private final EntityManager entityManager;
    public PersistentCustomerBase(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public void addCustomer(Customer customer) {
        entityManager.persist(customer);
    }
    public List<Customer> customersWithExpiredCreditCardsAt(Date deadline) {
        Query query = entityManager.createQuery(
                "select c from Customer c, CreditCardDetails d " +
                "where d member of c.paymentMethods " +
                " and d.expiryDate < :deadline");
        query.setParameter("deadline", deadline);
        return query.getResultList();
    }
}
