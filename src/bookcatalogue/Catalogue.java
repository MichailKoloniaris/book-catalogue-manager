package bookcatalogue;

import bookcatalogue.model.Book;
import bookcatalogue.model.Isbn;
import bookcatalogue.util.Searching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * A fixed-capacity collection of books stored in a plain array.
 * It remembers which field it was last sorted by, so searches on that field
 * can use binary search instead of sequential search.
 */
public class Catalogue {

    public static final int DEFAULT_CAPACITY = 10;

    private final Book[] books;
    private int size;
    private SortField sortedBy;

    public Catalogue() {
        this(DEFAULT_CAPACITY);
    }

    public Catalogue(int capacity) {
        this.books = new Book[capacity];
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return books.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == books.length;
    }

    public Optional<SortField> sortedBy() {
        return Optional.ofNullable(sortedBy);
    }

    /** Adds a book. Fails if the catalogue is full or the ISBN already exists. */
    public void add(Book book) {
        if (isFull()) {
            throw new IllegalStateException("Ο κατάλογος είναι γεμάτος (" + books.length + " βιβλία).");
        }
        if (findByIsbn(book.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("Υπάρχει ήδη βιβλίο με ISBN " + book.getIsbn() + ".");
        }
        books[size++] = book;
        sortedBy = null; // a new book may break the current order
    }

    public void sort(SortField field, SortAlgorithm algorithm) {
        algorithm.sort(books, size, field.comparator());
        sortedBy = field;
    }

    /** Result of a search, together with the method that was used. */
    public record SearchResult<T>(T value, String method) {
    }

    public SearchResult<Optional<Book>> searchByIsbn(String isbn) {
        String key = Isbn.normalize(isbn);
        if (sortedBy == SortField.ISBN) {
            int i = Searching.binarySearch(books, size, Book::getIsbn, key);
            return new SearchResult<>(i == -1 ? Optional.empty() : Optional.of(books[i]), "δυαδική αναζήτηση");
        }
        int i = Searching.sequentialSearch(books, size, Book::getIsbn, key);
        return new SearchResult<>(i == -1 ? Optional.empty() : Optional.of(books[i]), "σειριακή αναζήτηση");
    }

    public Optional<Book> findByIsbn(String isbn) {
        return searchByIsbn(isbn).value();
    }

    public SearchResult<List<Book>> searchByYear(short year) {
        if (sortedBy == SortField.YEAR) {
            return new SearchResult<>(Searching.binarySearchAll(books, size, Book::getYear, year), "δυαδική αναζήτηση");
        }
        return new SearchResult<>(Searching.sequentialSearchAll(books, size, Book::getYear, year), "σειριακή αναζήτηση");
    }

    public List<Book> inPriceRange(double from, double to) {
        double low = Math.min(from, to);
        double high = Math.max(from, to);
        List<Book> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (books[i].getPrice() >= low && books[i].getPrice() <= high) {
                result.add(books[i]);
            }
        }
        return result;
    }

    public List<Book> all() {
        return List.copyOf(Arrays.asList(books).subList(0, size));
    }
}
