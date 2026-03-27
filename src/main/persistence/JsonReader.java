package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Based on: JsonSerializationDemo
// Represents a reader that reads mediaLibrary from JSON data stored in file
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader that reads from source file
    public JsonReader(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    // EFFECTS: reads media library from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MediaLibrary read() throws IOException {
        String content = readFile(source);
        JSONObject jsonObject = new JSONObject(content);
        return parseMediaLibrary(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses media library from JSON object and returns it
    private MediaLibrary parseMediaLibrary(JSONObject json) {
        MediaLibrary ml = new MediaLibrary();
        addMediaItems(ml, json);
        return ml;
    }

    // MODIFIES: ml
    // EFFECTS: parses mediaItems from JSON object and adds them to ml
    private void addMediaItems(MediaLibrary ml, JSONObject json) {
        JSONArray mediaItems = json.getJSONArray("media");
        for (Object mediaJson : mediaItems) {
            JSONObject nextMedia = (JSONObject) mediaJson;
            addMedia(ml, nextMedia);
        }
    }

    // MODIFIES: ml
    // EFFECTS: parses media item from JSON object and adds it to ml
    private void addMedia(MediaLibrary ml, JSONObject json) {
        String title = json.getString("title");
        String genre = json.getString("genre");
        Status status = Status.valueOf(json.getString("status"));

        Media media = setTypeSpecificFields(json, title, genre, status);
        setOptionalFields(json, media);

        ml.addEntryQuiet(media);
    }

    // MODIFIES: media
    // EFFECTS: parses rating, review, and cover image from json and
    // adds it to media, if exist
    private void setOptionalFields(JSONObject json, Media media) {
        if (json.has("rating")) {
            int rating = json.getInt("rating");
            media.setRatingQuiet(rating);
        }
        if (json.has("review")) {
            String review = json.getString("review");
            media.setReviewQuiet(review);
        }
        if (json.has("coverImagePath")) {
            String path = json.getString("coverImagePath");
            if (!path.isEmpty()) {
                media.setCoverImagePath(path);
            }
        }
    }

    // EFFECTS: parses media item from json with title, genre, status, and
    // type-specific fields
    private Media setTypeSpecificFields(JSONObject json, String title, String genre, Status status) {
        Media media;
        if (json.has("author")) {
            String author = json.getString("author");
            media = new Book(title, status, author, genre);
        } else if (json.has("director")) {
            String director = json.getString("director");
            media = new Movie(title, status, director, genre);
        } else {
            int numSeasons = json.getInt("number of seasons");
            media = new TVShow(title, status, numSeasons, genre);
        }
        return media;
    }
}