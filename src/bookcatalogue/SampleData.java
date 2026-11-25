package bookcatalogue;

import bookcatalogue.model.Dictionary;
import bookcatalogue.model.ReadingBook;
import bookcatalogue.model.ScienceBook;

/** A handful of books used by {@code --demo} so the app can be tried out quickly. */
final class SampleData {

    private SampleData() {
    }

    static void load(Catalogue catalogue) {
        catalogue.add(new ScienceBook("Introduction to Algorithms", "Thomas H. Cormen", "9780262033848",
                "MIT Press", 1312, (short) 2009, 89.90, "Αλγόριθμοι", 4));
        catalogue.add(new ScienceBook("Effective Java", "Joshua Bloch", "9780134685991",
                "Addison-Wesley", 412, (short) 2018, 45.50, "Τεχνολογία λογισμικού", 6));
        catalogue.add(new ScienceBook("The C Programming Language", "Brian W. Kernighan", "9780131103627",
                "Prentice Hall", 272, (short) 1988, 39.00, "Προγραμματισμός", 3));
        catalogue.add(new Dictionary("Λεξικό της Κοινής Νεοελληνικής", "Ινστιτούτο Νεοελληνικών Σπουδών",
                "9789600316421", "ΙΝΣ", 1520, (short) 1998, 55.00, 45_000, 2));
        catalogue.add(new ReadingBook("Pride and Prejudice", "Jane Austen", "9780141439518",
                "Penguin Classics", 480, (short) 2003, 9.99, 10));
        catalogue.add(new ReadingBook("1984", "George Orwell", "9780451524935",
                "Signet Classics", 328, (short) 1961, 8.50, 8));
    }
}
