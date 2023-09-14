// The LibraryManagementSystem is an interface that represents the contract
// for a library management system. This interface defines methods that a
// library management system should implement

import java.util.List;

public interface LibraryManagementSystem {
    // Takes the ISBN (International Standard Book Number) of a book.
    // Updates the inventory to reflect the fact that a book has been lent to a student
    void lend(String isbn);


    // This method takes the ISBN (International Standard Book Number) of a book and is used
    // to return a book to the library. Updates the inventory to reflect the fact that the book
    // has been returned
    void putBack(String isbn);


    // This method takes the name of a file as a parameter and is used to read and load the inventory
    // of books from that file. The file which will be given as part of the project contains
    // an inventory sheet about all the books in the library
    void inventory(String filePath);


    // This method takes a student object as a parameter and is used to register
    // a new student in the library management system. When this method is called,
    // it adds the student to the list of registered students.
    void registerStudent(Student student);


    // This method returns a Book object.
    // This search runs on both the fictionBook and nonfictionBook lists.
    Book search(String isbn);


    // The sort method runs on the HashMap that will have ISBN as the key and quantity as the value.
    // Sort it based on either ISBN or quantity.
    List<Book> sort(int mode);
}
