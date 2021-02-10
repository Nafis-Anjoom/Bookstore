package model.persistence;

import exception.UnsuccessfulTransactionException;
import model.account.Account;
import model.book.Book;
import model.book.Textbook;
import model.inventory.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    private Account sampleAccount;
    private Inventory sampleInventory;
    private final Textbook sampleTBook = new Textbook("3", "sample textbook 1", 200,10, "gg.jpg");

    @BeforeEach
    void initialize() {
        sampleAccount = new Account("Sample", 1000);
        sampleInventory = new Inventory();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json", "./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterGeneral() {
        try {
            Book physics = sampleInventory.getBook(0);
            Book chem = sampleInventory.getBook(1);
            Book novel = sampleInventory.getBook(6);

            try {
                sampleAccount.rentBook(sampleInventory.getBook(6));
                sampleAccount.purchaseBook(sampleInventory.getBook(0));
            } catch (UnsuccessfulTransactionException e) {
                e.toString();
            }
            sampleAccount.addToWishlist(sampleInventory.getBook(1));
//            sampleAccount.purchaseBook(sampleInventory.getBook(0));
            sampleInventory.add(sampleTBook);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccount.json", "./data/testWriterGeneralInventory.json");
            writer.open();
            writer.writeAccount(sampleAccount);
            writer.writeInventory(sampleInventory);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccount.json", "./data/testWriterGeneralInventory.json");
            sampleAccount = reader.readAccount();
            sampleInventory =  reader.readInventory();

            assertEquals("Sample", sampleAccount.getName());
            assertEquals(1000 - 7 - 100, sampleAccount.getFunds());
            assertEquals(novel, sampleAccount.getBookFromRentedList(0));
            assertEquals(chem, sampleAccount.getBookFromWishlist(0));

            assertEquals(physics, sampleInventory.getBook(0));
            assertEquals(chem, sampleInventory.getBook(1));
            assertEquals(novel, sampleInventory.getBook(6));
            assertEquals(sampleTBook, sampleInventory.getBook(7));
        } catch (IOException e) {
            System.out.println("No exception expected.");
        }
    }

    @Test
    void testWriterEmpty() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccount.json", "./data/testWriterEmptyInventory.json");
            writer.open();
            writer.writeAccount(sampleAccount);
            writer.writeInventory(sampleInventory);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccount.json", "./data/testWriterEmptyInventory.json");
            sampleAccount = reader.readAccount();
            sampleInventory = reader.readInventory();

            assertEquals("Name: Sample\nFunds Remaining: $1000.0\n\n" +
                    "WishList:\n___________________\n\n\n" +
                    "Purchase history:\n___________________\n\n\n" +
                    "Borrowed books:\n___________________\n\n\n", sampleAccount.toString());
            assertEquals(7, sampleInventory.getLength());
        } catch (IOException e) {
            fail("No exception expected");
        }
    }
}
