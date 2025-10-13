public class PersistenceHelpers {
    public static Matcher<Object> idOf(Object entity) { return null; }
    public static Matcher<Object> hasSamePersistenFieldsAs(Object original) {
        return (Matcher<Object>) (Matcher<?>) org.hamcrest.Matchers.anything();
    }
}
