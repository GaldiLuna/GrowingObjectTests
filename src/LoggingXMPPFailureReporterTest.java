import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class LoggingXMPPFailureReporterTest {
    private final Mockery context = new Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
    // Setup
    final Logger logger = context.mock(Logger.class);
    final LoggingXMPPFailureReporter reporter = new LoggingXMPPFailureReporter(logger);

    @Test
    public void writesMessageTranslationFailureToLog() {
        Exception exception = new Exception("an exception");
        context.checking(new Expectations() {{
            oneOf(logger).severe("<auction id> " + "Could not translate message \"bad message\" " + "because \"java.lang.Exception: bad\"");
        }});
        reporter.cannotTranslateMessage("auction id", "bad message", new Exception("bad"));
        // implicitly check expectations are satisfied // Assert
    }

    @AfterClass
    public static void resetLogging() {
        LogManager.getLogManager().reset();
    }
    // Teardown
}
