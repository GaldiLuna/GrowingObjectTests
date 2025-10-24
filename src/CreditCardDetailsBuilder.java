public class CreditCardDetailsBuilder implements Builder<PaymentMethod> {
    public PaymentMethod build() {
        return new PaymentMethod();
    }
    public CreditCardDetailsBuilder withExpiryDate(String date) { return this; }
    public CreditCardDetailsBuilder withName(String name) { return this; }
}
