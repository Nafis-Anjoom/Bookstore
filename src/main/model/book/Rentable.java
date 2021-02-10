package model.book;

//provides blueprint for a book that rentable
public interface Rentable {

     //EFFECTS: renting process for eligible book
    void rent();

    //EFFECTS: returns rented book to the bookstore
    void returnBook();

    //EFFECTS: returns rent cost of the book
    double getRentCost();
}
