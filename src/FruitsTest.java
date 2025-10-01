import org.junit.Test;
import static org.junit.Assert.*; // Importa assertTrue e assertFalse
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.*; // Importa containsString e not
import org.hamcrest.core.StringContains; // Esta classe não é padrão do Hamcrest, mas mantida para replicar o exemplo do livro


public class FruitsTest {
    @Test
    public void exampleHamcrestMatchers() {
        String s = "yes we have no bananas today";

        // Usando Matchers diretamente
        @SuppressWarnings("unchecked")
        Matcher<String> containsBananas = new StringContains("bananas");
        @SuppressWarnings("unchecked")
        Matcher<String> containsMangoes = new StringContains("mangoes");

        // Asserções originais com .matches(s)
        assertTrue(containsBananas.matches(s));
        assertFalse(containsMangoes.matches(s));

        // Asserções usando o metodo de fábrica estático containsString()
        assertTrue(containsString("bananas").matches(s));
        assertFalse(containsString("mangoes").matches(s));

        // Asserção usando assertThat() para melhor diagnóstico de falhas
        assertThat(s, containsString("bananas"));

        // Asserção usando o matcher "not" (corrigido o parêntese faltante)
        assertThat(s, not(containsString("mangoes")));
    }
}
