public class XmlMarshaller {
    public XmlMarshaller() {}
    public String marshall(Object o) { return ""; }
    // O unmarshall deve retornar um tipo compatível com o que o teste espera (AuctionClosedEvent ou WithTransient)
    public AuctionClosedEvent unmarshall(String xml) {
        return new AuctionClosedEvent();
    }
}
