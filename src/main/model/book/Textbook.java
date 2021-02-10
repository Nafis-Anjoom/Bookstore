package model.book;

import org.json.JSONObject;

//A textbook has all the characteristics of a book. A textbook that is purchasable, but not rentable.
public class Textbook extends Book {

    //EFFECTS: Creates a textbook with given id, name, and price
    public Textbook(String id, String name, double price, String image) {
        super(id,name, false, price, "Textbook", image);
    }

    //EFFECTS: Creates a textbook with given id, name, price, and quantity
    public Textbook(String id, String name, double price, int quantity, String image) {
        this(id,name, price, image);
        this.quantity = quantity;
    }
}
