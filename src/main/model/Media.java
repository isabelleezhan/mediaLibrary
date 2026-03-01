package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a single media entry (book/movie/tvshow) having a title,
// genre, consumption status, and rating + review
public abstract class Media implements Writable {

    private String title;
    private String genre;
    private Status status;
    private String review;
    private int rating;

    // EFFECTS: constructs media item with title, genre, consumption status, empty
    // review, and zero rating
    public Media(String title, Status status, String genre) {
        this.title = title;
        this.genre = genre;
        this.status = status;
        this.review = null;
        this.rating = 0; // no rating
    }

    // MODIFIES: this
    // EFFECTS: if 1 <= rating <= 5, then rating of media is set to rating,
    // otherwise is unchanged
    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.rating = rating;
        }
    }

    // setters - don't need specification
    public void setReview(String review) {
        this.review = review;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // getters - don't need specification
    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public Status getStatus() {
        return status;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }

    // EFFECTS: returns the media type name (e.g. "Book", "Movie", "TV Show")
    public abstract String getMediaType();

    // EFFECTS: returns type-specific display info for this media
    public abstract String getDisplayInfo();

    // EFFECTS: returns a summary string of this media item
    @Override
    public String toString() {
        String info = getMediaType() + ": " + title + "\n"
                + "Genre: " + genre + "\n"
                + getDisplayInfo() + "\n"
                + "Status - " + status.getLabel() + "\n";

        if (status == Status.FINISHED) {
            if (review == null || review.equals("")) {
                return info + "Rating - " + rating + " stars\n";
            } else {
                return info + "Rating - " + rating + " stars\n"
                        + "Review: " + review;
            }
        } else {
            return info;
        }
    }

    // EFFECTS: returns media item written as JSON object 
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("genre", genre);
        json.put("status", status);
        json.put("review", review);
        json.put("rating", rating);
        return json;
    }
    
}
