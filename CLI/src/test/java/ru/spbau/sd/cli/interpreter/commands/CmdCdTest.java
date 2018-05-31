package ru.spbau.sd.cli.interpreter.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.spbau.sd.cli.interpreter.*;
import ru.spbau.sd.cli.interpreter.io.OutputStream;
import ru.spbau.sd.cli.interpreter.io.SimpleStream;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class CmdCdTest {

    @Test
    public void test(){
        Environment environment = new SimpleEnvironment();
        File test_dir = new File("src");
        Command cdCommand = new CmdCd(environment);
        SimpleStream outputStream = new SimpleStream();
        String res = test_dir.getAbsolutePath() + "\n";
        cdCommand.run(Collections.singletonList("src"), null, outputStream);
        assertEquals(res, outputStream.read());
        cdCommand.run(Collections.emptyList(),null, outputStream);
        res += System.getProperty("user.home") + "\n";
        assertEquals(res, outputStream.read());
    }
}
