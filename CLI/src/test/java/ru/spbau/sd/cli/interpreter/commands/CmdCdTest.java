package ru.spbau.sd.cli.interpreter.commands;

import org.junit.Test;
import ru.spbau.sd.cli.interpreter.*;
import ru.spbau.sd.cli.interpreter.io.SimpleStream;

import java.io.File;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class CmdCdTest {

    @Test
    public void simpleTest(){
        Environment environment = new SimpleEnvironment();
        File testDir = new File("src");
        Command cdCommand = new CmdCd(environment);
        SimpleStream outputStream = new SimpleStream();
        cdCommand.run(Collections.singletonList("src"), null, outputStream);
        assertEquals(testDir.getAbsolutePath() + "\n", outputStream.read());
    }

    @Test
    public void homeTest(){
        Environment environment = new SimpleEnvironment();
        Command cdCommand = new CmdCd(environment);
        SimpleStream outputStream = new SimpleStream();
        cdCommand.run(Collections.emptyList(), null, outputStream);
        assertEquals(System.getProperty("user.home") + "\n", outputStream.read());
    }

}
