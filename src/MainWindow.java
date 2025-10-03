import javax.swing.JFrame; // <--- CORREÇÃO PRINCIPAL: Importa JFrame
import javax.swing.JLabel; // JLabel usado em createLabel
import javax.swing.JPanel; // JPanel usado em makeControls
import javax.swing.JTable; // JTable usado em makeSnipersTable
import javax.swing.JScrollPane; // JScrollPane usado em fillContentPane
import javax.swing.JTextField; // JTextField usado em makeControls
import javax.swing.JButton; // JButton usado em makeControls
import javax.swing.border.LineBorder; // LineBorder usado em createLabel
import java.awt.Color; // Color usado em createLabel
import java.awt.Container; // Container usado em fillContentPane
import java.awt.BorderLayout; // BorderLayout usado em fillContentPane
import java.awt.FlowLayout; // FlowLayout usado em makeControls
import java.awt.event.ActionListener; // ActionListener usado em makeControls
import java.awt.event.ActionEvent; // ActionEvent usado em makeControls
// Importações de modelos e classes de domínio (SnipersTableModel, UserRequestListener, etc.)
//
public class MainWindow extends JFrame {
    public static final String SNIPER_STATUS_NAME = "sniper status";
    private final SnipersTableModel snipers = new SnipersTableModel();
    private final JLabel sniperStatus = createLabel(STATUS_JOINING);
    private final Announcer<UserRequestListener> userRequests = Announcer.to(UserRequestListener.class);

    public MainWindow(TableModel snipers) {
        super(APPLICATION_TITLE);
        setName(MainWindow.MAIN_WINDOW_NAME);
        fillContentPane(makeSnipersTable(snipers), makeControls());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void addUserRequestListener(UserRequestListener userRequestListener) {
        userRequests.addListener(userRequestListener);
    }

    private static JLabel createLabel(String initialText) {
        JLabel result = new JLabel(initialText);
        result.setName(SNIPER_STATUS_NAME);
        result.setBorder(new LineBorder(Color.BLACK));
        return result;
    }

    private JPanel makeControls(final SnipersTableModel snipers) {
        JPanel controls = new JPanel(new FlowLayout());
        final JTextField itemIdField = new JTextField();
        itemIdField.setColumns(25);
        itemIdField.setName(NEW_ITEM_ID_NAME);
        controls.add(itemIdField);
        JButton joinAuctionButton = new JButton("Join Auction");
        joinAuctionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userRequests.announce().joinAuction(new Item(itemId(), stopPrice()));
            }
            private String itemId() {
                return itemIdField.getText();
            }
            private int stopPrice() {
                return ((Number)stopPriceField.getValue()).intValue();
            }
        });
        controls.add(joinAuctionButton);
        return controls;
    }

    private JTable makeSnipersTable(SniperPortfolio portfolio) {
        SnipersTableModel model = new SnipersTableModel();
        portfolio.addPortfolioListener(model);
        JTable snipersTable = new JTable(model);
        snipersTable.setName(SNIPERS_TABLE_NAME);
        return snipersTable;
    }

    private void fillContentPane(JTable snipersTable) {
        final Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
    }

    private JTable makeSnipersTable() {
        final JTable snipersTable = new JTable(snipers);
        snipersTable.setName(SNIPERS_TABLE_NAME);
        return snipersTable;
    }

    public void sniperStatusChanged(SniperState sniperState, String statusText) {
        snipers.sniperStatusChanged(sniperState, statusText);
    }

    public void sniperStateChanged(SniperSnapshot snapshot) {
        snipers.sniperStateChanged(snapshot);
    }

    public void showStatusText(String statusText) {
        snipers.setStatusText(statusText);
    }

    public void showStatus(String status) {
        sniperStatus.setText(status);
    }

    public boolean acceptRequest(Request request) {
        final Date now = clock.now();
        if (dateOfFirstRequest == null) {
            dateOfFirstRequest = clock.now();
        } else if (clock.dayHasChangedFrom(dateOfFirstRequest)) {
            return false;
        }
        // process the request
        return true;
    }
}
