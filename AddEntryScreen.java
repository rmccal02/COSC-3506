package cosc3506;

import javax.swing.*;
import java.net.*;
import java.util.ArrayList;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddEntryScreen extends JFrame {

    private JButton homeButton;
    private JButton searchButton;
    private JButton addEntryButton;
    private JButton accountInfoButton;
    private JButton applicantButton;
    private JButton facultyButton;
    private JButton userButton;
    private JTextField idField;
    private JTextField nameField;
    private JTextField committeeChairField;
    private JTextField startingDateField;
    private JTextField endingDateField;
    private JTextField titleLabel;
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    private DataSaver dataSaver;
    
    // ArrayLists for storing Applicant, Faculty, and User information
    public static ArrayList<Applicant> applicantsList = new ArrayList<>();
	public static ArrayList<Faculty> facultyList = new ArrayList<>();
    public static ArrayList<User> userList = new ArrayList<>();
    


    public AddEntryScreen() throws IOException  {
    	
        createAddEntryScreen();
        setTitle("Add Entry");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        dataSaver = new DataSaver();

        dataSaver.createFilesIfNotExist(); 
        
        //**********
        loadData();
        
    }
    
    private void saveData() {
        dataSaver.saveApplicants(applicantsList);
        dataSaver.saveFaculty(facultyList);
        dataSaver.saveUsers(userList);
    }
    
    public void loadData() {
        applicantsList = dataSaver.loadApplicants();
        facultyList = dataSaver.loadFaculty();
        userList = dataSaver.loadUsers();
    }
    
    
    public ArrayList<Applicant> getApplicantsList() {
        return applicantsList;
    }

    public ArrayList<Faculty> getFacultyList() {
        return facultyList;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    private void createAddEntryScreen()throws IOException { {
        JPanel addEntryPanel = new JPanel(new BorderLayout());
        
        JPanel topPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        homeButton = new JButton("Home");
        searchButton = new JButton("Search");
        addEntryButton = new JButton("Add Entry");
        accountInfoButton = new JButton("Account Information");

        topPanel.add(homeButton);
        topPanel.add(searchButton);
        topPanel.add(addEntryButton);
        topPanel.add(accountInfoButton);

        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel roleButtonsPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        JButton applicantButton = new JButton("Applicant");
        JButton facultyButton = new JButton("Faculty");
        JButton userButton = new JButton("User");
        roleButtonsPanel.add(applicantButton);
        roleButtonsPanel.add(facultyButton);
        roleButtonsPanel.add(userButton);
        
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // Adjust the rows based on fields needed
        fieldsPanel.setVisible(false); // Initially hide the fields
        
        JButton enterEntryButton = new JButton("Enter Entry");
        enterEntryButton.setVisible(false); // Initially hide the button
        
        //applicant button
        applicantButton.addActionListener(e -> {
            fieldsPanel.removeAll(); 
            fieldsPanel.setVisible(true);
            enterEntryButton.setVisible(true);
            
            ActionListener[] listeners = enterEntryButton.getActionListeners();
            if (listeners.length > 0) {
                enterEntryButton.removeActionListener(listeners[0]);
            }

            // Add fields for Applicant
            JTextField applicantIDField = new JTextField();
            JTextField applicantNameField = new JTextField();
            JTextField applicantCommentField = new JTextField();
            JTextField applicantReviewField = new JTextField();
            JTextField applicantStatusField = new JTextField();
            fieldsPanel.add(new JLabel("ID:"));
            fieldsPanel.add(applicantIDField);
            fieldsPanel.add(new JLabel("Name:"));
            fieldsPanel.add(applicantNameField);
            fieldsPanel.add(new JLabel("Comment:"));
            fieldsPanel.add(applicantCommentField);
            fieldsPanel.add(new JLabel("Review:"));
            fieldsPanel.add(applicantReviewField);
            fieldsPanel.add(new JLabel("Status:"));
            fieldsPanel.add(applicantStatusField);
            
            
            
            enterEntryButton.addActionListener(entryEvent -> {
                String applicantID = applicantIDField.getText().trim();
                String applicantName = applicantNameField.getText().trim();
                String applicantComment = applicantCommentField.getText().trim();
                String applicantReview = applicantReviewField.getText().trim();
                String applicantStatus = applicantStatusField.getText().trim();
                
                
                
                	try (Socket socket = new Socket("127.0.0.1", 12345); // Replace with the server's IP and port
                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

                       // Example: "add admin1 newProfileInfo"
                		//add|f| for faculty and 
                       output.writeObject("add|a|"+ applicantIDField.getText() + "|" + applicantNameField.getText() + "|" + applicantCommentField.getText() + "|" + applicantReviewField.getText()
                    		   + "|" + applicantStatusField.getText());
                       // Replace with actual command
                	} catch (IOException ee) {
                       ee.printStackTrace();
                	}
                
                

                
                if (!applicantID.isEmpty() && !applicantName.isEmpty() && !applicantComment.isEmpty()
                        && !applicantReview.isEmpty() && !applicantStatus.isEmpty()) {

                    
                    Applicant newApplicant = new Applicant(applicantID, applicantName, applicantComment, applicantReview, applicantStatus);

                    
                    applicantsList.add(newApplicant);
                    saveData();

                    
                    JOptionPane.showMessageDialog(this, "Applicant added successfully!");

                    
                } else {
                    
                    JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                
                
            });
            
            
            
            centerPanel.revalidate();
            centerPanel.repaint();
        });
        //faculty button
        facultyButton.addActionListener(e -> {
            fieldsPanel.removeAll(); // Clear previous fields if any
            fieldsPanel.setVisible(true);
            enterEntryButton.setVisible(true);
            
            ActionListener[] listeners = enterEntryButton.getActionListeners();
            if (listeners.length > 0) {
                enterEntryButton.removeActionListener(listeners[0]);
            }

            // Add fields for Faculty
            JTextField facultyIDField = new JTextField();
            JTextField facultyNameField = new JTextField();
            JTextField committeeChairField = new JTextField();
            JTextField startingDateField = new JTextField();
            JTextField endingDateField = new JTextField();
            fieldsPanel.add(new JLabel("ID:"));
            fieldsPanel.add(facultyIDField);
            fieldsPanel.add(new JLabel("Name:"));
            fieldsPanel.add(facultyNameField);
            fieldsPanel.add(new JLabel("Committee Chair:"));
            fieldsPanel.add(committeeChairField);
            fieldsPanel.add(new JLabel("Starting Date:"));
            fieldsPanel.add(startingDateField);
            fieldsPanel.add(new JLabel("Ending Date:"));
            fieldsPanel.add(endingDateField);

            //  Enter Entry button
            enterEntryButton.addActionListener(entryEvent -> {
                String facultyID = facultyIDField.getText().trim();
                String facultyName = facultyNameField.getText().trim();
                String committeeChair = committeeChairField.getText().trim();
                String startingDate = startingDateField.getText().trim();
                String endingDate = endingDateField.getText().trim();
                
                
                try (Socket socket = new Socket("127.0.0.1", 12345); // Replace with the server's IP and port
                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

                       // Example: "add admin1 newProfileInfo"
                		//add|f| for faculty and 
                       output.writeObject("add|f|"+ facultyIDField.getText() + "|" + facultyNameField.getText() + "|" + committeeChairField.getText() + "|" + startingDateField.getText()
                    		   + "|" + endingDateField.getText());
                       // Replace with actual command
                	} catch (IOException ee) {
                       ee.printStackTrace();
                	}

                
                if (!facultyID.isEmpty() && !facultyName.isEmpty() && !committeeChair.isEmpty()
                        && !startingDate.isEmpty() && !endingDate.isEmpty()) {

                    
                    Faculty newFaculty = new Faculty(facultyID, facultyName, committeeChair, startingDate, endingDate);

                    
                    facultyList.add(newFaculty);
                    saveData();

                    
                    JOptionPane.showMessageDialog(this, "Faculty added successfully!");

                    
                } else {
                    
                    JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                

            });

            centerPanel.revalidate();
            centerPanel.repaint();
        });
        //user
        userButton.addActionListener(e -> {
            fieldsPanel.removeAll(); // Clear previous fields if any
            fieldsPanel.setVisible(true);
            enterEntryButton.setVisible(true);
            
            ActionListener[] listeners = enterEntryButton.getActionListeners();
            if (listeners.length > 0) {
                enterEntryButton.removeActionListener(listeners[0]);
            }

            // Add fields for User
            JTextField userIDField = new JTextField();
            JTextField userNameField = new JTextField();
            JTextField titleField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField usernameField = new JTextField();
            JPasswordField passwordField = new JPasswordField();
            fieldsPanel.add(new JLabel("ID:"));
            fieldsPanel.add(userIDField);
            fieldsPanel.add(new JLabel("Name:"));
            fieldsPanel.add(userNameField);
            fieldsPanel.add(new JLabel("Title:"));
            fieldsPanel.add(titleField);
            fieldsPanel.add(new JLabel("Email:"));
            fieldsPanel.add(emailField);
            fieldsPanel.add(new JLabel("Username:"));
            fieldsPanel.add(usernameField);
            fieldsPanel.add(new JLabel("Password:"));
            fieldsPanel.add(passwordField);

            // Action for Enter Entry button
            enterEntryButton.addActionListener(entryEvent -> {
                String userID = userIDField.getText().trim();
                String userName = userNameField.getText().trim();
                String title = titleField.getText().trim();
                String email = emailField.getText().trim();
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                
                try (Socket socket = new Socket("127.0.0.1", 12345); // Replace with the server's IP and port
                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

                       // Example: "add admin1 newProfileInfo"
                		//add|f| for faculty and 
                       output.writeObject("add|a|"+ userIDField.getText() + "|" + userNameField.getText() + "|" + titleField.getText() + "|" + emailField.getText()
                    		   + "|" + usernameField.getText() + "|" + passwordField.getPassword().toString());
                       
                       // Replace with actual command
                	} catch (IOException ee) {
                       ee.printStackTrace();
                	}
                

                
                if (!userID.isEmpty() && !userName.isEmpty() && !title.isEmpty()
                        && !email.isEmpty() && !username.isEmpty() && !password.isEmpty()) {

                    
                    User newUser = new User(userID, userName, title, email, username, password);

                    
                    userList.add(newUser);
                    saveData();

                    
                    JOptionPane.showMessageDialog(this, "User added successfully!");

                    
                } else {
                    
                    JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
               
            });

            centerPanel.revalidate();
            centerPanel.repaint();
        });

        addEntryPanel.add(topPanel, BorderLayout.NORTH);
        addEntryPanel.add(centerPanel, BorderLayout.CENTER);
        addEntryButton.setEnabled(false);
        
        centerPanel.add(roleButtonsPanel, BorderLayout.NORTH);
        centerPanel.add(fieldsPanel, BorderLayout.CENTER);
        centerPanel.add(enterEntryButton, BorderLayout.SOUTH);

        
        enterEntryButton.setVisible(false);
        
        applicantButton.addActionListener(e -> {
            fieldsPanel.setVisible(true);
            enterEntryButton.setVisible(true);
            
        });
        
        

        addEntryPanel.add(topPanel, BorderLayout.NORTH);
        addEntryPanel.add(centerPanel, BorderLayout.CENTER);
        addEntryButton.setEnabled(false);
        
        

        homeButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                goToHomeScreen();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                goToSearchScreen();
            }
        });

        accountInfoButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                goToAccountInfoScreen();
            }
        });

        setContentPane(addEntryPanel);
    }
    }



	private void goToHomeScreen() {
        
        HomeScreen homeScreen = new HomeScreen();
        homeScreen.setVisible(true);
        this.dispose(); // Close the current window
    }

    private void goToSearchScreen() {
        
        SearchScreen searchScreen = new SearchScreen();
        searchScreen.setVisible(true);
        this.dispose(); // Close the current window
    }

    private void goToAccountInfoScreen() {
        
        AccountInfoScreen accountInfoScreen = new AccountInfoScreen();
        accountInfoScreen.setVisible(true);
        this.dispose(); // Close the current window
    }
}
