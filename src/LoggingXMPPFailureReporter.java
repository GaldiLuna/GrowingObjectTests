import java.util.logging.Logger;

public class LoggingXMPPFailureReporter {
    private final Logger logger;
    public LoggingXMPPFailureReporter(Logger logger) {
        this.logger = logger;
    }
    public void cannotTranslateMessage(String auctionId, String failedMessage, Exception exception) {
        // A lógica do livro usa logger.severe(...)
        logger.severe(String.format("%s Could not translate message \"%s\" because \"%s\"", auctionId, failedMessage, exception.toString()));
    }
}
