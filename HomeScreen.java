package cosc3506;

import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreen extends JFrame {

    private JButton searchButton;
    private JButton addEntryButton;
    private JButton accountInfoButton;
    
    private DataSaver dataSaver;
    
    LoginScreen loginScreen = new LoginScreen();
    String username = loginScreen.getTypedUsername();
    
    public HomeScreen() {
        createHomeScreen();
        setTitle("Home");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        dataSaver = new DataSaver();

        dataSaver.createFilesIfNotExist(); 
    }

    private void createHomeScreen() {
        JPanel homePanel = new JPanel(new GridLayout(2, 2, 10, 10));

        searchButton = new JButton("Search");
        addEntryButton = new JButton("Add Entry");
        accountInfoButton = new JButton("Account Information");
        JButton homeButton = new JButton("Home");
        homeButton.setEnabled(false); // Grayed out and unpressable

        homePanel.add(searchButton);
        homePanel.add(addEntryButton);
        homePanel.add(accountInfoButton);
        homePanel.add(homeButton);

        searchButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                goToSearchScreen();
            }
        });

        addEntryButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                try {
					goToAddEntryScreen();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
            }
        });

        accountInfoButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                goToAccountInfoScreen();
            }
        });

        setContentPane(homePanel);
    }

    private void goToSearchScreen() {
        SearchScreen searchScreen = new SearchScreen();
        searchScreen.setVisible(true);
        this.dispose(); // Close the current window if needed
    }

    private void goToAddEntryScreen() throws IOException {
        AddEntryScreen addEntryScreen = new AddEntryScreen();
        addEntryScreen.setVisible(true);
        this.dispose(); // Close the current window if needed
    }

    private void goToAccountInfoScreen() {
    	AccountInfoScreen accountInfoScreen = new AccountInfoScreen();
        accountInfoScreen.setVisible(true);
        this.dispose(); // Close the current window if needed
    }
}
