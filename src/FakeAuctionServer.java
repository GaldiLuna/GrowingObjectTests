import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.ChatManager;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import java.util.concurrent.TimeUnit; // Assumindo que você precisa disso para SingleMessageListener

import org.hamcrest.Matcher;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;

public class FakeAuctionServer {
    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_RESOURCE = "Auction";
    public static final String XMPP_HOSTNAME = "localhost";
    private static final String AUCTION_PASSWORD = "auction";
    private final String itemId;
    private final XMPPConnection connection;
    private Chat currentChat;
    public FakeAuctionServer(String itemId) {
        this.itemId = itemId;
        this.connection = new XMPPTCPConnection(XMPP_HOSTNAME);
    }

    private final SingleMessageListener messageListener = new SingleMessageListener();

    public void reportPrice(int price, int increment, String bidder) throws XMPPException {
        currentChat.sendMessage(String.format("SOLVersion: 1.1; Event: PRICE; " + "CurrentPrice: %d; Increment: %d; Bidder: %s;", price, increment, bidder));
    }

    public void startSellingItem() throws XMPPException {
        connection.connect();
        connection.login(format(ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD, AUCTION_RESOURCE);
        ChatManager.getInstanceFor(connection).addChatListener(new ChatManagerListener() {
                    public void chatCreated(Chat chat, boolean createdLocally) {
                        currentChat = chat;
                        chat.addMessageListener(messageListener);
                    }
                });
    }
    public String getItemId() {
        return itemId;
    }

    public void hasReceivedJoinRequestFromSniper() throws InterruptedException {
        messageListener.receivesAMessage(is(anything()));
    }
    public void hasReceivedJoinRequestFrom(String sniperId) throws InterruptedException {
        receivesAMessageMatching(sniperId, equalTo(Main.JOIN_COMMAND_FORMAT));
    }
    public void hasReceivedBid(int bid, String sniperId) throws InterruptedException {
        receivesAMessageMatching(sniperId, equalTo(format(Main.BID_COMMAND_FORMAT, bid)));
    }
    private void receivesAMessageMatching(String sniperId, Matcher<? super String> messageMatcher) throws InterruptedException {
        messageListener.receivesAMessage(messageMatcher);
        assertThat(currentChat.getParticipant(), equalTo(sniperId));
    }
    public void announceClosed() throws XMPPException {
        currentChat.sendMessage("SOLVersion: 1.1; Event: CLOSE;");
    }
    public void stop() {
        connection.disconnect();
    }
}
