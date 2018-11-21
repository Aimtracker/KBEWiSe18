package de.htw.ai.kbe.servlet.marshalling;

import static de.htw.ai.kbe.servlet.utils.TestUtils.assertSong;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.htw.ai.kbe.servlet.marshalling.impl.MarshallerFactory;
import de.htw.ai.kbe.servlet.pojo.Song;
import de.htw.ai.kbe.servlet.utils.Constants;
import de.htw.ai.kbe.servlet.utils.TestUtils;

public class JsonHandlerTest {

    private static String READ_JSON_FILE = "testReadSongs.json";
    private static String READ_JSON_FILE_WITH_SINGLE_SONG = "testReadSingleSong.json";
    private static String WRITE_JSON_INTO_FILE = "testWriteSongs.json";

    private String readingInThisFilePath;
    private String writingInThisFilePath;
    private String singleReadingInThisFilePath;
    private IMarshaller marsh;

    @Before
    public void setUp() {
        readingInThisFilePath = getClass().getResource(READ_JSON_FILE).getFile();
        singleReadingInThisFilePath = getClass().getResource(READ_JSON_FILE_WITH_SINGLE_SONG).getFile();
        writingInThisFilePath = getClass().getResource(READ_JSON_FILE).getFile().replace(READ_JSON_FILE, WRITE_JSON_INTO_FILE);
        marsh = MarshallerFactory.getInstance().getMarshaller(Constants.CONTENTTYPE_JSON);
    }

    @Test
    public void testsIfJsonFileWasReadSuccessfully() {
        try(InputStream is = new BufferedInputStream(new FileInputStream(readingInThisFilePath))) {
            List<Song> testSong = marsh.readSongsFromStream(is);

            assertEquals(2, testSong.size());

            assertSong(testSong.get(0), 6, "Pantera", "Cowboys From Hell", "Domination", 1990);
            assertSong(testSong.get(1), 1, "Ufomammut", "Idolum", "Stigma", 2008);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            fail("Exception during method readSongsFromStream");
        }
    }
    
    @Test
    public void testsIfJsonFileWithSingleSongWasReadSuccessfully() {
        try(InputStream is = new BufferedInputStream(new FileInputStream(singleReadingInThisFilePath))) {
            Song testSong = marsh.readSongFromStream(is);


            assertSong(testSong, 1, "Sylosis", "Conclusion Of An Age", "After Lifeless Years", 2008);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            fail("Exception during method readSongFromStream");
        }
    }

    @Test
    public void testsIfJsonFileWasWrittenSuccessful() {
        List<Song> songList = TestUtils.getTestSongs();

        try(OutputStream os = new BufferedOutputStream(new FileOutputStream(writingInThisFilePath))) {
            marsh.writeSongsToStream(songList, os);
        } catch (Exception e) {
            fail("Exception during method writeSongsToStream");
        }

        try(BufferedReader br = new BufferedReader(new FileReader(writingInThisFilePath))){
            if (br.readLine() == null) {
                fail("JSON file is empty");
            }
        } catch (Exception e) {
            fail("Exception during test if written json file is empty");
        }
    }
}