package model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

import java.util.ArrayList;

// A collection of media entries (books, movies, TV shows) that supports
// adding, removing, and filtering by type and consumption status.
public class MediaLibrary implements Writable {

    private List<Media> allMedia;

    // EFFECTS: initializes an empty media library
    public MediaLibrary() {
        this.allMedia = new ArrayList<Media>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given media item to the library
    public void addEntry(Media entry) {
        allMedia.add(entry);
    }

    // REQUIRES: entry is in library
    // MODIFIES: this
    // EFFECTS: removes the given media item from the library
    public void deleteEntry(Media entry) {
        allMedia.remove(entry);
    }

    // REQUIRES: type is one of "Book", "Movie", "TV Show"
    // EFFECTS: returns a new list of all media in this library whose type equals
    // type
    public List<Media> filterByType(String type) {
        List<Media> filteredList = new ArrayList<Media>();
        for (Media m : allMedia) {
            if (m.getMediaType() == type) {
                filteredList.add(m);
            }
        }
        return filteredList;
    }

    // REQUIRES: type is one of "Book", "Movie", "TV Show"
    // EFFECTS: returns a new list of all media in this library with the given
    // status
    public List<Media> filterByStatus(Status status) {
        List<Media> filteredList = new ArrayList<Media>();
        for (Media m : allMedia) {
            if (m.getStatus() == status) {
                filteredList.add(m);
            }
        }
        return filteredList;
    }

    // REQUIRES: type is one of "Book", "Movie", "TV Show"
    // EFFECTS: returns a new list of all media in this library whose type equals
    // type and whose status equals status
    public List<Media> filterByTypeAndStatus(String type, Status status) {
        List<Media> filteredList = new ArrayList<Media>();
        for (Media m : allMedia) {
            if (m.getMediaType() == type && m.getStatus() == status) {
                filteredList.add(m);
            }
        }
        return filteredList;
    }

    // REQUIRES: mediaList is not null and all items must be of status "Finished"
    // EFFECTS: returns the average rating of all finished media items in the list
    // or returns 0.0 if there are no ratings
    public double getAverageRating(List<Media> mediaList) {
        double totalRating = 0.0;
        if (mediaList.size() == 0) {
            return totalRating;
        }
        for (Media m : mediaList) {
            totalRating += m.getRating();
        }
        return totalRating / mediaList.size();
    }

    // EFFECTS: returns number of finished items in this library
    public int getNumFinished() {
        return filterByStatus(Status.FINISHED).size();
    }

    // EFFECTS: returns the total number of media items in this library
    public int getTotalNumberItems() {
        return allMedia.size();
    }

    // getters - don't need specification
    public List<Media> getAllMedia() {
        return allMedia;
    }

    // EFFECTS: returns media library as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("media", mediasToJson());
        return json;
    }

    // EFFECTS: returns media in this media library as JSON array
    private JSONArray mediasToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Media m : allMedia) {
            jsonArray.put(m.toJson());
        }
        return jsonArray;
    }
}
