public class DaySales {
    private int[] sales = new int[10];

    public void addSale(String ISBN,Book[] books,int bookArraySize,int quantity,int copies) {
        if (quantity < 1){
            System.out.println("Η ποσότητα πρέπει να είναι >= 1");
            return;
        }
        if (copies > quantity){
            throw new IllegalArgumentException("Το πλήθος αντιτύπων που πωλείται δεν μπορεί να είναι μεγαλύτερο από το πλήθος των διαθέσιμων.");
        }

        for (int i = 0; i < bookArraySize; i++) {
            if (books[i].getISBN().equals(ISBN)) {
                sales[i] += quantity;
            }
        }
    }

    public double getTotalSalesByType(Book[] books,int bookArraySize,String type) {
        double total = 0.0;
        for (int i = 0; i < bookArraySize; i++) {
            if (books[i].getType().equals(type)) {
                total += books[i].getPrice() * sales[i];
            }
        }
        return total;
    }

    public double getTotalSales(Book[] books,int bookArraySize) {
        double total = 0.0;
        for (int i = 0; i < bookArraySize; i++) {
            total += books[i].getPrice() * sales[i];
        }
        return total;
    }

    public int getBestSellerIndex(Book[] books,int bookArraySize) {
        int maxIndex = -1;
        int maxSales = 0;
        for (int i = 0; i < bookArraySize; i++) {
            if (sales[i] > maxSales) {
                maxSales = sales[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public void printSales(Book[] books,int bookArraySize) {
        boolean foundSale = false;
        System.out.println("ISBN            Είδος Βιβλίου      Πλήθος Αντιτύπων");
        System.out.println("----------------------------------------------------");
        for (int i = 0; i < bookArraySize; i++) {
            if (sales[i] > 0) {
                System.out.printf("%-15s %-18s %d\n", books[i].getISBN(),books[i].getType(),sales[i]);
                foundSale = true;
            }
        }
        if (!foundSale) {
            System.out.println("Δεν έχουν γίνει πωλήσεις");
        }
    }
}
