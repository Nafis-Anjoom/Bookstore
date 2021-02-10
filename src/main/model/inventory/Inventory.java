package model.inventory;

import model.book.Book;
import model.book.Novel;
import model.book.Textbook;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import java.util.ArrayList;

//represents bookstore's inventory
public class Inventory implements Writable {
    private ArrayList<Book> books;

    public Inventory() {
        books = new ArrayList<>();
        add(new Textbook("1921", "Intro physics", 100,100, "physics.jpg"));
        add(new Textbook("2794", "Intro Chemistry", 50, 100, "chem.jpg"));
        add(new Textbook("3424", "Intro biology", 75,0, "bio.jpg"));
        add(new Textbook("9999", "You Can't Afford This", 1000000,100, "broke.jpg"));
        add(new Novel("4567", "Great Gatsby", 20, 2, "gr.jpg"));
        add(new Novel("7896", "Fulton Street", 45, 5, "fut.jpg"));
        add(new Novel("0968", "1984", 55, 7, "1984.jpg"));
    }

    //EFFECTS: returns book at the specified index in the books list
    public Book getBook(int index) {
        return books.get(index);
    }

    //EFFECTS: adds given book to the books list
    public void add(Book book) {
        books.add(book);
    }

    //EFFECTS: returns the length of the books list
    public int getLength() {
        return books.size();
    }

    @Override
    //EFFECTS: returns JSON representation of inventory object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("books", listsToJson(books));
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
    //EFFECTS: overwrites existing list of books
    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    //EFFECTS: returns string representation of inventory
    public String toString() {
        String output = "";
        for (Book b : books) {
            output += b.toString() + "\n";
        }
        return output;
    }
}
