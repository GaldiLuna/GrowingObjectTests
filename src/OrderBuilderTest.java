import org.junit.Test;

public class OrderBuilderTest {
    @Test
    public void chargesCustomerForTotalCostOfAllOrderedItems() {
        Order order = new Order(new Customer("Sherlock Holmes",
                new Address("221b Baker Street", "London", new PostCode("NW1", "3RX"))));
        order.addLine(new OrderLine("Deerstalker Hat", 1));
        order.addLine(new OrderLine("Tweed Cape", 1));
    }
    @Test
    public void reportsTotalSalesOfOrderedProducts() {
        havingReceived(anOrder()
                .withLine("Deerstalker Hat", 1)
                .withLine("Tweed Cape", 1));
        havingReceived(anOrder()
                .withLine("Deerstalker Hat", 1));
        TotalSalesReport report = gui.openSalesReport();
        report.displaysTotalSalesFor("Deerstalker Hat", equalTo(2));
        report.displaysTotalSalesFor("Tweed Cape", equalTo(1));
    }
    void sendAndProcess(OrderBuilder orderDetails) {
        Order order = orderDetails
                .withDefaultCustomersReference(nextCustomerReference())
                .build();
        requestSender.send(order);
        progressMonitor.waitForCompletion(order);
    }
    void submitOrderFor(String ... products) {
        OrderBuilder orderBuilder = anOrder().withCustomersReference(nextCustomerReference());
        for (String product : products) {
            orderBuilder = orderBuilder.withLine(product, 1);
        }
        Order order = orderBuilder.build();
        requestSender.send(order);
        progressMonitor.waitForCompletion(order);
    }
    @Test
    public void takesAmendmentsIntoAccountWhenCalculatingTotalSales() {
        Customer theCustomer = aCustomer().build();
        havingReceived(anOrder().from(theCustomer)
                .withLine("Deerstalker Hat", 1)
                .withLine("Tweed Cape", 1));
        havingReceived(anOrderAmendment().from(theCustomer)
                .withLine("Deerstalker Hat", 2));
        TotalSalesReport report = user.openSalesReport();
        report.containsTotalSalesFor("Deerstalker Hat", equalTo(2));
        report.containsTotalSalesFor("Tweed Cape", equalTo(1));
    }
}
