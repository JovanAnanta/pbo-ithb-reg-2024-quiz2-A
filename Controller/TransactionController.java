package Controller;

import Model.Classes.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionController {

    private static DatabaseHandler conn = new DatabaseHandler();

    public static boolean buyBook(int userId, int bookId) {
        try {
            DatabaseHandler conn = new DatabaseHandler();
            conn.connect();

            String checkQuery = "SELECT * FROM Transactions WHERE user_id = ? AND book_id = ?";
            PreparedStatement checkStmt = conn.con.prepareStatement(checkQuery);
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, bookId);

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return false;
            }

            String insertQuery = "INSERT INTO Transactions (user_id, book_id) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.con.prepareStatement(insertQuery);
            insertStmt.setInt(1, userId);
            insertStmt.setInt(2, bookId);

            insertStmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Transaction> getUserTransactions(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            conn.connect();

            String query = "SELECT t.id, u.name AS user_name, b.title AS book_title, b.genre AS book_genre, b.price " +
                    "FROM Transactions t " +
                    "JOIN Users u ON t.user_id = u.id " +
                    "JOIN Books b ON t.book_id = b.id " +
                    "WHERE u.id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String userName = rs.getString("user_name");
                String bookTitle = rs.getString("book_title");
                String bookGenre = rs.getString("book_genre");
                int price = rs.getInt("price");

                Transaction transaction = new Transaction(id, userId, -1, userName, bookTitle, bookGenre, price);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return transactions;
    }
}
