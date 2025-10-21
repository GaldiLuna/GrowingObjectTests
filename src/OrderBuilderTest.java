import org.junit.Test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class OrderBuilderTest implements RequestSender, ProgressMonitor, Gui{
    private Mockery context = new Mockery();

    private final RequestSender requestSender = context.mock(RequestSender.class, "requestSender");
    private final ProgressMonitor progressMonitor = context.mock(ProgressMonitor.class, "progressMonitor");
    private final Gui gui = context.mock(Gui.class, "gui");
    private final Gui user = context.mock(Gui.class, "user");

    private OrderBuilder anOrder() { return new OrderBuilder(); }
    private Object nextCustomerReference() { return new Object(); }
    private CustomerBuilder aCustomer() { return new CustomerBuilder(); }
    private OrderBuilder anOrderAmendment() { return new OrderBuilder(); }

    void havingReceived(OrderBuilder orderDetails) {
        sendAndProcess(orderDetails);
    }

    @Override
    public void send(Order order) { /* stub */ }
    @Override
    public void waitForCompletion(Order order) { /* stub */ }
    @Override
    public TotalSalesReport openSalesReport() {
        return (TotalSalesReport) gui.openSalesReport();
    }

    @Test
    public void chargesCustomerForTotalCostOfAllOrderedItems() {
        Order order = new Order(new Customer("Sherlock Holmes", new Address("221b Baker Street", "London", new PostCode("NW1", "3RX"))));
        order.addLine(new OrderLine("Deerstalker Hat", 1));
        order.addLine(new OrderLine("Tweed Cape", 1));
    }
    @Test
    public void reportsTotalSalesOfOrderedProducts() {
        havingReceived(anOrder().withLine("Deerstalker Hat", 1).withLine("Tweed Cape", 1));
        havingReceived(anOrder().withLine("Deerstalker Hat", 1));
        TotalSalesReport report = openSalesReport();
        report.displaysTotalSalesFor("Deerstalker Hat", equalTo(2));
        report.displaysTotalSalesFor("Tweed Cape", equalTo(1));
    }
    void sendAndProcess(OrderBuilder orderDetails) {
        Order order = orderDetails.withDefaultCustomersReference(nextCustomerReference()).build();
        send(order);
        waitForCompletion(order);
    }
    void submitOrderFor(String ... products) {
        OrderBuilder orderBuilder = anOrder().withCustomersReference(nextCustomerReference());
        for (String product : products) {
            orderBuilder = orderBuilder.withLine(product, 1);
        }
        Order order = orderBuilder.build();
        send(order);
        waitForCompletion(order);
    }
    @Test
    public void takesAmendmentsIntoAccountWhenCalculatingTotalSales() {
        Customer theCustomer = aCustomer().build();
        havingReceived(anOrder().from(theCustomer).withLine("Deerstalker Hat", 1).withLine("Tweed Cape", 1));
        havingReceived(anOrderAmendment().from(theCustomer).withLine("Deerstalker Hat", 2));
        TotalSalesReport report = openSalesReport();
        report.containsTotalSalesFor("Deerstalker Hat", equalTo(2));
        report.containsTotalSalesFor("Tweed Cape", equalTo(1));
    }

}
