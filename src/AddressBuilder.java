public class AddressBuilder implements Builder<Address> {
    public AddressBuilder withNoPostcode() { return this; }
    public AddressBuilder withStreet(String s) { return this; }
    public AddressBuilder withStreet2(String s) { return this; }
    public AddressBuilder withPostCode(String s) { return this; }
    public Address build() { return new Address(); }
}
