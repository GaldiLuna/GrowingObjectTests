public class CustomerBuilder implements Builder<Customer> {
    private String name = "Default Customer";
    private Address address = new Address();
    public CustomerBuilder() {}
    public CustomerBuilder withAddress(Address a) {
        this.address = a;
        return this;
    }
    public CustomerBuilder withNoPostcode() { return this; }
    @Override
    public Customer build() {
        return new Customer(name, address);
    }
    public CustomerBuilder usingAuctionSites(AuctionSiteCredentialsBuilder builder) { return this; }
    public CustomerBuilder withPaymentMethods(Builder<PaymentMethod>... builders) { return this; }
}
