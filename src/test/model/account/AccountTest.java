package model.account;

import exception.UnsuccessfulTransactionException;
import model.book.Novel;
import model.book.Textbook;
import model.inventory.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    private Account testAccount;
    private Inventory inventory;
    private final Novel sampleNovel1 = new Novel("1","sample novel 1", 100, 10, "gg.g");
    private final Novel sampleNovel2 = new Novel("2","sample novel 2", 100, 100, 0, "gg.g");
    private final Novel sampleNovel3 = new Novel("5","sample novel 3", 1000, 100000, 0, "gg.g");
    private final Novel sampleNovel4 = new Novel("6","sample novel 4", 1000, 10, 1, "gg.g");
    private final Textbook sampleTBook1 = new Textbook("3", "sample textbook 1", 200,10, "gg.g");
    private final Textbook sampleTBook2 = new Textbook("4", "sample textbook 1", 200000,0, "gg.g");


    @BeforeEach
    void initialize() {
        testAccount = new Account("Jane Doe", 1000);
        inventory = new Inventory();
        inventory.add(sampleNovel1);
        inventory.add(sampleNovel2);
        inventory.add(sampleTBook1);
        inventory.add(sampleTBook2);

    }

    @Test
    void testRentBookNoException() {
        try {
            testAccount.rentBook(sampleNovel1);
        } catch (UnsuccessfulTransactionException e) {
            fail();
        }
    }

    @Test
    void testRentBookExceptionOutOfStock() {
        try {
            testAccount.rentBook(sampleNovel2);
            fail();
        } catch (UnsuccessfulTransactionException e) {
            //pass
        }
    }

    @Test
    void testRentBookNoExceptionAlreadyHaveIt() {
        try {
            testAccount.rentBook(sampleNovel1);
            testAccount.rentBook(sampleNovel1);
            fail();
        } catch (UnsuccessfulTransactionException e) {
            //pass
        }
    }

    @Test
    void testRentBookNoExceptionNoFund() {
        try {
            testAccount.rentBook(sampleNovel3);
            fail();
        } catch (UnsuccessfulTransactionException e) {
            //pass
        }
    }


    @Test
    void testPurchaseNoException() {
        try {
            testAccount.purchaseBook(sampleTBook1);
        } catch (UnsuccessfulTransactionException e) {
            fail();
        }

    }

    @Test
    void testPurchaseExceptionOutOfStock() {
        try {
            testAccount.purchaseBook(sampleNovel2);
            fail();
        } catch (UnsuccessfulTransactionException e) {
            //pass
        }
    }

    @Test
    void testPurchaseExceptionNoFund() {
        try {
            testAccount.purchaseBook(sampleTBook2);
            fail();
        } catch (UnsuccessfulTransactionException e) {
            //pass
        }
    }

    @Test
    void testAddToWishList() {
        assertTrue(testAccount.addToWishlist(sampleNovel1));
        assertEquals(1,testAccount.getWishlistBooks().size());
        assertFalse(testAccount.addToWishlist(sampleNovel1));

    }

    @Test
    void testRemoveFromWishList() {
        assertFalse(testAccount.removeFromWishlist(sampleTBook1));
        testAccount.addToWishlist(sampleNovel1);
        assertTrue(testAccount.removeFromWishlist(sampleNovel1));

    }

    @Test
    void testReturnBook() {
        try {
            assertFalse(testAccount.returnBook(sampleNovel1));
            testAccount.rentBook(sampleNovel1);
            assertTrue(testAccount.returnBook(sampleNovel1));
        } catch (UnsuccessfulTransactionException e) {
            fail();
        }

    }

    @Test
    void testAddFundNoException() {
        try {
            assertEquals(1000, testAccount.getFunds());
            testAccount.addFund(10);
            assertEquals(1010, testAccount.getFunds());
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    void TestViewRentedBooksList() {
        try {
            testAccount.rentBook(sampleNovel1);
            assertEquals("Name: " + sampleNovel1.getName() + "\tID: "
                    + sampleNovel1.getId() + "\n", testAccount.viewRentedBooksList());
        } catch (UnsuccessfulTransactionException e) {
            fail();
        }
    }

    @Test
    void TestViewPurchasedList() {
        try {
            testAccount.purchaseBook(sampleNovel1);
            assertEquals("Name: " + sampleNovel1.getName() + "\tID: "
                    + sampleNovel1.getId() + "\tPrice: $" + sampleNovel1.getPrice() + "\n", testAccount.viewPurchasedList());
        } catch (UnsuccessfulTransactionException e) {
            fail();
        }
    }

    @Test
    void TestViewWishList() {
        try {
            testAccount.addToWishlist(sampleNovel1);
            assertEquals("Name: " + sampleNovel1.getName() + "\tID: " + sampleNovel1.getId() + "\t"
                    + "Price: $" + sampleNovel1.getPrice() + "\n" , testAccount.viewWishlist());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void TestGetBookFromWishlist() {
        testAccount.addToWishlist(sampleNovel1);
        testAccount.addToWishlist(sampleNovel2);
        testAccount.addToWishlist(sampleNovel3);

        assertEquals(sampleNovel1,testAccount.getBookFromWishlist(0));
        assertEquals(sampleNovel2,testAccount.getBookFromWishlist(1));
        assertEquals(sampleNovel3,testAccount.getBookFromWishlist(2));
    }

    @Test
    void TestGetBookFromRentedList() {
        try {
            assertEquals(0,testAccount.getRentedBooks().size());
            testAccount.rentBook(sampleNovel1);
            testAccount.rentBook(sampleNovel4);
            assertEquals(2,testAccount.getRentedBooks().size());
            assertEquals(sampleNovel1,testAccount.getBookFromRentedList(0));
            assertEquals(sampleNovel4,testAccount.getBookFromRentedList(1));
        } catch (UnsuccessfulTransactionException e) {
            fail();
        }
    }

    @Test
    void testToString() {
        assertEquals("Name: Jane Doe\nFunds Remaining: $1000.0\n\n" +
                "WishList:\n___________________\n\n\n" +
                "Purchase history:\n___________________\n\n\n" +
                "Borrowed books:\n___________________\n\n\n", testAccount.toString());
    }

}
