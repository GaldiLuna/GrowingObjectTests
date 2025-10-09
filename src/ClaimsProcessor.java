public interface ClaimsProcessor {
    void adjudicateIfReady(ThirdParty thirdParty, Issue issue);
}
