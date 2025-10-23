import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;
import java.util.Date;

public class PersistentCustomerBase implements CustomerBase {
    private final EntityManager entityManager;
    public PersistentCustomerBase(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Maybe<Customer> findCustomerWithEmailAddress(String emailAddress) {
        return Maybe.nothing();
    }
    @Override
    public void addCustomer(Customer customer) {
        entityManager.persist(customer);
    }

    @Override
    public List<Customer> customersWithExpiredCreditCardsAt(Date deadline) {
        Query query = entityManager.createQuery(
                "select c from Customer c, CreditCardDetails d " +
                "where d member of c.paymentMethods " +
                " and d.expiryDate < :deadline");
        query.setParameter("deadline", deadline);
        return query.getResultList();
    }
}
