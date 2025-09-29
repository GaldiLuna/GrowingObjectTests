public class OrderBuilder {
    private Customer customer = new CustomerBuilder().build();
    private List<OrderLine> lines = new ArrayList<OrderLine>();
    private BigDecimal discountRate = BigDecimal.ZERO;
    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }
    public OrderBuilder withCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }
    public OrderBuilder withOrderLines(OrderLines lines) {
        this.lines = lines;
        return this;
    }
    public OrderBuilder withDiscount(BigDecimal discountRate) {
        this.discountRate = discountRate;
        return this;
    }
    public Order build() {
        Order order = new Order(customer);
        for (OrderLine line : lines) order.addLine(line);
        order.setDiscountRate(discountRate);
    }

    // new OrderBuilder().fromCustomer(new CustomerBuilder().withAddress(new AddressBuilder().withNoPostcode().build()).build()).build();
    // new AddressBuilder().withStreet("221b Baker Street").withStreet2("London").withPostCode("NW1 6XE").build();
    // Order orderWithSmallDiscount = new OrderBuilder().withLine("Deerstalker Hat", 1).withLine("Tweed Cape", 1).withDiscount(0.10).build();
    // Order orderWithLargeDiscount = new OrderBuilder().withLine("Deerstalker Hat", 1).withLine("Tweed Cape", 1).withDiscount(0.25).build();
    // OrderBuilder hatAndCape = new OrderBuilder().withLine("Deerstalker Hat", 1).withLine("Tweed Cape", 1);
    // Order orderWithSmallDiscount = hatAndCape.withDiscount(0.10).build();
    // Order orderWithLargeDiscount = hatAndCape.withDiscount(0.25).build();
    // Order orderWithNoPostcode = new OrderBuilder().fromCustomer(new CustomerBuilder().withAddress(new AddressBuilder().withNoPostcode().build()).build()).build();
    // Order order = new OrderBuilder().fromCustomer(new CustomerBuilder().withAddress(anAddress().withNoPostcode()))).build();
    // Address aLongerAddress = anAddress().withStreet("221b Baker Street").withCity("London").with(postCode("NW1", "3RX")).build();

    void submitOrderFor(String ... products) { [...] }
    void submitOrderFor(String product, int count, String otherProduct, int otherCount) { [...] }
    void submitOrderFor(String product, double discount) { [...] }
    void submitOrderFor(String product, String giftVoucherCode) { [...] }
}
