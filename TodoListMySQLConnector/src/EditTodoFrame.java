import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditTodoFrame extends JFrame {
    private final String DB_URL = "jdbc:mysql://localhost:3306/todolist";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";

    private JTextField titleField;
    private JTextField descriptionField;

    public EditTodoFrame(int userId, String selectedTitle) {
        setTitle("Edit To-Do Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);

        JPanel editTodoPanel = new JPanel();
        editTodoPanel.setLayout(new GridLayout(3, 2));
        editTodoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(20);
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(20);
        JButton submitButton = new JButton("Submit");

        editTodoPanel.add(titleLabel);
        editTodoPanel.add(titleField);
        editTodoPanel.add(descriptionLabel);
        editTodoPanel.add(descriptionField);
        editTodoPanel.add(new JLabel());
        editTodoPanel.add(submitButton);

        add(editTodoPanel);

        fetchAndDisplayTodoItem(userId, selectedTitle);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newTitle = titleField.getText();
                String newDescription = descriptionField.getText();
                try {
                    Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    String updateQuery = "UPDATE todoitem SET title = ?, description = ? WHERE idAccount = ? AND title = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

                    preparedStatement.setString(1, newTitle);
                    preparedStatement.setString(2, newDescription);
                    preparedStatement.setInt(3, userId);
                    preparedStatement.setString(4, selectedTitle);

                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(EditTodoFrame.this, "To-Do item updated.");
                        ClientFrame.fetchAndDisplayUserTodoItems();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(EditTodoFrame.this, "Failed to update to-do item.");
                    }

                    preparedStatement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(EditTodoFrame.this, "Database connection error.");
                }
            }
        });
    }

    private void fetchAndDisplayTodoItem(int userId, String selectedTitle) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String selectQuery = "SELECT title, description FROM todoitem WHERE idAccount = ? AND title = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, selectedTitle);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                titleField.setText(resultSet.getString("title"));
                descriptionField.setText(resultSet.getString("description"));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch to-do item information.");
        }
    }
}
