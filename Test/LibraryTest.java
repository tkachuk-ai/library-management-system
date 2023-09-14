import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    private final String TEST_DATA = "src/test_inventory.txt";
    @Test
    void availableBooks() {
        Library library = new Library(TEST_DATA);
        assertArrayEquals(new int[]{7, 3}, library.availableBooks());
        assertDoesNotThrow( () -> {
            library.inventory("invalid_file.txt");
        });
    }

    @Test
    void sort() {
        Library library = new Library(TEST_DATA);
        assertEquals("90560 Becoming, by Michelle Obama. 448 pages. non-fiction.", library.sort(2).get(0).toString());
        assertEquals("67920 Animal Farm, by George Orwell. 141 pages. fiction.", library.sort(2).get(9).toString());
        assertEquals("38112 A Time for Mercy, by John Grisham. 464 pages. fiction.", library.sort(1).get(0).toString());
        assertEquals("38112 A Time for Mercy, by John Grisham. 464 pages. fiction.", library.sort(1).get(0).toString());
        assertEquals("90560 Becoming, by Michelle Obama. 448 pages. non-fiction.", library.sort(1).get(9).toString());

    }
    @Test
    void inventory() {
        Library library = new Library(TEST_DATA);
        HashMap<String, Integer> out = new HashMap<>();
        out.put("90560", 8);
        out.put("45623", 20);
        out.put("78350", 21);
        out.put("82749", 13);
        out.put("82419", 28);
        out.put("67920", 29);
        out.put("87562", 19);
        out.put("81759", 18);
        out.put("38112", 15);
        out.put("39470", 26);
        assertEquals(out, library.getInventory());
    }

    @Test
    void putBack() {
        Library library = new Library(TEST_DATA);
        HashMap<String, Integer> out = new HashMap<>();
        out.put("87562", 19);
        out.put("82749", 13);
        out.put("81759", 18);
        out.put("38112", 15);
        out.put("82419", 28);
        out.put("45623", 20);
        out.put("67920", 29);
        out.put("78350", 21);
        out.put("90560", 9);
        out.put("39470", 26);

        library.putBack("90560");
        assertEquals(out, library.getInventory());
        assertThrows(IllegalArgumentException.class, () -> {
            library.putBack("wrongISBN");
        });
    }

    @Test
    void lend() {
        Library library = new Library(TEST_DATA);
        HashMap<String, Integer> out = new HashMap<>();
        out.put("78350", 21);
        out.put("82749", 13);
        out.put("81759", 18);
        out.put("38112", 15);
        out.put("67920", 29);
        out.put("90560", 7);
        out.put("87562", 19);
        out.put("82419", 28);
        out.put("39470", 26);
        out.put("45623", 20);
        library.lend("90560");
        assertEquals(out, library.getInventory());
        assertThrows(IllegalArgumentException.class, () -> {
            library.lend("wrongISBN");
        });
    }


    @Test
    void search() {
        Library library = new Library(TEST_DATA);
        assertEquals(library.getFictionBooks().get(5), library.search("82419"));
        assertEquals(library.getFictionBooks().get(3), library.search("67920"));
        assertEquals(library.getFictionBooks().get(1), library.search("81759"));
        assertEquals(library.getFictionBooks().get(2), library.search("38112"));
        assertEquals(library.getFictionBooks().get(4), library.search("87562"));
        assertEquals(library.getNonfictionBooks().get(0), library.search("82749"));
        assertNull(library.search("Invalid ISBN"));
    }

    @Test
    void registerStudent() {
        Library library = new Library(TEST_DATA);
        library.registerStudent(new Student("Bogdan", "54c5438r3"));

        assertEquals("Bogdan", library.getStudents().get(0).getName());
        assertEquals("54c5438r3", library.getStudents().get(0).getRegistrationNumber());

        library.registerStudent(new Student("Sasha", "54d5438r3"));

        assertEquals("Sasha", library.getStudents().get(1).getName());
        assertEquals("54d5438r3", library.getStudents().get(1).getRegistrationNumber());


        assertThrows(IllegalArgumentException.class, () -> {
            library.registerStudent(new Student("Bogdan2", "54c5438r3"));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            library.registerStudent(new Student("Bogdan", "54c54348r3"));
        });
    }
}