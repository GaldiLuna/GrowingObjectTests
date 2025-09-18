public class Fruits {
    String s = "yes we have no bananas today";
    Matcher<String> containsBananas = new StringContains("bananas");
    Matcher<String> containsMangoes = new StringContains("mangoes");
    assertTrue(containsBananas.matches(s));
    assertFalse(containsMangoes.matches(s));
    assertTrue(containsString("bananas").matches(s));
    assertFalse(containsString("mangoes").matches(s));
    assertThat(s, not(containsString("bananas"));
}
