package View;

import Controller.TransactionController;
import Controller.BookListController;
import Model.Classes.Books;
import Model.Classes.Users;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewBookList {
    private JFrame frame;
    private Users loggedInUser;

    public ViewBookList(Users user) {
        this.loggedInUser = user;
        frame = new JFrame("Book List");
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);

        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // FlowLayout dengan jarak antar elemen

        List<Books> books = BookListController.getAllBooks(); // Ambil semua buku dari database

        for (Books book : books) {
            JPanel bookCard = new JPanel();
            bookCard.setLayout(new BoxLayout(bookCard, BoxLayout.Y_AXIS));
            bookCard.setBorder(BorderFactory.createTitledBorder(book.getTitle()));

            JTextField titleField = new JTextField(book.getTitle());
            titleField.setEditable(false);
            bookCard.add(titleField);

            JTextField authorField = new JTextField(book.getAuthor());
            authorField.setEditable(false);
            bookCard.add(authorField);

            JTextField genreField = new JTextField(book.getGenre());
            genreField.setEditable(false);
            bookCard.add(genreField);

            JTextField priceField = new JTextField(String.valueOf(book.getPrice()));
            priceField.setEditable(false);
            bookCard.add(priceField);

            JButton buyButton = new JButton("Buy Book");
            buyButton.addActionListener(e -> {
                boolean success = TransactionController.buyBook(loggedInUser.getId(), book.getId());
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Buku berhasil dibeli.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Anda sudah membeli buku ini.");
                }
            });
            bookCard.add(buyButton);

            bookPanel.add(bookCard);
        }

        JPanel bottomPanel = new JPanel();
        JButton transactionsButton = new JButton("Transactions");
        bottomPanel.add(transactionsButton);
        frame.add(bottomPanel, BorderLayout.NORTH);

        transactionsButton.addActionListener(e -> {
            new TransactionView(loggedInUser);
            frame.dispose();
        });

        frame.add(new JScrollPane(bookPanel), BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
