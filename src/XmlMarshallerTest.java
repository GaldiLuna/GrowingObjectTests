import org.junit.Test;

public class XmlMarshallerTest {
    public static class MarshalledObject {
        private String privateField = "private";
        public final String publicFinalField = "public final";
        public int primitiveField;
        // constructors, accessors for private field, etc.
    }
    public static class WithTransient extends MarshalledObject {
        public transient String transientField = "transient";
    }
    @Test
    public void marshallsAndUnmarshallsSerialisableFields() {
        XMLMarshaller marshaller = new XmlMarshaller();
        WithTransient original = new WithTransient();
        String xml = marshaller.marshall(original);
        AuctionClosedEvent unmarshalled = marshaller.unmarshall(xml);
        assertThat(unmarshalled, hasSameSerialisableFieldsAs(original));
    }
}
