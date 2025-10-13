import javax.swing.SwingUtilities;

//import java.util.concurrent.Runnable;

public class SniperStateDisplayer implements SniperListener {
    private final SniperListener delegate;

    public SniperStateDisplayer(SniperListener delegate) {
        this.delegate = delegate;
    }

    @Override
    public void sniperStateChanged(final SniperSnapshot newSnapshot) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                delegate.sniperStateChanged(newSnapshot);
            }
        });
    }

}
