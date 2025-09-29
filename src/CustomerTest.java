import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class CustomerTest {
    final LineItem item1 = context.mock(LineItem.class, "item1");
    final LineItem item2 = context.mock(LineItem.class, "item2");
    final Billing billing = context.mock(Billing.class);
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
