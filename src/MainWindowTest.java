import com.objogate.wl.swing.probe.ValueMatcherProbe;
import org.junit.Test;

import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

import static com.datastax.insight.core.entity.Context.NOW;
//import static com.sun.tools.javac.jvm.Gen.one;
import static org.hamcrest.Matchers.equalTo;
import static org.jmock.AbstractExpectations.returnValue;
import static org.junit.Assert.assertFalse;
//import static org.mockito.BDDMockito.will;
//import static sun.net.httpserver.HttpConnection.State.REQUEST;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import static org.jmock.Expectations.*;

import com.objogate.wl.swing.probe.ValueMatcherProbe;
import org.hamcrest.Matcher;

@RunWith(JMock.class)
public class MainWindowTest {
    private final Mockery context = new JUnit4Mockery();
    private final SniperPortfolio portfolio = context.mock(SniperPortfolio.class);
    private final SnipersTableModel tableModel = new SnipersTableModel();
    private final MainWindow mainWindow = new MainWindow(portfolio);
    private final AuctionSniperDriver driver = new AuctionSniperDriver(100);
    private final Clock clock = context.mock(Clock.class, "clock");
    private final Clock sameDayChecker = context.mock(Clock.class, "sameDayChecker");
    private final Receiver receiver = context.mock(Receiver.class, "receiver");
    private final Object NOW = new Object();
    private final Object TODAY = new Object();
    private final Object TOMORROW = new Object();
    private final Object FIRST_REQUEST = new Object();
    private final Object SECOND_REQUEST = new Object();
    private final Object REQUEST = new Object();
    @Test
    public void makesUserRequestWhenJoinButtonClicked() {
        final ValueMatcherProbe<Item> itemProbe = new ValueMatcherProbe<Item>(equalTo(new Item("an item-id", 789)), "item request");
        mainWindow.addUserRequestListener(
                new UserRequestListener() {
                    public void joinAuction(Item item) {
                        itemProbe.setReceivedValue(item);
                    }
                });
        driver.startBiddingFor("an item-id", 789);
        driver.check(itemProbe);
    }
    @Test
    public void rejectsRequestsNotWithinTheSameDay() {
        Receiver receiver = new Receiver(clock);
        context.checking(new Expectations() {{
            allowing(clock).now(); will(returnValue(NOW));
            oneOf(clock).dayHasChangedFrom(NOW); will(returnValue(false));
        }});
        receiver.acceptRequest(FIRST_REQUEST);
        assertFalse("too late now", receiver.acceptRequest(SECOND_REQUEST));
    }
    @Test
    public void rejectsRequestsOutsideAllowedPeriod() {
        Receiver receiver = new Receiver(sameDayChecker);
        context.checking(new Expectations() {{
            allowing(sameDayChecker).hasExpired(); will(returnValue(false));
        }});
        assertFalse("too late now", receiver.acceptRequest(REQUEST));
    }

}
