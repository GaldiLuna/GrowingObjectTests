public class CustomerBuilder implements Builder<Customer> {
    public CustomerBuilder withAddress(Address a) { return this; }
    public CustomerBuilder withNoPostcode() { return this; }
    public Customer build() { return new Customer(); }
    public CustomerBuilder usingAuctionSites(AuctionSiteCredentialsBuilder builder) { return this; }
    public CustomerBuilder withPaymentMethods(Builder<PaymentMethod>... builders) { return this; }
}
