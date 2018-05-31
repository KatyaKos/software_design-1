package ru.spbau.sd.cli.interpreter.commands;

import org.junit.Test;
import ru.spbau.sd.cli.interpreter.Environment;
import ru.spbau.sd.cli.interpreter.SimpleEnvironment;
import ru.spbau.sd.cli.interpreter.io.SimpleStream;

import java.io.File;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class CmdLsTest {
    @Test
    public void test(){
        Environment environment = new SimpleEnvironment();
        Command lsCommand = new CmdLs(environment);
        SimpleStream outputStream = new SimpleStream();
        String res = "interpreter\nui\n";
        lsCommand.run(Collections.singletonList("src/main/java/ru/spbau/sd/cli"), null, outputStream);
        assertEquals(res, outputStream.read());
        lsCommand.run(Collections.emptyList(),null, outputStream);
        assertEquals(true, !outputStream.read().substring(0, res.length()).isEmpty());
    }
}
