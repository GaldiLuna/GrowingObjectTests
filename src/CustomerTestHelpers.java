import org.hamcrest.Matcher;
import java.util.Date;

public class CustomerTestHelpers {
    public static CustomerBuilder aCustomer() { return new CustomerBuilder(); }
    public static CreditCardDetailsBuilder aCreditCard() { return new CreditCardDetailsBuilder(); }
    public static Customer aNewCustomer() { return new Customer(null, null); }
    public static Date date(String dateString) { return new Date(); }
    public static Matcher<Customer> customerNamed(String name) {
        return (Matcher<Customer>) (Matcher<?>) org.hamcrest.Matchers.anything();
    }
}
