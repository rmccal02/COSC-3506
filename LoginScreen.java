package cosc3506;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class LoginScreen extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private ArrayList<Applicant> applicantsList;
    private ArrayList<Faculty> facultyList;
    private ArrayList<User> userList;
    private String typedUsername;
    
    //default login values;
    private static final String DEFAULT_USERNAME = "username";
    private static final String DEFAULT_PASSWORD = "password";
    

    
    private static ArrayList<User> loggedInUserList = new ArrayList<>();
    
    
    public LoginScreen() {
    	this.userList = userList;
    	loadData();
        createLoginScreen();
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
    }
    
    public void setApplicantsList(ArrayList<Applicant> applicantsList) {
        this.applicantsList = applicantsList;
    }

    public void setFacultyList(ArrayList<Faculty> facultyList) {
        this.facultyList = facultyList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    private void createLoginScreen() {
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel()); 
        loginPanel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                
                
                if (isValidLogin(username, password)) {
                    dispose(); 
                    
                    showHomeScreen();
                    
                } else {
                    JOptionPane.showMessageDialog(LoginScreen.this,
                            "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setContentPane(loginPanel);
    }
    
    private boolean isValidLogin(String username, String password) {
    	//default login credentials incase others fail.
    	if (username.equals("username") && password.equals("password")) {
            
            return true;
            
        }
    	
    	try (Socket socket = new Socket("127.0.0.1", 12345); // Replace with the server's IP and port
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

               
               output.writeObject("login|" + username + "|" + password);
               String token = ((String) input.readObject());
               if (token.equals("true")) {
            	   return true;
               }
               else {
            	   return false;
               }
               
        	} catch (Exception ee) {
               ee.printStackTrace();
        	}
    	
        for (User user : userList) {
        	if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                user.logIn(); 
                return true;
            }
        }
        return false; 
    }
    
    
    
    
    private void loadData() {
        DataSaver dataSaver = new DataSaver();
        dataSaver.createFilesIfNotExist();

        // Simulate loading data like in AddEntryScreen
        applicantsList = dataSaver.loadApplicants();
        facultyList = dataSaver.loadFaculty();
        userList = dataSaver.loadUsers();

        // Further process userList if required
    }
    
    public String getTypedUsername() {
        return typedUsername; // Return the typed username
    }

    private void showHomeScreen() {
        HomeScreen homeScreen = new HomeScreen();
        homeScreen.setVisible(true);
    }
}