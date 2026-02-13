package model;

// Represents a TV show in the media library
public class TVShow extends Media {

    private int numSeasons;

    // EFFECTS: constructs a TV show with the given title, consumption status,
    // number of seasons, and genre
    public TVShow(String title, Status status, int numSeasons, String genre) {
        super(title, status, genre);
        this.numSeasons = numSeasons;
    }

    public int getNumSeasons() {
        return numSeasons;
    }

    // EFFECTS: returns number of seasons
    @Override
    public String getDisplayInfo() {
        return "Number of Seasons: " + numSeasons;
    }

    // EFFECTS: returns a string representation of this media item
    @Override
    public String getMediaType() {
        return "TV Show";
    }

}
