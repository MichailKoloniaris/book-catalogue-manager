package bookcatalogue;

import bookcatalogue.model.Book;
import bookcatalogue.model.BookType;
import bookcatalogue.model.Dictionary;
import bookcatalogue.model.Isbn;
import bookcatalogue.model.ReadingBook;
import bookcatalogue.model.ScienceBook;
import bookcatalogue.sales.DaySales;
import bookcatalogue.util.ConsoleInput.EndOfInputException;

import java.util.List;
import java.util.Optional;

import static bookcatalogue.util.ConsoleInput.readDouble;
import static bookcatalogue.util.ConsoleInput.readInt;
import static bookcatalogue.util.ConsoleInput.readNonEmpty;
import static bookcatalogue.util.ConsoleInput.readShort;

/**
 * Console front end of the Book Catalogue Manager.
 * Run with {@code --demo} to start with a few sample books already in the catalogue.
 */
public class Main {

    private final Catalogue catalogue = new Catalogue();
    private final DaySales daySales = new DaySales();

    public static void main(String[] args) {
        Main app = new Main();
        if (args.length > 0 && args[0].equals("--demo")) {
            SampleData.load(app.catalogue);
            System.out.println("Φορτώθηκαν " + app.catalogue.size() + " βιβλία επίδειξης.");
        }
        try {
            app.run();
        } catch (EndOfInputException e) {
            System.out.println();
        }
        System.out.println("Αντίο!");
    }

    private void run() {
        while (true) {
            System.out.println();
            System.out.println("=== Book Catalogue Manager ===");
            System.out.println("1. Εισαγωγή νέου βιβλίου");
            System.out.println("2. Επεξεργασία καταχωρημένων βιβλίων");
            System.out.println("3. Πωλήσεις");
            System.out.println("4. Έξοδος");
            switch (readInt("Επιλογή (1-4): ", 1, 4)) {
                case 1 -> addBook();
                case 2 -> manageBooks();
                case 3 -> manageSales();
                default -> {
                    return;
                }
            }
        }
    }

    // ---------------------------------------------------------------- 1. add

    private void addBook() {
        if (catalogue.isFull()) {
            System.out.println("Ο κατάλογος είναι γεμάτος (" + catalogue.capacity() + " βιβλία).");
            return;
        }
        String title = readNonEmpty("Τίτλος: ");
        String author = readNonEmpty("Ονοματεπώνυμο συγγραφέα: ");
        String isbn = readIsbn();
        String publisher = readNonEmpty("Εκδοτικός οίκος: ");
        int pages = readInt("Αριθμός σελίδων: ", 1, 100_000);
        short year = readShort("Έτος έκδοσης: ");
        double price = readDouble("Τιμή (€): ");

        System.out.println("Είδος βιβλίου: 1. Επιστημονικό  2. Λεξικό  3. Ανάγνωσης");
        int kind = readInt("Επιλογή (1-3): ", 1, 3);
        try {
            Book book = switch (kind) {
                case 1 -> {
                    String area = readNonEmpty("Επιστημονική περιοχή: ");
                    int copies = readInt("Αριθμός αντιτύπων: ");
                    yield new ScienceBook(title, author, isbn, publisher, pages, year, price, area, copies);
                }
                case 2 -> {
                    int entries = readInt("Πλήθος λημμάτων: ");
                    int copies = readInt("Αριθμός αντιτύπων: ");
                    yield new Dictionary(title, author, isbn, publisher, pages, year, price, entries, copies);
                }
                default -> {
                    int copies = readInt("Αριθμός αντιτύπων: ");
                    yield new ReadingBook(title, author, isbn, publisher, pages, year, price, copies);
                }
            };
            catalogue.add(book);
            System.out.println("Το βιβλίο καταχωρήθηκε: " + book);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Η καταχώριση απέτυχε: " + e.getMessage());
        }
    }

    private static String readIsbn() {
        while (true) {
            String isbn = readNonEmpty("ISBN (10 ή 13 ψηφία): ");
            if (Isbn.isValid(isbn)) {
                return Isbn.normalize(isbn);
            }
            System.out.println("  Μη έγκυρο ISBN, το ψηφίο ελέγχου δεν ταιριάζει. Δοκιμάστε ξανά.");
        }
    }

    // ------------------------------------------------------------- 2. manage

    private void manageBooks() {
        if (catalogue.isEmpty()) {
            System.out.println("Δεν έχει καταχωρηθεί κανένα βιβλίο.");
            return;
        }
        System.out.println();
        System.out.println("--- Επεξεργασία βιβλίων ---");
        System.out.println("1. Αναζήτηση βιβλίου");
        System.out.println("2. Ταξινόμηση βιβλίων");
        System.out.println("3. Βιβλία με τιμή μεταξύ ορίων");
        System.out.println("4. Εμφάνιση όλων των βιβλίων");
        System.out.println("5. Επιστροφή");
        switch (readInt("Επιλογή (1-5): ", 1, 5)) {
            case 1 -> searchBooks();
            case 2 -> sortBooks();
            case 3 -> {
                double from = readDouble("Τιμή από: ");
                double to = readDouble("Τιμή έως: ");
                printBooks(catalogue.inPriceRange(from, to));
            }
            case 4 -> printBooks(catalogue.all());
            default -> {
            }
        }
    }

    private void searchBooks() {
        System.out.println("Αναζήτηση με: 1. ISBN  2. Έτος κυκλοφορίας  3. Επιστροφή");
        switch (readInt("Επιλογή (1-3): ", 1, 3)) {
            case 1 -> {
                var result = catalogue.searchByIsbn(readNonEmpty("ISBN: "));
                System.out.println("(" + result.method() + ")");
                result.value().ifPresentOrElse(
                        b -> System.out.println("Βρέθηκε: " + b),
                        () -> System.out.println("Το βιβλίο δεν βρέθηκε."));
            }
            case 2 -> {
                var result = catalogue.searchByYear(readShort("Έτος: "));
                System.out.println("(" + result.method() + ")");
                printBooks(result.value());
            }
            default -> {
            }
        }
    }

    private void sortBooks() {
        SortField[] fields = SortField.values();
        System.out.println("Ταξινόμηση κατά:");
        for (int i = 0; i < fields.length; i++) {
            System.out.println((i + 1) + ". " + fields[i].label());
        }
        System.out.println((fields.length + 1) + ". Επιστροφή");
        int f = readInt("Επιλογή (1-" + (fields.length + 1) + "): ", 1, fields.length + 1);
        if (f > fields.length) {
            return;
        }

        SortAlgorithm[] algorithms = SortAlgorithm.values();
        System.out.println("Μέθοδος ταξινόμησης:");
        for (int i = 0; i < algorithms.length; i++) {
            System.out.println((i + 1) + ". " + algorithms[i].label());
        }
        System.out.println((algorithms.length + 1) + ". Επιστροφή");
        int a = readInt("Επιλογή (1-" + (algorithms.length + 1) + "): ", 1, algorithms.length + 1);
        if (a > algorithms.length) {
            return;
        }

        catalogue.sort(fields[f - 1], algorithms[a - 1]);
        System.out.println("Ταξινόμηση κατά \"" + fields[f - 1].label() + "\" με " + algorithms[a - 1].label() + ":");
        printBooks(catalogue.all());
    }

    // -------------------------------------------------------------- 3. sales

    private void manageSales() {
        if (catalogue.isEmpty()) {
            System.out.println("Δεν έχει καταχωρηθεί κανένα βιβλίο.");
            return;
        }
        System.out.println();
        System.out.println("--- Πωλήσεις ---");
        System.out.println("1. Καταχώριση νέας πώλησης");
        System.out.println("2. Αξία πωλήσεων ημέρας");
        System.out.println("3. Best-seller ημέρας");
        System.out.println("4. Εμφάνιση όλων των πωλήσεων");
        System.out.println("5. Επιστροφή");
        switch (readInt("Επιλογή (1-5): ", 1, 5)) {
            case 1 -> recordSale();
            case 2 -> {
                for (BookType type : BookType.values()) {
                    System.out.printf("Αξία πωλήσεων (%s): %.2f€%n", type.label(), daySales.totalValue(type));
                }
                System.out.printf("Συνολική αξία πωλήσεων ημέρας: %.2f€%n", daySales.totalValue());
            }
            case 3 -> daySales.bestSeller().ifPresentOrElse(
                    e -> System.out.println("Best-seller (" + e.getValue() + " αντίτυπα): " + e.getKey()),
                    () -> System.out.println("Δεν έχουν γίνει πωλήσεις."));
            case 4 -> printSales();
            default -> {
            }
        }
    }

    private void recordSale() {
        Optional<Book> found = catalogue.findByIsbn(readNonEmpty("ISBN βιβλίου προς πώληση: "));
        if (found.isEmpty()) {
            System.out.println("Δεν υπάρχει βιβλίο με αυτό το ISBN.");
            return;
        }
        Book book = found.get();
        System.out.println(book.getTitle() + ": " + book.getNumberOfCopies() + " διαθέσιμα αντίτυπα.");
        int quantity = readInt("Ποσότητα: ");
        try {
            daySales.sell(book, quantity);
            System.out.printf("Η πώληση καταχωρήθηκε (%.2f€).%n", book.getPrice() * quantity);
        } catch (IllegalArgumentException e) {
            System.out.println("Η πώληση απέτυχε: " + e.getMessage());
        }
    }

    private void printSales() {
        if (daySales.isEmpty()) {
            System.out.println("Δεν έχουν γίνει πωλήσεις.");
            return;
        }
        System.out.printf("%-15s %-14s %-30s %s%n", "ISBN", "Είδος", "Τίτλος", "Αντίτυπα");
        System.out.println("-".repeat(70));
        daySales.forEach((Book b, Integer qty) ->
                System.out.printf("%-15s %-14s %-30s %d%n", b.getIsbn(), b.getType().label(), b.getTitle(), qty));
    }

    // ------------------------------------------------------------- helpers

    private static void printBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("Δεν βρέθηκε κανένα βιβλίο.");
            return;
        }
        for (Book b : books) {
            System.out.println("  " + b);
        }
    }
}
