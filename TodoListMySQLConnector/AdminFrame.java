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

public class AdminFrame extends JFrame {
    private DefaultTableModel userModel;
    private JTable userTable;
    
    private final String DB_URL = "jdbc:mysql://localhost:3306/todolist";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";

    public AdminFrame() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        userModel = new DefaultTableModel();
        userModel.addColumn("Full Name");
        userModel.addColumn("Email");
        userModel.addColumn("Type");

        userTable = new JTable(userModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tableScrollPane = new JScrollPane(userTable);

        JButton addButton = new JButton("Add User");
        JButton editButton = new JButton("Edit User");
        JButton logOutButton = new JButton("Log out");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        
        JButton removeButton = new JButton("Remove User");
        removeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int selectedRow = userTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String fullName = userModel.getValueAt(selectedRow, 0).toString();
                    String email = userModel.getValueAt(selectedRow, 1).toString();
                    userModel.removeRow(selectedRow);
                    if (deleteUserFromDatabase(email)) {
                        JOptionPane.showMessageDialog(AdminFrame.this, "User " + fullName + " removed.");
                    } else {
                        JOptionPane.showMessageDialog(AdminFrame.this, "Failed to remove the user.");
                    }
                } else {
                    JOptionPane.showMessageDialog(AdminFrame.this, "Select a user to remove.");
                }
        	}
        });
        buttonPanel.add(removeButton);
        buttonPanel.add(logOutButton);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(tableScrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddUserFrame addUserFrame = new AddUserFrame();
                addUserFrame.setVisible(true);
                
                userModel.setRowCount(0);
                FetchAndDisplayUserData();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow >= 0) {
                    // edit function
                } else {
                    JOptionPane.showMessageDialog(AdminFrame.this, "Select a user to edit.");
                }
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
                AuthFrame authFrame = new AuthFrame();
                authFrame.setVisible(true);
            }
        });
    }
    private boolean deleteUserFromDatabase(String email) {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Account WHERE Email= ?");
            preparedStatement.setString(1, email);
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
    
    private void FetchAndDisplayUserData() {
    	try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT FullName,Email,Type from Account");
            ResultSet resultSet = preparedStatement.executeQuery();

            userModel.setRowCount(0);

            while (resultSet.next()) {
                String fullName = resultSet.getString("fullname");
                String email = resultSet.getString("email");
                String userType = resultSet.getString("type");
                userModel.addRow(new Object[]{fullName, email, userType});
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to retrieve user data.");
        }catch(Exception ex) {
        	JOptionPane.showMessageDialog(this, "An error has occured.");
        }
    }
}
