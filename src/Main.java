import java.util.Comparator;

public class Main {

    public static boolean correctISBN(String ISBN){
        if (ISBN.length() == 10) {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += (ISBN.charAt(i) - '0') * (10-i);
            }
            return sum % 11 == 0;
        }

        else if(ISBN.length() == 13){
            int sum = 0;
            for (int i = 0; i < 13; i++) {
                if(i % 2 == 0){
                    sum += (ISBN.charAt(i) - '0');
                }
                else{
                    sum += (ISBN.charAt(i) - '0') * 3;
                }
            }
            return sum % 10 == 0;
        }
        else{
            return false;
        }
    }

    public static void main(String[] args) {
        DaySales daySales = new DaySales();
        Book[] books = new Book[10];
        int bookArraySize = 0;
        int sorted = 0;
        int choice;
        do {
            System.out.println("    Λίστα Επιλογών");
            System.out.println("1. Εισαγωγή στοιχείων νέου βιβλίου");
            System.out.println("2. Επεξεργασία καταχωρημένων βιβλίων");
            System.out.println("3. Πώληση βιβλίου");
            System.out.println("4. Έξοδος από την εφαρμογή");
            System.out.println("    Δώστε Επιλογή (1-4) :");
            choice = UserInput.getInteger();
            switch (choice) {
                case 1: //Εισαγωγή στοιχείων βιβλίου
                    int posision = -1;
                    for (int i = 0; i < books.length; i++) {
                        if (books[i] == null) {
                            posision = i;
                            break;
                        }
                    }
                    if (posision == -1) {
                        System.out.println("Ο πίνακας είναι γεμάτος");
                        break;
                    } else {
                        String title, author, publisher, ISBN;
                        int pages,bookChoice;
                        double price;
                        short year;
                        System.out.println("Εισάγεται Τίτλος βιβλίου:");
                        title = UserInput.getString();

                        System.out.println("Εισάγεται Όνοματεπώνυμο συγγραφέα:");
                        author = UserInput.getString();

                        while (true) {
                            try {
                                System.out.println("Εισάγεται ISBN – International Standard Book Number:");
                                ISBN = UserInput.getString();
                                if (!correctISBN(ISBN)) {
                                    throw new WrongISBNException("Lathos eisagogi ISBN");
                                }
                                else {break;}

                            } catch (WrongISBNException e) {
                                System.out.println(e.getMessage());
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid input: " + e.getMessage());
                            }
                        }

                        System.out.println("Εισάγεται Εκδοτικός οίκος:");
                        publisher = UserInput.getString();

                        System.out.println("Εισάγεται Αριθμός σελίδων:");
                        pages = UserInput.getInteger();

                        System.out.println("Εισάγεται Έτος έκδοσης:");
                        year = UserInput.getShort();

                        System.out.println("Εισάγεται Τιμή:");
                        price = UserInput.getDouble();

                        System.out.println("Εισάγεται 1 αν είναι Επιστημονικό Βιβλείο ή 2 αν είναι Λεξικό ή 3 αν είναι για ανάγνωση:");
                        bookChoice = UserInput.getInteger();
                        int numberOfCopies;
                        switch (bookChoice) {
                            case 1:
                                String scientificArea;
                                System.out.println("Δώστε την Επιστιμονική Περιοχή του βιβλείου: ");
                                scientificArea = UserInput.getString();
                                System.out.println("Δώστε αριθμό αντιτύπων: ");
                                numberOfCopies = UserInput.getInteger();
                                books[posision] = new Science(title, author, ISBN, publisher, pages, year, price,scientificArea,numberOfCopies);
                                break;
                            case 2:
                                int sizeLimmaton;
                                System.out.println("Δώστε το πλήθος των λημμάτων του λεξικού: ");
                                sizeLimmaton = UserInput.getInteger();
                                System.out.println("Δώστε αριθμό αντιτύπων: ");
                                numberOfCopies = UserInput.getInteger();
                                books[posision] = new Dictionary(title, author, ISBN, publisher, pages, year, price,sizeLimmaton,numberOfCopies);
                                break;
                            case 3:
                                books[posision] = new ReadBook(title, author, ISBN, publisher, pages, year, price);
                                break;
                        }
                        bookArraySize++;
                    }
                    break;
                case 2:
                    if (bookArraySize == 0) {
                        System.out.println("Δεν εχει καταχωρηθεί καποιο βιβλείο!");
                        break;
                    }
                    System.out.println("    Επιλογές Επεξεργασίας Βιβλίων");
                    System.out.println("1. Αναζήτηση βιβλίου");
                    System.out.println("2. Ταξινόμηση βιβλίων");
                    System.out.println("3. Εμφάνιση στοιχείων βιβλίων με τιμή μεταξύ κάποιων ορίων");
                    System.out.println("4. Εμφάνιση στοιχείων όλων των βιβλίων");
                    System.out.println("5. Επιστροφή στην Αρχική Λίστα Επιλογών");
                    System.out.println("Δώστε Επιλογή (1-5) :");
                    int SecondMenuChoice = UserInput.getInteger();
                    switch (SecondMenuChoice) {
                        case 1: //Αναζήτηση βιβλίου
                            System.out.println("    Επιλογή Πεδίου Αναζήτησης");
                            System.out.println("1. ISBN");
                            System.out.println("2. Έτος κυκλοφορίας");
                            System.out.println("3. Επιστροφή στην Αρχική Λίστα Επιλογών");
                            System.out.println("    Δώστε Επιλογή (1-3) :");
                            int SearchBookChoice = UserInput.getInteger();
                            switch (SearchBookChoice) {
                                case 1: // Search ISBN
                                    System.out.println("Δώστε ISBN: ");
                                    String searchISBN = UserInput.getString();
                                    if (sorted == 2){
                                        int binSearchResult = MyUtils.binSearch(books,searchISBN,bookArraySize);
                                        System.out.println("Binnary Search");
                                        if (binSearchResult == -1) {
                                            System.out.println("Το βιβλειο δεν βρέθηκε");
                                        }
                                        else {
                                            System.out.println("Το βιβλέιο βρέθηκε");
                                        }
                                    }
                                    else{
                                        int seqSearchResult = MyUtils.seqSearch(books,searchISBN,bookArraySize);
                                        System.out.println("Seq Search");
                                        if (seqSearchResult == -1) {
                                            System.out.println("Το βιβλειο δεν βρέθηκε");
                                        }
                                        else {
                                            System.out.println("Το βιβλείο βρέθηκε");
                                        }
                                    }
                                    break;
                                case 2: // Search Year
                                    System.out.println("Δώστε Year: ");
                                    int searchYear = UserInput.getInteger();
                                    if (sorted == 3){
                                        Book[] binSearchResult = MyUtils.binSearch(books,searchYear,bookArraySize);
                                        System.out.println("Binnary Search");
                                        int counter = 0;
                                        for (int i = 0; i < binSearchResult.length; i++) {
                                            if (binSearchResult[i] != null) {
                                                System.out.println("Βρεθηκε το βιβλειο:");
                                                System.out.println(binSearchResult[i].toString());
                                                counter++;
                                            }
                                        }
                                        if (counter == 0){
                                            System.out.println("Δεν βρεθηκε καποιο βιβλειο");
                                        }
                                    }
                                    else{
                                        Book[] seqSearchResult = MyUtils.seqSearch(books,searchYear,bookArraySize);
                                        System.out.println("Seq Search");
                                        int counter = 0;
                                        for (int i = 0; i < seqSearchResult.length; i++) {
                                            if (seqSearchResult[i] != null) {
                                                System.out.println("Βρεθηκε το βιβλειο:");
                                                System.out.println(seqSearchResult[i].toString());
                                                counter++;
                                            }
                                        }
                                        if (counter == 0){
                                            System.out.println("Δεν βρεθηκε καποιο βιβλειο");
                                        }
                                    }
                                    break;
                            }
                            break;
                        case 2: //Ταξινόμηση βιβλίων
                            System.out.println("    Επιλογή Πεδίου Ταξινόμησης");
                            System.out.println("1. Όνομα Συγγραφέα");
                            System.out.println("2. ISBN");
                            System.out.println("3. Έτος κυκλοφορίας");
                            System.out.println("4. Τιμή βιβλίου");
                            System.out.println("5. Επιστροφή στην Αρχική Λίστα Επιλογών");
                            System.out.println("Δώστε Επιλογή (1-5) :");
                            int SearchChoice = UserInput.getInteger();

                            switch (SearchChoice) {
                                case 1: //Όνομα Συγγραφέα
                                    System.out.println("    Επιλογή Μεθόδου");
                                    System.out.println("1. Bubblesort");
                                    System.out.println("2. Ταξινόμηση με Εισαγωγή");
                                    System.out.println("3. Ταξινόμηση με Επιλογή");
                                    System.out.println("4. Γρήγορη Ταξινόμηση");
                                    System.out.println("5. Ταξινόμηση με Συγχώνευση");
                                    System.out.println("6. Επιστροφή στην Επιλογή Πεδίου Ταξινόμησης");
                                    System.out.println("Δώστε Επιλογή (1-6) :");
                                    int nameMethodChoice = UserInput.getInteger();
                                    switch (nameMethodChoice) {
                                        case 1: //bubbleSort
                                            MyUtils.bubbleSort(books, bookArraySize, Comparator.comparing(Book::getAuthor));
                                            sorted = 1;
                                            break;
                                        case 2: //insertSort
                                            MyUtils.insertSort(books, bookArraySize, Comparator.comparing(Book::getAuthor));
                                            sorted = 1;
                                            break;
                                        case 3: //selectionSort
                                            MyUtils.selectionSort(books, bookArraySize, Comparator.comparing(Book::getAuthor));
                                            sorted = 1;
                                            break;
                                        case 4: //quickSort
                                            MyUtils.quickSort(books, 0, bookArraySize - 1, Comparator.comparing(Book::getAuthor));
                                            sorted = 1;
                                            break;
                                        case 5: //mergeSort
                                            MyUtils.mergeSort(books, bookArraySize, Comparator.comparing(Book::getAuthor));
                                            sorted = 1;
                                            break;
                                    }
                                    break;
                                case 2://ISBN
                                    System.out.println("    Επιλογή Μεθόδου");
                                    System.out.println("1. Bubblesort");
                                    System.out.println("2. Ταξινόμηση με Εισαγωγή");
                                    System.out.println("3. Ταξινόμηση με Επιλογή");
                                    System.out.println("4. Γρήγορη Ταξινόμηση");
                                    System.out.println("5. Ταξινόμηση με Συγχώνευση");
                                    System.out.println("6. Επιστροφή στην Επιλογή Πεδίου Ταξινόμησης");
                                    System.out.println("Δώστε Επιλογή (1-6) :");
                                    int isbnMethodChoice = UserInput.getInteger();
                                    switch (isbnMethodChoice) {
                                        case 1: //bubbleSort
                                            MyUtils.bubbleSort(books, bookArraySize, Comparator.comparing(Book::getISBN));
                                            sorted = 2;
                                            break;
                                        case 2: //insertSort
                                            MyUtils.insertSort(books, bookArraySize, Comparator.comparing(Book::getISBN));
                                            sorted = 2;
                                            break;
                                        case 3: //selectionSort
                                            MyUtils.selectionSort(books, bookArraySize, Comparator.comparing(Book::getISBN));
                                            sorted = 2;
                                            break;
                                        case 4: //quickSort
                                            MyUtils.quickSort(books, 0, bookArraySize - 1, Comparator.comparing(Book::getISBN));
                                            sorted = 2;
                                            break;
                                        case 5: //mergeSort
                                            MyUtils.mergeSort(books, bookArraySize, Comparator.comparing(Book::getISBN));
                                            sorted = 2;
                                            break;
                                    }
                                    break;
                                case 3://Έτος κυκλοφορίας
                                    System.out.println("    Επιλογή Μεθόδου");
                                    System.out.println("1. Bubblesort");
                                    System.out.println("2. Ταξινόμηση με Εισαγωγή");
                                    System.out.println("3. Ταξινόμηση με Επιλογή");
                                    System.out.println("4. Γρήγορη Ταξινόμηση");
                                    System.out.println("5. Ταξινόμηση με Συγχώνευση");
                                    System.out.println("6. Επιστροφή στην Επιλογή Πεδίου Ταξινόμησης");
                                    System.out.println("Δώστε Επιλογή (1-6) :");
                                    int yearMethodChoice = UserInput.getInteger();
                                    switch (yearMethodChoice) {
                                        case 1: //bubbleSort
                                            MyUtils.bubbleSort(books, bookArraySize, Comparator.comparingInt(Book::getYear));
                                            sorted = 3;
                                            break;
                                        case 2: //insertSort
                                            MyUtils.insertSort(books, bookArraySize, Comparator.comparingInt(Book::getYear));
                                            sorted = 3;
                                            break;
                                        case 3: //selectionSort
                                            MyUtils.selectionSort(books, bookArraySize, Comparator.comparingInt(Book::getYear));
                                            sorted = 3;
                                            break;
                                        case 4: //quickSort
                                            MyUtils.quickSort(books, 0, bookArraySize - 1, Comparator.comparingInt(Book::getYear));
                                            sorted = 3;
                                            break;
                                        case 5: //mergeSort
                                            MyUtils.mergeSort(books, bookArraySize, Comparator.comparingInt(Book::getYear));
                                            sorted = 3;
                                            break;
                                    }
                                    break;
                                case 4://Τιμή βιβλίου
                                    System.out.println("    Επιλογή Μεθόδου");
                                    System.out.println("1. Bubblesort");
                                    System.out.println("2. Ταξινόμηση με Εισαγωγή");
                                    System.out.println("3. Ταξινόμηση με Επιλογή");
                                    System.out.println("4. Γρήγορη Ταξινόμηση");
                                    System.out.println("5. Ταξινόμηση με Συγχώνευση");
                                    System.out.println("6. Επιστροφή στην Επιλογή Πεδίου Ταξινόμησης");
                                    System.out.println("Δώστε Επιλογή (1-6) :");
                                    int priceMethodChoice = UserInput.getInteger();
                                    switch (priceMethodChoice) {
                                        case 1: //bubbleSort
                                            MyUtils.bubbleSort(books, bookArraySize, Comparator.comparingDouble(Book::getPrice));
                                            sorted = 4;
                                            break;
                                        case 2: //insertSort
                                            MyUtils.insertSort(books, bookArraySize, Comparator.comparingDouble(Book::getPrice));
                                            sorted = 4;
                                            break;
                                        case 3: //selectionSort
                                            MyUtils.selectionSort(books, bookArraySize, Comparator.comparingDouble(Book::getPrice));
                                            sorted = 4;
                                            break;
                                        case 4: //quickSort
                                            MyUtils.quickSort(books, 0, bookArraySize - 1, Comparator.comparingDouble(Book::getPrice));
                                            sorted = 4;
                                            break;
                                        case 5: //mergeSort
                                            MyUtils.mergeSort(books, bookArraySize, Comparator.comparingDouble(Book::getPrice));
                                            sorted = 4;
                                            break;
                                    }
                                    break;

                            }
                            break;
                        case 3://Εμφάνιση στοιχείων βιβλίων με τιμή μεταξύ κάποιων ορίων
                            System.out.println("Δώστε τιμή απο: ");
                            double from = UserInput.getDouble();
                            System.out.println("Δώστε τιμή μέχρι: ");
                            double to = UserInput.getDouble();
                            MyUtils.valueSearch(books, from, to);
                            break;
                        case 4: //Εμφάνιση στοιχείων όλων των βιβλίων
                            for (int i = 0; i < bookArraySize; i++){
                                System.out.println(books[i].toString());
                            }
                            break;
                        case 5:
                            break;
                    }
                    break;
                case 3: //Πώληση βιβλίου
                    if (bookArraySize == 0) {
                        System.out.println("Δεν εχει καταχωρηθεί καποιο βιβλείο!");
                        break;
                    }
                    System.out.println("    Επιλογές Διαχείρισης Πωλήσεων");
                    System.out.println("1. Καταχώριση Νέας Πώλησης Βιβλίου");
                    System.out.println("2. Υπολογισμός και εμφάνιση αξίας πωλήσεων");
                    System.out.println("3. Ημερήσιο best-seller");
                    System.out.println("4. Εμφάνιση στοιχείων όλων των πωλήσεων.");
                    System.out.println("5. Επιστροφή στην Αρχική Λίστα Επιλογών");
                    int SaleChoice = UserInput.getInteger();
                    switch (SaleChoice) {
                        case 1:
                            System.out.println("Δώστε ISBN του προς πώληση βιβλίου: ");
                            String ISBNSale = UserInput.getString();
                            int saleID = MyUtils.binSearch(books,ISBNSale,bookArraySize);
                            System.out.println("Δώστε ποσότητα αντιτύπων: ");
                            int quantitySale = UserInput.getInteger();
                            daySales.addSale(ISBNSale,books,bookArraySize,quantitySale,((ProsPolisi)books[saleID]).getNumberOfCopies());
                            break;

                        case 2:
                            System.out.println("H αξία των επιστημονικών βιβλίων: "+daySales.getTotalSalesByType(books,bookArraySize,"Επιστημονικό") + "€");
                            System.out.println("H αξία των λεξικών βιβλίων: "+daySales.getTotalSalesByType(books,bookArraySize,"Λεξικό") + "€");
                            System.out.println("Η συνολική αξία των βιβλίων που έχουν πουληθέι αυτή την ημέρα: "+daySales.getTotalSales(books,bookArraySize)+"€");
                            break;

                        case 3:
                            int bestSellerIndex = daySales.getBestSellerIndex(books,bookArraySize);
                            if(bestSellerIndex == -1){
                                System.out.println("Δεν έχουν γίνει πωλήσεις");
                                break;
                            }
                            System.out.println("το βιβλίο που έχει πουλήσει τα περισσότερα αντίτυπα για αυτή την ημέρα: ");
                            System.out.println(books[bestSellerIndex].toString());
                            break;

                        case 4:
                            daySales.printSales(books,bookArraySize);
                            break;

                        case 5:
                            break;
                    }
                    break;

            }
        }while (choice != 4);
    }
}