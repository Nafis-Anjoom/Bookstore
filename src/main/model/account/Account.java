package model.account;

import exception.UnsuccessfulTransactionException;
import model.book.Book;
import model.book.Rentable;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;

//represents an user account with lists of books rented, books purchased, wishlist, and funds available.
public class Account implements Writable {

    private ArrayList<Book> rentedBooks;
    private ArrayList<Book> purchasedBooks;
    private  ArrayList<Book> wishlistBooks;
    private double funds;
    private final String name;

    //MODIFIES: this
    //EFFECTS: initializes account
    public Account(String name, double funds) {
        this.name = name;
        rentedBooks = new ArrayList<>();
        purchasedBooks = new ArrayList<>();
        wishlistBooks = new ArrayList<>();
        this.funds = funds;
    }

    //EFFECTS: returns purchase history
    public ArrayList<Book> getPurchasedBooks() {
        return purchasedBooks;
    }

    //EFFECTS: returns list of rented books
    public ArrayList<Book> getRentedBooks() {
        return rentedBooks;
    }

    //EFFECTS: returns wishlist
    public ArrayList<Book> getWishlistBooks() {
        return wishlistBooks;
    }

    //MODIFIES: this
    //EFFECTS: adds amount to fund.
    public void addFund(double amount) throws InputMismatchException {
        funds += amount;
    }

    //EFFECTS: return funds
    public double getFunds() {
        return funds;
    }

    /*
     *MODIFIES: this
     *EFFECTS: process purchase. Throws UnsuccessfulTransactionException if unsuccessful.
     */
    public void purchaseBook(Book book) throws UnsuccessfulTransactionException {
        if (funds >= book.getPrice()) {
            if (book.getQuantity() > 0) {
                funds -= book.getPrice();
                book.purchase();
                purchasedBooks.add(book);
            } else {
                throw new UnsuccessfulTransactionException("Book out of stock.");
            }
        } else {
            throw new UnsuccessfulTransactionException("Not enough Fund.");
        }
    }

    /*
     *MODIFIES: this
     *EFFECTS: process rental transaction. Throws UnsuccessfulTransactionException is unsuccessful.
     */
    public void rentBook(Book book) throws UnsuccessfulTransactionException {
//    public int rentBook(Book book) {
        Rentable rentingBook = (Rentable) book;
        if (funds >= rentingBook.getRentCost()) {
            if (book.getQuantity() > 0) {
                if (!rentedBooks.contains(book)) {
                    funds -= rentingBook.getRentCost();
                    rentingBook.rent();
                    rentedBooks.add(book);
//                    return 0;
                } else {
//                    return 3;
                    throw new UnsuccessfulTransactionException("Book already in possession");
                }
            } else {
//                return 1;
                throw new UnsuccessfulTransactionException("Book out of stock.");
            }
        } else {
            throw new UnsuccessfulTransactionException("Not enough fund.");
        }
//        return 2;

    }

    //MODIFIES: this
    //EFFECTS: adds book to the wishlist. Returns true if successful. Returns false otherwise.
    public boolean addToWishlist(Book book) {
        if (!wishlistBooks.contains(book)) {
            wishlistBooks.add(book);
            return true;
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: removes book from wishlist. Returns true if successful. Returns false otherwise.
    public boolean removeFromWishlist(Book book) {
        return wishlistBooks.remove(book);
    }

    //MODIFIES: this
    //EFFECTS: give the book back to the bookstore. Returns true if successful. Returns false otherwise.
    public boolean returnBook(Book book) {
        Rentable rentingBook = (Rentable) book;
        rentingBook.returnBook();
        return rentedBooks.remove(book);

    }

    //EFFECTS: returns rented book at index at specified index
    public Book getBookFromRentedList(int index) {
        return rentedBooks.get(index);
    }

    //EFFECTS: returns book in the wishlist at specified index
    public Book getBookFromWishlist(int index) {
        return wishlistBooks.get(index);
    }

    //EFFECTS: returns string representation of the wishlist
    public String viewWishlist() {
        String output = "";
        for (Book b : wishlistBooks) {
            output += "Name: " + b.getName() + "\tID: " + b.getId() + "\t" + "Price: $" + b.getPrice() + "\n";
        }
        return output;
    }

    //EFFECTS: returns string representation of the purchasedList
    public String viewPurchasedList() {
        String output = "";
        for (Book b : purchasedBooks) {
            output += "Name: " + b.getName() + "\tID: " + b.getId() + "\tPrice: $" + b.getPrice() + "\n";
        }
        return output;
    }

    //EFFECTS: returns string representation of the rentedBooksList
    public String viewRentedBooksList() {
        String output = "";
        for (Book b : rentedBooks) {
            output += "Name: " + b.getName() + "\tID: " + b.getId() + "\n";
        }
        return output;
    }

    //EFFECTS: returns string representation of Account object.
    public String toString() {
        String output = "Name: " + name + "\n";
        output += "Funds Remaining: $" + funds + "\n\n";
        output += "WishList:\n___________________\n\n" + viewWishlist() + "\n";
        output += "Purchase history:\n___________________\n\n" + viewPurchasedList() + "\n";
        output += "Borrowed books:\n___________________\n\n" + viewRentedBooksList() + "\n";

        return output;
    }

    @Override
    //EFFECTS: returns JSON representation of Account object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name",name);
        json.put("funds",funds);
        json.put("rentedBooks", listsToJson(rentedBooks));
        json.put("purchasedBooks", listsToJson(purchasedBooks));
        json.put("booksWishlist", listsToJson(wishlistBooks));
        return json;
    }

    //EFFECTS: returns JSON representation of List<Book>s
    public JSONArray listsToJson(ArrayList<Book> bookList) {
        JSONArray jsonArray = new JSONArray();

        for (Book book : bookList) {
            jsonArray.put(book.toJson());
        }

        return jsonArray;
    }

    //MODIFIES: this
    //EFFECTS: sets rentedBooks list to the given list
    public void setRentedBooks(ArrayList<Book> bookList) {
        rentedBooks = bookList;
    }

    //MODIFIES: this
    //EFFECTS: sets rentedBooks list to the given list
    public void setWishlistBooks(ArrayList<Book> bookList) {
        wishlistBooks = bookList;
    }

    //MODIFIES: this
    //EFFECTS: sets rentedBooks list to the given list
    public void setPurchasedBooks(ArrayList<Book> bookList) {
        purchasedBooks = bookList;
    }

    //EFFECTS: returns name of the account holder
    public String getName() {
        return name;
    }
}
