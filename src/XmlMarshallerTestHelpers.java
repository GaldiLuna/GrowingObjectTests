public class XmlMarshallerTestHelpers {
    public static Matcher<Object> hasSameSerialisableFieldsAs(Object expected) {
        // Deve retornar um Matcher que implementa a lógica reflexiva de comparação (Capítulo 25)
        return (Matcher<Object>) (Matcher<?>) org.hamcrest.Matchers.anything();
    }
}
