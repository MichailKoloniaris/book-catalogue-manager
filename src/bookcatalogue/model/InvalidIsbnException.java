package bookcatalogue.model;

/** Thrown when an ISBN-10 / ISBN-13 fails its check digit validation. */
public class InvalidIsbnException extends IllegalArgumentException {
    public InvalidIsbnException(String isbn) {
        super("Μη έγκυρο ISBN: \"" + isbn + "\". Δώστε ISBN-10 ή ISBN-13 με σωστό ψηφίο ελέγχου.");
    }
}
