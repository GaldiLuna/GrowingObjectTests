import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.table.TableModel;

import java.awt.Color;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//
import java.util.Date;
import java.lang.Number;
//

public class MainWindow extends JFrame {
    public static final String SNIPER_STATUS_NAME = "sniper status";
    public static final String APPLICATION_TITLE = "Auction Sniper";
    public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
    public static final String STATUS_JOINING = "Joining";
    public static final String STATUS_BIDDING = "Bidding";
    public static final String STATUS_LOSING = "Losing";
    public static final String STATUS_WON = "Won";
    public static final String STATUS_LOST = "Lost";
    public static final String NEW_ITEM_ID_NAME = "item id";
    public static final String SNIPERS_TABLE_NAME = "snipers table";
    private final SnipersTableModel snipers = new SnipersTableModel();
    private final JLabel sniperStatus = createLabel(STATUS_JOINING);
    private final Announcer<UserRequestListener> userRequests = Announcer.to(UserRequestListener.class);
    private JTextField stopPriceField;
    private final Clock clock;
    private Date dateOfFirstRequest;

    public MainWindow(SniperPortfolio portfolio) {
        super(APPLICATION_TITLE);
        setName(MainWindow.MAIN_WINDOW_NAME);
        fillContentPane(makeSnipersTable(portfolio), makeControls(null));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.clock = new Clock() {
            @Override public Date now() { return new Date(); }
            @Override public boolean dayHasChangedFrom(Date date) { return false; }
        };
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
        stopPriceField = new JTextField();
        joinAuctionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userRequests.announce().joinAuction(new Item(itemId(), stopPrice()));
            }
            private String itemId() {
                return itemIdField.getText();
            }
            private int stopPrice() {
                return Integer.parseInt(stopPriceField.getText());
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

    private void fillContentPane(JTable snipersTable, JPanel controls) {
        final Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(controls, BorderLayout.NORTH);
        contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
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
