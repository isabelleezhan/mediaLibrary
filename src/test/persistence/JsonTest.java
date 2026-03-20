package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class JsonTest {

    protected void checkMedia(Media media, String title, String genre, Status status) {
        assertEquals(title, media.getTitle());
        assertEquals(genre, media.getGenre());
        assertEquals(status, media.getStatus());
    }

    protected void checkRatingAndReview(Media media, String review, int rating) {
        assertEquals(review, media.getReview());
        assertEquals(rating, media.getRating());
    }

    protected void checkCoverImagePath(Media media, String imagePath) {
        assertEquals(imagePath, media.getCoverImagePath());
    }

    protected void checkBook(Book book, String title, String genre, Status status, String author) {
        checkMedia(book, title, genre, status);
        assertEquals(author, book.getAuthor());
    }

    protected void checkMovie(Movie movie, String title, String genre, Status status, String director) {
        checkMedia(movie, title, genre, status);
        assertEquals(director, movie.getDirector());
    }

    protected void checkTVShow(TVShow show, String title, String genre, Status status, int numSeasons) {
        checkMedia(show, title, genre, status);
        assertEquals(numSeasons, show.getNumSeasons());
    }
}
