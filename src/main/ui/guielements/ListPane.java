package ui.guielements;

import model.account.Account;
import model.book.Book;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//helper class for AccountUI. supports the lists in the AccountUI.
public class ListPane extends JPanel {
    private final JList listContainer;
    private final DefaultListModel<Book> list;
    private Frame parentFrame;
    private Account account;

    //MODIFIES:this
    //EFFECTS: sets up the space for list
    public ListPane(Frame parentFrame, Dimension parentFrameDim, String title, int type, Account account) {
        initialize(parentFrame, parentFrameDim, account);

        list = new DefaultListModel<>();

        JLabel label = new JLabel(title + ":");
        label.setFont(new Font("Serif", Font.PLAIN, 50));
        add(label);
        listContainer = new JList(list) {
            @Override
            public int locationToIndex(Point location) {
                int index = super.locationToIndex(location);
                if (index != -1 && !getCellBounds(index, index).contains(location)) {
                    return -1;
                } else {
                    return index;
                }
            }
        };

        checkMouseBound();

        listContainer.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        listContainer.setFont(new Font("Serif", Font.PLAIN, 35));
        JScrollPane scrollPane = new JScrollPane(listContainer);
        add(scrollPane);

        checkType(type);
    }

    //MODIFIES:this
    //EFFECTS: checks if the list is purchase history, rented books, or wishlist.
    public void checkType(int type) {
        if (type == 0) {
            purchaseDetail();
        }
        if (type == 1) {
            wishlistDetail();
        }
        if (type == 2) {
            rentDetail();
        }
    }

    //MODIFIES:this
    //EFFECTS:checks if mouse selection is valid.
    public void checkMouseBound() {
        listContainer.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                JList listContainer = (JList) e.getSource();
                if (listContainer.locationToIndex(e.getPoint()) == -1 && !e.isShiftDown()
                        && !isMenuShortcutKeyDown(e)) {
                    listContainer.clearSelection();
                }
            }

            private boolean isMenuShortcutKeyDown(InputEvent event) {
                return (event.getModifiers() & Toolkit.getDefaultToolkit()
                        .getMenuShortcutKeyMask()) != 0;
            }
        });
    }

    //MODIFIES:this
    //EFFECTS:initializes the frame
    private void initialize(Frame parentFrame, Dimension parentFrameDim, Account account) {
        this.parentFrame = parentFrame;
        this.account = account;

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        Dimension rentedListDim = new Dimension(parentFrameDim.width / 3, (int)(parentFrameDim.height / 1.6));
        setMinimumSize(rentedListDim);
        setMaximumSize(rentedListDim);
        setPreferredSize(rentedListDim);
        setBackground(new Color(248, 169, 132));
    }

    //MODIFIES:this
    //EFFECTS:creates a wishlist
    private void wishlistDetail() {
        listContainer.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList listItem = (JList) e.getSource();
                Book book = (Book) listItem.getSelectedValue();
                listContainer.clearSelection();
                if (book != null) {
                    String output = book.toString() + "\nDo you want to remove this book from your wishlist?";
                    int input = JOptionPane.showConfirmDialog(parentFrame, output);
                    if (input == JOptionPane.YES_OPTION) {
                        listContainer.clearSelection();
                        account.removeFromWishlist(book);
                        list.removeElement(book);
                    }
                }
            }
        });
    }

    //MODIFIES:this
    //EFFECTS:creates a rented books list
    private void rentDetail() {
        listContainer.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList listItem = (JList) e.getSource();
                Book book = (Book) listItem.getSelectedValue();
                listContainer.clearSelection();
                if (book != null) {
                    String output = book.toString() + "\nDo you want to return this book?";
                    int input = JOptionPane.showConfirmDialog(parentFrame, output);
                    if (input == JOptionPane.YES_OPTION) {
                        account.returnBook(book);
                        list.removeElement(book);
                    }
                }
            }
        });
    }

    //MODIFIES:this
    //EFFECTS:creates a purchase history list
    public void purchaseDetail() {
        listContainer.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList listItem = (JList) e.getSource();
                Book book = (Book) listItem.getSelectedValue();
                if (book != null) {
//                if (listItem.getSelectedValue() != null) {
                    listContainer.clearSelection();
                    JOptionPane.showMessageDialog(parentFrame, book.toString());
                }
            }
        });
    }

    //MODIFIES:this
    //EFFECTS:adds book to list
    public void addBook(Book book) {
        list.addElement(book);
    }
}
