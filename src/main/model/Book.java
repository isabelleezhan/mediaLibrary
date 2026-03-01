package model;

import org.json.JSONObject;

// Represents a book in the media library
public class Book extends Media {

    private String author;

    // EFFECTS: constructs a book with the given title, consumption status, author,
    // and genre
    public Book(String title, Status status, String author, String genre) {
        super(title, status, genre);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    // EFFECTS: returns author information
    @Override
    public String getDisplayInfo() {
        return "Author: " + author;
    }

    // EFFECTS: returns a string representation of this media item
    @Override
    public String getMediaType() {
        return "Book";
    }

    // EFFECTS: returns book item written as JSON object 
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("author", author);
        return json;
    }

}
