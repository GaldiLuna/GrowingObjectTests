import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert.*;

//import static XmlMarshallerTestHelpers.hasSameSerialisableFieldsAs;

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
        XmlMarshaller marshaller = new XmlMarshaller();
        WithTransient original = new WithTransient();
        String xml = marshaller.marshall(original);
        AuctionClosedEvent unmarshalled = marshaller.unmarshall(xml);
        assertThat(unmarshalled, XmlMarshallerTestHelpers.hasSameSerialisableFieldsAs(original));
    }
}
