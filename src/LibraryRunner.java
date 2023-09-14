import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;
import java.io.PrintWriter;

public class LibraryRunner {
    static HashMap<String, List<String>> borrowedBooks; //I gave up on keeping this non-static
    private static Library library;

    public LibraryRunner() {
    }

    /**
     The main method of the library management system. It initializes various variables and objects,
     reads data from files, and provides a menu of actions for the user to choose from using a while loop.
     The loop continues until the user selects the "Exit" option.
     The method calls different methods depending on the user's chosen action.
     */
    public static void main(String[] args) {
        // read borrowed books and initialize library object
        borrowedBooks = readFile("src/borrowed_books.txt");
        library = new Library("src/inventory.txt");

        // initialize variables and scanner object
        boolean iterate = true;
        Scanner in = new Scanner(System.in);
        String opt;

        // read student data from file and create Student objects
        List<Map.Entry<String, List<String>>> studentMap = readFile("src/student_database.txt").entrySet().stream().toList();
        List<Student> students = new ArrayList<>();

        for (Map.Entry<String, List<String>> student : studentMap) {
            students.add(new Student(student.getKey(), student.getValue().get(0)));
        }
        library.setStudents(students);

        // provide a menu of actions for the user to choose from using a while loop
        while (iterate) {
            System.out.println("lease select an action by typing the number associated with it: ");
            System.out.println("1 - Register");
            System.out.println("2 - Sort Books");
            System.out.println("3 - Search Books");
            System.out.println("4 - Borrow Book");
            System.out.println("5 - Return Book");
            System.out.println("6 - Show Inventory Statistics");
            System.out.println("0 - Exit");
            // get user input and call appropriate method based on input
            opt = in.nextLine();
            if (opt.equals("1")){
                register();
            }
            else if(opt.equals("2")){
                sortBooks();
            }
            else if(opt.equals("3")){
                searchBooks();
            }
            else if(opt.equals("4")){
                borrowBook();
            }
            else if(opt.equals("5")){
                returnBook();
            }
            else if(opt.equals("6")){
                int[] stats = library.availableBooks();
                InventoryChart chart = new InventoryChart("Library Inventory", stats); // displays the distribution
                chart.displayGraph();
            }
            else if(opt.equals("0")){
                iterate = false; // exit while loop
            }
            else {
                System.out.println("INVALID OPTION, TRY AGAIN!"); // prompt user to try again if input is invalid
            }

        }
    }

    /**
     *
     This method allows a student to return a book to the library. The method prompts the student to
     enter the ISBN of the book they want to return.
     If the ISBN is invalid or the book is not found in the library,
     the method prints an error message.
     If the book is successfully returned, the method updates the borrowedBooks HashMap
     and writes the changes to the borrowed_books.txt file.
     */
    private static void returnBook() {
        String isbn;
        Book book;
        Scanner in = new Scanner(System.in);

        String registrationNumber = getRegistrationNumber();
        if (registrationNumber == null) { // If there is no registration number, return from method
            return;
        }
        while (true) {
            System.out.println("Enter the ISBN number of the book || Q to quit.");
            isbn = in.nextLine();
            if (isbn.toUpperCase().equals("Q")) { // If student enters "q" or "Q", return from method
                return;
            }
            book = library.search(isbn); // Search the book by ISBN
            if (book == null) { // If book is not found, print error message
                System.out.println("ISBN is invalid || Book is not found in library system.");
            }
            else {
                library.putBack(isbn); // Update library inventory
                borrowedBooks.get(registrationNumber).remove(isbn); // Remove book from borrowedBooks HashMap
                writeFile("src/borrowed_books.txt"); // Write changes to file
            }
        }
    }

    /** This method prompts the user to enter their registration number and returns the registration number if it is valid,
     *  or null if the user chooses to quit or enters an invalid registration number.
     * @return registration number
     */
    private static String getRegistrationNumber() {
        String regNumber;
        Scanner in = new Scanner(System.in);
        // get a list of students from the library
        List<Student> students = library.getStudents();
        while (true) {
            System.out.println("Please enter your registration number || Q to quit.");
            regNumber = in.nextLine();
            if (regNumber.toUpperCase().equals("Q")) { // If student enters "q" or "Q", return from method
                return null;
            }
            // loop through each student in the list of students
            for (Student student : students) {
                if (student.getRegistrationNumber().equals(regNumber)) { // check if the student's registration number matches the input
                    return regNumber; // return the registration number if it is valid
                }
            }
            System.out.println("Invalid registration number."); // print an error message if the registration number is invalid
        }
    }

    /**
     * The borrowBook() method allows a user to borrow a book from the library.
     * It prompts the user for their registration number and the book's ISBN,
     * and then checks if the book is available. If the book is available,
     * it is lent to the user and the borrowing is recorded in a file.
     * If the book is not available or the user cancels the operation, the method returns.
     */

    private static void borrowBook() {
        String ISBN;  // declare a variable to hold the ISBN
        Book book;  // declare a variable to hold the book object
        Scanner in = new Scanner(System.in);  // create a scanner to read user input
        String regNumber = getRegistrationNumber();  // call a method to get the user's registration number
        if (regNumber == null) {  // if the user didn't provide a registration number, return
            return;
        }
        while (true) {  // loop until the user quits or successfully borrows a book
            System.out.println("Enter the book's ISBN number || Q to quit.");  // prompt the user for the book's ISBN
            ISBN = in.nextLine();  // read the user's input
            if (ISBN.toUpperCase().equals("Q")){  // if the user enters Q, return
                return;
            }
            book = library.search(ISBN);  // search for the book in the library
            if (book == null) {  // if the book is not found, print an error message
                System.out.println("ISBN is invalid || Book is not found in library system.");
            }
            else {  // if the book is found, lend it to the user and record the borrowing in a file
                library.lend(ISBN);
                if (!borrowedBooks.containsKey(regNumber)) {
                    borrowedBooks.put(regNumber, new ArrayList<>());
                }
                borrowedBooks.get(regNumber).add(ISBN);
                writeFile("src/borrowed_books.txt");
                return;
            }
        }
    }


    /** This method searches for books in a library system based on the ISBN  entered by the user.
     */
    private static void searchBooks() {
        // Declare local variables
        String number; // The ISBN  entered by the user or Q option
        Scanner in = new Scanner(System.in);
        Book book; // A Book object for storing the book found in the library system
        boolean condition = true; // A boolean flag for controlling the while loop iteration

        // Loop until the user enters "Q" to quit
        while (condition) {
            System.out.println("Enter ISBN of the book || Enter Q to quit."); // Prompt the user for input
            number = in.nextLine(); // Read the user's input

            // If the user entered "Q", return from the method
            if (number.toUpperCase().equals("Q")) {
                return;
            }
            // Otherwise, search for the book in the library system
            book = library.search(number); // Call the "search" method of the "library" object

            // If the book is found, print its details to the console
            if (book != null) {
                System.out.println(book);
            }
            // Otherwise, print an error message
            else {
                System.out.println("ISBN is invalid || Book is not found in library system.");
            }
        }
    }


    /** This method allows the student to sort books either by ISBN or by quantity
     */
    private static void sortBooks() {
        // initialize variables and scanner object
        boolean iterate = true; // A boolean flag for controlling the while loop iteration
        String opt; // stores user's option
        Scanner in = new Scanner(System.in);

        List<Book> firstTen;
        while (iterate) {
            // prompt user for their preferred sorting method
            System.out.println("\nHow would you like to sort the books?");
            System.out.println("1 - By ISBN number");
            System.out.println("2 - By quantity held by the library\n");

            // get user input and check for validity
            opt = in.next(); // Reads user's input
                if(opt.equals("1")){
                    // sort books by ISBN and print out first ten
                    firstTen = library.sort(1);
                    System.out.println("Sorted TOP 10 books by ISBN number:");
                    for (Book book : firstTen) {
                        System.out.println(book.toString());
                    }
                    iterate = false;
                }
                else if(opt.equals("2")){
                    // sort books by quantity held and print out first ten
                    firstTen = library.sort(2);
                    System.out.println("Sorted TOP 10 books by quantity held:");

                    for (Book book : firstTen) {
                        System.out.println(book.toString());
                    }
                    iterate = false;
                }
                // user input is invalid, prompt them to try again
                else{
                    System.out.println("ERROR: Invalid option, try again!");
                }
        }
    }

    /** This function registers a new student to the library system
     */
    private static void register() {
        // Initialize variables
        boolean iterate = true; // Controls the main loop
        boolean NameC = true; // Controls the name check loop

        Scanner in = new Scanner(System.in); // Initialize scanner to take input
        String name, input;

        // Main loop to get student information and add to the library
        while (iterate) {
            System.out.println("Enter your name: ");
            name = in.nextLine();

            // Check if student already exists in the library
            for (Student student : library.getStudents()) {
                if (student.getName().equals(name)) {
                    System.out.println("Student already exists.");
                    NameC = false;
                }
            }

            // Loop to confirm name spelling
            while (NameC) {
                System.out.println("Please make sure that " + name + " is your name spelled correctly.");
                System.out.println("Yes/No");

                input = in.nextLine();

                // If name is spelled correctly, add student to library and save to file
                if (input.equalsIgnoreCase("Yes") || input.equalsIgnoreCase("y")) {
                    // Try to add student to the library
                    do {
                        try {
                            library.registerStudent(new Student(name, UUID.randomUUID().toString()));
                            break;
                        } catch (IllegalArgumentException e) {
                        }
                    } while (true);

                    // Save updated student list to file
                    try {
                        PrintWriter out = new PrintWriter("src/student_database.txt");
                        for (Student student : library.getStudents()) {
                            out.println(student.getName() + "," + student.getRegistrationNumber());
                        }
                        out.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("NO SUCH FILE IN DATABASE 'student_database.txt'.");
                    }

                    // Print confirmation message with registration number and exit loops
                    System.out.println("Welcome to the library, " + name + "!");
                    System.out.println("Your new registration number: " + library.getStudents().get(library.getStudents().size() - 1).getRegistrationNumber() + ".");

                    iterate = false;
                    NameC = false;
                }
                // If name is not spelled correctly, ask for name again
                else if (input.equalsIgnoreCase("No") || input.equalsIgnoreCase("n")) {
                    NameC = false;
                }
                // If input is invalid, ask for input again
                else {
                    System.out.println("Invalid option, try again.");
                }
            }
            // Reset name check variable
            NameC = true;
        }
    }

        /** The readFile() method reads a file containing information about borrowed books,
         * and returns a hash map where each key is a user ID and the value is a list of ISBNs
         * for the books they have borrowed.
         */
    private static HashMap<String, List<String>> readFile(String file) {
        try {
            // create a new file object based on the file name
            File inputFile = new File(file);
            // create a new scanner to read from the file
            Scanner in = new Scanner(inputFile);
            // create a new hash map to hold the contents of the file
            HashMap<String, List<String>> returnMap = new HashMap<>();
            // declare a variable to hold the current ID
            String currentID;
            // set the scanner's delimiter to ',' or '\n'
            in.useDelimiter("[,\n]");
            // loop through each line of the file
            while (in.hasNextLine()) {
                // if the scanner has a next token, read it into currentID
                if (in.hasNext()) {
                    currentID = in.next();
                }
                // otherwise, skip to the next line
                else {
                    in.nextLine();
                    continue;
                }
                // if the hash map doesn't already contain the current ID, add it
                if (!returnMap.containsKey(currentID)) {
                    returnMap.put(currentID, new ArrayList<>());
                }
                // add the next token (the book's ISBN) to the list of borrowed books for the current ID
                returnMap.get(currentID).add(in.next());
            }
            // close the scanner
            in.close();
            // return the hash map
            return returnMap;
        } catch (FileNotFoundException e) {
            // if the file is not found, print an error message and return an empty hash map
            System.out.println("FAILED TO FIND FILE '" + file + "'.");
            return new HashMap<>();
        }
    }


    /**
     This method writes the data in borrowedBooks HashMap to a file.
     The method takes the filename as a parameter and writes the data to that file.
     If the file is not found, the method prints an error message.
     @param file the filename to write the data to
     */
    private static void writeFile(String file) {
        try {
            PrintWriter out = new PrintWriter(file);
            Set<String> keys = borrowedBooks.keySet();
            for (String key : keys) {
                for (String book : borrowedBooks.get(key)) {
                    out.println(key + "," + book);
                }
            }
            out.close();
        } catch (FileNotFoundException e) {
            // print error message if file not found
            System.out.println("NO SUCH FILE IN DATABASE '" + file + "'.");
        }
    }
}