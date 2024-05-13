package cosc3506;

import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ProfileScreen extends JFrame {

    private JButton homeButton;
    private JButton searchButton;
    private JButton addEntryButton;
    private JButton accountInfoButton;
    private JButton backButton;
    private JTextField idField;
    private JTextField nameField;
    private User user;
    private Applicant applicant;
    private Faculty faculty;
    
    public ProfileScreen(User user) {
        this.user = user;
        createProfileScreenForUser();
    }

    public ProfileScreen(Applicant applicant) {
        this.applicant = applicant;
        createProfileScreenForApplicant();
    }

    public ProfileScreen(Faculty faculty) {
        this.faculty = faculty;
        createProfileScreenForFaculty();
    }

    
    

    

    private void createProfileScreenForUser() {
        JPanel profilePanel = new JPanel(new GridLayout(3, 2, 5, 5));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(user.getID());
        idField.setEditable(false);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(user.getName());
        nameField.setEditable(false);

        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField(user.getTitle());
        titleField.setEditable(false);

        profilePanel.add(idLabel);
        profilePanel.add(idField);
        profilePanel.add(nameLabel);
        profilePanel.add(nameField);
        profilePanel.add(titleLabel);
        profilePanel.add(titleField);

        setContentPane(profilePanel);
        // Add more fields as needed for User data
    }

    private void createProfileScreenForApplicant() {
        JPanel profilePanel = new JPanel(new GridLayout(5, 2, 5, 5));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(applicant.getID());
        idField.setEditable(false);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(applicant.getName());
        nameField.setEditable(false);

        JLabel commentLabel = new JLabel("Comment:");
        JTextField commentField = new JTextField(applicant.getComment());
        commentField.setEditable(false);

        JLabel reviewLabel = new JLabel("Review:");
        JTextField reviewField = new JTextField(applicant.getReview());
        reviewField.setEditable(false);

        JLabel statusLabel = new JLabel("Status:");
        JTextField statusField = new JTextField(applicant.getStatus());
        statusField.setEditable(false);

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

        setContentPane(profilePanel);
        // Add more fields as needed for Applicant data
    }

    private void createProfileScreenForFaculty() {
        JPanel profilePanel = new JPanel(new GridLayout(5, 2, 5, 5));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(faculty.getID());
        idField.setEditable(false);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(faculty.getName());
        nameField.setEditable(false);

        JLabel committeeChairLabel = new JLabel("Committee Chair:");
        JTextField committeeChairField = new JTextField(faculty.getCommitteeChair());
        committeeChairField.setEditable(false);

        JLabel startingDateLabel = new JLabel("Starting Date:");
        JTextField startingDateField = new JTextField(faculty.getStartingDate());
        startingDateField.setEditable(false);

        JLabel endingDateLabel = new JLabel("Ending Date:");
        JTextField endingDateField = new JTextField(faculty.getEndingDate());
        endingDateField.setEditable(false);

        profilePanel.add(idLabel);
        profilePanel.add(idField);
        profilePanel.add(nameLabel);
        profilePanel.add(nameField);
        profilePanel.add(committeeChairLabel);
        profilePanel.add(committeeChairField);
        profilePanel.add(startingDateLabel);
        profilePanel.add(startingDateField);
        profilePanel.add(endingDateLabel);
        profilePanel.add(endingDateField);

        setContentPane(profilePanel);
        // Add more fields as needed for Faculty data
    }

    private void goToHomeScreen() {
        HomeScreen homeScreen = new HomeScreen();
        homeScreen.setVisible(true);
        this.dispose(); 
    }

    private void goToSearchScreen() {
        SearchScreen searchScreen = new SearchScreen();
        searchScreen.setVisible(true);
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

    

}
