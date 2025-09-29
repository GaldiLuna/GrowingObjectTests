import org.junit.Test;

public class StringTemplateTest {
    @Test
    public void expandsMacrosSurroundedWithBraces() {
        StringTemplate template = new StringTemplate("{a}{b}"); // Setup
        HashMap<String,Object> macros = new HashMap<String,Object>();
        macros.put("a", "A");
        macros.put("b", "B");
        String expanded = template.expand(macros); // Execute
        assertThat(expanded, equalTo("AB")); // Assert
    }
    // No Teardown
}
