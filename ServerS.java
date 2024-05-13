import java.io.*;
import java.net.*;

public class ServerS {
	static ApplicantLinkedList applicants;
	static FacultyLinkedList faculty;
	static UserLinkedList users;

    public static void main(String[] args) throws IOException {
    	//Liam added the 3 lines below
    	applicants = new ApplicantLinkedList(true, false);
    	faculty = new FacultyLinkedList(true, false);
    	users = new UserLinkedList(true, false);
    	
        ServerSocket serverSocket = new ServerSocket(12345); // Replace with the port number
        System.out.println("Server started. Listening on Port 12345");

        try (serverSocket) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                     ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

                    String[] tokens = ((String) input.readObject()).split("|");
                    String action = tokens[0];

                    switch (action) {
                        case "add":
                            if (tokens[1].equals("a")) {
                            	addApplicant(tokens[2], tokens[3], tokens[4], tokens[5]);
                            }
                            else if (tokens[1].equals("f")) {
                            	addFaculty(tokens[2], tokens[3], tokens[4], tokens[5]);
                            }
                            else if (tokens[1].equals("u")) {
                            	addUser(tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7], tokens[8]);
                            }
                            break;
                        case "edit":
                            if(tokens[1].equals("a")) {
                            	editApplicant(1, tokens[3], tokens[2]);
                            	editApplicant(2, tokens[4], tokens[2]);
                            	editApplicant(3, tokens[5], tokens[2]);
                            	editApplicant(4, tokens[6], tokens[2]);
                            }
                            else if(tokens[1].equals("f")) {
                            	editFaculty(1, tokens[3], tokens[2]);
                            	editFaculty(2, tokens[4], tokens[2]);
                            	editFaculty(3, tokens[5], tokens[2]);
                            	editFaculty(4, tokens[6], tokens[2]);
                            }
                            else if(tokens[1].equals("u")) {
                            	editUser(1, tokens[3], tokens[2]);
                            	editUser(2, tokens[4], tokens[2]);
                            }
                            break;
                        case "search":
                        	if(tokens[1].equals("a")) {
                        		ApplicantLinkedList tempList = searchApplicant(1, tokens[2], false, null, null);
                            	String temp = "";
                            	ApplicantNode point = tempList.head;
                            	while(point != null) {
                            		temp = temp + point.id + "|";
                            		temp = temp + point.name + "|";
                            		//temp = temp + point.date + "|";
                            		temp = temp + point.comment + "|";
                            		temp = temp + point.review + "|";
                            		temp = temp + point.status + "|";
                            		point = point.next;
                            	}
                            	output.writeObject(temp);
                            }
                        	else if(tokens[1].equals("f")) {
                            	FacultyLinkedList tempList = searchFaculty(1, tokens[2], false, null, null);
                            	String temp = "";
                            	FacultyNode point = tempList.head;
                            	while(point != null) {
                            		temp = temp + point.id + "|";
                            		temp = temp + point.name + "|";
                            		//temp = temp + point.date + "|";
                            		temp = temp + point.comiteeChair + "|";
                            		temp = temp + point.startingDate + "|";
                            		temp = temp + point.endingDate + "|";
                            		point = point.next;
                            	}
                            	output.writeObject(temp);
                            }
                            else if(tokens[1].equals("u")) {
                            	UserLinkedList tempList = searchUser(1, tokens[2], false, null, null);
                            	String temp = "";
                            	UserNode point = tempList.head;
                            	while(point != null) {
                            		temp = temp + point.id + "|";
                            		temp = temp + point.name + "|";
                            		//temp = temp + point.date + "|";
                            		temp = temp + point.title + "|";
                            		temp = temp + point.email + "|";
                            		temp = temp + point.username + "|";
                            		temp = temp + point.password + "|";
                            		temp = temp + point.isAdministrator + "|";
                            		point = point.next;
                            	}
                            	output.writeObject(temp);
                            }
                            break;
                        case "login":
                        	String temp = (isValidLogin(tokens[2], tokens[3])) ? "true" : "false";
                        	output.writeObject(temp);
                        	break;
                        default:
                            output.writeObject("Action Invalid!");
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //Liam's added/edited methods
    private static boolean addApplicant(String name, String comment, String review, String status) throws IOException {
    	ApplicantNode n = new ApplicantNode();
    	n.name = (name != null && !name.equals("")) ? name : " ";
    	n.comment = (comment != null && !comment.equals("")) ? comment : " ";
    	n.review = (review != null && !review.equals("")) ? review : " ";
    	n.status = (status != null && !status.equals("")) ? status : " ";
    	return applicants.insertBad(n);
    }
    
    private static boolean editApplicant(int typeOfDataChanged, String dataToBeChangedTo, String idOfNodeToBeEdited) throws IOException {
    	return applicants.edit(typeOfDataChanged, dataToBeChangedTo, idOfNodeToBeEdited);
    }
    
    private static ApplicantLinkedList searchApplicant(int typeOfDataSearched, String dataSearched, boolean searchWithDate, String searchAfterDate, String searchBeforeDate) throws IOException {
    	return applicants.search(typeOfDataSearched, dataSearched, searchWithDate, searchAfterDate, searchBeforeDate);
    }
    
    private static boolean addFaculty(String name, String comiteeChair, String startingDate, String endingDate) throws IOException {
    	FacultyNode n = new FacultyNode();
    	n.name = (name != null && !name.equals("")) ? name : " ";
    	n.comiteeChair = (comiteeChair != null && !comiteeChair.equals("")) ? comiteeChair : " ";
    	n.startingDate = (startingDate != null && !startingDate.equals("")) ? startingDate : " ";
    	n.endingDate = (endingDate != null && !endingDate.equals("")) ? endingDate : " ";
    	return faculty.insertBad(n);
    }
    
    private static boolean editFaculty(int typeOfDataChanged, String dataToBeChangedTo, String idOfNodeToBeEdited) throws IOException {
    	return faculty.edit(typeOfDataChanged, dataToBeChangedTo, idOfNodeToBeEdited);
    }
    
    private static FacultyLinkedList searchFaculty(int typeOfDataSearched, String dataSearched, boolean searchWithDate, String searchAfterDate, String searchBeforeDate) throws IOException {
    	return faculty.search(typeOfDataSearched, dataSearched, searchWithDate, searchAfterDate, searchBeforeDate);
    }
    
    private static boolean addUser(String requesterID, String name, String title, String email, String username, String password, String isAdministrator) throws IOException {
    	if(!isAdmin(requesterID))
    		return false;
    	UserNode n = new UserNode();
    	n.name = (name != null && !name.equals("")) ? name : " ";
    	n.title = (title != null && !title.equals("")) ? title : " ";
    	n.email = (email != null && !email.equals("")) ? email : " ";
    	n.username = (username != null && !username.equals("")) ? username : " ";
    	n.password = (password != null && !password.equals("")) ? password : " ";
    	n.isAdministrator = (isAdministrator.equals("true")) ? isAdministrator : " ";
    	//just to make sure that there's no duplicate usernames
    	if(!isValidUsernamePassword(n))
    		return false;
    	return users.insertBad(n);
    }
    
    private static boolean editUser(int typeOfDataChanged, String dataToBeChangedTo, String idOfNodeToBeEdited) throws IOException {
    	return users.edit(typeOfDataChanged, dataToBeChangedTo, idOfNodeToBeEdited);
    }
    
    private static UserLinkedList searchUser(int typeOfDataSearched, String dataSearched, boolean searchWithDate, String searchAfterDate, String searchBeforeDate) throws IOException {
    	return users.search(typeOfDataSearched, dataSearched, searchWithDate, searchAfterDate, searchBeforeDate);
    }

    private static boolean isAdmin(String id) throws IOException {
    	//The search method will return a UserLinkedList; because id is being searched for and ids are unique, this will
    	//result in a UserLinkedList with only one node. This node will contain the same user as the user making a request
    	//involving administrator priviliges
        UserNode point = users.search(0, id, false, null, null).head;
        if(point.isAdministrator.equals("true"))
        	return true;
        return false;
    }
    
    private static boolean isValidLogin(String username, String password) {
    	if(username.equals("username") && password.equals("password"))
    		return true;
    	UserNode point = users.head;
    	while(point != null) {
    		if(username.equals(point.username) && password.equals(point.password))
    			return true;
    		point = point.next;
    	}
    	return false;
    }
    
    private static boolean isValidUsernamePassword(UserNode n) {
    	UserNode point = users.head;
    	while(point != null) {
    		if(n.username.equals(point.username))
    			return false;
    		point = point.next;
    	}
    	return true;
    }
}
