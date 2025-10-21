public interface ThirdParty {
    void startAdjudication(Object organization, Object firstParty, Issue issue);
    void proceedWith(Case caseVar);
    void adjourn();
}
