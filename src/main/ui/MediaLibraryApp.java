package ui;

import java.util.List;
import java.util.ArrayList;
import model.*;

// A Media Library application that lets users track media items
public class MediaLibraryApp {

    // EFFECTS: creates an instance of the Medialibrary console ui
    public MediaLibraryApp() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: initializes application with an empty media library
    public void init() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: runs the main menu loop until the user chooses to quit (option 5)
    public void runMainMenu() {
        // stub
    }

    // EFFECTS: displays the main menu of options to user
    public void displayMainMenu() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: process user's main menu command
    public void processMainMenuCommands(String command) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: displays add-media menu and processes user choice
    public void runAddMediaMenu() {
        // stub
    }

    // EFFECTS: displays the menu of options of adding media items to user
    public void displayAddMediaMenu() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds a book to mediaLibrary
    public void addBook() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds a Movie to mediaLibrary
    public void addMovie() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds a TV show to mediaLibrary
    public void addTVShow() {
        // stub
    }

    // EFFECTS: prompts user for a status choice and returns the status
    public Status returnStatusChoice() {
        return Status.WANT_TO; // stub
    }

    // EFFECTS: process user's status menu
    public Status translateStatusChoice(int choice) {
        return Status.WANT_TO; // stub
    }

    // MODIFIES: this
    // EFFECTS: displays the view media menu and processes the user's command
    public void runViewMediaMenu() {
        // stub
    }

    // MODIFIES: this 
    // EFFECTS: processes the user's view menu command
    public void processViewMenuCommands(String command) {
        // stub
    }

    // EFFECTS: displays the menu of options of viewing media items to user
    public void displayViewMediaMenu() {
        // stub
    }

    // EFFECTS: prints the given list of media items
    public void printMedia(List<Media> toPrint) {
        // stub
    }

    // EFFECTS: displays the view by status menu and processes the user's command
    public void runStatusViewMenu() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: displays the type menu and processes the user's command
    public void runTypeMenu() {
        // stub
    }

    // EFFECTS: displays the books menu and processes the user's command
    public void runBooks() {
        // stub
    }

    // EFFECTS: displays the movies menu and processes the user's command
    public void runMovies() {
        // stub
    }

    // EFFECTS: displays the tv shows menu and processes the user's command
    public void runTVShows() {
        // stub
    }

    // EFFECTS: processes type menu command for given type
    public void processTypeMenuCommands(String command, String type) {
        // stub
    }

    // EFFECTS: displays the menu of options of viewing media items by type to user
    public void displayTypeMenu() {
        // stub
    }

    // MODIFIES: this, media
    // EFFECTS: displays update media menu and lets user select a media item to update
    public void runUpdateMediaMenu() {
        // stub
    }

    // MODIFIES: this, media
    // EFFECTS: processes update command and applies it to the selected media
    public void processUpdateCommand(String command, Media media) {
        //stub
    }

    // MODIFIES: media
    // EFFECTS: updates the status of the given media item and adds rating and
    // review if status is FINISHED
    public void updateStatus(Media media) {
        // stub
    }

    // EFFECTS: prompts user for an integer rating 1-5 until valid input and returns
    // that rating
    public int returnRating() {
        return 0; // stub
    }

    // REQUIRES: user ends review by entering a line equal to "end"
    // EFFECTS: reads lines from user until "end", returns concatenated string
    public String returnReview() {
        return ""; // stub
    }

    // MODIFIES: media
    // EFFECTS: if media is FINISHED, prompts for new rating and updates; otherwise
    // prints that rating isn't allowed
    public void updateRating(Media media) {
        // stub
    }

    // MODIFIES: media
    // EFFECTS: if media is FINISHED, prompts for new review and updates; otherwise
    // prints that review isn't allowed
    public void updateReview(Media media) {
        // stub
    }

    // EFFECTS: prints users statistics on completion info, average ratings by type,
    // and favourites
    public void viewStatistics() {
        // stub
    }

    // EFFECTS: prints completion rate and counts of finished items by type
    public void printCompletionInfo() {
        // stub
    }

    // EFFECTS: prints average ratings by type
    public void printAvgRatings() {
        // stub
    }

    // EFFECTS: returns list of media of the given type that have a rating of 5
    public List<Media> getFavs(String type) {
        return new ArrayList<>(); // stub
    }

    // EFFECTS: prints the type and given list of favourites, or a message if empty
    public void printFavs(String type, List<Media> favs) {
        // stub
    }

    // EFFECTS: prints 5-star items for Books, Movies, and TV Shows
    public void printFavs() {
        // stub
    }

}
