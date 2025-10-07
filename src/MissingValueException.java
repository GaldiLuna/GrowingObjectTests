public class MissingValueException extends RuntimeException {
    public MissingValueException(String fieldName) {
        // Construtor simples que aceita o nome do campo ausente
        super("Missing value for field: " + fieldName);
    }
}
