public class SniperStateDisplayer implements SniperListener {
    public void sniperBidding(final SniperState state) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ui.sniperStatusChanged(state, MainWindow.STATUS_BIDDING);
            }
        });
    }

    public void sniperStateChanged(SniperSnapshot newSnapshot) {
        int row = rowMatching(newSnapshot);
        snapshots.set(row, newSnapshot);
        fireTableRowsUpdated(row, row);
    }

    private int rowMatching(SniperSnapshot snapshot) {
        for (int i = 0; i < snapshots.size(); i++) {
            if (newSnapshot.isForSameItemAs(snapshots.get(i))) {
                return i;
            }
        }
        throw new Defect("Cannot find match for " + snapshot);
    }

    public void sniperLost() {
        showStatus(MainWindow.STATUS_LOST);
    }

    public void sniperWon() {
        showStatus(MainWindow.STATUS_WON);
    }

    public void sniperWinning() {
        showStatus(MainWindow.STATUS_WINNING);
    }

    private void showStatus(final String status) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ui.showStatus(status);
            }
        });
    }
}
