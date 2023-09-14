// The student class represents a student who can search for books, sort books, borrow from and return
// books to the library.
public class Student {
    // Required attributes for Student Object:
    private String name;
    private String registrationNumber;

    // Object Constructor:
    public Student(String name, String registrationNumber) {
        this.name = name;
        this.registrationNumber = registrationNumber;
    }

    // Getter and setter methods:
    public String getName() {
        return name;
    }
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
