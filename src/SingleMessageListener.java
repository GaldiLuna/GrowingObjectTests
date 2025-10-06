import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.MessageListener;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.EventListener;
import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertEquals;

public class SingleMessageListener implements MessageListener {
    // ArrayBlockingQueue armazena mensagens XMPP
    private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(1);

    @Override
    public void processMessage(Chat chat, Message message) {
        messages.add(message);
    }

    @SuppressWarnings("unchecked")
    public void receivesAMessage(Matcher<? super String> messageMatcher) throws InterruptedException {
        final Message message = messages.poll(5, TimeUnit.SECONDS);
        // hasProperty é um matcher Hamcrest que verifica a propriedade "body" da mensagem
        assertThat(message, hasProperty("body", messageMatcher));
    }
}
