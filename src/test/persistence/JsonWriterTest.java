package persistence;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import model.*;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

@ExcludeFromJacocoGeneratedReport
public class JsonWriterTest extends JsonTest {
    
    private JsonWriter writer;
    private JsonReader reader;
    private MediaLibrary ml;
    private Media hamnet;
    private Media got;
    private Media babel;

    @BeforeEach
    void setUp() {
        hamnet = new Movie("Hamnet", Status.FINISHED, "Chloe Zhao", "Drama");
        hamnet.setRating(4);
        hamnet.setCoverImagePath("/data/dune");
        hamnet.setReview("It was good!");
        got = new TVShow("Game of Thrones", Status.FINISHED, 8, "Fantasy");
        got.setRating(5);
        got.setReview("If only they stopped at season 4...");
        babel = new Book("Babel", Status.IN_PROGRESS, "R.F. Kuang", "Historical Fantasy");
    }

    @Test
    void testWriterIllegal() {
        try {
            writer = new JsonWriter("./data/\0illegal:file.json");
            writer.open();
            fail("Should've thrown an IOException");
        } catch (IOException e) {
        }
    }

    @Test
    void testWriterEmpty() {
        try {
            ml = new MediaLibrary();
            writer = new JsonWriter("./data/writeEmpty.json");
            assertEquals("./data/writeEmpty.json", writer.getFilePath());
            writer.open();
            writer.write(ml);
            writer.close();

            reader = new JsonReader("./data/writeEmpty.json");
            ml = reader.read();
            assertEquals(0, ml.getAllMedia().size());
        } catch (IOException e) {
            fail("Shouldn't throw an exception");
        }
    }

    @Test
    void testWriterGeneralLibrary() {
        try {
            ml = new MediaLibrary();
            ml.addEntry(hamnet);
            ml.addEntry(got);
            ml.addEntry(babel);
            
            writer = new JsonWriter("./data/writeGeneralLibrary.json");
            writer.open();
            writer.write(ml);
            writer.close();

            reader = new JsonReader("./data/writeGeneralLibrary.json");
            ml = reader.read();
            List<Media> lib = ml.getAllMedia();
            assertEquals(3, lib.size());
            checkMovie((Movie) lib.get(0), "Hamnet", "Drama", Status.FINISHED, "Chloe Zhao");
            checkRatingAndReview(lib.get(0), "It was good!", 4);
            checkCoverImagePath(lib.get(0), "/data/dune");
            checkTVShow((TVShow) lib.get(1), "Game of Thrones", "Fantasy", Status.FINISHED, 8);
            checkRatingAndReview(lib.get(1), "If only they stopped at season 4...", 5);
            checkBook((Book) lib.get(2), "Babel", "Historical Fantasy", Status.IN_PROGRESS, "R.F. Kuang");
        } catch (IOException e) {
            fail("Shouldn't throw exception");
        }
    }
}

