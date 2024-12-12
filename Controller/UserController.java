package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Classes.Users;

public class UserController {

    private static UserController instance;
    static DatabaseHandler conn = new DatabaseHandler();

    public static UserController getInstance() {
        if (instance == null) {
            synchronized (UserController.class) {
                if (instance == null) {
                    instance = new UserController();
                }
            }
        }
        return instance;
    }

    public static Users verifyUser(String email, String password) {
        try {
            conn.connect();
            if (conn.con == null) {
                System.out.println("Database connection is null.");
                return null;
            }

            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                String name = rs.getString("name");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");

                return new Users(userId, name, userEmail, userPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return null;
    }
}
