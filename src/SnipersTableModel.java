import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel implements SniperListener, SniperCollector, PortfolioListener {
    private final static String[] STATUS_TEXT = { "Joining", "Bidding", "Winning", "Losing", "Lost", "Won" };
    private final static SniperSnapshot STARTING_UP = SniperSnapshot.joining("");
    private final ArrayList<AuctionSniper> notToBeGCd;
    private List<SniperSnapshot> snapshots; // Necessário para compilar métodos como addSniperSnapshot
    private SniperSnapshot snapshot; // Necessário para compilar getValueAt

    public int getColumnCount() {
        return Column.values().length;
    }

    public int getRowCount() {
        return 1;
    }

    @Override
    public String getColumnName(int column) {
        return Column.at(column).name;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return Column.at(columnIndex).valueIn(snapshot);
    }

    public SnipersTableModel() {
        this.snapshots = new ArrayList<>();
        this.notToBeGCd = new ArrayList<>();
        this.snapshot = STARTING_UP; // Deve ser inicializado
    }

    public void addSniper(AuctionSniper sniper) {
        notToBeGCd.add(sniper);
        addSniperSnapshot(sniper.getSnapshot());
        sniper.addSniperListener(new SwingThreadSniperListener(this));
    }
    private void addSniperSnapshot(SniperSnapshot sniperSnapshot) {
        snapshots.add(sniperSnapshot);
        int row = snapshots.size() - 1;
        fireTableRowsInserted(row, row);
    }

    public void sniperStateChanged(SniperSnapshot newSnapshot) {
        this.snapshot = newSnapshot;
        fireTableRowsUpdated(0, 0);
    }

    public static String textFor(SniperState state) {
        return STATUS_TEXT[state.ordinal()];
    }

    @Override
    public void sniperAdded(AuctionSniper sniper) {
        addSniper(sniper);
        // ...
    }
}

