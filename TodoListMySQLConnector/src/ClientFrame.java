import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientFrame extends JFrame {
	
	public static int userId;
	
	static JTable todoTable;
	
	private static String DB_URL = "jdbc:mysql://localhost:3306/todolist";
    private static String DB_USER = "root";
    private static String DB_PASSWORD = "";
    public ClientFrame(int id) {
    	userId = id;
        setTitle("Client Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        JPanel todoPanel = new JPanel();
        todoPanel.setLayout(new BorderLayout());

        String[] columnNames = {"Title", "Description"};
        DefaultTableModel todoModel = new DefaultTableModel(columnNames, 0);

        todoTable = new JTable(todoModel);
        JScrollPane todoScrollPane = new JScrollPane(todoTable);

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton removeButton = new JButton("Remove");

        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        todoPanel.add(todoScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search by Title:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        todoPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        JButton logOutButton = new JButton("Log out");
        logOutButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
                AuthFrame authFrame = new AuthFrame();
                authFrame.setVisible(true);
        	}
        });
        buttonPanel.add(logOutButton);
        todoPanel.add(searchPanel, BorderLayout.NORTH);
        
        fetchAndDisplayUserTodoItems();
        getContentPane().add(todoPanel);
        setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	AddTodoFrame addTodoFrame = new AddTodoFrame(userId);
                addTodoFrame.setVisible(true);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int selectedRow = todoTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String title = todoModel.getValueAt(selectedRow, 0).toString();

                    EditTodoFrame editTodoFrame = new EditTodoFrame(userId, title);
                    editTodoFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(ClientFrame.this, "Select a to-do item to edit.");
                }            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int selectedRow = todoTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String title = todoModel.getValueAt(selectedRow, 0).toString();
                    todoModel.removeRow(selectedRow);
                    if (deleteTodoItemFromDatabase(title)) {
                        JOptionPane.showMessageDialog(ClientFrame.this, "Todo item removed.");
                    } else {
                        JOptionPane.showMessageDialog(ClientFrame.this, "Failed to remove the todo item.");
                    }
                } else {
                    JOptionPane.showMessageDialog(ClientFrame.this, "Select a todo item to remove.");
                }            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String searchTitle = searchField.getText();
                searchTodoItems(userId, searchTitle);
            }
        });
    }

    public static void fetchAndDisplayUserTodoItems() {
        DefaultTableModel todoModel = (DefaultTableModel) todoTable.getModel();
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String selectQuery = "SELECT title,description FROM todoitem WHERE idAccount = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            todoModel.setRowCount(0);

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                todoModel.addRow(new Object[]{title, description});
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to fetch to-do items.");
        }catch(Exception ex) {
        	ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error has occured.");
        }
    }
    
    private boolean deleteTodoItemFromDatabase(String title) {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM todoitem WHERE title= ?");
            preparedStatement.setString(1, title);
            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();
            
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }catch(Exception ex) {
        	JOptionPane.showMessageDialog(this, "An error has occured.");
        	return false;
        }
    }
    
    private void searchTodoItems(int userId, String searchTitle) {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String selectQuery = "SELECT title, description FROM todoitem WHERE idAccount = ? AND title LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, "%" + searchTitle + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel todoModel = (DefaultTableModel) todoTable.getModel();
            todoModel.setRowCount(0);

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                todoModel.addRow(new Object[]{title, description});
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(ClientFrame.this, "Database connection error.");
        }catch(Exception ex) {
        	ex.printStackTrace();
            JOptionPane.showMessageDialog(ClientFrame.this, "An error has occured.");
        }
    }

}
