package cosc3506;

import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;





public class MainUI {

    public static void main(String[] args) throws IOException {
        DataSaver dataSaver = new DataSaver();
        dataSaver.createFilesIfNotExist();
        
        AddEntryScreen addEntryScreen = new AddEntryScreen();
        addEntryScreen.loadData();
        
        ArrayList<Applicant> applicantsList = dataSaver.loadApplicants();
        ArrayList<Faculty> facultyList = dataSaver.loadFaculty();
        ArrayList<User> userList = dataSaver.loadUsers();
        

        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setApplicantsList(applicantsList); 
            loginScreen.setFacultyList(facultyList); 
            loginScreen.setUserList(userList); 
            loginScreen.setVisible(true);
        });
    }
}