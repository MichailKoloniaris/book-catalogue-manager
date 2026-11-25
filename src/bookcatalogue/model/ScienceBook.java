package bookcatalogue.model;

/** A scientific book, classified by scientific area. */
public class ScienceBook extends Book {

    private String scientificArea;

    public ScienceBook(String title, String author, String isbn, String publisher, int pages,
                       short year, double price, String scientificArea, int numberOfCopies) {
        super(title, author, isbn, publisher, pages, year, price, numberOfCopies);
        setScientificArea(scientificArea);
    }

    public String getScientificArea() {
        return scientificArea;
    }

    public void setScientificArea(String scientificArea) {
        if (scientificArea == null || scientificArea.isBlank()) {
            throw new IllegalArgumentException("Η επιστημονική περιοχή δεν μπορεί να είναι κενή.");
        }
        this.scientificArea = scientificArea.trim();
    }

    @Override
    public BookType getType() {
        return BookType.SCIENCE;
    }

    @Override
    protected String extraDetails() {
        return "Περιοχή: " + scientificArea;
    }
}
