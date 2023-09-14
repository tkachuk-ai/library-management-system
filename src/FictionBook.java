// The FictionBoo class extends the Book class and represents a fiction Book.
// It inherits all the instance variables and methods from the Book class and provides a constructor
// to initialize these variables
public class FictionBook extends Book {
    // FictionBook Object constructor:
    public FictionBook(String author, String isbn, String name, String numberOfPages) {
        this.author = author;
        this.isbn = isbn;
        this.name = name;
        this.numberOfPages = numberOfPages;
    }

    // The toString() method is overridden in the FictionBook class to provide a custom string representation of a fiction book.
    // It specifies details about the book including the type (which is Fiction in this class)
    @Override
    public String toString() {
        String out = isbn + " " + name + ", by " + author + ". " + numberOfPages + " pages. fiction.";
        return out;
    }
}
