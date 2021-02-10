package ui.guielements;

import model.account.Account;
import model.book.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.InputMismatchException;

//User interface for Account object
public class AccountUI extends JFrame {
    private final Dimension screenDimension;
    private final Account account;
    private final ListPane rentedList;
    private final ListPane purchaseList;
    private final ListPane wishlist;
    private final JLabel name;
    private final JLabel balance;
    private final JFrame thisFrame;
    private JPanel accountInfo;
    private JPanel transactionList;
    private JButton addFund;

    //MODIFIES:this
    //REQUIRES: valid account object
    //EFFECTS: sets up account UI
    public AccountUI(Account account) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        screenDimension = new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());

        this.account = account;
        thisFrame = this;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(screenDimension);

        setAccountInfo();

        name = new JLabel("Name: " + account.getName());
        balance = new JLabel("Balance: $" + account.getFunds());

        setupAddFundButton();

        setupNameBalance();
        setupTransactionList();

        rentedList = new ListPane(this, screenDimension, "Rented List", 2, account);
        purchaseList = new ListPane(this, screenDimension, "Purchase history", 0, account);
        wishlist = new ListPane(this, screenDimension, "Wishlist", 1, account);

        addToAccountInfo();
        initializeLists();
        addToTransactionList();
        showWindow();
    }

    //MODIFIES:this
    //EFFECTS: adds purchase history, wishlist, and rented book list to the windowPane
    public void addToTransactionList() {
        transactionList.add(purchaseList);
        transactionList.add(wishlist);
        transactionList.add(rentedList);
    }

    //MODIFIES:this
    //EFFECTS: adds user name, balance remaining, and option to add fund to windowPane
    public void addToAccountInfo() {
        accountInfo.add(name);
        accountInfo.add(balance);
        accountInfo.add(addFund);
    }

    //MODIFIES:this
    //EFFECTS: set up the button to add fund
    public void setupAddFundButton() {
        addFund = new JButton("Add funds");
        addFund.setPreferredSize(new Dimension(getWidth() / 10, getHeight() / 21));
        addFund.setFont(new Font("Serif", Font.PLAIN, 33));
        addFund.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(JOptionPane.showInputDialog(
                            thisFrame, "Enter a valid amount: "));
                    account.addFund(amount);
                    balance.setText("Balance: $" + account.getFunds());
                    JOptionPane.showMessageDialog(thisFrame, "$" + amount + " successfully added to your account");
                } catch (Exception x) {
                    JOptionPane.showMessageDialog(thisFrame, "Invalid Input");
                }
            }
        });
    }

    //MODIFIES:this
    //EFFECTS: set up user name and balance containers
    public void setupNameBalance() {
        name.setPreferredSize(new Dimension(getWidth(),getHeight() / 21));
        balance.setPreferredSize(new Dimension(getWidth(), getHeight() / 21));

        name.setFont(new Font("Serif", Font.PLAIN, 33));
        balance.setFont(new Font("Serif", Font.PLAIN, 33));
    }

    //MODIFIES:this
    //EFFECTS: sets up list space in the windowPane
    public void setupTransactionList() {
        transactionList = new JPanel();
        transactionList.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        Dimension transactionListDim = new Dimension(getWidth(),(int)(getHeight() / 1.6));
        transactionList.setMinimumSize(transactionListDim);
        transactionList.setMaximumSize(transactionListDim);
        transactionList.setPreferredSize(transactionListDim);
//        transactionList.setBackground(new Color(243, 5, 5));
    }

    //MODIFIES:this
    //EFFECTS:sets up space for account info in the windowPane
    public void setAccountInfo() {
        accountInfo = new JPanel();
        accountInfo.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        accountInfo.setMinimumSize(new Dimension(getWidth(),getHeight() / 7));
        accountInfo.setMaximumSize(new Dimension(getWidth(),getHeight() / 7));
        accountInfo.setPreferredSize(new Dimension(getWidth(),getHeight() / 7));
        accountInfo.setBackground(new Color(255, 80, 0));
    }

    //MODIFIES:this
    //EFFECTS: makes the window pane visible
    public void showWindow() {
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(accountInfo);
        add(transactionList);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        updateOnClose();
        setUndecorated(false);
        setVisible(true);
    }

    //MODIFIES:this
    //EFFECTS: sets up all the lists
    public void initializeLists() {

        for (Book b : account.getWishlistBooks()) {
            wishlist.addBook(b);
        }
        for (Book b : account.getPurchasedBooks()) {
            purchaseList.addBook(b);
        }
        for (Book b : account.getRentedBooks()) {
            rentedList.addBook(b);
        }
    }

    //MODIFIES: Book
    //EFFECTS: updates quantities of books after closing the account info window
    public void updateOnClose() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                for (BookUI b : StoreUI.bookUIList) {
                    b.updateQuantity();
                }
            }
        });
    }

}
