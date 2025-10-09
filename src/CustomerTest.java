import org.junit.Test;
import org.junit.runner.RunWith;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;

import static org.jmock.Expectations.*;

@RunWith(JMock.class)
public class CustomerTest {
    private final Mockery context = new JUnit4Mockery();
    final LineItem item1 = context.mock(LineItem.class, "item1");
    final LineItem item2 = context.mock(LineItem.class, "item2");
    final Billing billing = context.mock(Billing.class);
    private final Customer customer = new Customer();
    @Test
    public void requestsInvoiceForPurchasedItems() {
        context.checking(new Expectations() {{
            oneOf(billing).add(item1);
            oneOf(billing).add(item2);
        }});
        customer.purchase(item1, item2);
        customer.requestInvoice(billing);
    }
}
