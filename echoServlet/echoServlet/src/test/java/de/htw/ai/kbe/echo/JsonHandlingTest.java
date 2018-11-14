package de.htw.ai.kbe.echo;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class JsonHandlingTest {
    private static String FILENAME_TO_BE_READ = "testReadSongs.json";
    private static String FILENAME_TO_BE_GENERATED = "testWriteSongs.json";


    private String filePathToBeRead;
    private String filePathForConvertedData;
    private Converter converter;

    @Before
    public void setUp() {
        filePathToBeRead = getClass().getResource(FILENAME_TO_BE_READ).getFile();
        filePathForConvertedData = getClass().getResource(FILENAME_TO_BE_READ).getFile().replace(FILENAME_TO_BE_READ, FILENAME_TO_BE_GENERATED);
        converter = ...
    }

    /**
     * Tests if song.json was converted successfully into an object
     */
    public void testsIfFileWasSuccessfullyConverted() {
        try(... placeholder = new ...(new ...(filePathToBeRead))) {
            Song testSong = handler.readSongFromStream(placeholder);


            assertSong(testSong, 1, "ILLENIUM", "Unknown", "God Damnit(with Call Me Karzima)", 2018);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Tests if the generation of a test xml was successful
     */
    @Test
    public void testWriteJsonFileSuccessful() {

        // check if written json file is empty
        try(... placeholder = new ...(new ...(filePathForConvertedData))){
            if (placeholder.readLine() == null) {
                fail("JSON file is empty");
            }
        } catch (Exception e) {
        }
    }
}