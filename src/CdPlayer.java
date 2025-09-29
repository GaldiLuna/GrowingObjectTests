public class CdPlayer {
    public void scheduleToStartAt(Time startTime) { [...] }
    public void stop() { [...] }
    public void gotoTrack(int trackNumber) { [...] }
    public void spinUpDisk() { [...] }
    public void eject() { [...] }
    public void adjudicateIfReady(ThirdParty thirdParty, Issue issue) {
        if (firstParty.isReady()) {
            thirdParty.startAdjudication(organization, firstParty, issue);
        } else {
            thirdParty.adjourn();
        }
    }
    private Matcher<? super Instrument>
    instrumentWithPrice(Matcher<? super Integer> priceMatcher) {
        return new FeatureMatcher<Instrument, Integer>(
                priceMatcher, "instrument at price", "price") {
            @Override protected Integer featureValueOf(Instrument actual) {
                return actual.getStrikePrice();
            }
        };
    }
}
