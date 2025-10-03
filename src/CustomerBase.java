import java.util.List;

public interface CustomerBase {
    // Retorna null se nenhum cliente for encontrado
    Maybe<Customer> findCustomerWithEmailAddress(String emailAddress);
    void addCustomer(Customer customer);
    List<Customer> customersWithExpiredCreditCardsAt(Date deadline);
}
