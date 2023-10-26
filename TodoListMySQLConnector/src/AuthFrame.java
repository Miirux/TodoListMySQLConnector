import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthFrame extends JFrame {
    private final String DB_URL = "jdbc:mysql://localhost:3306/todolist";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";

    private JTextField loginField;
    private JPasswordField passwordField;
    private JTextField registerNameField;
    private JTextField registerEmailField;
    private JPasswordField registerPasswordField;

    public AuthFrame() {
        setTitle("Authentication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel loginLabel = new JLabel("Email:");
        loginField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(66, 139, 202));
        loginButton.setForeground(Color.WHITE);

        loginPanel.add(loginLabel);
        loginPanel.add(loginField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        tabbedPane.addTab("Login", null, loginPanel, "Login");

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(4, 2));
        registerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel nameLabel = new JLabel("Full Name:");
        registerNameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        registerEmailField = new JTextField(20);
        JLabel registerPasswordLabel = new JLabel("Password:");
        registerPasswordField = new JPasswordField(20);
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(34, 139, 34));
        registerButton.setForeground(Color.WHITE);

        registerPanel.add(nameLabel);
        registerPanel.add(registerNameField);
        registerPanel.add(emailLabel);
        registerPanel.add(registerEmailField);
        registerPanel.add(registerPasswordLabel);
        registerPanel.add(registerPasswordField);
        registerPanel.add(new JLabel());
        registerPanel.add(registerButton);

        tabbedPane.addTab("Register", null, registerPanel, "Register");

        add(tabbedPane);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String email = loginField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    String query = "SELECT * FROM Account WHERE Email = ? AND Password = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, password);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        String userType = resultSet.getString("Type");
                        int userId = resultSet.getInt("id");
                        if ("client".equals(userType)) {
                            ClientFrame clientFrame = new ClientFrame(userId);
                        } else if ("admin".equals(userType)) {
                            AdminFrame adminFrame = new AdminFrame();
                        }
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(AuthFrame.this, "Login failed. Invalid email or password.");
                    }

                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AuthFrame.this, "Database connection error.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = registerNameField.getText();
                String email = registerEmailField.getText();
                String password = new String(registerPasswordField.getPassword());
                String userType = "client";

                try {
                	Class.forName("com.mysql.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    String insertQuery = "INSERT INTO Account (Type,Fullname,Email,Password) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

                    preparedStatement.setString(1, userType);
                    preparedStatement.setString(2, fullName);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, password);

                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                    	registerNameField.setText("");
                    	registerEmailField.setText("");
                    	registerPasswordField.setText("");
                        JOptionPane.showMessageDialog(AuthFrame.this, "Registration successful.");
                    } else {
                        JOptionPane.showMessageDialog(AuthFrame.this, "Registration failed.");
                    }

                    preparedStatement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AuthFrame.this, "Database connection error.");
                }catch(Exception ex) {
                	ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuthFrame frame = new AuthFrame();
            frame.setVisible(true);
        });
    }
}
