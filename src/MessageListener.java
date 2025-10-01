public class MessageListener {
    public void processMessage(Chat aChat, Message message) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ui.showStatus(MainWindow.STATUS_LOST);
            }
        });
    }
}
