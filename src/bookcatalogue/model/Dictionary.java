package bookcatalogue.model;

/** A dictionary, described by its number of entries (λήμματα). */
public class Dictionary extends Book {

    public static final int MIN_ENTRIES = 1_000;
    public static final int MAX_ENTRIES = 100_000;

    private int entryCount;

    public Dictionary(String title, String author, String isbn, String publisher, int pages,
                      short year, double price, int entryCount, int numberOfCopies) {
        super(title, author, isbn, publisher, pages, year, price, numberOfCopies);
        setEntryCount(entryCount);
    }

    public int getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(int entryCount) {
        if (entryCount < MIN_ENTRIES || entryCount > MAX_ENTRIES) {
            throw new IllegalArgumentException(
                    "Το πλήθος λημμάτων πρέπει να είναι από " + MIN_ENTRIES + " έως " + MAX_ENTRIES + ".");
        }
        this.entryCount = entryCount;
    }

    @Override
    public BookType getType() {
        return BookType.DICTIONARY;
    }

    @Override
    protected String extraDetails() {
        return "Λήμματα: " + entryCount;
    }
}
