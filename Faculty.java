package cosc3506;

public class Faculty {
    private String ID;
    private String name;
    private String committeeChair;
    private String startingDate;
    private String endingDate;

    // Constructor for Faculty
    public Faculty(String ID, String name, String committeeChair, String startingDate, String endingDate) {
        this.ID = ID;
        this.name = name;
        this.committeeChair = committeeChair;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
    }
    
    public String toString() {
        return this.ID + "," + this.name + "," + this.committeeChair + "," +
               this.startingDate + "," + this.endingDate;
    }

    // Getter methods for Faculty fields
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getCommitteeChair() {
        return committeeChair;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    // Setter methods for Faculty fields
    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCommitteeChair(String committeeChair) {
        this.committeeChair = committeeChair;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }
}