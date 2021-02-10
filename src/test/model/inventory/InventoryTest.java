package model.inventory;

import model.account.Account;
import model.book.Book;
import model.book.Novel;
import model.book.Textbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    private Inventory inventory;
    private final Novel sampleNovel1 = new Novel("1","sample novel 1", 100, 10, "gg.jpg");

    @BeforeEach
    void initialize() {
        inventory = new Inventory();
    }

    @Test
    void testGetBook() {
        Book physics = new Textbook("1921", "Intro physics", 100,100, "physics.jpg");
        Book chem = new Textbook("2794", "Intro Chemistry", 50, 100, "chem.jpg");

        assertEquals(physics, inventory.getBook(0));
        assertEquals(chem, inventory.getBook(1));
    }

    @Test
    void testAddBook() {
        Book test = new Textbook("1321", "Intro test", 100,100, "test.jpg");
        inventory.add(test);
        assertEquals(test, inventory.getBook(inventory.getLength() - 1));
    }

    @Test
    void testGetLength() {
        Book test = new Textbook("1321", "Intro test", 100,100, "test.jpg");
        assertEquals(7, inventory.getLength());
        inventory.add(test);
        assertEquals(8, inventory.getLength());
    }

    @Test
    void testSetBooks() {
        Book physics = new Textbook("1921", "Intro physics", 100,100, "physics.jpg");
        Book chem = new Textbook("2294", "Intro Chemistry", 50, 100, "chem.jpg");
        Book test = new Textbook("1321", "Intro test", 100,100, "test.jpg");

        ArrayList<Book> testBooks = new ArrayList<>();
        testBooks.add(physics);
        testBooks.add(chem);
        testBooks.add(test);

        inventory.setBooks(testBooks);

        for (int i = 0; i < 3; i++) {
            assertEquals(testBooks.get(i), inventory.getBook(i));
        }
    }

    @Test
    void testToString() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Textbook("1921", "Intro physics", 100,100, "physics.jpg"));
        books.add(new Textbook("2794", "Intro Chemistry", 50, 100, "chem.jpg"));
        books.add(new Textbook("3424", "Intro biology", 75,0, "bio.jpg"));
        books.add(new Textbook("9999", "You Can't Afford This", 1000000,100, "broke.jpg"));
        books.add(new Novel("4567", "Great Gatsby", 20, 2, "gr.jpg"));
        books.add(new Novel("7896", "Fulton Street", 45, 5, "fut.jpg"));
        books.add(new Novel("0968", "1984", 55, 7, "1984.jpg"));

        String output = "";
        for (Book b : books) {
            output += b.toString() + "\n";
        }

        assertEquals(output, inventory.toString());
    }

}
