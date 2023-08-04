package start.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class WriteReadDataTest {

    private File file1;
    private File file2;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        file1 = folder.newFile("testfile1.ser");
        file2 = folder.newFile("testfile2.ser");
    }

    @Test
    public void test2TempFiles() throws IOException {
        writeContentToFile(file1, "ContentForUnitTest");
        writeContentToFile(file2, "ContentForUnitTest");

        assertTrue(file1.exists());
        assertTrue(file2.isFile());

        assertEquals(18L, file1.length());
        assertEquals(file1.length(), file2.length());

        assertTrue(file1.getAbsolutePath().endsWith("testfile1.ser"));
    }

    private void writeContentToFile(File file, String content) throws IOException {
        try (FileWriter fw = new FileWriter(file);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(content);
        }
    }
}