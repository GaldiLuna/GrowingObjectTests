import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class AuctionMessageTranslatorTest {
    private final Mockery context = new JUnit4Mockery(); // 2
    private final AuctionEventListener listener =
            context.mock(AuctionEventListener.class); // 3
    private final AuctionMessageTranslator translator =
            new AuctionMessageTranslator(listener); // 4
    @Test public void
    notifiesAuctionClosedWhenCloseMessageReceived() {
        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: CLOSE;"); // 5
        context.checking(new Expectations() {{ // 6
            oneOf(listener).auctionClosed(); // 7
        }});
        translator.processMessage(UNUSED_CHAT, message); // 8
    } // 9
}
