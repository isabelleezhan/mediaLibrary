package model;

// Represents a TV show in the media library
public class TVShow extends Media {
    
    // EFFECTS: constructs a TV show with the given title, consumption status, number of seasons, and genre
    public TVShow(String title, Status status, int numSeasons, String genre) {
        // stub
    }

    public int getNumSeasons() {
        return 0;
    }

    // EFFECTS: returns number of seasons 
    @Override
    public String getDisplayInfo() {
        return "";
    }

    // EFFECTS: returns a string representation of this media item
    @Override
    public String getMediaType() {
        return "";
    }

}
