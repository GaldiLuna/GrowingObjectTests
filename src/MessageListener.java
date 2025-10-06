import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;

public interface MessageListener {
//    private final JFrame ui;
//    public MessageListener(JFrame ui) {
//        this.ui = ui;
//    }
    void processMessage(Chat chat, Message message); // {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                ((MainWindow)ui).showStatus(MainWindow.STATUS_LOST);
//            }
//        });
//    }
}
