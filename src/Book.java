public class Book{
    protected String title;
    protected String author;
    protected String ISBN;
    protected String publisher;
    protected int pages;
    protected short year;
    protected double price;
    protected String type;

    public Book(String title, String author, String ISBN,String publisher ,int pages, short year, double price, String type) {
        if (year < 1500 || year > 2200)
            throw new IllegalArgumentException("Ο χρονος πρεπει να ειναι 1500 με 2200.");
        if (price < 0 || price > 1000)
            throw new IllegalArgumentException("Τιμή βιβλείου πρεπει να ειναι απο 0 μεχρι 1000.");
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.publisher = publisher;
        this.pages = pages;
        this.year = year;
        this.price = price;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", publisher='" + publisher + '\'' +
                ", pages=" + pages +
                ", year=" + year +
                ", price=" + price +
                ", type='" + type + '\'' +
                '}';
    }
}
