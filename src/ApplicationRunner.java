import java.io.IOException;
import static org.hamcrest.Matchers.containsString;
import static java.lang.String.format;

//import static SnipersTableModel.textFor;
//import static MainWindow.STATUS_BIDDING;
//import static MainWindow.STATUS_WON;
//import static MainWindow.STATUS_LOST;
//import static MainWindow.NEW_ITEM_ID_NAME;

public class ApplicationRunner {
    private String itemId;
    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    private AuctionSniperDriver driver;
    private AuctionLogDriver logDriver = new AuctionLogDriver();
    public static final String XMPP_HOSTNAME = "localhost";
    private Object lastPrice, lastBid, auction;
    public void startBiddingIn(final FakeAuctionServer... auctions) {
        startSniper();
        for (FakeAuctionServer auction : auctions) {
            final String itemId = auction.getItemId();
            driver.startBiddingFor(itemId, Integer.MAX_VALUE);
            //driver.showsSniperStatus(itemId, 0, 0, textFor(SniperState.JOINING));
        }
    }
    private void startSniper() {
        logDriver.clearLog();
        Thread thread = new Thread("Test Application") {
            @Override
            public void run() {
                try {
                    // Chamada ao metodo main (depende de argumentos() e Main.main)
                    Main.main(arguments(new FakeAuctionServer[0]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(1000);
        driver.showsSniperStatus(textFor(SniperState.JOINING));
    }
    private void openBiddingFor(FakeAuctionServer auction, int stopPrice) {
        // Lógica de abertura para o startBiddingWithStopPrice
        driver.startBiddingFor(auction.getItemId(), stopPrice);
    }
    protected static String[] arguments(FakeAuctionServer... auctions) {
        String[] arguments = new String[auctions.length + 3];
        arguments[0] = FakeAuctionServer.XMPP_HOSTNAME;
        arguments[1] = SNIPER_ID;
        arguments[2] = SNIPER_PASSWORD;
        for (int i = 0; i < auctions.length; i++) {
            arguments[i + 3] = auctions[i].getItemId();
        }
        return arguments;
    }
    public void hasShownSniperIsBidding(FakeAuctionServer auction, int lastPrice, int lastBid) {
        driver.showsSniperStatus(itemId, lastPrice, lastBid, MainWindow.STATUS_BIDDING);
    }
    public void hasShownSniperIsWinning(int winningBid) {
        driver.showsSniperStatus(itemId, winningBid, winningBid, textFor(SniperState.WINNING));
    }
    public void showsSniperHasWonAuction(int lastPrice) {
        driver.showsSniperStatus(itemId, lastPrice, lastPrice, MainWindow.STATUS_WON);
    }
    public void showsSniperHasLostAuction() {
        driver.showsSniperStatus(MainWindow.STATUS_LOST);
    }
    public void stop() {
        if (driver != null) {
            driver.dispose();
        }
    }
    public void reportsInvalidMessage(FakeAuctionServer auction, String message) throws IOException {
        logDriver.hasEntry(containsString(message));
    }
    public void startBiddingWithStopPrice(FakeAuctionServer auction, int stopPrice) {
        startSniper();
        openBiddingFor(auction, stopPrice);
    }

}
