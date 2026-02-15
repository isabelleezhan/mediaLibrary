package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class MediaLibraryTest {

    private MediaLibrary testLibrary;
    private Book item1;
    private Movie item2;
    private TVShow item3;
    private Book item4; 

    @BeforeEach
    void setUp() {
        testLibrary = new MediaLibrary();
        item1 = new Book("The Poppy War", Status.WANT_TO, "R.F. Kuang", "Fantasy");
        item2 = new Movie("Avatar 3", Status.WANT_TO, "James Cameron", "Sci-Fi");
        item3 = new TVShow("PONIES", Status.FINISHED, 1, "Comedy");
        item4 = new Book("The Poppy War", Status.IN_PROGRESS, "R.F. Kuang", "Fantasy");
    }

    @Test
    void testConstructor() {
        assertTrue(testLibrary.getAllMedia().isEmpty());
        assertEquals(testLibrary.getTotalNumberItems(), 0);
    }

    @Test
    void testAddEntries() {
        testLibrary.addEntry(item1);
        assertEquals(testLibrary.getTotalNumberItems(), 1);
        assertEquals(testLibrary.getAllMedia().get(0), item1);

        testLibrary.addEntry(item2);
        assertEquals(testLibrary.getTotalNumberItems(), 2);
        assertEquals(testLibrary.getAllMedia().get(1), item2);
    }

    @Test
    void testDeleteEntry() {
        testLibrary.addEntry(item1);
        testLibrary.addEntry(item2);
        testLibrary.addEntry(item3);
        testLibrary.addEntry(item4);

        testLibrary.deleteEntry(item2);
        assertEquals(testLibrary.getTotalNumberItems(), 3);
        assertFalse(testLibrary.getAllMedia().contains(item2));

        // test when media item is not in medialibrary 
        testLibrary.deleteEntry(item2);
        assertEquals(testLibrary.getTotalNumberItems(), 3);
    }

    @Test
    void testFilterByType() {
        testLibrary.addEntry(item1);
        testLibrary.addEntry(item3);
        testLibrary.addEntry(item4);

        List<Media> books = testLibrary.filterByType("Book");
        assertEquals(books.size(), 2);
        assertEquals(books.get(0), item1);
        assertEquals(books.get(1), item4);

        List<Media> movies = testLibrary.filterByType("Movie");
        assertTrue(movies.isEmpty());
    }

    @Test
    void testFilterbyStatus() {
        testLibrary.addEntry(item1);
        testLibrary.addEntry(item2);
        testLibrary.addEntry(item3);
        testLibrary.addEntry(item4);

        List<Media> tbr = testLibrary.filterByStatus(Status.WANT_TO);
        assertEquals(tbr.size(), 2);
        assertEquals(tbr.get(0), item1);
        assertEquals(tbr.get(1), item2);

        List<Media> current = testLibrary.filterByStatus(Status.DNF);
        assertTrue(current.isEmpty());
    }

    @Test
    void testFilterByTypeAndStatus() {
        testLibrary.addEntry(item1);
        testLibrary.addEntry(item2);
        testLibrary.addEntry(item3);
        testLibrary.addEntry(item4);

        List<Media> bookTBR = testLibrary.filterByTypeAndStatus("Book", Status.WANT_TO);
        assertEquals(bookTBR.size(), 1);
        assertEquals(bookTBR.get(0), item1);

        List<Media> currentTV = testLibrary.filterByTypeAndStatus("TV Show", Status.IN_PROGRESS);
        assertTrue(currentTV.isEmpty());
    }

    @Test
    void testGetAverageRating() {
        item4.setStatus(Status.FINISHED); 
        item3.setRating(3);
        item4.setRating(5); 
        testLibrary.addEntry(item1);
        testLibrary.addEntry(item2);
        testLibrary.addEntry(item3);
        testLibrary.addEntry(item4);
        assertEquals(testLibrary.getNumFinished(), 2);

        List<Media> finished = testLibrary.filterByStatus(Status.FINISHED);
        assertEquals(testLibrary.getAverageRating(finished), 4.0);

        List<Media> books = testLibrary.filterByTypeAndStatus("Book", Status.FINISHED);
        assertEquals(testLibrary.getAverageRating(books), 5.0);

        List<Media> movies = testLibrary.filterByType("Movie");
        assertEquals(testLibrary.getAverageRating(movies), 0.0);
    }

    @Test
    void testGetAverageRatingEmpty() {
        assertEquals(testLibrary.getNumFinished(), 0);

        List<Media> finished = testLibrary.filterByStatus(Status.FINISHED);
        assertEquals(testLibrary.getAverageRating(finished), 0.0);
    }

}
