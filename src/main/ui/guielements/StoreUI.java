package ui.guielements;

import model.account.Account;
import model.inventory.Inventory;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

//Main Window
public class StoreUI extends JFrame {

    private Dimension screenDimension;
    private Inventory inventory;
    private Account account;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private final JPanel booksPanel;
    protected static ArrayList<BookUI> bookUIList;
    private AccountPanel accountPanel;

    //MODIFIES:this
    //EFFECTS: sets the main UI for the bookstore
    public StoreUI(Account account, Inventory inventory, JsonReader jsonReader, JsonWriter jsonWriter) {
        initialize(account, inventory, jsonReader, jsonWriter);

        booksPanel = new JPanel();
        booksPanel.setBackground(new Color(255, 80, 0));
        booksPanel.setLayout(new GridLayout((int) Math.ceil(inventory.getLength() / 2.0),2,5,5));

        accountPanel = new AccountPanel(screenDimension, this.account);
        for (int i = 0; i < inventory.getLength(); i++) {
            BookUI bookTemp = new BookUI(screenDimension, this.inventory.getBook(i), this, this.account);
            bookUIList.add(bookTemp);
            booksPanel.add(bookTemp);
        }

        JScrollPane scrollPane = new JScrollPane(booksPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        confirmSaveOnClose();

        setLocationRelativeTo(null);
        add(this.accountPanel);
        add(scrollPane);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setVisible(true);
    }

    //MODIFIES:this
    //EFFECTS:initializes books, account, and data read/write features
    public void initialize(Account account, Inventory inventory, JsonReader jsonReader, JsonWriter jsonWriter) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        screenDimension = new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());

        this.inventory = inventory;
        this.account = account;
        this.jsonReader = jsonReader;
        this.jsonWriter = jsonWriter;

        bookUIList = new ArrayList<>();

        confirmLoadOnOpen();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(screenDimension);
    }

    //EFFECTS: asks users if they want to load the saved data. If yes, then loads the data.
    public void confirmLoadOnOpen() {
        int confirmed = JOptionPane.showConfirmDialog(null,
                "Do you want to load your saved data?", "Load Data",
                JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            try {
                this.account = jsonReader.readAccount();
                this.inventory = jsonReader.readInventory();
                System.out.println("Data loaded successfully");
            } catch (IOException e) {
                System.out.println("Unable to load file.");
            }
        }
    }

    //EFFECTS: asks users if they want to save the data. If yes, then saves the data.
    public void confirmSaveOnClose() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Do you want to save your data?", "Save Data",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    try {
                        StoreUI.this.jsonWriter.open();
                        StoreUI.this.jsonWriter.writeAccount(account);
                        StoreUI.this.jsonWriter.writeInventory(inventory);
                        StoreUI.this.jsonWriter.close();
                        JOptionPane.showMessageDialog(null, "Data saved successfully");
                    } catch (FileNotFoundException x) {
                        JOptionPane.showMessageDialog(null, "Unable to write to save data");
                    }
                }
            }
        });
    }
}
