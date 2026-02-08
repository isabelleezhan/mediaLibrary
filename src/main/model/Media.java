package model;

// Represents a single media entry (book/movie/tvshow) having a title,
// genre, consumption status, and optional rating (1-5) + review
public abstract class Media {

    // EFFECTS: constructs media item with title, genre, consumption status, empty
    // review, and zero rating
    public Media(String title, Status status, String genre) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: if 1 <= rating <= 5, then rating of media is set to rating,
    // otherwise is unchanged
    public void setRating(int rating) {
        // stub
    }

    // setters - don't need specification
    public void setReview(String review) {
        // stub
    }

    public void setStatus(Status status) {
        // stub
    }

    // getters - don't need specification
    public String getTitle() {
        return "";
    }

    public String getGenre() {
        return "";
    }

    public Status getStatus() {
        return Status.WANT_TO;
    }

    public String getReview() {
        return "";
    }

    public int getRating() {
        return 0;
    }

    // EFFECTS: returns the media type name (e.g. "Book", "Movie", "TV Show")
    public abstract String getMediaType();

    // EFFECTS: returns type-specific display info for this media
    public abstract String getDisplayInfo();

    // EFFECTS: returns a summary string of this media item
    @Override
    public String toString() {
        return "";
    }

}
