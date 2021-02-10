package model.book;

import model.account.Account;
import org.json.JSONObject;

//A novel has all the characteristics of a book. A novel is rentable and purchasable.
public class Novel extends Book implements Rentable {

    private final double rentCost;

    //EFFECTS: Create a Novel object with given id, name, and renting cost
    public Novel(String id, String name, double price, double rentCost, String image) {
        super(id,name, true, price, "Novel", image);
        this.rentCost = rentCost;
    }

    //EFFECTS: create a novel object with given id, name, price, rentCost, and quantity
    public Novel(String id, String name, double price, double rentCost, int quantity, String image) {
        super(id, name, true, price, "Novel", quantity, image);
        this.rentCost = rentCost;
    }

    //EFFECTS: return rent cost
    public double getRentCost() {
        return rentCost;
    }

    //EFFECTS: string representation of the Novel object.
    public String toString() {
        String output = super.toString();
        output += "Rent cost: $" + rentCost + " \t";
        return output;
    }

    @Override
    //MODIFIES: this
    //EFFECTS: decrements quantity by 1
    public void rent() {
        quantity--;
    }

    @Override
    //MODIFIES: this
    //EFFECTS: increments quantity by 1
    public void returnBook() {
        quantity++;
    }

    //EFFECTS: returns JSON representation of a book object
    public JSONObject toJson() {
        JSONObject jsonBook = super.toJson();
        jsonBook.put("rentCost", rentCost);
        return jsonBook;
    }
}
