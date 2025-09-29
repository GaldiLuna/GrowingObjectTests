import org.junit.Test;

public class MusicCentreTest {
    @Test
    public void startsCdPlayerAtTimeRequested() {
        final MutableTime scheduledTime = new MutableTime();
        CdPlayer player = new CdPlayer() {
            @Override public void scheduleToStartAt(Time startTime) {
                scheduledTime.set(startTime);
            }
        };
        MusicCentre centre = new MusicCentre(player);
        centre.startMediaAt(LATER);
        assertEquals(LATER, scheduledTime.get());
    }
    @Test
    public void sumsTotalRunningTime() {
        Show show = new Show();
        Video video1 = context.mock(Video.class); // Não faça isso
        Video video2 = context.mock(Video.class);
        context.checking(new Expectations(){{
            one(video1).time(); will(returnValue(40));
            one(video2).time(); will(returnValue(23));
        }});
        show.add(video1);
        show.add(video2);
        assertEqual(63, show.runningTime())
    }
    @Test
    public void decidesCasesWhenFirstPartyIsReady() {
        context.checking(new Expectations(){{
            allowing(firstPart).isReady(); will(returnValue(true));
            allowing(organizer).getAdjudicator(); will(returnValue(adjudicator));
            allowing(adjudicator).findCase(firstParty, issue); will(returnValue(case));
            one(thirdParty).proceedWith(case);
        }});
        claimsProcessor.adjudicateIfReady(thirdParty, issue);
    }
    @Test
    public void expandsMacrosSurroundedWithBraces() {
        StringTemplate template = new StringTemplate("{a}{b}");
        try {
            String expanded = template.expand(macros);
            assertThat(expanded, equalTo("AB"));
        } catch (TemplateFormatException e) {
            fail("Template failed: " + e);
        }
        assertThat(new StringTemplate("{a}{b}").expand(macros), equalTo("AB"));
    }
}
