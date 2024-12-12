package Controller;

import Model.Classes.Books;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookListController {

    private static DatabaseHandler conn = new DatabaseHandler();

    public static List<Books> getAllBooks() {
        List<Books> booksList = new ArrayList<>();

        try {
            conn.connect();
            if (conn.con == null) {
                System.out.println("Database connection is null.");
                return null;
            }

            String query = "SELECT * FROM books";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                int price = rs.getInt("price");

                Books book = new Books(id, title, author, genre, price);
                booksList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return booksList;
    }
}
