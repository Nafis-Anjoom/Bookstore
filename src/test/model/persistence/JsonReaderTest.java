package model.persistence;

import model.account.Account;
import model.book.Book;
import model.book.Textbook;
import model.inventory.Inventory;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {


    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json", "./data/noSuchFile2.json");
        try {
            Account sampleAccount = reader.readAccount();
            Inventory inventory = reader.readInventory();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmpty() {
        try {
            JsonReader reader = new JsonReader("./data/testWriterEmptyAccount.json", "./data/testWriterEmptyInventory.json");
            Account sampleAccount = reader.readAccount();
            Inventory sampleInventory = reader.readInventory();
            assertEquals("Sample", sampleAccount.getName());
            assertEquals(7, sampleInventory.getLength());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneral() {
        try {
            JsonReader reader = new JsonReader("./data/testWriterGeneralAccount.json", "./data/testWriterGeneralInventory.json");
            Account sampleAccount = reader.readAccount();
            Inventory sampleInventory = reader.readInventory();
            Textbook sampleTBook = new Textbook("3", "sample textbook 1", 200,10, "gg.jpg");

            Book physics = sampleInventory.getBook(0);
            Book chem = sampleInventory.getBook(1);
            Book novel = sampleInventory.getBook(6);



            assertEquals("Sample", sampleAccount.getName());
            assertEquals(1000 - 7 - 100, sampleAccount.getFunds());
            assertEquals(novel, sampleAccount.getBookFromRentedList(0));
            assertEquals(chem, sampleAccount.getBookFromWishlist(0));

            assertEquals(physics, sampleInventory.getBook(0));
            assertEquals(chem, sampleInventory.getBook(1));
            assertEquals(novel, sampleInventory.getBook(6));
            assertEquals(sampleTBook, sampleInventory.getBook(7));
        } catch (IOException e) {
            fail("No exception expected");
        }
    }
}
