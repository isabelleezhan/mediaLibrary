package model;

// Represents a movie in the media library
public class Movie extends Media {

    private String director;

    // EFFECTS: constructs a movie with the given title, consumption status,
    // director, and genre
    public Movie(String title, Status status, String director, String genre) {
        super(title, status, genre);
        this.director = director;
    }

    public String getDirector() {
        return director;
    }

    // EFFECTS: returns director information
    @Override
    public String getDisplayInfo() {
        return "Director: " + director;
    }

    // EFFECTS: returns a string representation of this media item
    @Override
    public String getMediaType() {
        return "Movie";
    }

}
