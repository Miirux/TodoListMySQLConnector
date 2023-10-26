import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserFrame extends JFrame {
    private final String DB_URL = "jdbc:mysql://localhost:3306/todolist";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";

    private JTextField fullNameField;
    private JTextField emailField;
    private JComboBox<String> userTypeComboBox;
    private JPasswordField passwordField;

    public AddUserFrame() {
        setTitle("Add User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);

        JPanel addUserPanel = new JPanel();
        addUserPanel.setLayout(new GridLayout(5, 2));
        addUserPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel userTypeLabel = new JLabel("Type:");
        String[] userTypes = {"client", "admin"};
        userTypeComboBox = new JComboBox<>(userTypes);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        JButton submitButton = new JButton("Submit");

        addUserPanel.add(fullNameLabel);
        addUserPanel.add(fullNameField);
        addUserPanel.add(emailLabel);
        addUserPanel.add(emailField);
        addUserPanel.add(userTypeLabel);
        addUserPanel.add(userTypeComboBox);
        addUserPanel.add(passwordLabel);
        addUserPanel.add(passwordField);
        addUserPanel.add(new JLabel());
        addUserPanel.add(submitButton);

        add(addUserPanel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = fullNameField.getText();
                String email = emailField.getText();
                String userType = userTypeComboBox.getSelectedItem().toString();
                String password = new String(passwordField.getPassword());

                try {
                    Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    String insertQuery = "INSERT INTO Account (FullName,Type,Email,Password) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

                    preparedStatement.setString(1, fullName);
                    preparedStatement.setString(2, userType);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, password);

                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(AddUserFrame.this, "User added successfully.");
                        
                        AdminFrame.FetchAndDisplayUserData();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(AddUserFrame.this, "Failed to add user.");
                    }

                    preparedStatement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddUserFrame.this, "Database connection error.");
                }catch(Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddUserFrame.this, "An error has occured.");
                }
            }
        });
    }
}
