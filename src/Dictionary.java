public class Dictionary extends Book implements ProsPolisi {
    private int numberOfCopies;
    protected int sizeLimmaton;

    public Dictionary(String title, String author, String ISBN, String publisher , int pages, short year, double price, int sizeLimmaton, int numberOfCopies) {
        super(title, author, ISBN, publisher, pages, year, price,"Λεξικό");
        if (sizeLimmaton < 1000 || sizeLimmaton > 100000)
            throw new IllegalArgumentException("Αποδεκτές τιμές για το πλήθος λημμάτων ενός λεξικού από 1000 έως 100000");
        this.sizeLimmaton = sizeLimmaton;
        setNumberOfCopies(numberOfCopies);



    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", publisher='" + publisher + '\'' +
                ", pages=" + pages +
                ", year=" + year +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", sizeLimmaton='" + sizeLimmaton + '\'' +
                '}';
    }

    @Override
    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    @Override
    public void setNumberOfCopies(int copies) {
        if (copies < 0) throw new IllegalArgumentException("Copies cannot be negative.");
        this.numberOfCopies = copies;
    }

    public void displayInfo() {
        System.out.println("Dictionary: " + title + " with " + sizeLimmaton + " entries (" + numberOfCopies + " copies available)");
    }

}
