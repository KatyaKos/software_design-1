package ru.spbau.sd.cli.interpreter.commands;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.OutputStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class CmdGrepTest {
    private static TemporaryFolder testFolder;
    private static Path filepath;
    private static final String filename = "file";
    private static final String fileContent = "foo bar\nbars\nbuz";
    private Command cmd = new CmdGrep();

    @BeforeClass
    public static void writeFile() throws IOException {
        testFolder = new TemporaryFolder();
        testFolder.create();
        Path testFolderPath =  testFolder.getRoot().toPath();
        filepath = testFolderPath.resolve(filename);
        Files.write(filepath, fileContent.getBytes());
    }

    @Test
    public void testBasic() {
        OutputStream oStreamMock = Mockito.mock(OutputStream.class);
        cmd.run(Arrays.asList("bar", filepath.toString()), null, oStreamMock);
        Mockito.verify(oStreamMock).write("foo bar\n");
        Mockito.verify(oStreamMock).write("bars\n");
    }

    @Test
    public void testWord() {
        OutputStream oStreamMock = Mockito.mock(OutputStream.class);
        cmd.run(Arrays.asList("bar", filepath.toString(), "-w"), null, oStreamMock);
        Mockito.verify(oStreamMock).write("foo bar\n");
    }

    @Test
    public void testIgnoreCase() {
        OutputStream oStreamMock = Mockito.mock(OutputStream.class);
        cmd.run(Arrays.asList("Fo", filepath.toString(), "-i"), null, oStreamMock);
        Mockito.verify(oStreamMock).write("foo bar\n");
    }

    @Test
    public void testA() {
        OutputStream oStreamMock = Mockito.mock(OutputStream.class);
        cmd.run(Arrays.asList("foo", filepath.toString(), "-A", "1"), null, oStreamMock);
        Mockito.verify(oStreamMock).write("foo bar\n");
        Mockito.verify(oStreamMock).write("bars\n");
    }

    @Test
    public void testStream() {
        InputStream iStreamMock = Mockito.mock(InputStream.class);
        Mockito.when(iStreamMock.read()).thenReturn(fileContent);
        OutputStream oStreamMock = Mockito.mock(OutputStream.class);
        cmd.run(Arrays.asList("foo"), iStreamMock, oStreamMock);
        Mockito.verify(oStreamMock).write("foo bar\n");
    }

    @AfterClass
    public static void deleteFile() {
        testFolder.delete();
    }
}
