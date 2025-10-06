import org.junit.Test;
import org.junit.runner.RunWith;
import org.jmock.Expectations; // Classe Expectations real
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import java.util.HashMap;

// --- Estruturas Auxiliares (Definidas FORA da Classe de Teste) ---


@RunWith(JMock.class)
// ABRE o corpo da classe de teste
public class MusicCentreTest {

    // 1. Configuração do Mockery (Test Fixture)
    private final Mockery context = new JUnit4Mockery();

    // Variáveis auxiliares para os testes
    private final Object LATER = new Object();
    private final HashMap<String, Object> macros = new HashMap<>();

    // Mock objects e variáveis para o teste de "sumsTotalRunningTime"
    private final Video video1 = context.mock(Video.class, "video1");
    private final Video video2 = context.mock(Video.class, "video2");

    // Mock objects e variáveis para o teste de "decidesCasesWhenFirstPartyIsReady"
    private final Object firstPart = context.mock(Object.class, "firstPart");
    private final Object organizer = context.mock(Object.class, "organizer");
    private final Object adjudicator = context.mock(Object.class, "adjudicator");
    private final Object issue = new Object();
    private final Object caseVar = new Object();
    private final Object thirdParty = context.mock(Object.class, "thirdParty");
    private final Object claimsProcessor = context.mock(Object.class, "claimsProcessor");


    @Test
    public void startsCdPlayerAtTimeRequested() {
        // Este teste não é um teste de mock (Teste de Subclasse Anônima)
        final MutableTime scheduledTime = new MutableTime() {
            private Object time;
            @Override public void set(Object t) { this.time = t; }
            @Override public Object get() { return this.time; }
        };
        CdPlayer player = new CdPlayer() {
            @Override public void scheduleToStartAt(Object startTime) {
                scheduledTime.set(startTime);
            }
        };
        MusicCentre centre = new MusicCentre(player);
        centre.startMediaAt(LATER);

        assertEquals(LATER, scheduledTime.get());
    }

// ---

    @Test
    public void sumsTotalRunningTime() {
        Show show = new Show();

        context.checking(new Expectations(){{
            // Note: will() e oneOf() são métodos da classe org.jmock.Expectations,
            // que agora está corretamente importada e em uso.
            oneOf(video1).time(); will(returnValue(40));
            oneOf(video2).time(); will(returnValue(23));
        }});

        show.add(video1);
        show.add(video2);

        assertEquals(63, show.runningTime());
    }

// ---

    @Test
    public void decidesCasesWhenFirstPartyIsReady() {

        context.checking(new Expectations(){{
            // allowing() é um método do JMock, agora corretamente referenciado
            allowing(firstPart).isReady(); will(returnValue(true));
            allowing(organizer).getAdjudicator(); will(returnValue(adjudicator));
            // Substituído 'case' por 'caseVar'
            allowing(adjudicator).findCase(firstPart, issue); will(returnValue(caseVar));
            oneOf(thirdParty).proceedWith(caseVar);
        }});

        claimsProcessor.adjudicateIfReady(thirdParty, issue);
    }

// ---

    @Test
    public void expandsMacrosSurroundedWithBraces() throws Exception {
        StringTemplate template = new StringTemplate("{a}{b}");

        assertThat(new StringTemplate("{a}{b}").expand(macros), equalTo("AB"));
    }
} // FECHA o corpo da classe de teste