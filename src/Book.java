// The Book class is an abstract class that defines the basic properties of a book.
// Serves as a base class for the two types of books in library management system
public abstract class Book {
    // Book attributes:
    public String author; // Book's author
    public String isbn; // Book's ISBN
    public String name; // Book's name
    public String numberOfPages; // Book's number of pages

    // Book constructor:
    public Book() {}

    // Getter and setter methods
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }


    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getPages() {
        return numberOfPages;
    }
    public void setPages(String numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    // The toString() method is declared as abstract and implemented in Book subclasses
    public abstract String toString();
}
