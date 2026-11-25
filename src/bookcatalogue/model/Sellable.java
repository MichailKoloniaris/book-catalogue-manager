package bookcatalogue.model;

/** Anything that is kept in stock and can be sold by the copy. */
public interface Sellable {
    int getNumberOfCopies();

    void setNumberOfCopies(int copies);
}
