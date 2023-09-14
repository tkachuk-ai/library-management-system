// The NonfictionBoo class extends the Book class and represents a non-fiction Book.
// It inherits all the instance variables and methods from the Book class and provides a constructor
// to initialize these variables
public class NonfictionBook extends Book {
    // NonfictionBook Object constructor:
    public NonfictionBook(String author, String isbn, String name, String numberOfPages) {
        this.author = author;
        this.isbn = isbn;
        this.name = name;
        this.numberOfPages = numberOfPages;
    }

    // The toString() method is overridden in the NonfictionBook class to provide a custom string
    // representation of a non-fiction book.
    // It specifies details about the book including the type (which is Non-fiction in this class)
    @Override
    public String toString() {
        String out = isbn + " " + name + ", by " + author + ". " + numberOfPages + " pages. non-fiction.";
        return out;
    }
}
