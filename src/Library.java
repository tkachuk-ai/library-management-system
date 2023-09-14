// The Library class implements the LibraryManagementSystem interface.
// This class represents a library and provides methods to manage the library's inventory
// of books and registered students.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Library implements LibraryManagementSystem {
    // The constructor of Library class accepts a string as its parameter, path to inventory.txt file.
    // When the object of Library is created, it parses the inventory.txt file and load the values into
    // corresponding attributes listed below
    public Library(String filePath) {
        students = new ArrayList<>();

        fictionBooks = new ArrayList<>();
        nonfictionBooks = new ArrayList<>();

        inventory = new HashMap<>();
        inventory(filePath);
    }

    // Library attributes:
    private List<Student> students;
    private List<FictionBook> fictionBooks;
    private List<NonfictionBook> nonfictionBooks;
    private HashMap<String, Integer> inventory;


    // availableBooks method calculates the number of fiction and non-fiction books currently available in the inventory.
    // It returns an integer array of size 2 where the first element would be the number of fiction books and second element
    // would be the number of non-fiction books.
    public int[] availableBooks() {
        // Initialize an array to keep count of available books for each
        int[] count = new int[]{0,0};
        // Loop through the list of fiction books and add the number of available copies to the count of available fiction books
        for (FictionBook book : fictionBooks) {
            count[0] = count[0] + inventory.get(book.getIsbn());
        }

        // Loop through the list of nonfiction books and add the number of available copies to the count of available nonfiction books
        for (NonfictionBook book : nonfictionBooks) {
            count[1] = inventory.get(book.getIsbn()) + count[1];
        }

        // Return the array with the counts of available books for each category
        return count;
    }


    // Getter methods
    public List<FictionBook> getFictionBooks() {
        return fictionBooks;
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public List<NonfictionBook> getNonfictionBooks() {
        return nonfictionBooks;
    }

    public List<Student> getStudents() {
        return students;
    }


    // inventory method is called once in the beginning to load data from inventory.txt file.
    // It reads through the file and store fiction books in fictionBooks list, non-fiction books in nonfictionBooks list
    // and the ISBNs and their respective quantities in inventory HashMap.
    // Throws an exception if the inventory sheet is not found in the file system or
    // when it is unable to read it.
    @Override
    public void inventory(String filePath) {
        try {
            // Create a File object with the given file path
            File inputFile = new File(filePath);

            // Create a Scanner object to read from the file
            Scanner in = new Scanner(inputFile);

            // Declare variables to store the book information
            String ISBN, Name, Author, Pages;

            // Use the delimiter to split the input into tokens separated by commas or newlines
            in.useDelimiter("[,\n]");

            while (in.hasNextLine()) {
                // Skip the first token in each line
                in.nextLine();

                ISBN = in.next();
                Name = in.next();
                Author = in.next();
                Pages = in.next();

                // Read the quantity of the book and add it to the inventory
                inventory.put(ISBN, Integer.parseInt(in.next()));

                // Check the book type and add it to the corresponding collection
                if (in.next().contains("nonfiction")) {
                    nonfictionBooks.add(new NonfictionBook(Author, ISBN, Name, Pages));
                }
                else {
                    fictionBooks.add(new FictionBook(Author, ISBN, Name, Pages));
                }
            } in.close();
        } catch (FileNotFoundException e) {
            // If the file is not found, print an error message and initialize the inventory
            System.out.println("NO SUCH FILE IN DATABASE '" + filePath + "'.");
            inventory = new HashMap<String, Integer>();
        }
    }


    // Lend method updates the quantity of the given book in the inventory sheet.
    // If the quantity is zero and the book cannot be lent or if the given book
    // is not available in the library, it throws an exception.
    @Override
    public void lend(String isbn) throws IllegalArgumentException {
        if (search(isbn) == null) {
            throw new IllegalArgumentException("The ISBN is not found in storage");
        }
        if (inventory.get(isbn) > 0) {
            inventory.put(isbn, inventory.get(isbn) - 1);
        }
    }

    // putBack method updates i.e., increment by 1, the quantity of the given book in the inventory sheet.
    // It throws an exception if the given book does not match the records in the inventory sheet.
    @Override
    public void putBack(String isbn) throws IllegalArgumentException {
        if (search(isbn) == null) {
            throw new IllegalArgumentException("The ISBN is not found in storage.");
        }
        inventory.put(isbn, inventory.get(isbn) + 1);
    }

    // This method is used to register a new student in the library management system
    // It takes a Student object as a parameter and adds it to the list of registered students
    // It checks whether the student is already registered or not
    // Throws an exception if the student is already registered and keeps the list of students updated
    @Override
    public void registerStudent(Student student) throws IllegalArgumentException {
        // Iterate through the list of existing students and check if the new student's name or registration number already exists
        for (Student existingStudent : students) {
            if (existingStudent.getName().equals(student.getName()) || existingStudent.getRegistrationNumber().equals(student.getRegistrationNumber())) {
                // If there is a match, throw an exception with an error message
                throw new IllegalArgumentException("Error: the registration number already exists in database.");
            }
        }
        // If there is no match, add the new student to the list of existing students.
        students.add(student);
    }


    // The search method performs a recursive binary search on the inventory to find a book
    // matching the given ISBN in both fiction and non-fiction books
    @Override
    public Book search(String isbn) {
        // Iterate through the fictionBooks collection and check if any book matches the given ISBN
        for (FictionBook book : fictionBooks) {
            if (book.getIsbn().equals(isbn)) {
                // If the match is found, return a book
                return book;
            }
        }
        for (NonfictionBook book : nonfictionBooks) {
            // Iterate through the nonfictionBooks collection and check if any book matches the given ISBN
            if (book.getIsbn().equals(isbn)) {
                // If the match is found, return a book
                return book;
            }
        }
        return null;
    }


    // Setter methods
    public void setFictionBooks(List<FictionBook> fictionBooks) {
        this.fictionBooks = fictionBooks;
    }

    public void setInventory(HashMap<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public void setNonfictionBooks(List<NonfictionBook> nonfictionBooks) {
        this.nonfictionBooks = nonfictionBooks;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }


    // The sort method sorts the books based on ISBN or quantity.
    @Override
    public List<Book> sort(int mode) {
        // Get a set of key-value pairs from the inventory
        Set<Map.Entry<String, Integer>> pairSet = inventory.entrySet();

        // Convert the set to a list of key-value pairs
        List<Map.Entry<String, Integer>> pairs = new ArrayList<>(pairSet);

        // Create a list to hold the first 10 books
        List<Book> sortedBooks = new ArrayList<>();

        // Sort the list of pairs based on the given mode
        if (mode == 1) {
            pairs.sort(new SortingBasedOnISBN());
        }
        else {
            pairs.sort(new SortingBasedOnQuantity());
        }

        // Iterate through the first 10 pairs and add the corresponding books to the firstTen list
        for (int i = 0; i < 10; i++) {
            sortedBooks.add(search(pairs.get(i).getKey()));
        }
        // Return the sortedBook list
        return sortedBooks;
    }
}
