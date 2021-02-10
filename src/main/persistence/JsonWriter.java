package persistence;

import model.account.Account;
import model.inventory.Inventory;
import org.json.JSONObject;
import java.io.*;

// Sources: "JSONDemo"; author: "Paul Carter"
// Represents a writer that writes JSON representation of Account and Inventory to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter accountWriter;
    private PrintWriter inventoryWriter;
    private final String accountDestination;
    private final String inventoryDestination;

    // EFFECTS: constructs writers to write to destination file
    public JsonWriter(String accountDestination, String inventoryDestination) {
        this.accountDestination = accountDestination;
        this.inventoryDestination = inventoryDestination;
    }

    // MODIFIES: this
    // EFFECTS: opens the writers; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        accountWriter = new PrintWriter(new File(accountDestination));
        inventoryWriter = new PrintWriter(new File(inventoryDestination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Account to file
    public void writeAccount(Account account) {
        JSONObject accountJson = account.toJson();
        accountWriter.print(accountJson.toString(TAB));

    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Inventory to file
    public void writeInventory(Inventory inventory) {
        JSONObject inventoryJson = inventory.toJson();
        inventoryWriter.print(inventoryJson.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writers
    public void close() {
        accountWriter.close();
        inventoryWriter.close();
    }
}
