package model;

// represents the consumption status of a media entry
public enum Status {
    WANT_TO("Want to Read/Watch"),
    IN_PROGRESS("Currently Reading/Watching"),
    FINISHED("Finished"),
    DNF("Did Not Finish");

    private String label; 

    // EFFECTS: constructs a status constant with the given label
    private Status(String label) {
        this.label = label; 
    }

    // EFFECTS: returns the display label for this status
    public String getLabel() {
        return label; 
    }
}
