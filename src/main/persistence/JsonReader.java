package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import model.account.Account;
import model.book.Book;
import model.book.Novel;
import model.book.Textbook;
import model.inventory.Inventory;
import org.json.*;

//source: "JSONDemo"; author: Paul Carter
// Represents a reader that reads Account object and Inventory object from JSON data stored in file
public class JsonReader {
    private final String accountSource;
    private final String inventorySource;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String accountSource, String inventorySource) {
        this.accountSource = accountSource;
        this.inventorySource = inventorySource;
    }

    // EFFECTS: reads Account object from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Account readAccount() throws IOException {
        String jsonData = readFile(accountSource);
        JSONObject jsonAccount = new JSONObject(jsonData);
        return parseAccount(jsonAccount);
    }

    // EFFECTS: reads Inventory object from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Inventory readInventory() throws IOException {
        String jsonData = readFile(inventorySource);
        JSONObject jsonInventory = new JSONObject(jsonData);
        return parseInventory(jsonInventory);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Inventory object from JSON object and returns it
    private Inventory parseInventory(JSONObject jsonInventory) {
        Inventory inventory = new Inventory();
        inventory.setBooks(parseBookLists(jsonInventory, "books"));
        return inventory;
    }

    // EFFECTS: parses Account object from JSON object and returns it
    private Account parseAccount(JSONObject jsonAccount) {
        String name = jsonAccount.getString("name");
        double funds = jsonAccount.getDouble("funds");
        Account account = new Account(name, funds);

        account.setWishlistBooks(parseBookLists(jsonAccount, "booksWishlist"));
        account.setPurchasedBooks(parseBookLists(jsonAccount, "purchasedBooks"));
        account.setRentedBooks(parseBookLists(jsonAccount, "rentedBooks"));

        return account;
    }

    // EFFECTS: parses list of books from JSON object and returns it
    private ArrayList<Book> parseBookLists(JSONObject jsonBookList, String key) {
        ArrayList<Book> outputList = new ArrayList<>();
        JSONArray jsonArray = jsonBookList.getJSONArray(key);

        for (Object json : jsonArray) {
            JSONObject jsonBook = (JSONObject) json;
            outputList.add(parseBook(jsonBook));
        }
        return outputList;
    }

    //EFFECTS: parse Book object from JSON object and returns it
    private Book parseBook(JSONObject jsonBook) {
        Book book;
        String name = jsonBook.getString("name");
        int quantity = jsonBook.getInt("quantity");
        String id = jsonBook.getString("id");
        double price = jsonBook.getDouble("price");
        String category = jsonBook.getString("category");
        String image = jsonBook.getString("image");

        if (category.equals("Novel")) {
            double rentCost = jsonBook.getDouble("rentCost");
            book = new Novel(id, name, price, rentCost, quantity, image);
        } else {
            book = new Textbook(id, name, price, quantity, image);
        }
        return book;
    }

}
