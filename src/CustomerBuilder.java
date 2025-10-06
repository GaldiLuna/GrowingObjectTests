public class CustomerBuilder {
    public CustomerBuilder withAddress(Address a) { return this; }
    public CustomerBuilder withNoPostcode() { return this; }
    public Customer build() { return new Customer(); }
}
