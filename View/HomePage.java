package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Controller.UserController;
import Model.Classes.Users;

public class HomePage {

    private JFrame frame;

    public HomePage() {
        showHomePage();
    }

    public void showHomePage() {
        frame = new JFrame("Login");
        frame.setBounds(0, 0, 350, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        JLabel welcomeLabel = new JLabel("Login");
        welcomeLabel.setBounds(150, 30, 135, 30);
        frame.add(welcomeLabel);

        JLabel usernameLabel = new JLabel("Email : ");
        usernameLabel.setBounds(50, 100, 500, 20);
        frame.add(usernameLabel);

        JTextField inputUsername = new JTextField(16);
        inputUsername.setHorizontalAlignment(JTextField.CENTER);
        inputUsername.setBorder(BorderFactory.createEmptyBorder());
        inputUsername.setBounds(50, 120, 260, 20);
        frame.add(inputUsername);

        JLabel passLabel = new JLabel("Input Password : ");
        passLabel.setBounds(50, 180, 500, 20);
        frame.add(passLabel);

        JPasswordField inputPassword = new JPasswordField(16);
        inputPassword.setHorizontalAlignment(JTextField.CENTER);
        inputPassword.setBorder(BorderFactory.createEmptyBorder());
        inputPassword.setBounds(50, 200, 260, 20);
        frame.add(inputPassword);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(50, 240, 260, 50);
        frame.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = inputUsername.getText();
                String password = new String(inputPassword.getPassword());

                if (!email.isEmpty() && !password.isEmpty()) {
                    Users verifying = UserController.verifyUser(email, password);

                    if (verifying == null) {
                        JOptionPane.showMessageDialog(frame, "Salah email/password!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Login berhasil, Selamat datang " + verifying.getName());
                        new ViewBookList(verifying);
                        frame.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Isi terlebih dahulu kawan!");
                }
            }
        });

        frame.setVisible(true);
    }
}
