package ui;

import java.util.Scanner;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

// A Media Library application that lets users track media items
@ExcludeFromJacocoGeneratedReport
public class MediaLibraryApp {

    private String username;
    private String jsonStore;
    private MediaLibrary mediaLibrary;
    private Scanner scanner;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: creates an instance of the Medialibrary console ui
    public MediaLibraryApp() {
        init();
        runMainMenu();
    }

    // MODIFIES: this
    // EFFECTS: initializes application with an empty media library of given name
    public void init() {
        this.scanner = new Scanner(System.in);
        System.out.print("Welcome to MediaLibrary!");
        System.out.println("\nEnter your username: ");
        this.username = scanner.nextLine();
        this.jsonStore = "./data/" + username + "Library.json";
        this.jsonWriter = new JsonWriter(jsonStore);
        this.jsonReader = new JsonReader(jsonStore);

        if (new File(jsonStore).exists()) {
            System.out.println("Found a saved library for " + username + ". Load it? (yes/no)");
            String choice = scanner.nextLine().toLowerCase();
            if (choice.equals("yes")) {
                loadMediaLibrary();
                System.out.println("Library loaded successfully!");
                return;
            } 
        }
        this.mediaLibrary = new MediaLibrary();
        System.out.println("Starting a new library...");
    }

    // MODIFIES: this
    // EFFECTS: runs the main menu loop until the user chooses to quit (option 5)
    public void runMainMenu() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMainMenu();
            command = scanner.nextLine();

            if (command.equals("6")) {
                System.out.println("Would you like to save your library? (yes/no)");
                String choice = scanner.nextLine().toLowerCase();
                if (choice.equals("yes")) {
                    saveMediaLibrary();
                    System.out.println("Library saved successfully!");
                }
                System.out.println("Thanks for visiting MediaLibrary :)");
                System.out.println("See you next time!");
                keepGoing = false;
            } else {
                processMainMenuCommands(command);
            }
        }
    }

    // EFFECTS: displays the main menu of options to user
    public void displayMainMenu() {
        System.out.println("\n===== MEDIA LIBRARY =====");
        System.out.println("Choose an option:");
        System.out.println("1: Log a new media item");
        System.out.println("2: View library");
        System.out.println("3: Update media item");
        System.out.println("4: See user statistics");
        System.out.println("5: Save library to file");
        System.out.println("6: Quit application");
    }

    // MODIFIES: this
    // EFFECTS: process user's main menu command
    public void processMainMenuCommands(String command) {
        switch (command) {
            case "1":
                runAddMediaMenu();
                break;
            case "2":
                runViewMediaMenu();
                break;
            case "3":
                runUpdateMediaMenu();
                break;
            case "4":
                viewStatistics();
                break;
            case "5":
                saveMediaLibrary();
                break;
            default:
                System.out.println("Invalid. Please select one of the following: 1-6");
        }
    }

    // MODIFIES: this
    // EFFECTS: displays add-media menu and processes user choice
    public void runAddMediaMenu() {
        displayAddMediaMenu();
        String command = scanner.nextLine();

        switch (command) {
            case "1":
                addBook();
                break;
            case "2":
                addMovie();
                break;
            case "3":
                addTVShow();
                break;
            case "4":
                runMainMenu();
                break;
            default:
                System.out.println("Invalid. Please select one of the following: 1 2 3 4");
        }
    }

    // EFFECTS: displays the menu of options of adding media items to user
    public void displayAddMediaMenu() {
        System.out.println("\n===== ADDING MEDIA =====");
        System.out.println("Select a media type: ");
        System.out.println("1: Book");
        System.out.println("2: Movie");
        System.out.println("3: TV Show");
        System.out.println("4: Return to Main Menu");
    }

    // MODIFIES: this
    // EFFECTS: adds a book to mediaLibrary
    public void addBook() {
        System.out.println("\nBook title: ");
        String title = this.scanner.nextLine();

        System.out.println("\nAuthor: ");
        String author = this.scanner.nextLine();

        System.out.println("\nGenre: ");
        String genre = this.scanner.nextLine();

        System.out.println("\nChoose status: ");
        Status status = returnStatusChoice();

        Media newBook = new Book(title, status, author, genre);
        if (status == Status.FINISHED) {
            System.out.println("\nWhat do you rate this (1-5)?");
            int rating = returnRating();
            newBook.setRating(rating);
            System.out.println("\nHow do you review this? (enter line \"end\" to finish review)");
            String review = returnReview();
            newBook.setReview(review);
        }
        mediaLibrary.addEntry(newBook);
        System.out.println("\nBook added successfully!");
        System.out.println("\n" + newBook);
    }

    // MODIFIES: this
    // EFFECTS: adds a Movie to mediaLibrary
    public void addMovie() {
        System.out.println("\nMovie title: ");
        String title = this.scanner.nextLine();

        System.out.println("\nDirector: ");
        String director = this.scanner.nextLine();

        System.out.println("\nGenre: ");
        String genre = this.scanner.nextLine();

        System.out.println("\nChoose status: ");
        Status status = returnStatusChoice();

        Media newMovie = new Movie(title, status, director, genre);
        if (status == Status.FINISHED) {
            System.out.println("\nWhat do you rate this (1-5)?");
            int rating = returnRating();
            newMovie.setRating(rating);
            System.out.println("\nHow do you review this? (enter line \"end\" to finish review)");
            String review = returnReview();
            newMovie.setReview(review);
        }
        mediaLibrary.addEntry(newMovie);
        System.out.println("\nMovie added successfully!");
        System.out.println("\n" + newMovie);
    }

    // MODIFIES: this
    // EFFECTS: adds a TV show to mediaLibrary
    public void addTVShow() {
        System.out.println("\nShow title: ");
        String title = this.scanner.nextLine();

        System.out.println("\nNumber of Seasons: ");
        int numSeasons = returnNumSeasons();

        System.out.println("\nGenre: ");
        String genre = this.scanner.nextLine();

        System.out.println("\nChoose status: ");
        Status status = returnStatusChoice();

        Media newTV = new TVShow(title, status, numSeasons, genre);
        if (status == Status.FINISHED) {
            System.out.println("\nWhat do you rate this (1-5)?");
            int rating = returnRating();
            newTV.setRating(rating);
            System.out.println("\nHow do you review this? (enter line \"end\" to finish review)");
            String review = returnReview();
            newTV.setReview(review);
        }
        mediaLibrary.addEntry(newTV);
        System.out.println("\nTV Show added successfully!");
        System.out.println("\n" + newTV);
    }

    // MODIFIES: this
    // EFFECTS: displays the view media menu and processes the user's command
    public void runViewMediaMenu() {
        displayViewMediaMenu();
        String command = scanner.nextLine();
        processViewMenuCommands(command);
    }

    // EFFECTS: displays the menu of options of viewing media items to user
    public void displayViewMediaMenu() {
        System.out.println("\n===== VIEWING MEDIA =====");
        System.out.println("Filter view: ");
        System.out.println("1: View all media");
        System.out.println("2: View by type: Book/Movie/TV");
        System.out.println("3: View by status");
        System.out.println("4: Return to Main Menu");
    }

    // MODIFIES: this
    // EFFECTS: processes the user's view menu command
    public void processViewMenuCommands(String command) {
        switch (command) {
            case "1":
                if (mediaLibrary.getAllMedia().isEmpty()) {
                    System.out.println("\nYour library is empty.");
                } else {
                    System.out.println("\n===== All Media =====");
                    printMedia(mediaLibrary.getAllMedia());
                }
                break;
            case "2":
                runTypeMenu();
                break;
            case "3":
                runStatusViewMenu();
                break;
            case "4":
                runMainMenu();
                break;
            default:
                System.out.println("Invalid. Please select one of the following: 1 2 3 4");
        }
    }

    // EFFECTS: prints the given list of media items
    public void printMedia(List<Media> toPrint) {
        if (toPrint.isEmpty()) {
            System.out.println("\nNo media logged. Try logging a new media item!");
        } else {
            for (int i = 0; i < toPrint.size(); i++) {
                System.out.println("" + (i + 1) + ": ");
                System.out.println(toPrint.get(i));
            }
        }
    }

    // EFFECTS: displays the view by status menu and processes the user's command
    public void runStatusViewMenu() {
        System.out.println("\n----- Select Status -----");
        Status command = returnStatusChoice();

        switch (command) {
            case WANT_TO:
                System.out.println("\n===== Want to Read/Watch =====");
                printMedia(mediaLibrary.filterByStatus(command));
                break;
            case IN_PROGRESS:
                System.out.println("\n===== Currently Reading/Watching =====");
                printMedia(mediaLibrary.filterByStatus(command));
                break;
            case FINISHED:
                System.out.println("\n===== Finished =====");
                printMedia(mediaLibrary.filterByStatus(command));
                break;
            case DNF:
                System.out.println("\n===== Did Not Finish =====");
                printMedia(mediaLibrary.filterByStatus(command));
                break;
            default:
                System.out.println("Invalid. Please select one of the following: 1 2 3 4");
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the type menu and processes the user's command
    public void runTypeMenu() {
        displayTypeMenu();
        String command = scanner.nextLine();

        switch (command) {
            case "1":
                runBooks();
                break;
            case "2":
                runMovies();
                break;
            case "3":
                runTVShows();
                break;
            case "4":
                runViewMediaMenu();
                break;
            default:
                System.out.println("Invalid. Please select one of the following: 1 2 3 4");
        }
    }

    // EFFECTS: displays the menu of options of viewing media items by type to user
    public void displayTypeMenu() {
        System.out.println("\n----- Select Media Type -----");
        System.out.println("1: Books");
        System.out.println("2: Movies");
        System.out.println("3: TV Shows");
        System.out.println("4: Return to previous Menu");
    }

    // EFFECTS: displays the books menu and processes the user's command
    public void runBooks() {
        System.out.println("\n===== Books =====");
        System.out.println("1: View all Books");
        System.out.println("2: Filter Books by Status");
        String command = scanner.nextLine();
        processTypeMenuCommands(command, "Book");
    }

    // EFFECTS: displays the movies menu and processes the user's command
    public void runMovies() {
        System.out.println("\n===== Movies =====");
        System.out.println("1: View all Movies");
        System.out.println("2: Filter Movies by Status");
        String command = scanner.nextLine();
        processTypeMenuCommands(command, "Movie");
    }

    // EFFECTS: displays the tv shows menu and processes the user's command
    public void runTVShows() {
        System.out.println("\n===== TV Shows =====");
        System.out.println("1: View all TV Shows");
        System.out.println("2: Filter TV Shows by Status");
        String command = scanner.nextLine();

        processTypeMenuCommands(command, "TV Show");
    }

    // EFFECTS: processes type menu command for given type
    public void processTypeMenuCommands(String command, String type) {
        switch (command) {
            case "1":
                System.out.println("\n----- " + "All " + type + "s -----");
                printMedia(mediaLibrary.filterByType(type));
                break;
            case "2":
                System.out.println("\nChoose status to filter by: ");
                Status status = returnStatusChoice();
                System.out.println("\n----- " + type + "s: " + status.getLabel() + " -----");
                printMedia(mediaLibrary.filterByTypeAndStatus(type, status));
                break;
            default:
                System.out.println("Invalid. Please select one of the following: 1 2");
        }
    }

    // MODIFIES: this, media
    // EFFECTS: displays update media menu and lets user select a media item to
    // update
    public void runUpdateMediaMenu() {
        System.out.println("\n===== UPDATE MEDIA =====");
        if (mediaLibrary.getAllMedia().isEmpty()) {
            System.out.println("\nYour library is empty. Try logging a new media item before updating!");
            return;
        }
        Media selected = returnUpdateMediaNum();

        System.out.println("\nWhat do you want to update?");
        System.out.println("1: Status");
        System.out.println("2: Rating");
        System.out.println("3: Review");
        System.out.println("4: Delete Entry");
        String command = scanner.nextLine();
        processUpdateCommand(command, selected);
    }

    // REQUIRES: media library is not empty
    // EFFECTS: prompts user for media number until recieves int between 1 and size
    // of library,
    // and returns that media ite,
    public Media returnUpdateMediaNum() {
        List<Media> lib = mediaLibrary.getAllMedia();
        printMedia(lib);
        System.out.println("Enter the number of the media to update: ");
        int choice = 0;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= lib.size()) {
                    break;
                }
                System.out.println("Invalid choice. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter an integer.");
            }
        }
        return lib.get(choice - 1);
    }

    // MODIFIES: this, media
    // EFFECTS: processes update command and applies it to the selected media
    public void processUpdateCommand(String command, Media media) {
        switch (command) {
            case "1":
                updateStatus(media);
                break;
            case "2":
                updateRating(media);
                break;
            case "3":
                updateReview(media);
                break;
            case "4":
                mediaLibrary.deleteEntry(media);
                System.out.println("\nEntry deleted successfully!");
                break;
            default:
                System.out.println("Invalid. Please select one of the following: 1 2 3 4");
        }
    }

    // MODIFIES: media
    // EFFECTS: updates the status of the given media item and adds rating and
    // review if status is FINISHED
    public void updateStatus(Media media) {
        System.out.println("\nChoose your updated status: ");
        Status status = returnStatusChoice();
        media.setStatus(status);
        if (status == Status.FINISHED) {
            System.out.println("\nWhat do you rate this (1-5)?");
            int rating = returnRating();
            media.setRating(rating);
            System.out.println("\nHow do you review this? (enter line \"end\" to finish review)");
            String review = returnReview();
            media.setReview(review);
        }
        System.out.println("\nStatus updated successfully!");
        System.out.println("\nYour updated entry: ");
        System.out.println(media);
    }

    // MODIFIES: media
    // EFFECTS: if media is FINISHED, prompts for new rating and updates; otherwise
    // prints that rating isn't allowed
    public void updateRating(Media media) {
        if (media.getStatus() != Status.FINISHED) {
            System.out.println("You didn't finish this yet... can't rate!");
            return;
        }
        System.out.println("\nEnter your new rating: ");
        int rating = returnRating();
        media.setRating(rating);
        System.out.println("\nRating updated successfully!");
        System.out.println("\nYour updated entry: ");
        System.out.println(media);
    }

    // MODIFIES: media
    // EFFECTS: if media is FINISHED, prompts for new review and updates; otherwise
    // prints that review isn't allowed
    public void updateReview(Media media) {
        if (media.getStatus() != Status.FINISHED) {
            System.out.println("You didn't finish this yet... can't review!");
            return;
        }
        System.out.println("\nEnter your new review (enter line \"end\" to finish review): ");
        String review = returnReview();
        media.setReview(review);
        System.out.println("\nReview updated successfully!");
        System.out.println("\nYour updated entry: ");
        System.out.println(media);
    }

    // EFFECTS: prints users statistics on completion info, average ratings by type,
    // and favourites
    public void viewStatistics() {
        System.out.println("\n===== Library Statistics =====");
        printCompletionInfo();
        System.out.println();
        System.out.println("--------------------------");
        printAvgRatings();
        System.out.println();
        System.out.println("--------------------------");
        printFavsByType();
    }

    // EFFECTS: prints completion rate and counts of finished items by type
    public void printCompletionInfo() {
        double percent = (double) mediaLibrary.getNumFinished() / mediaLibrary.getTotalNumberItems() * 100;
        System.out.println("\nWhat is your completion rate?");
        if (mediaLibrary.getTotalNumberItems() == 0) {
            System.out.println("Percentage of items finished: N/A (no media logged)");
        } else {
            System.out.println("Percentage of items finished: " + percent + "%");
        }
        System.out.println("Number of books finished: "
                + mediaLibrary.filterByTypeAndStatus("Book", Status.FINISHED).size());
        System.out.println("Number of movies finished: "
                + mediaLibrary.filterByTypeAndStatus("Movie", Status.FINISHED).size());
        System.out.println("Number of tv shows finished: "
                + mediaLibrary.filterByTypeAndStatus("TV Show", Status.FINISHED).size());
    }

    // EFFECTS: prints all average ratings by type, or N/A if no finished items of
    // that type
    public void printAvgRatings() {
        System.out.println("\nYour Average Ratings");
        double bookAvg = mediaLibrary.getAverageRating(mediaLibrary.filterByTypeAndStatus("Book", Status.FINISHED));
        double movieAvg = mediaLibrary.getAverageRating(mediaLibrary.filterByTypeAndStatus("Movie", Status.FINISHED));
        double tvAvg = mediaLibrary.getAverageRating(mediaLibrary.filterByTypeAndStatus("TV Show", Status.FINISHED));
        printAvgRatingByType("Book", bookAvg);
        printAvgRatingByType("Movie", movieAvg);
        printAvgRatingByType("TV Show", tvAvg);
    }

    // EFFECTS: prints average of given type, or N/A if average is 0.0
    // (no finished items of that type)
    public void printAvgRatingByType(String type, double avg) {
        if (avg == 0.0) {
            System.out.println("By " + type + "s: N/A");
        } else {
            System.out.println("By " + type + "s: " + avg);
        }
    }

    // EFFECTS: prints 5-star items for Books, Movies, and TV Shows
    public void printFavsByType() {
        List<Media> favBooks = getFavs("Book");
        List<Media> favMovies = getFavs("Movie");
        List<Media> favShows = getFavs("TV Show");

        System.out.println("\nYour Overall Favourites");
        System.out.println("Everything you thought deserved 5 stars...");
        printFavs("Book", favBooks);
        printFavs("Movie", favMovies);
        printFavs("TV Show", favShows);
    }

    // EFFECTS: returns list of media of the given type that have a rating of 5
    public List<Media> getFavs(String type) {
        List<Media> favs = new ArrayList<Media>();
        for (Media m : mediaLibrary.filterByType(type)) {
            if (m.getRating() == 5) {
                favs.add(m);
            }
        }
        return favs;
    }

    // EFFECTS: prints the type and given list of favourites, or a message if empty
    public void printFavs(String type, List<Media> favs) {
        System.out.println("\nFavourite " + type + "s: ");
        if (favs.isEmpty()) {
            System.out.println("No Favourites yet :(");
        } else {
            printMedia(favs);
        }
    }

    // EFFECTS: prompts user for a status choice and returns the status
    public Status returnStatusChoice() {
        System.out.println("1: " + Status.WANT_TO.getLabel());
        System.out.println("2: " + Status.IN_PROGRESS.getLabel());
        System.out.println("3: " + Status.FINISHED.getLabel());
        System.out.println("4: " + Status.DNF.getLabel());
        while (true) {
            try {
                int choice = Integer.parseInt(this.scanner.nextLine());
                if (choice >= 1 && choice <= 4) {
                    return processStatusChoice(choice);
                }
                System.out.println("Invalid choice. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // EFFECTS: process user's status menu
    public Status processStatusChoice(int choice) {
        switch (choice) {
            case 1:
                return Status.WANT_TO;
            case 2:
                return Status.IN_PROGRESS;
            case 3:
                return Status.FINISHED;
            default:
                return Status.DNF;
        }
    }

    // EFFECTS: prompts user for rating until recieves integer rating between 1-5
    // and returns that rating
    public int returnRating() {
        int rating = 0;
        boolean validRating = false;

        while (!validRating) {
            String input = scanner.nextLine();
            try {
                int temp = Integer.parseInt(input);
                if (temp < 1 || temp > 5) {
                    System.out.println("Invalid rating. Please enter an integer between 1-5: ");
                } else {
                    rating = temp;
                    validRating = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer between 1-5: ");
            }
        }
        return rating;
    }

    // REQUIRES: user ends review by entering a line equal to "end"
    // EFFECTS: reads lines from user until "end" and returns review string
    public String returnReview() {
        String review = "";
        String line = scanner.nextLine();

        while (!line.equals("end")) {
            review += line + "\n";
            line = scanner.nextLine();
        }
        return review;
    }

    // EFFECTS: prompts user for number of seasons until recieves a non-negative
    // integer, and returns that number
    public int returnNumSeasons() {
        while (true) {
            try {
                int num = Integer.parseInt(scanner.nextLine());
                if (num >= 0) {
                    return num;
                }
                System.out.println("Invalid input. Please enter a non-negative integer: ");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer: ");
            }
        }
    }

    // Based on: JsonSerializationDemo
    // EFFECTS: saves the mediaLibrary to file in jsonStore;
    // prints error message if unable to write
    private void saveMediaLibrary() {
        try {
            jsonWriter.open();
            jsonWriter.write(mediaLibrary);
            jsonWriter.close();
            System.out.println("Saved library to " + jsonWriter.getFilePath());
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + jsonWriter.getFilePath());
        }
    }

    // MODIFIES: this
    // EFFECTS: loads mediaLibrary from file in jsonStore;
    // prints error message if unable to read
    private void loadMediaLibrary() {
        try {
            mediaLibrary = jsonReader.read();
            System.out.println("Loaded library from " + jsonReader.getSource());
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + jsonReader.getSource());
        }
    }

}