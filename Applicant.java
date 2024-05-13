package cosc3506;

public class Applicant {
    private String ID;
    private String name;
    private String comment;
    private String review;
    private String status;

    // Constructor for Applicant
    public Applicant(String ID, String name, String comment, String review, String status) {
        this.ID = ID;
        this.name = name;
        this.comment = comment;
        this.review = review;
        this.status = status;
    }
    public String toString() {
        return this.ID + "," + this.name + "," + this.comment + "," + this.review + "," + this.status;
    }

    // Getter methods for Applicant fields
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getReview() {
        return review;
    }

    public String getStatus() {
        return status;
    }

    // Setter methods for Applicant fields
    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

