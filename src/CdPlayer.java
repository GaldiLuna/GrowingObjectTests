import org.hamcrest.Matcher;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matchers;

public interface CdPlayer {
    default void scheduleToStartAt(Object startTime) {
    }
    void stop();
    void gotoTrack(int trackNumber);
    void spinUpDisk();
    void eject();
    default void adjudicateIfReady(ThirdParty thirdParty, Issue issue) {
        Object firstParty = ClaimsProcessorCollaborators.firstParty; // Usando stub
        Object organization = ClaimsProcessorCollaborators.organization; // Usando stub

        if (true) {
            thirdParty.startAdjudication(organization, firstParty, issue);
        } else {
            thirdParty.adjourn();
        }
    }
    private Matcher<? super Instrument> instrumentWithPrice(Matcher<? super Integer> priceMatcher) {
        return new FeatureMatcher<Instrument, Integer>(
                priceMatcher, "instrument at price", "price") {
            @Override protected Integer featureValueOf(Instrument actual) {
                return (Integer) actual.getStrikePrice();
            }
        };
    }
}
