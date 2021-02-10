package ui.guielements;

import model.account.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//The top bar of the windowPane to access account
public class AccountPanel extends JPanel {
    private final Account account;

    //MODIFIES:this
    //EFFECTS: Creates top border with account button
    public AccountPanel(Dimension parentFrameDim, Account account) {
        this.account = account;
        setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));
        setMinimumSize(new Dimension(parentFrameDim.width,parentFrameDim.height / 7));
        setMaximumSize(new Dimension(parentFrameDim.width,parentFrameDim.height / 7));
        setPreferredSize(new Dimension(parentFrameDim.width,parentFrameDim.height / 7));
        setBackground(new Color(255, 255, 255));

        JButton accountButton = new JButton("Account Info");
        accountButton.setFont(new Font("Serif", Font.PLAIN, 30));

        accountButton.setPreferredSize(new Dimension(
                getPreferredSize().width / 10, getPreferredSize().height));
        accountButton.setBackground(new Color(246, 139, 112));

        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccountUI(account);
            }
        });

        add(accountButton);
    }
}
