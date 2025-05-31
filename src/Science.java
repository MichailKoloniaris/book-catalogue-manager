public class Science extends Book implements ProsPolisi{

    private int numberOfCopies;
    protected String scientificArea;
    public Science(String title, String author, String ISBN,String publisher ,int pages, short year, double price ,String scientificArea, int numberOfCopies) {
        super(title, author, ISBN, publisher, pages, year, price, "Επιστημονικό");
        this.scientificArea = scientificArea;
        setNumberOfCopies(numberOfCopies);
    }

    @Override
    public String toString() {
        return "Science{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", publisher='" + publisher + '\'' +
                ", pages=" + pages +
                ", year=" + year +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", scientificArea='" + scientificArea + '\'' +
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
        System.out.println("Science Book: " + title + " (" + numberOfCopies + " copies available)");
    }


}
