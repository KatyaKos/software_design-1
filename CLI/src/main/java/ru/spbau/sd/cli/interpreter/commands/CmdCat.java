package ru.spbau.sd.cli.interpreter.commands;

import ru.spbau.sd.cli.interpreter.Environment;
import ru.spbau.sd.cli.interpreter.ExecutionResult;
import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.OutputStream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * If any arguments are passed, writes content of files with that names to the
 * output stream. Otherwise copies input to output stream.
 */
public class CmdCat implements Command {
    private final Environment environment;

    public CmdCat(Environment environment) {
        this.environment = environment;
    }

    @Override
    public ExecutionResult run(List<String> arguments, InputStream inputStream,
                               OutputStream outputStream) {
        if (arguments.isEmpty()) {
            outputStream.write(inputStream.read());
            return ExecutionResult.OK;
        }
        for (String filename: arguments) {
            if (!filename.contains(environment.get("PWD"))) filename = environment.get("PWD") + File.separator + filename;
            Path filepath = Paths.get(filename);
            try {
                Files.lines(filepath)
                        .map(s -> s + '\n')
                        .forEach(outputStream::write);
            } catch (IOException e) {
                outputStream.write(e.getMessage());
                return ExecutionResult.Error;
            }
        }
        return ExecutionResult.OK;
    }
}
