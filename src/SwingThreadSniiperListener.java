public class SwingThreadSniiperListener implements  SniperListener {
    public SwingThreadSniiperListener(SniperListener delegate) {
        // Construtor para injeção
    }
    public void sniperStateChanged(SniperSnapshot snapshot) { /* ... */ }
}
