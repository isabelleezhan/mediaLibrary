package model;

import java.util.List;
import java.util.ArrayList;

// A collection of media entries (books, movies, TV shows) that supports
// adding, removing, and filtering by type and consumption status.
public class MediaLibrary {

    // EFFECTS: initializes an empty media library
    public MediaLibrary() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds the given media item to the library
    public void addEntry(Media entry) {
        // stub
    }

    // REQUIRES: entry is in library
    // MODIFIES: this
    // EFFECTS: removes the given media item from the library
    public void deleteEntry(Media entry) {
        // stub
    }

    // REQUIRES: type is one of "Book", "Movie", "TV Show"
    // EFFECTS: returns a new list of all media in this library whose type equals type
    public List<Media> filterByType(String type) {
        return new ArrayList<>(); // stub
    }

    // REQUIRES: type is one of "Book", "Movie", "TV Show"
    // EFFECTS: returns a new list of all media in this library with the given status
    public List<Media> filterByStatus(Status status) {
        return new ArrayList<>(); // stub
    }

    // REQUIRES: type is one of "Book", "Movie", "TV Show"
    // EFFECTS: returns a new list of all media in this library whose type equals type
    //          and whose status equals status
    public List<Media> filterByTypeAndStatus(String type, Status status) {
        return new ArrayList<>(); // stub
    }

    // REQUIRES: mediaList is not null and all items must be of status "Finished"
    // EFFECTS: returns the average rating of all finished media items in the list
    // or returns 0.0 if there are no ratings
    public double getAverageRating(List<Media> mediaList) {
        return 0.0; // stub
    }

    // EFFECTS: returns number of finished items in this library
    public int getNumFinished() {
        return 0; // stub
    }

    // EFFECTS: returns the total number of media items in this library
    public int getTotalNumberItems() {
        return 0; // stub
    }

    // getters - don't need specification
    public List<Media> getAllMedia() {
        return new ArrayList<>(); // stub
    }

}
