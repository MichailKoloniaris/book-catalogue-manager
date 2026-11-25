package bookcatalogue.sales;

import bookcatalogue.model.Book;
import bookcatalogue.model.BookType;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * Records the copies sold during one day.
 * Sales are keyed by book (not by array position), so sorting the catalogue
 * never mixes up which book a sale belongs to.
 */
public class DaySales {

    private final Map<Book, Integer> copiesSold = new LinkedHashMap<>();

    /** Sells {@code quantity} copies of {@code book} and removes them from stock. */
    public void sell(Book book, int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Η ποσότητα πρέπει να είναι τουλάχιστον 1.");
        }
        if (quantity > book.getNumberOfCopies()) {
            throw new IllegalArgumentException("Δεν υπάρχουν αρκετά αντίτυπα. Διαθέσιμα: "
                    + book.getNumberOfCopies() + ".");
        }
        book.setNumberOfCopies(book.getNumberOfCopies() - quantity);
        copiesSold.merge(book, quantity, Integer::sum);
    }

    public boolean isEmpty() {
        return copiesSold.isEmpty();
    }

    public double totalValue() {
        return copiesSold.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
    }

    public double totalValue(BookType type) {
        return copiesSold.entrySet().stream()
                .filter(e -> e.getKey().getType() == type)
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
    }

    /** The book with the most copies sold today, if any sale was made. */
    public Optional<Map.Entry<Book, Integer>> bestSeller() {
        return copiesSold.entrySet().stream().max(Map.Entry.comparingByValue());
    }

    public Map<Book, Integer> copiesSold() {
        return Map.copyOf(copiesSold);
    }

    public void forEach(BiConsumer<Book, Integer> action) {
        copiesSold.forEach(action);
    }
}
