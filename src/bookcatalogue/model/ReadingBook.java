package bookcatalogue.model;

/** A general reading book (fiction, essays, etc.). */
public class ReadingBook extends Book {

    public ReadingBook(String title, String author, String isbn, String publisher, int pages,
                       short year, double price, int numberOfCopies) {
        super(title, author, isbn, publisher, pages, year, price, numberOfCopies);
    }

    @Override
    public BookType getType() {
        return BookType.READING;
    }
}
