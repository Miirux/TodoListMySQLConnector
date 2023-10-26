import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditUserFrame extends JFrame {
    private final String DB_URL = "jdbc:mysql://localhost:3306/todolist";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";

    private JTextField fullNameField;
    private JTextField emailField;
    private JComboBox<String> userTypeComboBox;
    private JPasswordField passwordField;
    private String userEmail;

    public EditUserFrame(String email) {
        this.userEmail = email;

        setTitle("Edit User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);

        JPanel editUserPanel = new JPanel();
        editUserPanel.setLayout(new GridLayout(5, 2));
        editUserPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel userTypeLabel = new JLabel("Type:");
        String[] userTypes = {"client", "admin"};
        userTypeComboBox = new JComboBox<>(userTypes);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        JButton saveButton = new JButton("Save");

        editUserPanel.add(fullNameLabel);
        editUserPanel.add(fullNameField);
        editUserPanel.add(emailLabel);
        editUserPanel.add(emailField);
        editUserPanel.add(userTypeLabel);
        editUserPanel.add(userTypeComboBox);
        editUserPanel.add(passwordLabel);
        editUserPanel.add(passwordField);
        editUserPanel.add(new JLabel());
        editUserPanel.add(saveButton);

        add(editUserPanel);

        
        fetchAndDisplayUserData(userEmail);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = fullNameField.getText();
                String newEmail = emailField.getText();
                String userType = userTypeComboBox.getSelectedItem().toString();
                String password = new String(passwordField.getPassword());

                
                try {
                    Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    String updateQuery = "UPDATE account SET fullname = ?, type = ?, email = ?, password = ? WHERE email = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

                    preparedStatement.setString(1, fullName);
                    preparedStatement.setString(2, userType);
                    preparedStatement.setString(3, newEmail);
                    preparedStatement.setString(4, password);
                    preparedStatement.setString(5, userEmail);

                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(EditUserFrame.this, "User information updated.");
                        AdminFrame.FetchAndDisplayUserData();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(EditUserFrame.this, "Failed to update user information.");
                    }

                    preparedStatement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(EditUserFrame.this, "Database connection error.");
                }
            }
        });
    }

    
    private void fetchAndDisplayUserData(String email) {
        
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String selectQuery = "SELECT * FROM Account WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                fullNameField.setText(resultSet.getString("fullname"));
                emailField.setText(resultSet.getString("email"));
                userTypeComboBox.setSelectedItem(resultSet.getString("type"));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch user information.");
        }
    }
}
