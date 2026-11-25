package bookcatalogue.model;

/**
 * Common data for every book in the catalogue.
 * Concrete categories ({@link ScienceBook}, {@link Dictionary}, {@link ReadingBook})
 * add their own fields and the number of copies in stock.
 */
public abstract class Book implements Sellable {

    public static final int MIN_YEAR = 1500;
    public static final int MAX_YEAR = 2200;
    public static final double MAX_PRICE = 1000.0;

    private String title;
    private String author;
    private final String isbn;
    private String publisher;
    private int pages;
    private short year;
    private double price;
    private int numberOfCopies;

    protected Book(String title, String author, String isbn, String publisher,
                   int pages, short year, double price, int numberOfCopies) {
        if (!Isbn.isValid(isbn)) {
            throw new InvalidIsbnException(isbn);
        }
        this.isbn = Isbn.normalize(isbn);
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setPages(pages);
        setYear(year);
        setPrice(price);
        setNumberOfCopies(numberOfCopies);
    }

    /** Human readable category name, e.g. "Λεξικό". */
    public abstract BookType getType();

    /** Category specific details shown after the common fields. */
    protected String extraDetails() {
        return "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = requireText(title, "Ο τίτλος");
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = requireText(author, "Ο συγγραφέας");
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = requireText(publisher, "Ο εκδοτικός οίκος");
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        if (pages < 1) {
            throw new IllegalArgumentException("Ο αριθμός σελίδων πρέπει να είναι θετικός.");
        }
        this.pages = pages;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        if (year < MIN_YEAR || year > MAX_YEAR) {
            throw new IllegalArgumentException(
                    "Το έτος έκδοσης πρέπει να είναι από " + MIN_YEAR + " έως " + MAX_YEAR + ".");
        }
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0 || price > MAX_PRICE) {
            throw new IllegalArgumentException("Η τιμή του βιβλίου πρέπει να είναι από 0 έως " + MAX_PRICE + "€.");
        }
        this.price = price;
    }

    @Override
    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    @Override
    public void setNumberOfCopies(int copies) {
        if (copies < 0) {
            throw new IllegalArgumentException("Το πλήθος αντιτύπων δεν μπορεί να είναι αρνητικό.");
        }
        this.numberOfCopies = copies;
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " δεν μπορεί να είναι κενός.");
        }
        return value.trim();
    }

    @Override
    public String toString() {
        String extra = extraDetails();
        return String.format("[%s] \"%s\" - %s | ISBN %s | %s, %d σελ., %d | %.2f€ | %d αντίτυπα%s",
                getType().label(), title, author, isbn, publisher, pages, year, price, numberOfCopies,
                extra.isEmpty() ? "" : " | " + extra);
    }
}
