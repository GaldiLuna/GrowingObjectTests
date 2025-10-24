import com.objogate.wl.swing.ComponentSelector;
import org.hamcrest.Matcher;
import com.objogate.wl.Prober;
import com.objogate.wl.gesture.Tracker;
import com.objogate.wl.swing.driver.ComponentDriver;

import javax.swing.JMenuItem;

public class AbstractButtonDriver extends ComponentDriver {
    public AbstractButtonDriver(ComponentDriver parent, Class<?> componentType, Matcher... matchers) {
        super(parent, componentType, matchers);
    }

    public AbstractButtonDriver(ComponentDriver parent, Class<JMenuItem> jMenuItemClass, Matcher matcher) {
        super(parent, jMenuItemClass, matcher);
    }

    public void click() {}
    public Prober prober() { return null; }
    public ComponentSelector component() { return null; }
    @Override
    public void isShowingOnScreen() { super.isShowingOnScreen(); }
    public void performGesture(Object mouseMove, Object mouseClick) {}
    @Override
    public Tracker centerOfComponent() { return super.centerOfComponent(); }
}
