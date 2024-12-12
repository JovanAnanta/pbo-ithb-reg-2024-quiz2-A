package View;

import Controller.TransactionController;
import Model.Classes.Transaction;
import Model.Classes.Users;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TransactionView {
    private JFrame frame;
    private Users loggedInUser;

    public TransactionView(Users user) {
        this.loggedInUser = user;
        frame = new JFrame("Transactions");
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        JLabel headingLabel = new JLabel("Transaction History", JLabel.CENTER);
        frame.add(headingLabel, BorderLayout.NORTH);

        JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(new BoxLayout(transactionPanel, BoxLayout.Y_AXIS)); // Menyusun secara vertikal

        List<Transaction> transactions = TransactionController.getUserTransactions(loggedInUser.getId());

        for (Transaction t : transactions) {
            JPanel transactionCard = new JPanel();
            transactionCard.setLayout(new GridLayout(1, 5, 10, 10)); // Grid untuk data transaksi

            transactionCard.add(new JLabel(String.valueOf(t.getId())));
            transactionCard.add(new JLabel(loggedInUser.getName()));
            transactionCard.add(new JLabel(t.getBookTitle()));
            transactionCard.add(new JLabel(t.getBookGenre()));
            transactionCard.add(new JLabel(String.valueOf(t.getPrice())));

            transactionPanel.add(transactionCard);
        }

        JPanel bottomPanel = new JPanel();
        JButton backButton = new JButton("Back to Book List");
        bottomPanel.add(backButton);

        backButton.addActionListener(e -> {
            new ViewBookList(loggedInUser);
            frame.dispose();
        });

        frame.add(transactionPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
