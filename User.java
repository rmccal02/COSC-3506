package cosc3506;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String ID;
    private String name;
    private String title;
    private String email;
    private String username;
    private String password;
    
    private static List<User> loggedInUsers = new ArrayList<>();

    // Constructor for User
    public User(String ID, String name, String title, String email, String username, String password) {
        this.ID = ID;
        this.name = name;
        this.title = title;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    public static List<User> getLoggedInUsers() {
        return loggedInUsers;
    }
    
    
    public String toString() {
        return this.ID + "," + this.name + "," + this.title + "," +
               this.email + "," + this.username + "," + this.password; 
    }

    // Getter methods 
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setter methods 
    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void logIn() {
        loggedInUsers.add(this);
    }

    public void logOut() {
        loggedInUsers.remove(this);
    }
}