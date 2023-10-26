import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddTodoFrame extends JFrame {
    private final String DB_URL = "jdbc:mysql://localhost:3306/todolist";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";

    private JTextField titleField;
    private JTextField descriptionField;

    public AddTodoFrame(int userId) {
        setTitle("Add To-Do Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);

        JPanel addTodoPanel = new JPanel();
        addTodoPanel.setLayout(new GridLayout(3, 2));
        addTodoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(20);
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(20);
        JButton submitButton = new JButton("Submit");

        addTodoPanel.add(titleLabel);
        addTodoPanel.add(titleField);
        addTodoPanel.add(descriptionLabel);
        addTodoPanel.add(descriptionField);
        addTodoPanel.add(new JLabel());
        addTodoPanel.add(submitButton);

        add(addTodoPanel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String description = descriptionField.getText();
                
                try {
                    Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    String insertQuery = "INSERT INTO todoitem (title, description, idAccount) VALUES (?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

                    preparedStatement.setString(1, title);
                    preparedStatement.setString(2, description);
                    preparedStatement.setInt(3, userId);

                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(AddTodoFrame.this, "To-Do item added.");
                        ClientFrame.fetchAndDisplayUserTodoItems();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(AddTodoFrame.this, "Failed to add to-do item.");
                    }

                    preparedStatement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddTodoFrame.this, "Database connection error.");
                }
            }
        });
    }
}
