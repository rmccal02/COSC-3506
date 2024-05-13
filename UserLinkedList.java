import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserLinkedList {
	//constructor
	UserLinkedList(boolean iM, boolean isExport) throws IOException {
		isMain = iM;
		if(isMain)
			startupReadFile();
		//this will check to see if the current instance of UserLinkedList needs to read from the export file
		if(isExport)
			readExportFile();
	}
	//basic linked list methods
	protected UserNode head;
	protected int amountLoaded = 0;
	//this variable will allow writing to file or not
	protected boolean isMain;
	public boolean isEmpty() {
		if(head == null)
			return true;
		return false;
	}
	public int size() {
		int counter = 0;
		UserNode point = head;
		while(point != null) {
			counter++;
			point = point.next;
		}
		return counter;
	}
	public boolean insertBad(UserNode n) throws IOException {
		if(isEmpty()) {
			head = n;
			amountLoaded++;
			return true;
		}
		UserNode point = head;
		while(point.next != null)
			point = point.next;
		point.next = n;
		amountLoaded++;
		if(isMain)
			toFile();
		return true;
	}
	public boolean remove(int n) {
		if(isEmpty())
			return true;
		if(size() < n)
			return false;
		UserNode point = head;
		for(int a = 0; a < n - 1; a++)
			point = point.next;
		UserNode temp = point.next;
		point.next = temp;
		return true;
	}
	public void destroy() {
		head = null;
	}
	//Adding a Node
	//The only parameter is the name
	public boolean insert(String n) throws IOException {
		UserNode inserting = new UserNode();
		inserting.id = "a" + Integer.toString(amountLoaded);
		inserting.name = n;
		inserting.date = java.time.LocalDateTime.now().toString().substring(0, 10);
		insertBad(inserting);
		return true;
	}
	//another insert method
	//this is for startup purposes which will bypass the toFile() method that every insert method has
	public boolean startupInsert(UserNode n) throws IOException {
		if(isEmpty()) {
			head = n;
			amountLoaded++;
			return true;
		}
		UserNode point = head;
		while(point.next != null)
			point = point.next;
		point.next = n;
		amountLoaded++;
		return true;
	}
	//The very big search method
	//The first two parameters, an integer and String, represents the type of data being searched for an the data being searched for
	//1 = all
	//2 = search id
	//3 = search name
	//4 = search email
	//The last three parameters are a boolean and two Strings which represents whether there are date search constraints or not and what
	//these constraints are
	//Additionally, after the search is complete, the results of the search will be pasted into a text file
	public UserLinkedList search(int typeOfData, String data, boolean searchDate, String afterDate, String beforeDate) throws IOException {
		UserLinkedList a = new UserLinkedList(false, false);
		UserNode point = head;
		UserNode newNode;
		boolean isMatch;
		while(point != null) {
			isMatch = false;
			switch(typeOfData) {
				case 1:
					if(point.id.contains(data) || point.name.contains(data) || point.email.contains(data))
						isMatch = true;
					break;
				case 2:
					if(point.id.contains(data))
						isMatch = true;
					break;
				case 3:
					if(point.name.contains(data))
						isMatch = true;
					break;
				case 4:
					if(point.email.contains(data))
						isMatch = true;
					break;
			}
			//create clones of object because we don't want all the nodes connected to it
			if(isMatch) {
				newNode = new UserNode();
				newNode.id = point.id;
				newNode.name = point.name;
				newNode.date = point.date;
				newNode.title = point.title;
				newNode.email = point.email;
				newNode.username = point.username;
				newNode.password = point.password;
				newNode.isAdministrator = point.isAdministrator;
				a.insertBad(newNode);
			}
			point = point.next;
		}
		if(searchDate)
			a = searchYesDate(a, afterDate, beforeDate);
		toFileExportToClient(a);
		return a;
	}
	public UserLinkedList searchYesDate(UserLinkedList a, String afterDate, String beforeDate) throws IOException {
		UserLinkedList aa = new UserLinkedList(false, false);
		if(afterDate != null && beforeDate != null)
			aa = searchAfterDate(searchBeforeDate(a, beforeDate), afterDate);
		else if(afterDate != null)
			aa = searchAfterDate(a, afterDate);
		else if(beforeDate != null)
			aa = searchBeforeDate(a, beforeDate);
		return aa;
	}
	public UserLinkedList searchAfterDate(UserLinkedList a, String afterDate) throws IOException {
		UserLinkedList aa = new UserLinkedList(false, false);
		UserNode point = a.head;
		int afterYear = Integer.valueOf(afterDate.substring(0,4));
		int afterMonth = Integer.valueOf(afterDate.substring(0,4));
		int afterDay = Integer.valueOf(afterDate.substring(0,4));
		
		while(a.head != null) {
			int year = Integer.valueOf(point.date.substring(0, 4));
			int month = Integer.valueOf(point.date.substring(5, 7));
			int day = Integer.valueOf(point.date.substring(8, 10));
			
			if(afterYear < year)
				aa.insertBad(point);
			else if(afterYear == year) {
				if(afterMonth < month)
					aa.insertBad(point);
				else if(afterMonth == month) {
					if(afterDay < day)
						aa.insertBad(point);
				}
			}
			point = point.next;
		}
		return aa;
	}
	public UserLinkedList searchBeforeDate(UserLinkedList a, String beforeDate) throws IOException {
		UserLinkedList aa = new UserLinkedList(false, false);
		UserNode point = a.head;
		int beforeYear = Integer.valueOf(beforeDate.substring(0,4));
		int beforeMonth = Integer.valueOf(beforeDate.substring(0,4));
		int beforeDay = Integer.valueOf(beforeDate.substring(0,4));
		
		while(a.head != null) {
			int year = Integer.valueOf(point.date.substring(0, 4));
			int month = Integer.valueOf(point.date.substring(5, 7));
			int day = Integer.valueOf(point.date.substring(8, 10));
			
			if(year < beforeYear)
				aa.insertBad(point);
			else if(year == beforeYear) {
				if(month < beforeMonth)
					aa.insertBad(point);
				else if(month == beforeMonth) {
					if(day < beforeDay)
						aa.insertBad(point);
				}
			}
			point = point.next;
		}
		return aa;
	}
	//The edit method.
	//It's the same as the search method
	//but, its parameters are an integer representing the type of data
	//it will be changing, a String containing the data edit, and
	//another String with the id of the node that will have its data
	//changed.
	//1 = edit name
	//2 = edit title
	//3 = edit email
	//4 = edit username
	//5 = edit password
	//6 = edit administrator status
	//id can't be changed because it should be a unique identifier
	public boolean edit(int typeOfData, String data, String id) throws IOException {
		UserNode point = head;
		while(point != null) {
			if(point.id.equals(id))
				break;
			point = point.next;
		}
		if(point == null)
			return false;
		switch(typeOfData) {
		case 1:
			point.name = data;
			break;
		case 2:
			point.title = data;
			break;
		case 3:
			point.email = data;
			break;
		case 4:
			point.username = data;
			break;
		case 5:
			point.password = data;
			break;
		case 6:
			point.isAdministrator = data;
			break;
		default:
			return false;
		}
		toFile();
		return true;
	}
	//Puts the contents of UserLinkedList into a textfile
	public boolean toFile() throws IOException {
		File aLL = new File("UserLinkedList.txt");
		if(!aLL.exists())
			aLL.createNewFile();
		FileWriter fw = new FileWriter(aLL);
		BufferedWriter bw = new BufferedWriter(fw);
		
		UserNode point = head;
		//first line is gonna be amountLoaded
		bw.write(Integer.toString(amountLoaded));
		while(point != null) {
			bw.newLine();
			bw.write(point.id);
			bw.newLine();
			bw.write(point.name);
			bw.newLine();
			bw.write(point.date);
			bw.newLine();
			bw.write(point.title);
			bw.newLine();
			bw.write(point.email);
			bw.newLine();
			bw.write(point.username);
			bw.newLine();
			bw.write(point.password);
			bw.newLine();
			bw.write(point.isAdministrator);
			bw.flush();
			
			point = point.next;
		}
		fw.close();
		bw.close();
		return true;
	}
	//same method like above, but the text files is meant to be exported to client programs
	//parameter is an UserLinkedList, which the search method gives.
	public boolean toFileExportToClient(UserLinkedList a) throws IOException {
		File aLL = new File("UserLinkedListExport.txt");
		if(!aLL.exists())
			aLL.createNewFile();
		FileWriter fw = new FileWriter(aLL);
		BufferedWriter bw = new BufferedWriter(fw);
		//this is what's different from the method above
		UserNode point = a.head;
		//first line is gonna be amountLoaded
		bw.write(Integer.toString(amountLoaded));
		while(point != null) {
			bw.newLine();
			bw.write(point.id);
			bw.newLine();
			bw.write(point.name);
			bw.newLine();
			bw.write(point.date);
			bw.newLine();
			bw.write(point.title);
			bw.newLine();
			bw.write(point.email);
			bw.newLine();
			bw.write(point.username);
			bw.newLine();
			bw.write(point.password);
			bw.newLine();
			bw.write(point.isAdministrator);
			bw.flush();
			
			point = point.next;
		}
		fw.close();
		bw.close();
		return true;
	}
	//reading from file  on startup - persistent data
	public boolean startupReadFile() throws IOException {
		File f = new File("UserLinkedList.txt");
		if(!f.exists())
			return false;
		Scanner s = new Scanner(f);
		UserNode inserting = new UserNode();
		int amountLoadedTemp = Integer.valueOf(s.nextLine());
		while(s.hasNextLine()) {
			inserting = new UserNode();
			inserting.id = s.nextLine();
			inserting.name = s.nextLine();
			inserting.date = s.nextLine();
			inserting.title = s.nextLine();
			inserting.email = s.nextLine();
			inserting.username = s.nextLine();
			inserting.password = s.nextLine();
			inserting.isAdministrator = s.nextLine();
			startupInsert(inserting);
			amountLoaded++;
		}
		s.close();
		amountLoaded = amountLoadedTemp;
		toFile();
		return true;
	}
	//for client programs to use
	public boolean readExportFile() throws IOException {
		File f = new File("UserLinkedListExport.txt");
		if(!f.exists())
			return false;
		Scanner s = new Scanner(f);
		UserNode inserting = new UserNode();
		int amountLoadedTemp = Integer.valueOf(s.nextLine());
		while(s.hasNextLine()) {
			inserting = new UserNode();
			inserting.id = s.nextLine();
			inserting.name = s.nextLine();
			inserting.date = s.nextLine();
			inserting.title = s.nextLine();
			inserting.email = s.nextLine();
			inserting.username = s.nextLine();
			inserting.password = s.nextLine();
			inserting.isAdministrator = s.nextLine();
			startupInsert(inserting);
			amountLoaded++;
		}
		s.close();
		amountLoaded = amountLoadedTemp;
		toFile();
		return true;
	}
}