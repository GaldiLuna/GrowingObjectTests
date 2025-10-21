import com.objogate.wl.swing.probe.ValueMatcherProbe;
import org.junit.Test;

import static com.datastax.insight.core.entity.Context.NOW;
import static com.sun.tools.javac.jvm.Gen.one;
import static org.hamcrest.Matchers.equalTo;
import static org.jmock.AbstractExpectations.returnValue;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.will;
import static sun.net.httpserver.HttpConnection.State.REQUEST;

public class MainWindowTest {
    private final SnipersTableModel tableModel = new SnipersTableModel();
    private final MainWindow mainWindow = new MainWindow(tableModel);
    private final AuctionSniperDriver driver = new AuctionSniperDriver(100);
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
            one(clock).dayHasChangedFrom(NOW); will(returnValue(false));
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
    //public boolean acceptRequest(Request request) {
    //    if (sameDayChecker.hasExpired()) {
    //        return false;
    //    }
    //    // process the request
    //    return true;
    //}
}
