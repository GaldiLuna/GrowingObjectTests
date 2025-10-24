import java.util.List;

import java.util.Date;

public interface CustomerBase {
    // Retorna null se nenhum cliente for encontrado
    List<Customer> customersWithExpiredCreditCardsAt(Date deadline);
    Maybe<Customer> findCustomerWithEmailAddress(String emailAddress);
    void addCustomer(Customer customer);

}
