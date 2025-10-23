import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.File;

public class XMPPAuctionHouse implements AuctionHouse {
    private final XMPPConnection connection;
    private final LoggingXMPPFailureReporter failureReporter;
    public static final String LOGGER_NAME = "auction-sniper";
    public static final String LOG_FILE_NAME = "auction-sniper.log";
    public static XMPPAuctionHouse connect(String h, String u, String p) { return null; }
    private String getFullPath(String fileName) {
        return new File(fileName).getAbsolutePath();
    }
    public XMPPAuctionHouse(XMPPConnection connection) throws XMPPAuctionException {
        this.connection = connection;
        this.failureReporter = new LoggingXMPPFailureReporter(makeLogger());
    }

    @Override
    public List<AuctionDescription> findAuctions(Set<String> keywords) {
        return List.of();
    }

    public Auction auctionFor(String itemId) {
        return new XMPPAuction(connection, auctionId(itemId, connection), failureReporter);
    }
    private Logger makeLogger() throws XMPPAuctionException {
        Logger logger = Logger.getLogger(LOGGER_NAME);
        logger.setUseParentHandlers(false);
        logger.addHandler(simpleFileHandler());
        return logger;
    }
    private FileHandler simpleFileHandler() throws XMPPAuctionException {
        try {
            FileHandler handler = new FileHandler(LOG_FILE_NAME);
            handler.setFormatter(new SimpleFormatter());
            return handler;
        } catch (Exception e) {
            throw new XMPPAuctionException("Could not create logger FileHandler " + getFullPath(LOG_FILE_NAME), e);
        }
    }
    private String auctionId(String itemId, XMPPConnection connection) {
        return itemId + connection;
    }
    public void disconnect() throws SmackException.NotConnectedException {
        connection.disconnect();
    }
}
