package model.book;

import model.account.Account;
import model.inventory.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    private Account testAccount;
    private Inventory inventory;
    private final Novel sampleNovel1 = new Novel("1","sample novel 1", 100, 10, "gg.g");
    private final Novel sampleNovel2 = new Novel("2","sample novel 2", 100, 100, 0, "gg.g");
    private final Novel sampleNovel3 = new Novel("1","sample novel 1", 100, 10, "gg.g");
    private final Novel sampleNovel4 = new Novel("2","sample novel 1", 100, 10, "gg.g");
    private final Novel sampleNovel5 = new Novel("1","sample novel 5", 100, 10, "gg.g");
    private final Textbook sampleTBook1 = new Textbook("3", "sample textbook 1", 200,10, "gg.g");
    private final Textbook sampleTBook2 = new Textbook("4", "sample textbook 1", 200000,0, "gg.g");


    @BeforeEach
    void initialize() {
        testAccount = new Account("Jane Doe",1000);
        inventory = new Inventory();
        inventory.add(sampleNovel1);
        inventory.add(sampleNovel2);
        inventory.add(sampleNovel3);
        inventory.add(sampleTBook1);
        inventory.add(sampleTBook2);
    }

    @Test
    void testEquals() {
        assertTrue(sampleNovel1.equals(sampleNovel3));
        assertFalse(sampleNovel1.equals(sampleTBook1));
        assertFalse(sampleNovel1.equals(sampleNovel4));
        assertFalse(sampleNovel1.equals(sampleNovel5));
        assertFalse(sampleNovel1.equals(null));
    }

    @Test
    void testGetImage() {
        assertEquals("gg.g", sampleNovel1.getImage());
    }

    @Test
    void testPurchase() {
       assertEquals(100,sampleNovel3.getQuantity());
       sampleNovel3.purchase();
        assertEquals(99,sampleNovel3.getQuantity());
    }

    @Test
    void testToString() {
        assertEquals("Title: " + sampleTBook1.getName() + " \tId: " + sampleTBook1.getId() + " \tCategory: " +
                sampleTBook1.getCategory() + " \tQuantity available: "
                + sampleTBook1.getQuantity() + " \tPrice: $" + sampleTBook1.getPrice() + " \t", sampleTBook1.toString());
    }

    @Test
    void testIsPurchasable() {
        assertTrue(sampleNovel1.isPurchasable());
    }

    @Test
    void testIsRentable() {
        assertTrue(sampleNovel1.isRentable());
    }

    //Unable to test properly due to unknown implementation. Dummy test.
    @Test
    void testHashCode() {
        assertEquals(sampleNovel1.hashCode(), sampleNovel1.hashCode());
    }
}
