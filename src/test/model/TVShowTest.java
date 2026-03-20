package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class TVShowTest {

    private TVShow testTV;

    @BeforeEach
    void setUp() {
        testTV = new TVShow("The Vampire Diaries", Status.FINISHED, 8, "Drama");
    }

    @Test
    void testConstructor() {
        assertEquals(testTV.getTitle(), "The Vampire Diaries");
        assertEquals(testTV.getStatus(), Status.FINISHED);
        assertEquals(testTV.getNumSeasons(), 8);
        assertEquals(testTV.getGenre(), "Drama");
        assertNull(testTV.getReview());
        assertEquals(testTV.getRating(), 0);
    }

    @Test
    void testGetDisplayInfo() {
        assertEquals(testTV.getDisplayInfo(), "Number of Seasons: 8");
    }

    @Test
    void testSetNumSeasons() {
        testTV.setNumSeasons(3);

        assertEquals(3, testTV.getNumSeasons());
    }

    @Test
    void testToStringFinished() {
        testTV.setRating(5);
        testTV.setReview("An all time favourite!");

        assertTrue(testTV.toString().contains("TV Show: The Vampire Diaries"));
        assertTrue(testTV.toString().contains("Genre: Drama"));
        assertTrue(testTV.toString().contains("Number of Seasons: 8"));
        assertTrue(testTV.toString().contains("Status - Finished"));
        assertTrue(testTV.toString().contains("Rating - 5 stars"));
        assertTrue(testTV.toString().contains("Review: An all time favourite!"));
    }
}
