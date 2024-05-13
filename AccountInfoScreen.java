package cosc3506;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;

public class AccountInfoScreen extends JFrame {

    private JTextField userIDField;
    private JTextField userNameField;
    private JTextField titleField;
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private User loggedInUser;
    
    


    public void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public AccountInfoScreen() {
    	try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            if ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                
                loggedInUser = new User(
                    userInfo[0], // ID
                    userInfo[1], // Name
                    userInfo[2], // Title
                    userInfo[3], // Email
                    userInfo[4], // Username
                    userInfo[5]  // Password
                );

                createAccountInfoScreen(loggedInUser);
                setTitle("Account Information");
                setSize(400, 300);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null); // Center the window
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the file read exception
        }
    	
    	JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton homeButton = new JButton("Home");
        JButton searchButton = new JButton("Search");
        JButton addEntryButton = new JButton("Add Entry");

        navigationPanel.add(homeButton);
        navigationPanel.add(searchButton);
        navigationPanel.add(addEntryButton);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToHomeScreen();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToSearchScreen();
            }
        });

        addEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					goToAddEntryScreen();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
    }

    

    private void createAccountInfoScreen(User loggedInUser) {
    	
    	
        JPanel accountInfoPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        JLabel userIDLabel = new JLabel("ID:");
        userIDField = new JTextField(loggedInUser.getID());
        userIDField.setEditable(false);
        JLabel userNameLabel = new JLabel("Name:");
        userNameField = new JTextField(loggedInUser.getName());
        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(loggedInUser.getTitle());
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(loggedInUser.getEmail());
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(loggedInUser.getUsername());
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(loggedInUser.getPassword());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override

            
        public
         
        void
         
        actionPerformed(ActionEvent e) {
                goToHomeScreen();
            }
        });

        

        accountInfoPanel.add(userIDLabel);
        accountInfoPanel.add(userIDField);
        accountInfoPanel.add(userNameLabel);
        accountInfoPanel.add(userNameField);
        accountInfoPanel.add(titleLabel);
        accountInfoPanel.add(titleField);
        accountInfoPanel.add(emailLabel);
        accountInfoPanel.add(emailField);
        accountInfoPanel.add(usernameLabel);
        accountInfoPanel.add(usernameField);
        accountInfoPanel.add(passwordLabel);
        accountInfoPanel.add(passwordField);

        JButton saveButton = new JButton("Save Changes");
        accountInfoPanel.add(saveButton);
        accountInfoPanel.add(backButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save changes to the logged in user
                loggedInUser.setID(userIDField.getText().trim());
                loggedInUser.setName(userNameField.getText().trim());
                loggedInUser.setTitle(titleField.getText().trim());
                loggedInUser.setEmail(emailField.getText().trim());
                loggedInUser.setUsername(usernameField.getText().trim());
                loggedInUser.setPassword(new String(passwordField.getPassword()).trim());

                
                DataSaver dataSaver = new DataSaver();
                ArrayList<User> userList = dataSaver.loadUsers();
                // Update the user information in the list
                for (User user : userList) {
                    if (user.getUsername().equals(loggedInUser.getUsername())) {
                        user.setID(loggedInUser.getID());
                        user.setName(loggedInUser.getName());
                        user.setTitle(loggedInUser.getTitle());
                        user.setEmail(loggedInUser.getEmail());
                        user.setUsername(loggedInUser.getUsername());
                        user.setPassword(loggedInUser.getPassword());
                        break;
                    }
                }
                dataSaver.saveUsers(userList);

                JOptionPane.showMessageDialog(AccountInfoScreen.this, "Changes saved successfully!");
            }
        });
        
        

        setContentPane(accountInfoPanel);
    }
    private void goToHomeScreen() {
        HomeScreen homeScreen = new HomeScreen();
        homeScreen.setVisible(true);
        this.dispose(); 
    }

    private void goToAddEntryScreen() throws IOException {
        AddEntryScreen addEntryScreen = new AddEntryScreen();
        addEntryScreen.setVisible(true);
        this.dispose(); 
    }

    private void goToSearchScreen() {
        SearchScreen searchScreen = new SearchScreen();
        searchScreen.setVisible(true);
        this.dispose(); 
    }
}
