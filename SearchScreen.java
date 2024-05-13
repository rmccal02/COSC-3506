package cosc3506;

import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SearchScreen extends JFrame {

    private JButton homeButton;
    private JButton searchButton;
    private JButton addEntryButton;
    private JButton accountInfoButton;
    private JComboBox<String> entryTypeComboBox;
    private JTextField searchField;
    private JButton executeSearchButton;
    private JList<String> resultList;
    
    public static ArrayList<Applicant> applicantsList = new ArrayList<>();
	public static ArrayList<Faculty> facultyList = new ArrayList<>();
    public static ArrayList<User> userList = new ArrayList<>();

    public SearchScreen() {
        
        setTitle("Search");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        createSearchScreen();
        
    }

    public void createSearchScreen() {
        JPanel searchPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        homeButton = new JButton("Home");
        searchButton = new JButton("Search");
        addEntryButton = new JButton("Add Entry");
        accountInfoButton = new JButton("Account Information");
        searchButton.setEnabled(false); // Grayed out and unpressable

        topPanel.add(homeButton);
        topPanel.add(searchButton);
        topPanel.add(addEntryButton);
        topPanel.add(accountInfoButton);

        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel searchCriteriaPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        entryTypeComboBox = new JComboBox<>(new String[]{"Applicant", "Faculty", "User"});
        searchField = new JTextField();
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        executeSearchButton = new JButton("Search ID");

        searchCriteriaPanel.add(entryTypeComboBox);
        searchCriteriaPanel.add(searchField);

        centerPanel.add(searchCriteriaPanel, BorderLayout.NORTH);
        bottomPanel.add(executeSearchButton);
        searchPanel.add(bottomPanel, BorderLayout.SOUTH);

        resultList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(resultList);

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        searchPanel.add(topPanel, BorderLayout.NORTH);
        searchPanel.add(centerPanel, BorderLayout.CENTER);

        JButton viewProfileButton = new JButton("View Profile");
        bottomPanel.add(viewProfileButton);

        viewProfileButton.addActionListener(e -> {
            String selectedCategory = (String) entryTypeComboBox.getSelectedItem();
            String searchTerm = searchField.getText().trim();

            if (selectedCategory != null && !searchTerm.isEmpty()) {
                switch (selectedCategory) {
                    case "User":
                        User selectedUser = findUser(searchTerm);
                        if (selectedUser != null) {
                            createProfileScreenForUser(selectedUser);
                        }
                        break;
                    case "Applicant":
                        Applicant selectedApplicant = findApplicant(searchTerm);
                        if (selectedApplicant != null) {
                            createProfileScreenForApplicant(selectedApplicant);
                        }
                        break;
                    case "Faculty":
                        Faculty selectedFaculty = findFaculty(searchTerm);
                        if (selectedFaculty != null) {
                            createProfileScreenForFaculty(selectedFaculty);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        


        executeSearchButton.addActionListener(e -> {
            String selectedCategory = (String) entryTypeComboBox.getSelectedItem();
            String searchTerm = searchField.getText().trim();
            try (Socket socket = new Socket("127.0.0.1", 12345);
            		
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {
                   String temp = "";
                   if(selectedCategory.equals("Applicant"))
                       temp = "a";
                   else if(selectedCategory.equals("Faculty"))
                       temp = "f";
                   else if(selectedCategory.equals("User"))
                       temp = "u";
                   output.writeObject("search|" + temp + "|" + searchTerm);
                   String[] tokens = ((String) input.readObject()).split("|");
                   if(selectedCategory.equals("Applicant")) {
                	   for(int x = 0; x < tokens.length; x++) {
                		   Applicant newApplicant = new Applicant(tokens[x++], tokens[x++],tokens[x++], tokens[x++], tokens[x++]);

                           
                           applicantsList.add(newApplicant);
                	   }
                   }
                   else if(selectedCategory.equals("Faculty")){
                	   for(int x = 0; x < tokens.length; x++) {
                		   Faculty newFaculty = new Faculty(tokens[x++], tokens[x++],tokens[x++], tokens[x++], tokens[x++]);

                           
                           facultyList.add(newFaculty);
                		   
                	   }
                   }
                   else if(selectedCategory.equals("User")){
                	   for(int x = 0; x < tokens.length; x++) {
                		   User newUser = new User(tokens[x++], tokens[x++],tokens[x++], tokens[x++], tokens[x++], tokens[x++]);

                           
                		   userList.add(newUser);
                	   }
                   }
                   
               } catch (Exception e1) {
				
				e1.printStackTrace();
			}
            if (selectedCategory != null && !searchTerm.isEmpty()) {
                DefaultListModel<String> resultListModel = new DefaultListModel<>();
                switch (selectedCategory) {
                    case "Applicant":
                        for (Applicant applicant : applicantsList) {
                            if (applicant.getID().equalsIgnoreCase(searchTerm)) {
                                resultListModel.addElement(applicant.getID() + " | " 
                                    + applicant.getName() + " | " + applicant.getComment() + " | " + applicant.getReview() + " | " + applicant.getStatus());
                            }
                        }
                        break;
                    case "Faculty":
                        for (Faculty faculty : facultyList) {
                            if (faculty.getID().equalsIgnoreCase(searchTerm)) {
                                resultListModel.addElement(faculty.getID() + " | "
                                    + faculty.getName() + " | " + faculty.getCommitteeChair() + " | " + faculty.getStartingDate() + " | " + faculty.getEndingDate());
                            }
                        }
                        break;
                    case "User":
                        for (User user : userList) {
                            if (user.getID().equalsIgnoreCase(searchTerm)) {
                                resultListModel.addElement(user.getID() + " | "
                                    + user.getName() + " | " + user.getTitle() + " | " + user.getEmail() + " | " + user.getUsername());
                            }
                        }
                        break;
                    default:
                        break;
                }
                resultList.setModel(resultListModel);
            }
        });

        setContentPane(searchPanel);
        homeButton.addActionListener(event -> goToHomeScreen());
        addEntryButton.addActionListener(event -> {
            try {
                goToAddEntryScreen();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        accountInfoButton.addActionListener(event -> goToAccountInfoScreen());
    }
    private void goToProfileScreen(User selectedUser) {
        ProfileScreen profileScreen = new ProfileScreen(selectedUser);
        profileScreen.setVisible(true);
        this.dispose();
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

    private void goToAccountInfoScreen() {
        AccountInfoScreen accountInfoScreen = new AccountInfoScreen();
        accountInfoScreen.setVisible(true);
        this.dispose(); 
    }
    private User findUser(String searchTerm) {
        for (User user : AddEntryScreen.userList) {
            if (user.getID().equalsIgnoreCase(searchTerm)) {
                return user;
            }
        }
        return null;
    }

    private Applicant findApplicant(String searchTerm) {
        for (Applicant applicant : AddEntryScreen.applicantsList) {
            if (applicant.getID().equalsIgnoreCase(searchTerm)) {
                return applicant;
            }
        }
        return null;
    }

    private Faculty findFaculty(String searchTerm) {
        for (Faculty faculty : AddEntryScreen.facultyList) {
            if (faculty.getID().equalsIgnoreCase(searchTerm)) {
                return faculty;
            }
        }
        return null;
    }
    private void createProfileScreenForUser(User user) {
        JPanel profilePanel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(user.getID());
        idField.setEditable(true);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(user.getName());
        nameField.setEditable(true);

        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField(user.getTitle());
        titleField.setEditable(true);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goToSearchScreen());
        
        JButton saveChangesButton = new JButton("Save Changes");
        saveChangesButton.addActionListener(e -> {
            user.setID(idField.getText());
            user.setName(nameField.getText());
            user.setTitle(titleField.getText());
            try (Socket socket = new Socket("127.0.0.1", 12345); // Replace with the server's IP and port
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

                   // Example: "add admin1 newProfileInfo"
            		//add|f| for faculty and 
                   output.writeObject("edit|u|" + idField.getText() + "|" + nameField.getText() + "|" + titleField.getText());
                   // Replace with actual command
            	} catch (IOException ee) {
                   ee.printStackTrace();
            	}
            
            
        });

        profilePanel.add(idLabel);
        profilePanel.add(idField);
        profilePanel.add(nameLabel);
        profilePanel.add(nameField);
        profilePanel.add(titleLabel);
        profilePanel.add(titleField);
        profilePanel.add(backButton);
        profilePanel.add(saveChangesButton);

        setContentPane(profilePanel);
        setTitle("Profile - User");
        setSize(400, 250);
        setLocationRelativeTo(null);
    }

    private void createProfileScreenForApplicant(Applicant applicant) {
        JPanel profilePanel = new JPanel(new GridLayout(6, 2, 10, 10));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(applicant.getID());
        idField.setEditable(true);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(applicant.getName());
        nameField.setEditable(true);

        JLabel commentLabel = new JLabel("Comment:");
        JTextField commentField = new JTextField(applicant.getComment());
        commentField.setEditable(true);

        JLabel reviewLabel = new JLabel("Review:");
        JTextField reviewField = new JTextField(applicant.getReview());
        reviewField.setEditable(true);

        JLabel statusLabel = new JLabel("Status:");
        JTextField statusField = new JTextField(applicant.getStatus());
        statusField.setEditable(true);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goToSearchScreen());
        JButton saveChangesButton = new JButton("Save Changes");
        saveChangesButton.addActionListener(e -> {
            applicant.setID(idField.getText());
            applicant.setName(nameField.getText());
            applicant.setComment(commentField.getText());
            applicant.setReview(reviewField.getText());
            applicant.setStatus(statusField.getText());
            
            try (Socket socket = new Socket("127.0.0.1", 12345); // Replace with the server's IP and port
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

                   // Example: "add admin1 newProfileInfo"
            		//add|f| for faculty and 
                   output.writeObject("edit|a|" + idField.getText() + "|" + nameField.getText() + "|" + commentField.getText()
                		   + "|" + reviewField.getText() + "|" + statusField.getText());
                   // Replace with actual command
            	} catch (IOException ee) {
                   ee.printStackTrace();
            	}
        });

        profilePanel.add(idLabel);
        profilePanel.add(idField);
        profilePanel.add(nameLabel);
        profilePanel.add(nameField);
        profilePanel.add(commentLabel);
        profilePanel.add(commentField);
        profilePanel.add(reviewLabel);
        profilePanel.add(reviewField);
        profilePanel.add(statusLabel);
        profilePanel.add(statusField);
        profilePanel.add(backButton);
        profilePanel.add(saveChangesButton);

        setContentPane(profilePanel);
        setTitle("Profile - Applicant");
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    private void createProfileScreenForFaculty(Faculty faculty) {
        JPanel profilePanel = new JPanel(new GridLayout(6, 2, 10, 10));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(faculty.getID());
        idField.setEditable(true);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(faculty.getName());
        nameField.setEditable(true);

        JLabel chairLabel = new JLabel("Committee Chair:");
        JTextField chairField = new JTextField(faculty.getCommitteeChair());
        chairField.setEditable(true);

        JLabel startDateLabel = new JLabel("Starting Date:");
        JTextField startDateField = new JTextField(faculty.getStartingDate());
        startDateField.setEditable(true);

        JLabel endDateLabel = new JLabel("Ending Date:");
        JTextField endDateField = new JTextField(faculty.getEndingDate());
        endDateField.setEditable(true);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goToSearchScreen());
        
        JButton saveChangesButton = new JButton("Save Changes");
        saveChangesButton.addActionListener(e -> {
            faculty.setID(idField.getText());
            faculty.setName(nameField.getText());
            faculty.setCommitteeChair(chairField.getText());
            faculty.setStartingDate(startDateField.getText());
            faculty.setEndingDate(endDateField.getText());
            try (Socket socket = new Socket("127.0.0.1", 12345); // Replace with the server's IP and port
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

                   // Example: "add admin1 newProfileInfo"
            		//add|f| for faculty and 
                   output.writeObject("edit|f|" + idField.getText() + "|" + nameField.getText() + "|" + chairField.getText()
                		   + "|" + startDateField.getText() + "|" + endDateField.getText());
                   // Replace with actual command
            	} catch (IOException ee) {
                   ee.printStackTrace();
            	}
            
            
        });

        profilePanel.add(idLabel);
        profilePanel.add(idField);
        profilePanel.add(nameLabel);
        profilePanel.add(nameField);
        profilePanel.add(chairLabel);
        profilePanel.add(chairField);
        profilePanel.add(startDateLabel);
        profilePanel.add(startDateField);
        profilePanel.add(endDateLabel);
        profilePanel.add(endDateField);
        profilePanel.add(backButton);
        profilePanel.add(saveChangesButton);

        setContentPane(profilePanel);
        setTitle("Profile - Faculty");
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    private void goToSearchScreen() {
        SearchScreen searchScreen = new SearchScreen();
        searchScreen.setVisible(true);
        this.dispose();
    }

}
