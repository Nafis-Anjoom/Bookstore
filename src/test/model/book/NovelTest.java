package model.book;

import model.account.Account;
import model.inventory.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NovelTest {
    private Account testAccount;
    private Inventory inventory;
    private final Novel sampleNovel1 = new Novel("1","sample novel 1", 100, 10, "gg.g");
    private final Novel sampleNovel2 = new Novel("2","sample novel 2", 100, 100, 0, "gg.g");
    private final Novel sampleNovel3 = new Novel("1","sample novel 1", 100, 10, "gg.g");
    private final Novel sampleNovel4 = new Novel("6","sample novel 4", 1000, 10, 1, "gg.g");

    @BeforeEach
    void initialize() {
        testAccount = new Account("Jane Doe", 1000);
        inventory = new Inventory();
        inventory.add(sampleNovel1);
        inventory.add(sampleNovel2);
        inventory.add(sampleNovel3);
    }


    @Test
    void testToString() {
        assertEquals("Title: " + sampleNovel1.getName() + " \tId: " + sampleNovel1.getId() + " \tCategory: " +
                sampleNovel1.getCategory() + " \tQuantity available: "
                + sampleNovel1.getQuantity() + " \tPrice: $" + sampleNovel1.getPrice() + " \t"
                + "Rent cost: $" + sampleNovel1.getRentCost() + " \t", sampleNovel1.toString());
    }

    @Test
    void testRent() {
        assertEquals(100,sampleNovel1.getQuantity());
        sampleNovel1.rent();
        assertEquals(99,sampleNovel1.getQuantity());
    }

    @Test
    void returnBook() {
        assertEquals(100,sampleNovel1.getQuantity());
        sampleNovel1.returnBook();
        assertEquals(101,sampleNovel1.getQuantity());
    }
}
