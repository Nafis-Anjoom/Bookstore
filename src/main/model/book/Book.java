package model.book;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

//represents a book having a name, id, quantity, price, purchasing eligibility, and renting eligibility
public class Book implements Purchasable, Writable {

    public int quantity;
    private final String id;
    private final String name;
    private final boolean isPurchasable;
    private final boolean isRentable;
    private final double price;
    private final String category;
    private String image;

    //EFFECTS: construct Book object with given id, name, and price. isPurchasable and isBorrowable determine
    //         renting and purchasing eligibility of the book.
    public Book(String id, String name, boolean isRentable, double price, String category, String image) {
        this.id = id;
        this.name = name;
        this.isRentable = isRentable;
        this.isPurchasable = true;
        this.quantity = 100;
        this.price = price;
        this.category = category;
        this.image = image;
    }

    //EFFECTS: construct Book object with given id, name, price, and quantity. isPurchasable and isBorrowable
    //         determine renting and purchasing eligibility of the book.
    public Book(
            String id, String name, boolean isRentable, double price, String category, int quantity, String image) {
        this(id, name, isRentable, price, category, image);
        this.quantity = quantity;
    }

    //EFFECTS: returns quantity of the book object
    public int getQuantity() {
        return quantity;
    }

    //EFFECTS: returns the id of the book
    public String getId() {
        return id;
    }

    //EFFECTS: returns the name of the book
    public String getName() {
        return name;
    }

    //EFFECTS: if the book is purchasable, returns true. returns false otherwise.
    public boolean isPurchasable() {
        return isPurchasable;
    }

    //EFFECTS: if the book is borrowable, returns true. returns false otherwise.
    public boolean isRentable() {
        return isRentable;
    }

    //EFFECTS: returns the price of the book
    public double getPrice() {
        return price;
    }

    //EFFECTS: returns the category of the book
    public String getCategory() {
        return category;
    }

    //EFFECTS: returns the file name of the image
    public String getImage() {
        return image;
    }

    //EFFECTS: string representation of the book object
    public String toString() {
        return "Title: " + name + " \tId: " + id + " \tCategory: " + category + " \tQuantity available: "
                + quantity + " \tPrice: $" + price + " \t";
    }

    //REQUIRES: Actual type of the object is book.
    //EFFECTS: returns true if the name and id of this object is equal to the provided object. Returns false otherwise.
//    public boolean equals(Object object) {
//        Book temp = (Book) object;
//        if (id.equals(temp.id) && name.equals(temp.name)) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    //REQUIRES: Actual type of the object is book.
    //EFFECTS: returns true if the name and id of this object is equal to the provided object. Returns false otherwise.
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return quantity == book.quantity
                && Objects.equals(id, book.id)
                && Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    //MODIFIES: this
    //EFFECTS: decrements the quantity available by 1
    public void purchase() {
        this.quantity--;
    }

    //EFFECTS: returns JSON representation of a book object
    public JSONObject toJson() {
        JSONObject bookJsonObject = new JSONObject();

        bookJsonObject.put("name", name);
        bookJsonObject.put("quantity", quantity);
        bookJsonObject.put("id", id);
        bookJsonObject.put("price", price);
        bookJsonObject.put("isBorrowable", isRentable);
        bookJsonObject.put("category", category);
        bookJsonObject.put("image", image);


        return bookJsonObject;
    }
}
