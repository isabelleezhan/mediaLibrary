package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class JsonReaderTest extends JsonTest {

    private JsonReader reader;

    @Test
    void testConstructor() {
        reader = new JsonReader("./data/mediaLibrary.json");
        assertEquals("./data/mediaLibrary.json", reader.getSource());
    }
    
    @Test
    void testReaderNoFile() {
        reader = new JsonReader("./data/NA.json");
        try {
            reader.read();
            fail("Should've thrown IOException");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmpty() {
        reader = new JsonReader("./data/empty.json");
        try {
            MediaLibrary ml = reader.read();
            assertEquals(0, ml.getAllMedia().size());
        } catch (IOException e) {
            fail("Shouldn't throw an exception");
        }
    }

    @Test
    void testReaderGeneralLibrary() {
        reader = new JsonReader("./data/generalLibrary.json");
        try {
            MediaLibrary ml = reader.read();
            List<Media> lib = ml.getAllMedia();
            assertEquals(3, lib.size());
            checkMovie((Movie) lib.get(0), "Hamnet", "Drama", Status.FINISHED, "Chloe Zhao");
            checkRatingAndReview(lib.get(0), "It was good!", 4);
            checkTVShow((TVShow) lib.get(1), "Game of Thrones", "Fantasy", Status.FINISHED, 8);
            checkRatingAndReview(lib.get(1), "If only they stopped at season 4...", 5);
            checkBook((Book) lib.get(2), "Babel", "Historical Fantasy", Status.IN_PROGRESS, "R.F. Kuang");
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
