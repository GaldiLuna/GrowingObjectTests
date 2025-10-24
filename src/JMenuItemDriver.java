import javax.swing.JMenuItem;

import com.objogate.wl.gesture.Tracker;
import org.hamcrest.Matcher;
import com.objogate.wl.swing.driver.AbstractButtonDriver;
import com.objogate.wl.swing.driver.ComponentDriver;

public class JMenuItemDriver extends AbstractButtonDriver<JMenuItem> {
    //JMenuItemDriver buttonDriver = menuItem(matcher);

    @Override
    public void isShowingOnScreen() {
        super.isShowingOnScreen();
    }
    @Override
    public Tracker centerOfComponent() {
        return super.centerOfComponent();
    }

    public JMenuItemDriver(ComponentDriver parent, Matcher matcher) {
        super(parent, JMenuItem.class, matcher);
    }

    public void buttonDriver() {

    }

}
