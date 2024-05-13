package cosc3506;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;


public class DataSaver {
	
	private final String APPLICANTS_FILE = "C:/Users/Robert/eclipse-workspace/JavaConcepts/applicants.txt";
	private final String FACULTY_FILE = "C:/Users/Robert/eclipse-workspace/JavaConcepts/faculty.txt";
	private final String USERS_FILE = "C:/Users/Robert/eclipse-workspace/JavaConcepts/users.txt";


    public void saveApplicants(ArrayList<Applicant> applicantsList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(APPLICANTS_FILE))) {
            for (Applicant applicant : applicantsList) {
                writer.println(applicant.toString());
                
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFaculty(ArrayList<Faculty> facultyList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FACULTY_FILE))) {
            for (Faculty faculty : facultyList) {
                writer.println(faculty.toString()); 
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUsers(ArrayList<User> userList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User user : userList) {
                writer.println(user.toString()); 
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Applicant> loadApplicants() {
        ArrayList<Applicant> applicantsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(APPLICANTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into attributes
                String[] attributes = line.split(",");

                // Create a new Applicant object from the attributes
                Applicant applicant = new Applicant(attributes[0], attributes[1], attributes[2], attributes[3], attributes[4]);
                
                
                applicantsList.add(applicant);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return applicantsList;
    }

    public ArrayList<Faculty> loadFaculty() {
        ArrayList<Faculty> facultyList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FACULTY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // Split the line 
                
                Faculty faculty = new Faculty(data[0], data[1], data[2], data[3], data[4]);
                facultyList.add(faculty);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return facultyList;
    }

    public ArrayList<User> loadUsers() {
        ArrayList<User> userList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                User user = new User(data[0], data[1], data[2], data[3], data[4], data[5]);
                userList.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }
    
    public void createFilesIfNotExist() {
        createFileIfNotExist(APPLICANTS_FILE);
        createFileIfNotExist(FACULTY_FILE);
        createFileIfNotExist(USERS_FILE);
    }

    private void createFileIfNotExist(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}