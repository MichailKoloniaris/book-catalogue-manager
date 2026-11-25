package bookcatalogue.model;

/** The book categories supported by the catalogue. */
public enum BookType {
    SCIENCE("Επιστημονικό"),
    DICTIONARY("Λεξικό"),
    READING("Ανάγνωσης");

    private final String label;

    BookType(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
