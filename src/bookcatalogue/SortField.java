package bookcatalogue;

import bookcatalogue.model.Book;

import java.util.Comparator;

/** The fields the catalogue can be sorted by. */
public enum SortField {
    AUTHOR("Όνομα συγγραφέα", Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER)),
    ISBN("ISBN", Comparator.comparing(Book::getIsbn)),
    YEAR("Έτος κυκλοφορίας", Comparator.comparingInt(Book::getYear)),
    PRICE("Τιμή βιβλίου", Comparator.comparingDouble(Book::getPrice));

    private final String label;
    private final Comparator<Book> comparator;

    SortField(String label, Comparator<Book> comparator) {
        this.label = label;
        this.comparator = comparator;
    }

    public String label() {
        return label;
    }

    public Comparator<Book> comparator() {
        return comparator;
    }
}
