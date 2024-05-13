import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ApplicantLinkedList {
	//constructor
	ApplicantLinkedList(boolean iM, boolean isExport) throws IOException {
		isMain = iM;
		if(isMain)
			startupReadFile();
		//this will check to see if the current instance of ApplicantLinkedList needs to read from the export file
		if(isExport)
			readExportFile();
	}
	//basic linked list methods
	protected ApplicantNode head;
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
		ApplicantNode point = head;
		while(point != null) {
			counter++;
			point = point.next;
		}
		return counter;
	}
	public boolean insertBad(ApplicantNode n) throws IOException {
		if(isEmpty()) {
			head = n;
			amountLoaded++;
			return true;
		}
		ApplicantNode point = head;
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
		ApplicantNode point = head;
		for(int a = 0; a < n - 1; a++)
			point = point.next;
		ApplicantNode temp = point.next;
		point.next = temp;
		return true;
	}
	public void destroy() {
		head = null;
	}
	//Adding a Node
	//The only parameter is the name
	public boolean insert(String n) throws IOException {
		ApplicantNode inserting = new ApplicantNode();
		inserting.id = "a" + Integer.toString(amountLoaded);
		inserting.name = n;
		inserting.date = java.time.LocalDateTime.now().toString().substring(0, 10);
		insertBad(inserting);
		return true;
	}
	//another insert method
	//this is for startup purposes which will bypass the toFile() method that every insert method has
	public boolean startupInsert(ApplicantNode n) throws IOException {
		if(isEmpty()) {
			head = n;
			amountLoaded++;
			return true;
		}
		ApplicantNode point = head;
		while(point.next != null)
			point = point.next;
		point.next = n;
		amountLoaded++;
		return true;
	}
	//The very big search method
	//The first two parameters, an integer and String, represents the type of data being searched for an the data being searched for
	//1 = search everything
	//2 = search id
	//3 = search name
	//4 = search comments
	//5 = search reviews
	//6 = search status
	//The last three parameters are a boolean and two Strings which represents whether there are date search constraints or not and what
	//these constraints are
	//Additionally, after the search is complete, the results of the search will be pasted into a text file
	public ApplicantLinkedList search(int typeOfData, String data, boolean searchDate, String afterDate, String beforeDate) throws IOException {
		ApplicantLinkedList a = new ApplicantLinkedList(false, false);
		ApplicantNode point = head;
		ApplicantNode newNode;
		boolean isMatch;
		while(point != null) {
			isMatch = false;
			switch(typeOfData) {
				case 1:
					if(point.id.contains(data) || point.name.contains(data) || point.comment.contains(data) || point.review.contains(data) || point.status.contains(data))
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
					if(point.comment.contains(data))
						isMatch = true;
					break;
				case 5:
					if(point.review.contains(data))
						isMatch = true;
					break;
				case 6:
					if(point.status.contains(data))
						isMatch = true;
					break;
			}
			//create clones of object because we don't want all the nodes connected to it
			if(isMatch) {
				newNode = new ApplicantNode();
				newNode.id = point.id;
				newNode.name = point.name;
				newNode.date = point.date;
				newNode.comment = point.comment;
				newNode.review = point.review;
				newNode.status = point.status;
				a.insertBad(newNode);
			}
			point = point.next;
		}
		if(searchDate)
			a = searchYesDate(a, afterDate, beforeDate);
		toFileExportToClient(a);
		return a;
	}
	public ApplicantLinkedList searchYesDate(ApplicantLinkedList a, String afterDate, String beforeDate) throws IOException {
		ApplicantLinkedList aa = new ApplicantLinkedList(false, false);
		if(afterDate != null && beforeDate != null)
			aa = searchAfterDate(searchBeforeDate(a, beforeDate), afterDate);
		else if(afterDate != null)
			aa = searchAfterDate(a, afterDate);
		else if(beforeDate != null)
			aa = searchBeforeDate(a, beforeDate);
		return aa;
	}
	public ApplicantLinkedList searchAfterDate(ApplicantLinkedList a, String afterDate) throws IOException {
		ApplicantLinkedList aa = new ApplicantLinkedList(false, false);
		ApplicantNode point = a.head;
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
	public ApplicantLinkedList searchBeforeDate(ApplicantLinkedList a, String beforeDate) throws IOException {
		ApplicantLinkedList aa = new ApplicantLinkedList(false, false);
		ApplicantNode point = a.head;
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
	//2 = edit comments
	//3 = edit reviews
	//4 = edit status
	//id can't be changed because it should be a unique identifier
	public boolean edit(int typeOfData, String data, String id) throws IOException {
		ApplicantNode point = head;
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
			point.comment = data;
			break;
		case 3:
			point.review = data;
			break;
		case 4:
			point.status = data;
			break;
		default:
			return false;
		}
		toFile();
		return true;
	}
	//Puts the contents of ApplicantLinkedList into a textfile
	public boolean toFile() throws IOException {
		File aLL = new File("ApplicantLinkedList.txt");
		if(!aLL.exists())
			aLL.createNewFile();
		FileWriter fw = new FileWriter(aLL);
		BufferedWriter bw = new BufferedWriter(fw);
		
		ApplicantNode point = head;
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
			bw.write(point.comment);
			bw.newLine();
			bw.write(point.review);
			bw.newLine();
			bw.write(point.status);
			bw.flush();
			
			point = point.next;
		}
		fw.close();
		bw.close();
		return true;
	}
	//same method like above, but the text files is meant to be exported to client programs
	//parameter is an ApplicantLinkedList, which the search method gives.
	public boolean toFileExportToClient(ApplicantLinkedList a) throws IOException {
		File aLL = new File("ApplicantLinkedListExport.txt");
		if(!aLL.exists())
			aLL.createNewFile();
		FileWriter fw = new FileWriter(aLL);
		BufferedWriter bw = new BufferedWriter(fw);
		//this is what's different from the method above
		ApplicantNode point = a.head;
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
			bw.write(point.comment);
			bw.newLine();
			bw.write(point.review);
			bw.newLine();
			bw.write(point.status);
			bw.flush();
			
			point = point.next;
		}
		fw.close();
		bw.close();
		return true;
	}
	//reading from file  on startup - persistent data
	public boolean startupReadFile() throws IOException {
		File f = new File("ApplicantLinkedList.txt");
		if(!f.exists())
			return false;
		Scanner s = new Scanner(f);
		ApplicantNode inserting = new ApplicantNode();
		int amountLoadedTemp = Integer.valueOf(s.nextLine());
		while(s.hasNextLine()) {
			inserting = new ApplicantNode();
			inserting.id = s.nextLine();
			inserting.name = s.nextLine();
			inserting.date = s.nextLine();
			inserting.comment = s.nextLine();
			inserting.review = s.nextLine();
			inserting.status = s.nextLine();
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
		File f = new File("ApplicantLinkedListExport.txt");
		if(!f.exists())
			return false;
		Scanner s = new Scanner(f);
		ApplicantNode inserting = new ApplicantNode();
		int amountLoadedTemp = Integer.valueOf(s.nextLine());
		while(s.hasNextLine()) {
			inserting = new ApplicantNode();
			inserting.id = s.nextLine();
			inserting.name = s.nextLine();
			inserting.date = s.nextLine();
			inserting.comment = s.nextLine();
			inserting.review = s.nextLine();
			inserting.status = s.nextLine();
			startupInsert(inserting);
			amountLoaded++;
		}
		s.close();
		amountLoaded = amountLoadedTemp;
		toFile();
		return true;
	}
}