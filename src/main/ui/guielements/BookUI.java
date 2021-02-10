package ui.guielements;

import exception.UnsuccessfulTransactionException;
import model.account.Account;
import model.book.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//UI for a single book
public class BookUI extends JPanel {
    private JPanel coverPanel;
    private JPanel detailPanel;
    private JLabel nameLabel;
    private JLabel idLabel;
    private JLabel priceLabel;
    private JLabel rentCostLabel;
    private JLabel quantityLabel;
    private JPanel buttonsPanel;
    private final Dimension priceLabelDim;
    private final Dimension bookPanelDim;
    private final Dimension detailPanelDim;
    private final Dimension coverPanelDim;
    private JButton purchaseButton;
    private JButton rentButton;
    private JButton wishlistButton;
    private final Frame parentFrame;
    private final Account account;
    private final Book book;

    //MODIFIES: this
    //EFFECTS: sets up book object graphics
    public BookUI(Dimension frame, Book book, Frame parentFrame, Account account) {
        this.parentFrame = parentFrame;
        this.account = account;
        this.book = book;

        setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        setBackground(new Color(255, 80, 0));
        bookPanelDim = new Dimension((int)(frame.getWidth() / 2.25),(int)(frame.getHeight() / 2.5) - 20);
        detailPanelDim = new Dimension((int)(bookPanelDim.width / 1.5), bookPanelDim.height);
        priceLabelDim = new Dimension(detailPanelDim.width, detailPanelDim.height / 12);
        coverPanelDim = new Dimension((int)(bookPanelDim.width / 3.5), bookPanelDim.height);
        setMinimumSize(bookPanelDim);
        setPreferredSize(bookPanelDim);
        setMaximumSize(bookPanelDim);
        setBorder(new EmptyBorder(10,0,10,0));
//        setBackground(new Color(243, 133, 133));

        createAllPanels();

        addToDetailPanel();

        add(coverPanel);
        add(detailPanel);
    }

    //MODIFIES: this
    //EFFECTS: sets up all placeholders for the book info
    public void createAllPanels() {
        createCoverPanel();
        createDetailsPanel();
        createNameLabel(book.getName());
        createIdLabel(book.getId());
        createPriceLabel(book.getPrice());
        createQuantityLabel(book.getQuantity());
        createButtonsPanel();

        if (book.isRentable()) {
            Novel temp = (Novel)(book);
            createRentPriceLabel(temp.getRentCost());
        }

    }

    //MODIFIES: this
    //EFFECTS: adds all placeholders to a single panel
    private void addToDetailPanel() {
        detailPanel.add(nameLabel);
        detailPanel.add(idLabel);
        if (book.isRentable()) {
            detailPanel.add(rentCostLabel);
        }
        detailPanel.add(priceLabel);
        detailPanel.add(quantityLabel);
        detailPanel.add(buttonsPanel);


    }

    //MODIFIES: this
    //EFFECTS: sets up book cover
    private void createCoverPanel() {
        coverPanel = new JPanel();
        coverPanel.setLayout(new BoxLayout(coverPanel, BoxLayout.Y_AXIS));
        coverPanel.setBorder(new EmptyBorder(0,0,0,15));
        coverPanel.setPreferredSize(coverPanelDim);

        try {
            BufferedImage image = ImageIO.read(new File("covers/" + book.getImage()));
            Image newImage = image.getScaledInstance(coverPanelDim.width, coverPanelDim.height, Image.SCALE_DEFAULT);
            JLabel pic = new JLabel(new ImageIcon(newImage));
            coverPanel.add(pic);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //MODIFIES: this
    //EFFECTS: sets up book details panel
    private void createDetailsPanel() {
        detailPanel = new JPanel();
        detailPanel.setBorder(new EmptyBorder(0,10,0,0));
        detailPanel.setPreferredSize(detailPanelDim);

    }

    //MODIFIES: this
    //EFFECTS: sets up book name panel
    private void createNameLabel(String name) {
        nameLabel = new JLabel(name);
        nameLabel.setPreferredSize(new Dimension(detailPanelDim.width, detailPanelDim.height / 6));
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 57));
    }

    //MODIFIES: this
    //EFFECTS: sets up book ID panel
    private void createIdLabel(String id) {
        idLabel = new JLabel("ID: " + id);
        idLabel.setPreferredSize(priceLabelDim);
        idLabel.setFont(new Font("Serif", Font.PLAIN, 30));
    }

    //MODIFIES: this
    //EFFECTS: sets up book price panel
    private void createPriceLabel(Double price) {
        priceLabel = new JLabel("Price: $" + price.toString());
        priceLabel.setPreferredSize(priceLabelDim);
        priceLabel.setFont(new Font("Serif", Font.PLAIN, 30));
    }

    //MODIFIES: this
    //EFFECTS: sets up book rent price panel
    private void createRentPriceLabel(Double rentPrice) {
//        rentCostLabel = new JLabel("Rent Price: $5");
        rentCostLabel = new JLabel("Rent Price: $" + rentPrice.toString());
        rentCostLabel.setPreferredSize(priceLabelDim);
        rentCostLabel.setFont(new Font("Serif", Font.PLAIN, 30));
    }

    //MODIFIES: this
    //EFFECTS: sets up book quantity panel
    private void createQuantityLabel(int quantity) {
        quantityLabel = new JLabel("Quantity Remaining: " + quantity);
        quantityLabel.setPreferredSize(priceLabelDim);
        quantityLabel.setFont(new Font("Serif", Font.PLAIN, 30));
    }

    //MODIFIES: this
    //EFFECTS: sets up book options buttons panel
    private void createBookButtons() {
        purchaseButton = new JButton("Purchase");
        rentButton = new JButton("Rent");
        wishlistButton = new JButton("Add to Wishlist");

        purchaseButton.setBorder(new EmptyBorder(0,0,0,0));
        rentButton.setBorder(new EmptyBorder(0,0,0,0));
        wishlistButton.setBorder(new EmptyBorder(0,0,0,0));

        Dimension buttonsDimension = new Dimension((int)(detailPanelDim.width / 3.5), detailPanelDim.height / 7);

        purchaseButton.setPreferredSize(buttonsDimension);
        rentButton.setPreferredSize(buttonsDimension);
        wishlistButton.setPreferredSize(buttonsDimension);

        purchaseButton.setSize(buttonsDimension);
        rentButton.setSize(buttonsDimension);
        wishlistButton.setSize(buttonsDimension);

        purchaseButton.setMinimumSize(buttonsDimension);
        rentButton.setMinimumSize(buttonsDimension);
        wishlistButton.setMinimumSize(buttonsDimension);

        Font buttonFont = new Font("Arial", Font.PLAIN, 23);

        purchaseButton.setFont(buttonFont);
        rentButton.setFont(buttonFont);
        wishlistButton.setFont(buttonFont);

        handleBookOptions();
    }

    //MODIFIES: this
    //EFFECTS: sets up book options buttons panel
    private void createButtonsPanel() {

        createBookButtons();

        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,15,0));
        buttonsPanel.setPreferredSize(new Dimension(detailPanelDim.width, detailPanelDim.height / 7));

//        buttonsPanel.setBackground(new Color(238, 214, 0));
        buttonsPanel.add(purchaseButton);
        buttonsPanel.add(wishlistButton);

        if (book.isRentable()) {
            buttonsPanel.add(rentButton);
        }

    }

    //MODIFIES: this
    //EFFECTS: updates book quantity
    public void updateQuantity() {
        quantityLabel.setText("Quantity Remaining: " + book.quantity);
    }

    //MODIFIES: this
    //EFFECTS: handles user options
    private void handleBookOptions() {
        createPurchaseBtnAction();
        createRentBtnAction();
        createWishBtnAction();
    }

    //EFFECTS: assign purchase button event handling
    private void createPurchaseBtnAction() {
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    account.purchaseBook(book);
                    updateQuantity();
                    JOptionPane.showMessageDialog(parentFrame, "Transaction Successful!");
                } catch (UnsuccessfulTransactionException x) {
                    JOptionPane.showMessageDialog(parentFrame, x.toString());
                }
//                JOptionPane.showMessageDialog(parentFrame, Utils.transactionStatus(account.purchaseBook(book)));

            }
        });
    }

    //EFFECTS: assign rent button event handling
    private void createRentBtnAction() {
        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    account.rentBook(book);
                    updateQuantity();
                    JOptionPane.showMessageDialog(parentFrame, "Book successfully rented!");
                } catch (UnsuccessfulTransactionException x) {
                    JOptionPane.showMessageDialog(parentFrame, x.toString());
                }
            }
        });
    }

    //EFFECTS: assign wishlist button event handling
    private void createWishBtnAction() {
        wishlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                account.addToWishlist(book);
                JOptionPane.showMessageDialog(parentFrame, "This book has been added to the wishlist");
//                parentFrame.invalidate();
//                parentFrame.repaint();
            }
        });
    }
}
