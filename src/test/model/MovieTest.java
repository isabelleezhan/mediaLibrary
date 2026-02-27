package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class MovieTest {

    private Movie testMovie;

    @BeforeEach
    void setUp() {
        testMovie = new Movie("Avatar 3", Status.DNF, "James Cameron", "Sci-Fi");
    }

    @Test
    void testConstructor() {
        assertEquals(testMovie.getTitle(), "Avatar 3");
        assertEquals(testMovie.getStatus(), Status.DNF);
        assertEquals(testMovie.getDirector(), "James Cameron");
        assertEquals(testMovie.getGenre(), "Sci-Fi");
        assertNull(testMovie.getReview());
        assertEquals(testMovie.getRating(), 0);
    }

    @Test
    void testSetRatingValid() {
        testMovie.setRating(1);
        assertEquals(testMovie.getRating(), 1);

        testMovie.setRating(5);
        assertEquals(testMovie.getRating(), 5);
    }

    @Test
    void testSetReview() {
        testMovie.setReview("Engaging, but way too long");
        assertEquals(testMovie.getReview(), "Engaging, but way too long");
    }

    @Test
    void testGetDisplayInfo() {
        assertEquals(testMovie.getDisplayInfo(), "Director: James Cameron");
    }

    @Test
    void testToStringUnfinished() {
        assertTrue(testMovie.toString().contains("Movie: Avatar 3"));
        assertTrue(testMovie.toString().contains("Genre: Sci-Fi"));
        assertTrue(testMovie.toString().contains("Director: James Cameron"));
        assertTrue(testMovie.toString().contains("Status - Did Not Finish"));
    }

    @Test
    void testToStringFinished() {
        testMovie.setStatus(Status.FINISHED); 
        assertEquals(testMovie.getStatus(), Status.FINISHED);
        testMovie.setRating(3);
        testMovie.setReview("Engaging, but way too long");

        assertTrue(testMovie.toString().contains("Movie: Avatar 3"));
        assertTrue(testMovie.toString().contains("Genre: Sci-Fi"));
        assertTrue(testMovie.toString().contains("Director: James Cameron"));
        assertTrue(testMovie.toString().contains("Status - Finished"));
        assertTrue(testMovie.toString().contains("Rating - 3 stars"));
        assertTrue(testMovie.toString().contains("Review: Engaging, but way too long"));
    }
}
