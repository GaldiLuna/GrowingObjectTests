import org.junit.Test;
import org.junit.runner.RunWith;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

@RunWith(JMock.class)
public class AuctionMessageTranslatorTest {
    private static final String SNIPER_ID = "sniper-id";
    public static final Chat UNUSED_CHAT = null;
    private final Mockery context = new JUnit4Mockery();
    private final AuctionEventListener listener = context.mock(AuctionEventListener.class);
    private final XMPPFailureReporter failureReporter = context.mock(XMPPFailureReporter.class);
    private final AuctionMessageTranslator translator = new AuctionMessageTranslator(SNIPER_ID, listener, failureReporter);
    private Message message(String body) {
        Message message = new Message();
        message.setBody(body);
        return message;
    }
    private void expectFailureWithMessage(final String badMessage) {
        context.checking(new Expectations() {{
            oneOf(listener).auctionFailed();
            oneOf(failureReporter).cannotTranslateMessage(with(SNIPER_ID), with(badMessage), with(any(Exception.class)));
        }});
    }
    @Test
    public void notifiesAuctionClosedWhenCloseMessageReceived() {
        context.checking(new Expectations() {{ oneOf(listener).auctionClosed(); }});
        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: CLOSE;");
        translator.processMessage(UNUSED_CHAT, message);
    }
    @Test
    public void notifiesAuctionFailedWhenBadMessageReceived() {
        String badMessage = "a bad message";
        expectFailureWithMessage(badMessage);
        translator.processMessage(UNUSED_CHAT, message(badMessage));
    }
    @Test
    public void notifiesBidDetailsWhenCurrentPriceMessageReceived() {
        context.checking(new Expectations() {{ exactly(1).of(listener).currentPrice(192, 7); }});
        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");
        translator.processMessage(UNUSED_CHAT, message);
    }
    @Test
    public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromOtherBidder() {
        context.checking(new Expectations() {{
            exactly(1).of(listener).currentPrice(192, 7);
        }});
        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");
        translator.processMessage(UNUSED_CHAT, message);
    }
    @Test
    public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromSniper() {
        context.checking(new Expectations() {{
            exactly(1).of(listener).currentPrice(234, 5);
        }});
        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 234; Increment: 5; Bidder: " + SNIPER_ID + ";");
        translator.processMessage(UNUSED_CHAT, message);
    }
    @Test
    public void notifiesAuctionFailedWhenEventTypeMissing() {
        context.checking(new Expectations() {{
            exactly(1).of(listener).auctionFailed();
        }});
        Message message = new Message();
        message.setBody("SOLVersion: 1.1; CurrentPrice: 234; Increment: 5; Bidder: " + SNIPER_ID + ";");
        translator.processMessage(UNUSED_CHAT, message);
    }

}
