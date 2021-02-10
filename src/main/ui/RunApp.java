package ui;

import exception.UnsuccessfulTransactionException;
import model.account.Account;
import model.book.*;
import model.inventory.Inventory;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.guielements.StoreUI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

//Bookstore application
public class RunApp {
    private Scanner input;
    private Account account;
    private Inventory inventory;
    private static final String JSON_ACCOUNT = "./data/account.json";
    private static final String JSON_INVENTORY = "./data/inventory.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    //MODIFIES: this
    //EFFECTS: starts the application
    public RunApp(Boolean isTextBased) {
        initialize();
        if (isTextBased) {
            input = new Scanner(System.in);
            processMainMenuCommand();
        } else {
            new StoreUI(account, inventory, jsonReader, jsonWriter);
        }

    }

    //MODIFIES: this
    //EFFECTS: initializes inventory and account
    public void initialize() {
        inventory = new Inventory();
        account = new Account("Young Boy Never Broke Again", 1000);
        jsonWriter = new JsonWriter(JSON_ACCOUNT, JSON_INVENTORY);
        jsonReader = new JsonReader(JSON_ACCOUNT, JSON_INVENTORY);
    }

    //EFFECTS: displays main menu
    public void viewMainMenu() {
        System.out.println("Main Menu:");
        System.out.println("______________\n");
        System.out.println("[1]\tView account summary");
        System.out.println("[2]\tView catalog");
        System.out.println();
        System.out.println("[3]\tLoad data");
        System.out.println("[0]\tExit Program");
        System.out.print("\nEnter option: ");
    }

    //MODIFIES: this
    //EFFECTS:processes user commands in the main menu
    public void processMainMenuCommand() {
        String command = "";
        boolean keepGoing = true;
        while (keepGoing) {
            viewMainMenu();
            command = input.next();
            if (command.equals("1")) {
                processAccountOptions();
            } else if (command.equals("2")) {
                processCatalogOptions();
            } else if (command.equals("3")) {
                loadData();
            } else if (command.equals("0")) {
                keepGoing = false;
                processSaveOptions();
                System.out.println("Program ended.");
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: loads data from file
    private void loadData() {
        try {
            account = jsonReader.readAccount();
            inventory = jsonReader.readInventory();
            System.out.println("Data loaded successfully");
        } catch (IOException e) {
            System.out.println("Unable to load file.");
        }
    }

    //MODIFIES: this
    //EFFECTS: process user commands for saving data
    public void processSaveOptions() {
        boolean keepGoing = true;
        String command = "";
        while (keepGoing) {
            viewSaveOptions();
            command = input.next();
            if (command.equals("y")) {
                saveData();
                System.out.println("Data saved successfully");
                keepGoing = false;
            } else if (command.equals("n")) {
                System.out.println("No data saved.");
                keepGoing = false;
            } else {
                System.out.println("Invalid input.");
            }
        }

    }

    //EFFECTS: saves current state of inventory object and account object
    private void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.writeAccount(account);
            jsonWriter.writeInventory(inventory);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to save data");
        }
    }

    //EFFECTS: displays options for saving data
    public void viewSaveOptions() {
        System.out.println("Do you want to preserve current inventory data and account data?");
        System.out.println("[y]\tyes");
        System.out.println("[n]\tno");
    }

    //EFFECTS: displays account summary
    public void viewAccountSummary() {
        System.out.println(account.toString());
    }

    //EFFECTS: Displays account options
    public void viewAccountOptions() {
        System.out.println("Account options:");
        System.out.println("_________________\n");
        System.out.println("[1] add funds");
        if (account.getRentedBooks().size() > 0) {
            System.out.println("[2] return book");
        }
        if (account.getWishlistBooks().size() > 0) {
            System.out.println("[3] remove from wishlist");
        }
        System.out.println("\n");
        System.out.println("[0] Main menu");
        System.out.print("\nEnter option: ");
    }

    //MODIFIES: this, Account
    //EFFECTS: process user commands in the account options
    public void processAccountOptions() {
        boolean keepGoing = true;
        int index;
        while (keepGoing) {
            try {
                viewAccountSummary();
                viewAccountOptions();
                index = input.nextInt();
                if (index == 1) {
                    addFundsToAccount();
                } else if (index == 2 && account.getRentedBooks().size() > 0) {
                    processRentedBooksOptions();
                } else if (index == 3 && account.getWishlistBooks().size() > 0) {
                    processWishlistBooksOptions();
                } else if (index == 0) {
                    keepGoing = false;
                } else if (index > 3) {
                    System.out.println("Invalid input.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Try again.");
                input.nextLine();
            }
        }
    }

    //MODIFIES: Account
    //EFFECTS: Asks user for amount and adds it to current balance.
    //         Throws InputMismatchException if input is not a number.
    public void addFundsToAccount() throws InputMismatchException {
        System.out.print("\nEnter amount: $");
        double amount = input.nextDouble();
        account.addFund(amount);
        System.out.println("$" + amount + " added successfully to your account.");
    }

    //MODIFIES: Account
    //EFFECTS:displays options for the wishlist
    public void viewWishlistBooksOptions() {
        for (int i = 0; i < account.getWishlistBooks().size(); i++) {
            System.out.printf("[%d]\t%s\n",i + 1, account.getBookFromWishlist(i));
        }
        System.out.println("[0]\t Return to menu");
        System.out.print("\nEnter option:");
    }

    //MODIFIES: Account
    //EFFECTS: processes user commands for the wishlist
    public void processWishlistBooksOptions() {

        boolean keepGoing = true;
        int index;
        while (keepGoing) {
            viewWishlistBooksOptions();
            index = input.nextInt();
            if (index == 0) {
                keepGoing = false;
            } else if (index >= 1 && index <= account.getWishlistBooks().size()) {
                Book book = account.getBookFromWishlist(index - 1);
                if (account.removeFromWishlist(book)) {
                    System.out.println("\"" + book.getName() + "\" removed from the wishlist.");
                }
            }
        }
    }

    //EFFECTS: displays options for rented books
    public void viewRentedBooksOptions() {
        for (int i = 0; i < account.getRentedBooks().size(); i++) {
            System.out.printf("[%d]\t%s\n",i + 1, account.getBookFromRentedList(i));
        }
        System.out.println("[0]\t Go back");
        System.out.println();
        System.out.print("\nEnter option:");
    }

    //EFFECTS: processes user command for rented books
    public void processRentedBooksOptions() {
        int index;
        boolean keepGoing = true;
        while (keepGoing) {
            viewRentedBooksOptions();
            index = input.nextInt();
            if (index == 0) {
                keepGoing = false;
            } else if (index >= 1 && index <= account.getRentedBooks().size()) {
                Book book = account.getBookFromRentedList(index - 1);
                if (account.returnBook(book)) {
                    System.out.println("\"" + book.getName() + "\" returned successfully.");
                }
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    //EFFECTS: displays book catalog
    public void viewCatalog() {
        System.out.println("Catalog:");
        System.out.println("________\n");
        for (int i = 0; i < inventory.getLength(); i++) {
            System.out.printf("[%d]\t%s\n",i + 1,inventory.getBook(i).getName());
        }
        System.out.println("\n[0]\tGo back");
        System.out.print("\nEnter option:");
    }

    //EFFECTS: process catalog options
    public void processCatalogOptions() {
        boolean keepGoing = true;
        int index;
        while (keepGoing) {
            try {
                viewCatalog();
                index = input.nextInt();
                if (index == 0) {
                    keepGoing = false;
                } else if (index >= 1 && index <= inventory.getLength()) {
                    processBookOptions(inventory.getBook(index - 1));
                } else {
                    System.out.println("Invalid input.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Try again.");
                input.nextLine();
            }

        }
    }

    //EFFECTS: displays options for the user-selected book and processes user command
    public void viewBookDetails(Book book) {
        System.out.println(book.toString());
        printBookDetailOptions(book);

    }

    //EFFECTS: process options for the book
    public void processBookOptions(Book book) {
        boolean keepGoing = true;
        String command;
        while (keepGoing) {
            viewBookDetails(book);
            command = input.next();
            if (command.equals("r") && book.isRentable()) {
                rentBook(book);
            } else if (command.equals("b")) {
                purchaseBook(book);
            } else if (command.equals("w")) {
                account.addToWishlist(book);
            } else if (command.equals("f")) {
                keepGoing = false;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    //MODIFIES: Inventory, Account
    //EFFECTS: rents book
    private void rentBook(Book book) {
        try {
            account.rentBook(book);
            System.out.println("\"" + book.getName() + "\" has been rented.");
        } catch (UnsuccessfulTransactionException e) {
            e.toString();
        }
    }

    //MODIFIES: Inventory, Account
    //EFFECTS: purchase book
    private void purchaseBook(Book book) {
        try {
            account.purchaseBook(book);
            System.out.println("\"" + book.getName() + "\" has been purchased.");
        } catch (UnsuccessfulTransactionException e) {
            e.toString();
        }
    }

    //EFFECTS: displays options for user-selected book
    public void printBookDetailOptions(Book book) {
        if (book.isRentable()) {
            System.out.println("[r]\tRent this book");
        }
        if (book.isPurchasable()) {
            System.out.println("[b]\tBuy this book");
        }
        System.out.println("[w]\tAdd this book to the wishlist");
        System.out.println("\n");
        System.out.println("[f]\tBack");
        System.out.println();
        System.out.print("\nEnter option: ");
    }

}
