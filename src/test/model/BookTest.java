package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class BookTest {

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book("The Poppy War", Status.WANT_TO, "R.F. Kuang", "Fantasy");
    }

    @Test
    void testConstructor() {
        assertEquals(testBook.getTitle(), "The Poppy War");
        assertEquals(testBook.getStatus(), Status.WANT_TO);
        assertEquals(testBook.getAuthor(), "R.F. Kuang");
        assertEquals(testBook.getGenre(), "Fantasy");
        assertNull(testBook.getReview());
        assertEquals(testBook.getRating(), 0);
    }

    @Test
    void testSetRatingInvalid() {
        testBook.setRating(6);
        assertEquals(testBook.getRating(), 0);

        testBook.setRating(0);
        assertEquals(testBook.getRating(), 0);
    }

    @Test
    void testSetRatingValid() {
        testBook.setRating(1);
        assertEquals(testBook.getRating(), 1);

        testBook.setRating(3);
        assertEquals(testBook.getRating(), 3);

        testBook.setRating(5);
        assertEquals(testBook.getRating(), 5);
    }

    @Test
    void testSetReview() {
        testBook.setReview("This book was so good!");
        assertEquals(testBook.getReview(), "This book was so good!");
    }

    @Test
    void testGetDisplayInfo() {
        assertEquals(testBook.getDisplayInfo(), "Author: R.F. Kuang");
    }

    @Test
    void testToStringUnfinished() {
        assertTrue(testBook.toString().contains("Book: The Poppy War"));
        assertTrue(testBook.toString().contains("Genre: Fantasy"));
        assertTrue(testBook.toString().contains("Author: R.F. Kuang"));
        assertTrue(testBook.toString().contains("Status - Want to Read/Watch"));
    }

    @Test
    void testToStringFinished() {
        testBook.setStatus(Status.FINISHED); 
        assertEquals(testBook.getStatus(), Status.FINISHED);
        testBook.setRating(5);
        testBook.setReview("This book was so good!");

        assertTrue(testBook.toString().contains("Book: The Poppy War"));
        assertTrue(testBook.toString().contains("Genre: Fantasy"));
        assertTrue(testBook.toString().contains("Author: R.F. Kuang"));
        assertTrue(testBook.toString().contains("Status - Finished"));
        assertTrue(testBook.toString().contains("Rating - 5 stars"));
        assertTrue(testBook.toString().contains("Review: This book was so good!"));
    }

    @Test
    void testToStringFinishedNoReview() {
        testBook.setStatus(Status.FINISHED); 

        assertTrue(testBook.toString().contains("Book: The Poppy War"));
        assertTrue(testBook.toString().contains("Genre: Fantasy"));
        assertTrue(testBook.toString().contains("Author: R.F. Kuang"));
        assertTrue(testBook.toString().contains("Status - Finished"));
        assertTrue(testBook.toString().contains("Rating - 0 stars"));
    }

    @Test
    void testToStringFinishedEmptyReview() {
        testBook.setStatus(Status.FINISHED);
        testBook.setReview("");
        assertTrue(testBook.toString().contains("Rating - 0 stars"));
        assertFalse(testBook.toString().contains("Review:"));
    }
}
