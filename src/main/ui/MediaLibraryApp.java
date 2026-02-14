package ui;

import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;
import model.*;

// A Media Library application that lets users track media items
public class MediaLibraryApp {

    private MediaLibrary mediaLibrary;
    private Scanner scanner;

    // EFFECTS: creates an instance of the Medialibrary console ui
    public MediaLibraryApp() {
        init();
        runMainMenu();
    }

    // MODIFIES: this
    // EFFECTS: initializes application with an empty media library
    public void init() {
        this.mediaLibrary = new MediaLibrary();
        this.scanner = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: runs the main menu loop until the user chooses to quit (option 5)
    public void runMainMenu() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMainMenu();
            command = scanner.nextLine();

            if (command.equals("5")) {
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
        System.out.println("5: Quit application");
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
            default:
                System.out.println("Invalid. Please select one of the following: 1 2 3 4 5");
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
        int numSeasons = Integer.parseInt(this.scanner.nextLine());

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

    // EFFECTS: prompts user for a status choice and returns the status
    public Status returnStatusChoice() {
        System.out.println("1: " + Status.WANT_TO.getLabel());
        System.out.println("2: " + Status.IN_PROGRESS.getLabel());
        System.out.println("3: " + Status.FINISHED.getLabel());
        System.out.println("4: " + Status.DNF.getLabel());
        int choice = Integer.parseInt(this.scanner.nextLine());
        while (choice < 1 || choice > 4) {
            System.out.println("Invalid choice. Try again.");
            choice = Integer.parseInt(this.scanner.nextLine());
        }
        return processStatusChoice(choice);
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

    // MODIFIES: this
    // EFFECTS: displays the view media menu and processes the user's command
    public void runViewMediaMenu() {
        displayViewMediaMenu();
        String command = scanner.nextLine();
        processViewMenuCommands(command);
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

    // EFFECTS: displays the menu of options of viewing media items to user
    public void displayViewMediaMenu() {
        System.out.println("\n===== VIEWING MEDIA =====");
        System.out.println("Filter view: ");
        System.out.println("1: View all media");
        System.out.println("2: View by type: Book/Movie/TV");
        System.out.println("3: View by status");
        System.out.println("4: Return to Main Menu");
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

    // MODIFIES: this
    // EFFECTS: displays the view by status menu and processes the user's command
    public void runStatusViewMenu() {
        System.out.println("\n----- Select Status -----");
        Status command = returnStatusChoice();

        switch (command) {
            case WANT_TO:
                System.out.println("\n===== WANT TO READ/WATCH =====");
                printMedia(mediaLibrary.filterByStatus(command));
                break;
            case IN_PROGRESS:
                System.out.println("\n===== CURRENTLY READING/WATCHING =====");
                printMedia(mediaLibrary.filterByStatus(command));
                break;
            case FINISHED:
                System.out.println("\n===== FINISHED =====");
                printMedia(mediaLibrary.filterByStatus(command));
                break;
            case DNF:
                System.out.println("\n===== DID NOT FINISH =====");
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

    // EFFECTS: displays the books menu and processes the user's command
    public void runBooks() {
        System.out.println("\n===== BOOKS =====");
        System.out.println("1: View all Books");
        System.out.println("2: Filter Books by Status");
        String command = scanner.nextLine();
        processTypeMenuCommands(command, "Book");
    }

    // EFFECTS: displays the movies menu and processes the user's command
    public void runMovies() {
        System.out.println("\n===== MOVIES =====");
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
                System.out.println();
                printMedia(mediaLibrary.filterByType(type));
                break;
            case "2":
                System.out.println("\n----- Select Status -----");
                Status status = returnStatusChoice();
                System.out.println();
                printMedia(mediaLibrary.filterByTypeAndStatus(type, status));
                break;
            default:
                System.out.println("Invalid. Please select one of the following: 1 2");
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

    // MODIFIES: this, media
    // EFFECTS: displays update media menu and lets user select a media item to update
    public void runUpdateMediaMenu() {
        System.out.println("\n===== UPDATE MEDIA =====");
        List<Media> lib = mediaLibrary.getAllMedia();
        if (lib.isEmpty()) {
            printMedia(lib);
            return;
        } else {
            printMedia(lib);
            System.out.println("Enter number of media to update");
            int choice = Integer.parseInt(scanner.nextLine());

            while (choice < 1 || choice > lib.size()) {
                System.out.println("Invalid choice. Try again.");
                choice = Integer.parseInt(scanner.nextLine());
            }
            Media selected = lib.get(choice - 1);

            System.out.println("\nWhat do you want to update?");
            System.out.println("1: Status");
            System.out.println("2: Rating");
            System.out.println("3: Review");
            System.out.println("4: Delete Entry");
            String command = scanner.nextLine(); // can the scanner read an int?
            processUpdateCommand(command, selected);
        }
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
        System.out.println("Status updated successfully!");
        System.out.println(media);
    }

    // EFFECTS: prompts user for an integer rating 1-5 until valid input and returns
    // that rating
    public int returnRating() {
        int rating = 0;
        boolean validRating = false;

        while (!validRating) {
            if (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Invalid input. Please enter an integer between 1-5: ");
            } else {
                int temp = Integer.parseInt(this.scanner.nextLine());
                if (temp < 1 || temp > 5) {
                    System.out.println("Invalid rating. Please enter an integer between 1-5: ");
                } else {
                    rating = temp;
                    validRating = true;
                }
            }
        }
        return rating;
    }

    // REQUIRES: user ends review by entering a line equal to "end"
    // EFFECTS: reads lines from user until "end", returns concatenated string
    public String returnReview() {
        String review = "";
        String line = scanner.nextLine();

        while (!line.equals("end")) {
            review += line + "\n";
            line = scanner.nextLine();
        }
        return review;
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
        System.out.println("Rating updated successfully!");
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
        System.out.println("Review updated successfully!");
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
        printFavs();
    }

    // EFFECTS: prints completion rate and counts of finished items by type
    public void printCompletionInfo() {
        double percent = (double) mediaLibrary.getNumFinished() / mediaLibrary.getTotalNumberItems() * 100;
        System.out.println("\nWhat is your completion rate?");
        System.out.println("Percentage of items finished: " + percent + "%");
        System.out.println(
                "Number of books finished: " + mediaLibrary.filterByTypeAndStatus("Book", Status.FINISHED).size());
        System.out.println(
                "Number of movies finished: " + mediaLibrary.filterByTypeAndStatus("Movie", Status.FINISHED).size());
        System.out.println("Number of tv shows finished: "
                + mediaLibrary.filterByTypeAndStatus("TV Show", Status.FINISHED).size());
    }

    // EFFECTS: prints average ratings by type
    public void printAvgRatings() {
        System.out.println("\nYour Average Ratings");
        double bookAvg = mediaLibrary.getAverageRating(mediaLibrary.filterByType("Book"));
        double movieAvg = mediaLibrary.getAverageRating(mediaLibrary.filterByType("Movie"));
        double tvAvg = mediaLibrary.getAverageRating(mediaLibrary.filterByType("TV Show"));
        System.out.println("By Books: " + bookAvg);
        System.out.println("By Movies: " + movieAvg);
        System.out.println("By TV Shows: " + tvAvg);
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

    // EFFECTS: prints 5-star items for Books, Movies, and TV Shows
    public void printFavs() {
        List<Media> favBooks = getFavs("Book");
        List<Media> favMovies = getFavs("Movie");
        List<Media> favShows = getFavs("TV Show");

        System.out.println("\nYour Overall Favourites");
        System.out.println("Everything you thought deserved 5 stars...");
        printFavs("Book", favBooks);
        printFavs("Movie", favMovies);
        printFavs("TV Show", favShows);
    }

}